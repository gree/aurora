package net.gree.aurora.domain.shardingconfig;

import net.gree.aurora.domain.clustergroup.ClusterGroup;
import net.gree.aurora.domain.clustergroup.ClusterGroupId;
import org.sisioh.dddbase.model.Entity;
import org.sisioh.dddbase.utils.Try;

import java.util.Set;

/**
 * Shardingに必要な情報を含むエンティティ。
 */
public interface ShardingConfig
        extends Entity<ShardingConfigId>, Cloneable, Comparable<ShardingConfig> {

    /**
     * {@link ShardingConfigType}を取得する
     *
     * @return {@link ShardingConfigType}
     */
    ShardingConfigType getType();

    /**
     * {@link ClusterGroup}の集合を取得する。
     *
     * @return {@link ClusterGroup}
     */
    Set<ClusterGroup> getClusterGroups();

    /**
     * オーダーを取得する。
     *
     * @return オーダー
     */
    Long getOrder();

    /**
     * {@link ClusterGroupId}から{@link ClusterGroup}を解決する。
     *
     * @param clusterGroupId {@link ClusterGroupId}
     * @return {@link ClusterGroup}
     */
    Try<ClusterGroup> resolveClusterGroup(ClusterGroupId clusterGroupId);

}
