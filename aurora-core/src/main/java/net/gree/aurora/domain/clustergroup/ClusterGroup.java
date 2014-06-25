package net.gree.aurora.domain.clustergroup;

import net.gree.aurora.domain.cluster.Cluster;
import net.gree.aurora.domain.cluster.ClusterId;
import org.sisioh.dddbase.model.Entity;
import org.sisioh.dddbase.utils.Try;

import java.util.List;

/**
 * {@link Cluster}の集合を表すエンティティ。
 */
public interface ClusterGroup
        extends Entity<ClusterGroupId>, Comparable<ClusterGroup> {

    /**
     * {@link ClusterId}から{@link Cluster}を解決する。
     *
     * @param clusterId {@link ClusterId}
     * @return Tryでラップした{@link Cluster}
     */
    Try<Cluster> resolveCluster(ClusterId clusterId);

    /**
     * {@link Cluster}の集合を取得する。
     *
     * @return Tryでラップされた{@link Cluster}のリスト
     */
    Try<List<Cluster>> getClusters();

    /**
     * オーダーを取得する。
     *
     * @return オーダー
     */
    Long getOrder();

    /**
     * インスタンスを複製する。
     *
     * @return 複製したインスタンス
     */
    ClusterGroup clone();

}
