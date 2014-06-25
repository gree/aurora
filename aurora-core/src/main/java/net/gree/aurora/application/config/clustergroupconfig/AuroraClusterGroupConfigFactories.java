package net.gree.aurora.application.config.clustergroupconfig;

import com.typesafe.config.Config;
import net.gree.aurora.application.config.AuroraShardingConfigType;
import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfig;

import java.util.List;

/**
 * {@link AuroraClusterGroupConfig}のためのファクトリ。
 */
public final class AuroraClusterGroupConfigFactories {

    private AuroraClusterGroupConfigFactories() {

    }

    /**
     * {@link AuroraClusterGroupConfig}を生成する。
     *
     * @param id       識別子
     * @param clusters {@link AuroraClusterConfig}の集合
     * @return {@link AuroraClusterGroupConfig}
     */
    public static AuroraClusterGroupConfig create(String id, List<AuroraClusterConfig> clusters) {
        return new AuroraClusterGroupConfigImpl(id, clusters);
    }

    /**
     * {@link Config}から{@link AuroraClusterGroupConfig}を生成する{@link AuroraClusterGroupConfigFactory}を生成する。
     *
     * @param type {@link AuroraShardingConfigType}
     * @param key  キー名
     * @return {@link AuroraClusterGroupConfigFactory}
     */
    public static AuroraClusterGroupConfigFactory<List<? extends Config>> createFactoryFromConfig(
            AuroraShardingConfigType type,
            String key
    ) {
        return new AuroraClusterGroupConfigFactoryByConfig(type, key);
    }

}
