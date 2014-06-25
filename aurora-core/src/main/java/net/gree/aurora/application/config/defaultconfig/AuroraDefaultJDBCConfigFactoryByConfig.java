package net.gree.aurora.application.config.defaultconfig;

import com.typesafe.config.Config;
import net.gree.aurora.application.ConfigUtil;

import java.util.Arrays;

final class AuroraDefaultJDBCConfigFactoryByConfig
        extends AbstractAuroraDefaultConfigFactory
        implements AuroraDefaultJDBCConfigFactory<Config> {

    private String getDriverClassName(Config source) {
        return ConfigUtil.getString(source, Arrays.asList("driverClassName", "driver-class-name"));
    }

    private String getPrefixUrl(Config source) {
        return ConfigUtil.getString(source, Arrays.asList("prefixUrl", "prefix-url"));
    }

    private String getUserName(Config source) {
        return ConfigUtil.getString(source, Arrays.asList("userName", "user-name"));
    }

    private String getPassword(Config source) {
        return source.getString("password");
    }

    private String getReadOnlyUserName(Config source) {
        return ConfigUtil.getString(source, Arrays.asList("readOnlyUserName", "read-only-user-name"));
    }

    private String getReadOnlyPassword(Config source) {
        return ConfigUtil.getString(source, Arrays.asList("readOnlyPassword", "read-only-password"));
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
