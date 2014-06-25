package net.gree.aurora.application.config.defaultconfig;

import com.typesafe.config.Config;

final class AuroraDefaultRedisConfigFactoryImpl
        extends AbstractAuroraDefaultConfigFactory
        implements AuroraDefaultRedisConfigFactory {

    private Integer getDatabaseNumber(Config source) {
        return source.getInt("database");
    }

    private String getPassword(Config source) {
        return source.getString("password");
    }

    @Override
    public AuroraDefaultRedisConfig create(Config source) {
        return new AuroraDefaultRedisConfigImpl(
                getMasterHost(source),
                getSlaveHost(source),
                getPort(source),
                getDatabaseNumber(source),
                getPassword(source)
        );
    }
}
