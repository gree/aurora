package net.gree.aurora.application.config;

import com.typesafe.config.Config;
import net.gree.aurora.application.ConfigUtil;
import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfig;
import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfigFactories;
import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfigFactory;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfig;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfigFactories;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfigFactory;
import org.apache.commons.lang.Validate;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

final class AuroraShardingConfigFactoryByConfig
        implements AuroraShardingConfigFactory<Config> {

    private final String key;

    AuroraShardingConfigFactoryByConfig(String key) {
        Validate.notNull(key);
        this.key = key;
    }

    private AuroraShardingConfigType getType(Config source) {
        Validate.notNull(source);
        String typeCode = source.getString("type");
        return AuroraShardingConfigType.resolveByCode(typeCode);
    }

    private AuroraDefaultConfig getDefaultConfig(AuroraShardingConfigType type, Config source) {
        Validate.notNull(type);
        Validate.notNull(source);
        Config config = source.getConfig("default");
        AuroraDefaultConfigFactory<Config> factory = AuroraDefaultConfigFactories.createFactoryFromConfig(type);
        AuroraDefaultConfig result = factory.create(config);
        return result;
    }

    private Set<AuroraClusterGroupConfig> getClusterGroupConfigs(AuroraShardingConfigType type, Config source) {
        Validate.notNull(type);
        Validate.notNull(source);
        Set<AuroraClusterGroupConfig> result = new HashSet<>();
        List<? extends Config> clusterGroupConfigs = ConfigUtil.getConfigList(source, Arrays.asList("clusterGroups", "cluster-groups"));
        for (Config clusterGroupConfig : clusterGroupConfigs) {
            for (String clusterGroupKey : clusterGroupConfig.root().keySet()) {
                Config clusterGroup = clusterGroupConfig.getConfig(clusterGroupKey);
                List<? extends Config> configList = clusterGroup.getConfigList("clusters");
                AuroraClusterGroupConfigFactory<List<? extends Config>> factory = AuroraClusterGroupConfigFactories.createFactoryFromConfig(type, clusterGroupKey);
                AuroraClusterGroupConfig auroraShardGroupConfig = factory.create(configList);
                result.add(auroraShardGroupConfig);
            }
        }
        return result;
    }

    @Override
    public AuroraShardingConfig create(Config source) {
        Validate.notNull(source);
        AuroraShardingConfigType type = getType(source);
        return new AuroraShardingConfigImpl(
                key,
                type,
                getDefaultConfig(type, source),
                getClusterGroupConfigs(type, source)
        );
    }

}
