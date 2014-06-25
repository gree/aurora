package net.gree.aurora.application;

import net.gree.aurora.domain.cluster.*;
import net.gree.aurora.domain.clustergroup.ClusterGroup;
import net.gree.aurora.domain.clustergroup.ClusterGroupId;
import net.gree.aurora.domain.clustergroup.ClusterGroupIdFactory;
import net.gree.aurora.domain.datasource.DataSource;
import net.gree.aurora.domain.datasource.DataSourceId;
import net.gree.aurora.domain.datasource.DataSourceRepository;
import net.gree.aurora.domain.datasource.DataSourceRepositoryFactory;
import net.gree.aurora.domain.hint.Hint;
import net.gree.aurora.domain.shardingconfig.*;
import org.apache.commons.lang.Validate;
import org.sisioh.dddbase.lifecycle.sync.SyncResultWithEntity;
import org.sisioh.dddbase.utils.Function1;
import org.sisioh.dddbase.utils.Option;
import org.sisioh.dddbase.utils.Try;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

final class AuroraShardingServiceImpl<T> implements AuroraShardingService<T> {

    protected final Option<ClusterIdResolver<T>> clusterIdResolver;
    protected final DataSourceRepository dataSourceRepository;
    protected final ShardingConfigRepository shardingConfigRepository;
    protected final AuroraShardingConfigLoadService configLoadService = AuroraShardingConfigLoadServiceFactory.create();

    protected final TaggedClusterIdResolverRepository<T> taggedClusterIdResolverRepository = TaggedClusterIdResolverRepositoryFactory.create();

    AuroraShardingServiceImpl(Set<TaggedClusterIdResolver<T>> taggedClusterIdResolvers, File configFile) {
        this(taggedClusterIdResolvers, com.typesafe.config.ConfigFactory.parseFile(configFile));
    }

    AuroraShardingServiceImpl(Set<TaggedClusterIdResolver<T>> taggedClusterIdResolvers, com.typesafe.config.Config config) {
        this.clusterIdResolver = Option.ofNone();
        for (TaggedClusterIdResolver<T> taggedClusterIdResolver : taggedClusterIdResolvers) {
            taggedClusterIdResolverRepository.store(taggedClusterIdResolver);
        }
        Repositories repositories = configLoadService.loadFromConfig(config).get();
        shardingConfigRepository = repositories.getShardingConfigRepository();
        dataSourceRepository = repositories.getDataSourceRepository();
    }

    AuroraShardingServiceImpl(ClusterIdResolver<T> clusterIdResolver, File configFile) {
        this(clusterIdResolver, com.typesafe.config.ConfigFactory.parseFile(configFile));
    }

    AuroraShardingServiceImpl(ClusterIdResolver<T> clusterIdResolver, com.typesafe.config.Config config) {
        Validate.notNull(clusterIdResolver);
        Validate.notNull(config);
        this.clusterIdResolver = Option.ofSome(clusterIdResolver);
        Repositories repositories = configLoadService.loadFromConfig(config).get();
        shardingConfigRepository = repositories.getShardingConfigRepository();
        dataSourceRepository = repositories.getDataSourceRepository();
    }

    AuroraShardingServiceImpl(
            ClusterIdResolver<T> clusterIdResolver,
            ShardingConfigRepository shardingConfigRepository,
            DataSourceRepository dataSourceRepository
    ) {
        Validate.notNull(shardingConfigRepository);
        Validate.notNull(dataSourceRepository);
        Validate.notNull(clusterIdResolver);
        this.shardingConfigRepository = shardingConfigRepository;
        this.dataSourceRepository = dataSourceRepository;
        this.clusterIdResolver = Option.ofSome(clusterIdResolver);
    }

    @Override
    public Try<DataSourceRepository> getDataSourceRepository() {
        DataSourceRepository result = DataSourceRepositoryFactory.create();
        for (DataSource dataSource : dataSourceRepository.toList()) {
            Try<SyncResultWithEntity<DataSourceRepository, DataSourceId, DataSource>> store = result.store(dataSource);
            if (store.isFailure()) {
                return Try.ofFailure(store.getCause());
            }
        }
        return Try.ofSuccess(result);
    }

    @Override
    public Try<ShardingConfigRepository> getShardingConfigRepository() {
        ShardingConfigRepository result = ShardingConfigRepositoryFactory.create();
        for (ShardingConfig shardingConfig : shardingConfigRepository.toList()) {
            Try<SyncResultWithEntity<ShardingConfigRepository, ShardingConfigId, ShardingConfig>> store = result.store(shardingConfig);
            if (store.isFailure()) {
                return Try.ofFailure(store.getCause());
            }
        }
        return Try.ofSuccess(result);
    }

