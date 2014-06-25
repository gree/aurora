package net.gree.aurora.domain.clustergroup;

import net.gree.aurora.domain.cluster.Cluster;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link ClusterGroup}のためのファクトリ。
 */
public final class ClusterGroupFactory {

    private static final AtomicLong order = new AtomicLong(1L);

    private ClusterGroupFactory() {
    }

    /**
     * {@link ClusterGroup}を生成する。
     *
     * @param id       {@link ClusterGroup}
     * @param clusters {@link Cluster}のリスト
     * @return {@link ClusterGroup}
     */
    public static ClusterGroup create(ClusterGroupId id, List<Cluster> clusters) {
        return new ClusterGroupImpl(id, clusters, order.getAndIncrement());
    }

}
