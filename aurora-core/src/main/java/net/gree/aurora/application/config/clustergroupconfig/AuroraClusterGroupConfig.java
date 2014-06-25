package net.gree.aurora.application.config.clustergroupconfig;

import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfig;

import java.util.List;
import java.util.Map;

/**
 * {@link AuroraClusterConfig}の集合。
 */
public interface AuroraClusterGroupConfig {

    /**
     * 識別子を取得する。
     *
     * @return 識別子
     */
    String getId();

    /**
     * {@link AuroraClusterConfig}の集合を取得する。
     * 　
     * @return {@link AuroraClusterConfig}の集合
     */
    List<AuroraClusterConfig> getClusterConfigsAsList();

    /**
     * {@link AuroraClusterConfig}のマップを取得する。
     *
     * @return {@link AuroraClusterConfig}のマップ
     */
    Map<String, AuroraClusterConfig> getClusterConfigsAsMap();

}
