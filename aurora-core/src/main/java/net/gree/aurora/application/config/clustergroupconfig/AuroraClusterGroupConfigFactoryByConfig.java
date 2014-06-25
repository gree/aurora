package net.gree.aurora.application.config.clustergroupconfig;

import com.typesafe.config.Config;
import net.gree.aurora.application.config.AuroraShardingConfigType;
import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfig;
import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfigFactories;
import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfigFactory;
import org.apache.commons.lang.Validate;

import java.util.ArrayList;
import java.util.List;

final class AuroraClusterGroupConfigFactoryByConfig
        implements AuroraClusterGroupConfigFactory<List<? extends Config>> {

    private final AuroraShardingConfigType type;
    private final String key;

    public AuroraClusterGroupConfigFactoryByConfig(AuroraShardingConfigType type, String key) {
        Validate.notNull(type);
        Validate.notNull(key);
        this.type = type;
        this.key = key;
    }

    private List<AuroraClusterConfig> getAuroraShardConfig(List<? extends Config> source) {
        List<AuroraClusterConfig> result = new ArrayList<>();
        for (Config config : source) {
            for (String key : config.root().keySet()) {
                Config shardConfig = config.getConfig(key);
                AuroraClusterConfigFactory<Config> factory = AuroraClusterConfigFactories.createFactoryFromConfig(type, key);
                AuroraClusterConfig obj = factory.create(shardConfig);
                result.add(obj);
            }
        }
        return result;
    }

    @Override
    public AuroraClusterGroupConfig create(List<? extends Config> source) {
        return new AuroraClusterGroupConfigImpl(
                key,
                getAuroraShardConfig(source)
        );
    }

}
