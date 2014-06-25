package net.gree.aurora.application.config.defaultconfig;

import com.typesafe.config.Config;
import net.gree.aurora.application.config.AuroraShardingConfigType;

/**
 * {@link AuroraDefaultConfig}のためのファクトリ。
 */
public final class AuroraDefaultConfigFactories {

    private AuroraDefaultConfigFactories() {

    }

    /**
     * {@link AuroraDefaultJDBCConfig}を生成する。
     *
     * @param driverClassName  ドライバークラス名
     * @param prefixUrl        プレフィックスURL
     * @param masterHost       マスターホスト
     * @param slaveHost        スレーブホスト
     * @param port             ポート番号
     * @param userName         ユーザ名
     * @param password         パスワード
     * @param readOnlyUserName 読み込み用ユーザ名
     * @param readOnlyPassword 読み込み用パスワード
     * @return {@link AuroraDefaultJDBCConfig}
     */
    public static AuroraDefaultJDBCConfig createAuroraDefaultJDBCConfig(
            String driverClassName,
            String prefixUrl,
            String masterHost,
            String slaveHost,
            Integer port,
            String userName,
            String password,
            String readOnlyUserName,
            String readOnlyPassword
    ) {
        return new AuroraDefaultJDBCConfigImpl(
                driverClassName,
                prefixUrl,
                masterHost,
                slaveHost,
                port,
                userName,
                password,
                readOnlyUserName,
                readOnlyPassword
        );
    }

    /**
     * {@link AuroraDefaultJDBCConfig}を生成する。
     *
     * @param driverClassName ドライバークラス名
     * @param prefixUrl       プレフィックス名
     * @return {@link AuroraDefaultJDBCConfig}
     */
    public static AuroraDefaultJDBCConfig createAuroraDefaultJDBCConfig(
            String driverClassName,
            String prefixUrl
    ) {
        return new AuroraDefaultJDBCConfigImpl(
                driverClassName,
                prefixUrl
        );
    }

    /**
     * {@link AuroraDefaultGenericConfig}を生成する。
     *
     * @param masterHost マスターホスト名
     * @param slaveHost  スレーブホスト名
     * @param port       ポート番号
     * @return {@link AuroraDefaultGenericConfig}
     */
    public static AuroraDefaultGenericConfig createAuroraDefaultMemcachedConfig(
            String masterHost,
            String slaveHost,
            Integer port
    ) {
        return new AuroraDefaultGenericConfigImpl(masterHost, slaveHost, port);
    }

    /**
     * {@link AuroraDefaultGenericConfig}を生成する。
     *
     * @return {@link AuroraDefaultGenericConfig}
     */
    public static AuroraDefaultGenericConfig createAuroraDefaultMemcachedConfig() {
        return new AuroraDefaultGenericConfigImpl();
    }

    /**
     * {@link AuroraDefaultRedisConfig}を生成する。
     *
     * @param masterHost     マスターホスト名
     * @param slaveHost      スレーブホスト名
     * @param port           ポート番号
     * @param databaseNumber データベース番号
     * @param password       パスワード
     * @return {@link AuroraDefaultRedisConfig}
     */
    public static AuroraDefaultRedisConfig createAuroraDefaultRedisConfig(
            String masterHost,
            String slaveHost,
            Integer port,
            Integer databaseNumber,
            String password
    ) {
        return new AuroraDefaultRedisConfigImpl(masterHost, slaveHost, port, databaseNumber, password);
    }

    /**
     * {@link AuroraDefaultRedisConfig}を生成する。
     *
     * @return {@link AuroraDefaultRedisConfig}
     */
    public static AuroraDefaultRedisConfig createAuroraDefaultRedisConfig() {
        return new AuroraDefaultRedisConfigImpl();
    }

    /**
     * {@link Config}から{@link AuroraDefaultConfig}を生成する{@link AuroraDefaultConfigFactory}を生成する
     *
     * @param configType {@link AuroraShardingConfigType}
     * @return {@link AuroraDefaultConfigFactory}
     */
    public static AuroraDefaultConfigFactory<Config> createFactoryFromConfig(AuroraShardingConfigType configType) {
        switch (configType) {
            case JDBC_TYPE:
                return new AuroraDefaultJDBCConfigFactoryByConfig();
            case GENERIC_TYPE:
                return new AuroraDefaultGenericConfigFactoryByConfig();
            case REDIS_TYPE:
                return new AuroraDefaultRedisConfigFactoryImpl();
            default:
                throw new IllegalArgumentException();
        }
    }

}
