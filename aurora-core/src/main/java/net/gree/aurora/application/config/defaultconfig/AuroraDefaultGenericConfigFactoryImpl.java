package net.gree.aurora.application.config.defaultconfig;

import com.typesafe.config.Config;

public class AuroraDefaultGenericConfigFactoryImpl
        extends AbstractAuroraDefaultConfigFactory
        implements AuroraDefaultGenericConfigFactory<Config> {

    @Override
    public AuroraDefaultGenericConfig create(Config source) {
        return new AuroraDefaultGenericConfigImpl(
                getMasterHost(source),
                getSlaveHost(source),
                getPort(source)
        );
    }

}
