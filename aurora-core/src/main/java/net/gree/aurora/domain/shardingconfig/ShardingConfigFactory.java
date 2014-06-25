package net.gree.aurora.domain.shardingconfig;

import net.gree.aurora.domain.clustergroup.ClusterGroup;

import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link ShardingConfig}のためのファクトリ。
 */
public final class ShardingConfigFactory {

    private static final AtomicLong order = new AtomicLong(1L);

    private ShardingConfigFactory() {
    }

    /**
     * {@link ShardingConfig}を生成する。
     *
     * @param id            識別子
     * @param configType    {@ShardingConfigType}
     * @param clusterGroups {@link ClusterGroup}の集合
     * @return {@link ShardingConfig}
     */
    public static ShardingConfig create(ShardingConfigId id, ShardingConfigType configType, Set<ClusterGroup> clusterGroups) {
        return new ShardingConfigImpl(id, configType, clusterGroups, order.getAndIncrement());
    }

}
