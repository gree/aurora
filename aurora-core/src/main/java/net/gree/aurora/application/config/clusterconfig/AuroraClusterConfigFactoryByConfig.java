package net.gree.aurora.application.config.clusterconfig;

import com.typesafe.config.Config;
import org.apache.commons.lang.Validate;

final class AuroraClusterConfigFactoryByConfig extends AbstractAuroraClusterConfigFactory {

    private final String key;

    AuroraClusterConfigFactoryByConfig(String key) {
        Validate.notNull(key);
        this.key = key;
    }

    @Override
    public AuroraClusterConfig create(Config source) {
        return new AuroraClusterConfigImpl(
                key,
                getMasterHost(source),
                getSlaveHosts(source),
                getStandbyHost(source)
        );
    }

}
