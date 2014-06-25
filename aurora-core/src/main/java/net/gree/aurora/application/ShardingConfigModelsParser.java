package net.gree.aurora.application;

import com.typesafe.config.Config;
import net.gree.aurora.application.config.AuroraShardingConfig;
import net.gree.aurora.application.config.AuroraShardingConfigLoader;
import net.gree.aurora.application.config.AuroraShardingConfigLoaderFactory;
import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfig;
import net.gree.aurora.application.config.clusterconfig.AuroraClusterJDBCConfig;
import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfig;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfig;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultJDBCConfig;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultRedisConfig;
import net.gree.aurora.domain.cluster.Cluster;
import net.gree.aurora.domain.cluster.ClusterFactory;
import net.gree.aurora.domain.cluster.ClusterId;
import net.gree.aurora.domain.cluster.ClusterIdFactory;
import net.gree.aurora.domain.clustergroup.ClusterGroup;
import net.gree.aurora.domain.clustergroup.ClusterGroupFactory;
import net.gree.aurora.domain.clustergroup.ClusterGroupId;
import net.gree.aurora.domain.clustergroup.ClusterGroupIdFactory;
import net.gree.aurora.domain.datasource.*;
import net.gree.aurora.domain.shardingconfig.*;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.sisioh.dddbase.utils.Function1;
import org.sisioh.dddbase.utils.Option;
import org.sisioh.dddbase.utils.Try;

import java.io.File;
import java.util.*;

/**
 * 設定ファイルをパースするためのクラス。
 */
final class ShardingConfigModelsParser {

    private AuroraShardingConfigLoader auroraConfigLoader = AuroraShardingConfigLoaderFactory.create();
    private ShardingConfigRepository shardingConfigRepository = ShardingConfigRepositoryFactory.create();
    private DataSourceRepository dataSourceRepository = DataSourceRepositoryFactory.create();

    /**
     * {@link Config}から{@link ParseResult}を返す。
     *
     * @param configFile {@link File}
     * @return {@link ParseResult}
     */
    Try<ParseResult> parse(File configFile) {
        return parse(com.typesafe.config.ConfigFactory.parseFile(configFile));
    }

    /**
     * {@link Config}から{@link ParseResult}を返す。
     *
     * @param config {@link Config}
     * @return {@link ParseResult}
     */
    Try<ParseResult> parse(Config config) {
        Validate.notNull(config);
        try {
            Config auroraConfigInTypesafeConfig = config.getConfig("aurora");
            Config shardingInTypesafeConfig = ConfigUtil.getConfig(auroraConfigInTypesafeConfig, Arrays.asList("shardingConfigs", "sharding-configs"));
            Set<AuroraShardingConfig> auroraShardingConfigs = auroraConfigLoader.load(shardingInTypesafeConfig);
            for (AuroraShardingConfig auroraShardingConfig : auroraShardingConfigs) {
                ShardingConfig shardingConfig = createShardingConfig(auroraShardingConfig);
                shardingConfigRepository.store(shardingConfig);
            }
            return Try.ofSuccess(new ParseResult(shardingConfigRepository, dataSourceRepository));
        } catch (RuntimeException e) {
            return Try.ofFailure(e);
        }
    }

    private ShardingConfig createShardingConfig(AuroraShardingConfig auroraConfig) {
        ShardingConfigId configId = ShardingConfigIdFactory.create(auroraConfig.getId());
        AuroraDefaultConfig defaultConfig = auroraConfig.getDefaultConfig();
        ShardingConfigType configType = ShardingConfigType.resolveByCode(auroraConfig.getType().getCode());
        Set<AuroraClusterGroupConfig> shardGroupsAsSet = auroraConfig.getClusterGroupsAsSet();
        Set<ClusterGroup> shardGroups = new HashSet<>();
        for (AuroraClusterGroupConfig auroraClusterGroupConfig : shardGroupsAsSet) {
            ClusterGroup clusterGroup = createClusterGroup(auroraConfig, defaultConfig, auroraClusterGroupConfig);
            shardGroups.add(clusterGroup);
        }
        return ShardingConfigFactory.create(configId, configType, shardGroups);
    }

    private ClusterGroup createClusterGroup(
            AuroraShardingConfig auroraConfig,
            AuroraDefaultConfig defaultConfig,
            AuroraClusterGroupConfig auroraClusterGroupConfig
    ) {
        ClusterGroupId clusterGroupId = ClusterGroupIdFactory.create(auroraClusterGroupConfig.getId());
        List<Cluster> clusters = new ArrayList<>();
        for (AuroraClusterConfig auroraCluster : auroraClusterGroupConfig.getClusterConfigsAsList()) {
            Cluster cluster = createCluster(auroraConfig, defaultConfig, auroraCluster);
            clusters.add(cluster);
        }
        return ClusterGroupFactory.create(clusterGroupId, clusters);
    }

