package net.gree.aurora.application.config;

import com.typesafe.config.Config;
import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfig;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfig;

import java.util.Set;

/**
 * {@link AuroraShardingConfig}のためのファクトリ。
 */
public final class AuroraShardingConfigFactories {

    private AuroraShardingConfigFactories() {

    }

    /**
     * {@link AuroraShardingConfig}を生成する。
     *
     * @param id            識別子
     * @param type          {@link AuroraShardingConfigType}
     * @param defaultConfig {@link AuroraDefaultConfig}
     * @param shardGroups   {@link AuroraClusterGroupConfig}の集合
     * @return
     */
    public static AuroraShardingConfig create(
            String id,
            AuroraShardingConfigType type,
            AuroraDefaultConfig defaultConfig,
            Set<AuroraClusterGroupConfig> shardGroups
    ) {
        return new AuroraShardingConfigImpl(id, type, defaultConfig, shardGroups);
    }

    /**
     * {@link Config}から{@link AuroraShardingConfig}を生成するファクトリを生成する。
     *
     * @param key キー名
     * @return {@link AuroraShardingConfig}
     */
    public static AuroraShardingConfigFactory<Config> createFactoryFromConfig(String key) {
        return new AuroraShardingConfigFactoryByConfig(key);
    }

}
