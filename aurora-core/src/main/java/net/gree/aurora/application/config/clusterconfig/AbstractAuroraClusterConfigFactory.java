package net.gree.aurora.application.config.clusterconfig;

import com.typesafe.config.Config;

import java.util.List;

abstract class AbstractAuroraClusterConfigFactory
        implements AuroraClusterConfigFactory<Config> {

    protected String getMasterHost(Config source) {
        return source.getString("master");
    }

    protected List<String> getSlaveHosts(Config source) {
        return source.getStringList("slaves");
    }

    protected String getStandbyHost(Config source) {
        return source.getString("standby");
    }

}
