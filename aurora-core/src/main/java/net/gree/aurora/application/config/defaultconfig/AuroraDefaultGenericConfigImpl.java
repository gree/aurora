package net.gree.aurora.application.config.defaultconfig;

final class AuroraDefaultGenericConfigImpl extends AbstractAuroraDefaultConfig implements AuroraDefaultGenericConfig {

    AuroraDefaultGenericConfigImpl(String masterHost, String slaveHost, Integer port) {
        super(masterHost, slaveHost, port);
    }

    AuroraDefaultGenericConfigImpl() {
        super();
    }

}