    private Cluster createCluster(
            AuroraShardingConfig auroraConfig,
            AuroraDefaultConfig defaultConfig,
            AuroraClusterConfig auroraCluster
    ) {
        ClusterId clusterId = ClusterIdFactory.create(auroraCluster.getId());
        List<String> masterHostAndPort = parseHostAndPort(auroraCluster.getMasterHostAndPort(), defaultConfig.getPort());
        Option<Integer> masterPort = null;
        if (masterHostAndPort.size() > 1) {
            masterPort = Option.ofSome(Integer.valueOf(masterHostAndPort.get(1)));
        } else {
            masterPort = Option.ofNone();
        }
        DataSource masterDataSource = createDataSource(
                DataSourceType.MASTER,
                auroraConfig,
                defaultConfig,
                auroraCluster,
                masterHostAndPort.get(0),
                masterPort
        );
        dataSourceRepository.store(masterDataSource);
        List<DataSourceId> slaveDataSourceIds = new ArrayList<>();
        for (String slaveHostAndPortString : auroraCluster.getSlaveHostAndPorts()) {
            List<String> slaveHostAndPort = parseHostAndPort(slaveHostAndPortString, defaultConfig.getPort());
            Option<Integer> slavePort = null;
            if (slaveHostAndPort.size() > 1) {
                slavePort = Option.ofSome(Integer.valueOf(slaveHostAndPort.get(1)));
            } else {
                slavePort = Option.ofNone();
            }
            DataSource slaveDataSource = createDataSource(
                    DataSourceType.SLAVE,
                    auroraConfig,
                    defaultConfig,
                    auroraCluster,
                    slaveHostAndPort.get(0),
                    slavePort
            );
            dataSourceRepository.store(slaveDataSource);
            slaveDataSourceIds.add(slaveDataSource.getIdentity());
        }
        return ClusterFactory.create(clusterId, masterDataSource.getIdentity(), slaveDataSourceIds);
    }

    private DataSource createDataSource(
            DataSourceType dataSourceType,
            AuroraShardingConfig auroraConfig,
            AuroraDefaultConfig defaultConfig,
            AuroraClusterConfig auroraCluster,
            String host,
            Option<Integer> port
    ) {
        DataSourceId dataSourceId = DataSourceIdFactory.create(UUID.randomUUID());
        DataSource dataSource = null;
        switch (auroraConfig.getType()) {
            case JDBC_TYPE: {
                AuroraDefaultJDBCConfig jdbcDefaultConfig = (AuroraDefaultJDBCConfig) defaultConfig;
                AuroraClusterJDBCConfig jdbcCluster = (AuroraClusterJDBCConfig) auroraCluster;
                String driverClassName = jdbcDefaultConfig.getDriverClassName();
                String prefixUrl = jdbcDefaultConfig.getPrefixUrl();
                dataSource = DataSourceFactory.createAsJDBC(
                        dataSourceId,
                        dataSourceType,
                        driverClassName,
                        host,
                        port,
                        prefixUrl + host + (port.isDefined() ? ":" + port.map(new Function1<Integer, String>() {
                            @Override
                            public String apply(Integer value) {
                                return value.toString();
                            }
                        }).getOrElse("") : "") + "/" + jdbcCluster.getDatabaseName(),
                        dataSourceType == DataSourceType.MASTER ? jdbcDefaultConfig.getUserName() : jdbcDefaultConfig.getReadOnlyUserName(),
                        dataSourceType == DataSourceType.MASTER ? jdbcDefaultConfig.getPassword() : jdbcDefaultConfig.getReadOnlyPassword()
                );
            }
            break;
            case GENERIC_TYPE: {
                dataSource = DataSourceFactory.create(
                        dataSourceId,
                        dataSourceType,
                        host,
                        port
                );
            }
            break;
            case REDIS_TYPE: {
                AuroraDefaultRedisConfig jdbcDefaultConfig = (AuroraDefaultRedisConfig) defaultConfig;
                dataSource = DataSourceFactory.createAsRedis(
                        dataSourceId,
                        dataSourceType,
                        host,
                        port,
                        jdbcDefaultConfig.getDatabaseNumber(),
                        jdbcDefaultConfig.getPassword()
                );
            }
            break;
            default:
                throw new IllegalArgumentException();
        }
        return dataSource;
    }

    private List<String> parseHostAndPort(String hostAndPort, Integer defaultPort) {
        String[] splits = hostAndPort.split(":");
        if (splits.length == 1) {
            if (defaultPort != null) {
                return Arrays.asList(splits[0], defaultPort.toString());
            } else {
                return Arrays.asList(splits);
            }
        } else {
            return Arrays.asList(splits);
        }
    }

    /**
     * 設定ファイルをパースした結果を表すクラス。
     */
    static class ParseResult {

        final DataSourceRepository dataSourceRepository;
        final ShardingConfigRepository shardingConfigRepository;

        ParseResult(ShardingConfigRepository shardingConfigRepository, DataSourceRepository dataSourceRepository) {
            Validate.notNull(shardingConfigRepository);
            Validate.notNull(dataSourceRepository);
            this.shardingConfigRepository = shardingConfigRepository;
            this.dataSourceRepository = dataSourceRepository;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ParseResult that = (ParseResult) o;

            if (!dataSourceRepository.equals(that.dataSourceRepository)) return false;
            if (!shardingConfigRepository.equals(that.shardingConfigRepository)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            return 31 * shardingConfigRepository.hashCode() + dataSourceRepository.hashCode();
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).
                    append("shardingConfigRepository", shardingConfigRepository).
                    append("dataSourceRepository", dataSourceRepository).
                    toString();
        }
    }

}
