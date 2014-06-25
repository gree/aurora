package net.gree.aurora.application.config;

import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfig;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfig;

import java.util.Map;
import java.util.Set;

/**
 * Shardingに必要な情報を保持するためのインターフェイス。
 */
public interface AuroraShardingConfig {

    /**
     * 識別子を取得する。
     *
     * @return 識別子
     */
    String getId();

    /**
     * {@link AuroraShardingConfigType}を取得する。
     *
     * @return {@link AuroraShardingConfigType}
     */
    AuroraShardingConfigType getType();

    /**
     * {@link AuroraDefaultConfig}を取得する。
     *
     * @return {@link AuroraDefaultConfig}
     */
    AuroraDefaultConfig getDefaultConfig();

    /**
     * {@link AuroraClusterGroupConfig}の集合を取得する。
     *
     * @return {@link AuroraClusterGroupConfig}の集合
     */
    Set<AuroraClusterGroupConfig> getClusterGroupsAsSet();

    /**
     * {@link AuroraClusterGroupConfig}のマップを取得する。
     *
     * @return {@link AuroraClusterGroupConfig}のマップ
     */
    Map<String, AuroraClusterGroupConfig> getClusterGroupsAsMap();

}
