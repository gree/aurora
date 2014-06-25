package net.gree.aurora.application.config.clusterconfig;

import java.util.List;

final class AuroraClusterConfigImpl extends AbstractAuroraClusterConfig {

    AuroraClusterConfigImpl(String id, String masterHost, List<String> slaveHosts, String standbyHost) {
        super(id, masterHost, slaveHosts, standbyHost);
    }

    AuroraClusterConfigImpl(String id) {
        this(id, null, null, null);
    }

}
