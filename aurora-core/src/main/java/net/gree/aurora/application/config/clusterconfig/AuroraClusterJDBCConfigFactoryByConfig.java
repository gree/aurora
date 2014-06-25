package net.gree.aurora.application.config.clusterconfig;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import net.gree.aurora.application.ConfigUtil;
import org.apache.commons.lang.Validate;

import java.util.Arrays;

public final class AuroraClusterJDBCConfigFactoryByConfig
        extends AbstractAuroraClusterConfigFactory
        implements AuroraClusterJDBCConfigFactory<Config> {

    private final String key;

    public AuroraClusterJDBCConfigFactoryByConfig(String key) {
        Validate.notNull(key);
        this.key = key;
    }

    private String getDriverClassName(Config source) {
        try {
            return ConfigUtil.getString(source, Arrays.asList("driverClassName", "driver-class-name"));
        } catch (ConfigException.Missing e) {
            return null;
        }
    }

    private String getDatabaseName(Config source) {
        return ConfigUtil.getString(source, Arrays.asList("database"));
    }

    private String getUserName(Config source) {
        try {
            return ConfigUtil.getString(source, Arrays.asList("userName", "user-name"));
        } catch (ConfigException.Missing e) {
            return null;
        }
    }

    private String getPassword(Config source) {
        try {
            return source.getString("password");
        } catch (ConfigException.Missing e) {
            return null;
        }
    }

    @Override
    public AuroraClusterJDBCConfig create(Config source) {
        return new AuroraClusterJDBCConfigImpl(
                key,
                getMasterHost(source),
                getSlaveHosts(source),
                getStandbyHost(source),
                getDriverClassName(source),
                getDatabaseName(source),
                getUserName(source),
                getPassword(source)
        );
    }

}
