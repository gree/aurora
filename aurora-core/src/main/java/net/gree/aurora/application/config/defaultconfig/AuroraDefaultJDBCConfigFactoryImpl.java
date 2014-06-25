package net.gree.aurora.application.config.defaultconfig;

import com.typesafe.config.Config;

final class AuroraDefaultJDBCConfigFactoryImpl
        extends AbstractAuroraDefaultConfigFactory
        implements AuroraDefaultJDBCConfigFactory<Config> {

    private String getDriverClassName(Config source) {
        return source.getString("driverClassName");
    }

    private String getPrefixUrl(Config source) {
        return source.getString("prefixUrl");
    }

    private String getUserName(Config source) {
        return source.getString("userName");
    }

    private String getPassword(Config source) {
        return source.getString("password");
    }

    private String getReadOnlyUserName(Config source) {
        return source.getString("readOnlyUserName");
    }

    private String getReadOnlyPassword(Config source) {
        return source.getString("readOnlyPassword");
    }

    @Override
    public AuroraDefaultJDBCConfig create(Config source) {
        return new AuroraDefaultJDBCConfigImpl(
                getDriverClassName(source),
                getPrefixUrl(source),
                getMasterHost(source),
                getSlaveHost(source),
                getPort(source),
                getUserName(source),
                getPassword(source),
                getReadOnlyUserName(source),
                getReadOnlyPassword(source)
        );
    }

}