    @Override
    public Try<DataSource> resolveDataSourceById(DataSourceId id) {
        return dataSourceRepository.resolve(id);
    }

    @Override
    public Try<List<DataSource>> resolveDataSourcesByIds(List<DataSourceId> ids) {
        Validate.notNull(ids);
        List<DataSource> dataSources = new ArrayList<>();
        for (DataSourceId id : ids) {
            Try<DataSource> dataSourceTry = resolveDataSourceById(id);
            if (dataSourceTry.isFailure()) {
                return Try.ofFailure(dataSourceTry.getCause());
            } else {
                dataSources.add(dataSourceTry.get());
            }
        }
        return Try.ofSuccess(dataSources);
    }

    @Override
    public Try<Cluster> resolveCluster(ShardingConfigId shardingConfigId, final ClusterGroupId clusterGroupId) {
        Validate.notNull(shardingConfigId);
        Validate.notNull(clusterGroupId);
        return shardingConfigRepository.resolve(shardingConfigId).flatMap(new Function1<ShardingConfig, Try<Cluster>>() {
            @Override
            public Try<Cluster> apply(ShardingConfig shardingConfig) {
                return shardingConfig.resolveClusterGroup(clusterGroupId).flatMap(new Function1<ClusterGroup, Try<Cluster>>() {
                    @Override
                    public Try<Cluster> apply(ClusterGroup clusterGroup) {
                        return clusterGroup.getClusters().flatMap(new Function1<List<Cluster>, Try<Cluster>>() {
                            @Override
                            public Try<Cluster> apply(List<Cluster> values) {
                                if (values.size() == 1) {
                                    return Try.ofSuccess(values.get(0));
                                } else {
                                    return Try.ofFailure(new IllegalArgumentException("cluster is too many."));
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public Try<Cluster> resolveCluster(String shardingConfigId, String clusterGroupId) {
        Validate.notNull(shardingConfigId);
        Validate.notNull(clusterGroupId);
        return resolveCluster(
                ShardingConfigIdFactory.create(shardingConfigId),
                ClusterGroupIdFactory.create(clusterGroupId)
        );
    }

    @Override
    public Try<Cluster> resolveClusterByHint(ShardingConfigId shardingConfigId, final ClusterGroupId clusterGroupId, final Hint<T> hint) {
        Validate.notNull(shardingConfigId);
        Validate.notNull(clusterGroupId);
        Validate.notNull(hint);
        if (clusterIdResolver.isDefined()) {
            return shardingConfigRepository.resolve(shardingConfigId).flatMap(new Function1<ShardingConfig, Try<Cluster>>() {
                @Override
                public Try<Cluster> apply(ShardingConfig shardingConfig) {
                    return shardingConfig.resolveClusterGroup(clusterGroupId).flatMap(new Function1<ClusterGroup, Try<Cluster>>() {
                        @Override
                        public Try<Cluster> apply(final ClusterGroup clusterGroup) {
                            ClusterId clusterId = clusterIdResolver.get().apply(hint, clusterGroup.getClusters().get().size());
                            return clusterGroup.resolveCluster(clusterId);
                        }
                    });
                }
            });
        } else {
            return shardingConfigRepository.resolve(shardingConfigId).flatMap(new Function1<ShardingConfig, Try<Cluster>>() {
                @Override
                public Try<Cluster> apply(ShardingConfig shardingConfig) {
                    return shardingConfig.resolveClusterGroup(clusterGroupId).flatMap(new Function1<ClusterGroup, Try<Cluster>>() {
                        @Override
                        public Try<Cluster> apply(final ClusterGroup clusterGroup) {
                            Object tag = hint.getTag();
                            return taggedClusterIdResolverRepository.resolve(TaggedClusterIdResolverIdFactory.create(tag)).flatMap(new Function1<TaggedClusterIdResolver<T>, Try<Cluster>>() {
                                @Override
                                public Try<Cluster> apply(TaggedClusterIdResolver<T> value) {
                                    ClusterId clusterId = value.getClusterIdResolver().apply(hint, clusterGroup.getClusters().get().size());
                                    return clusterGroup.resolveCluster(clusterId);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    @Override
    public Try<Cluster> resolveClusterByHint(String shardingConfigId, String clusterGroupId, Hint<T> hint) {
        Validate.notNull(shardingConfigId);
        Validate.notNull(clusterGroupId);
        Validate.notNull(hint);
        return resolveClusterByHint(
                ShardingConfigIdFactory.create(shardingConfigId),
                ClusterGroupIdFactory.create(clusterGroupId),
                hint
        );
    }
}
