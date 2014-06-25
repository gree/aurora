package net.gree.aurora.application.config.clusterconfig;

import com.typesafe.config.Config;
import net.gree.aurora.application.config.AuroraShardingConfigType;

import java.util.List;

/**
 * {@link AuroraClusterConfig}のためのファクトリ。
 */
public final class AuroraClusterConfigFactories {

    private AuroraClusterConfigFactories() {

    }

    /**
     * {@link AuroraClusterConfig}を生成する。
     *
     * @param id          識別子
     * @param masterHost  マスター
     * @param slaveHosts  スレーブ
     * @param standByHost スタンバイ
     * @return {@link AuroraClusterConfig}
     */
    public static AuroraClusterConfig create(String id, String masterHost, List<String> slaveHosts, String standByHost) {
        return new AuroraClusterConfigImpl(id, masterHost, slaveHosts, standByHost);
    }

    /**
     * {@link AuroraClusterConfig}を生成する。
     *
     * @param id 識別子
     * @return {@link AuroraClusterConfig}
     */
    public static AuroraClusterConfig create(String id) {
        return new AuroraClusterConfigImpl(id);
    }

    /**
     * {@link AuroraClusterJDBCConfig}を生成する。
     *
     * @param id              識別子
     * @param masterHost      マスター
     * @param slaveHosts      スレーブ
     * @param standByHost     スタンバイ
     * @param driverClassName ドライバークラス名
     * @param databaseName    データベース名
     * @param userName        ユーザ名
     * @param password        パスワード
     * @return {@link AuroraClusterJDBCConfig}
     */
    public static AuroraClusterJDBCConfig createForJDBC(
            String id,
            String masterHost,
            List<String> slaveHosts,
            String standByHost,
            String driverClassName,
            String databaseName,
            String userName,
            String password
    ) {
        return new AuroraClusterJDBCConfigImpl(
                id,
                masterHost,
                slaveHosts,
                standByHost,
                driverClassName,
                databaseName,
                userName,
                password
        );
    }

    /**
     * {@link AuroraClusterJDBCConfig}を生成する。
     *
     * @param id 識別子
     * @return {@link AuroraClusterJDBCConfig}
     */
    public static AuroraClusterJDBCConfig createForJDBC(
            String id) {
        return new AuroraClusterJDBCConfigImpl(id);
    }

    /**
     * {@link Config}から{@link AuroraClusterConfig}を生成するための{@link AuroraClusterConfigFactory}を生成する。
     *
     * @param configType {@link AuroraShardingConfigType}
     * @param key        キー名
     * @return {@link AuroraClusterConfigFactory}
     */
    public static AuroraClusterConfigFactory<Config> createFactoryFromConfig(AuroraShardingConfigType configType, String key) {
        switch (configType) {
            case JDBC_TYPE:
                return new AuroraClusterJDBCConfigFactoryByConfig(key);
            case GENERIC_TYPE:
            case REDIS_TYPE:
                return new AuroraClusterConfigFactoryByConfig(key);
            default:
                throw new IllegalArgumentException(key);
        }
    }


}
