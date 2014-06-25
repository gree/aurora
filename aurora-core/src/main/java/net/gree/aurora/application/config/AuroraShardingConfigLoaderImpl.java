package net.gree.aurora.application.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.gree.aurora.application.ConfigUtil;
import org.apache.commons.lang.Validate;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

final class AuroraShardingConfigLoaderImpl implements AuroraShardingConfigLoader {

    @Override
    public Set<AuroraShardingConfig> load(File configFile) {
        Validate.notNull(configFile);
        Config config = ConfigFactory.parseFile(configFile);
        Config auroraConfig = config.getConfig("aurora");
        Config shardingConfig = ConfigUtil.getConfig(auroraConfig, Arrays.asList("shardingConfigs", "sharding-configs"));
        return load(shardingConfig);
    }

    @Override
    public Set<AuroraShardingConfig> load(Config config) {
        Set<AuroraShardingConfig> result = new HashSet<>();
        for (String key : config.root().keySet()) {
            Config auroraShardingConfig = config.getConfig(key);
            AuroraShardingConfigFactory<Config> configAuroraConfigFactory = AuroraShardingConfigFactories.createFactoryFromConfig(key);
            AuroraShardingConfig auroraConfigModel = configAuroraConfigFactory.create(auroraShardingConfig);
            result.add(auroraConfigModel);
        }
        return result;
    }

}
