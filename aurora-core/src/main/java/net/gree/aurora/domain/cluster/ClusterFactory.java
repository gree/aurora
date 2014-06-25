package net.gree.aurora.domain.cluster;

import net.gree.aurora.domain.datasource.DataSourceId;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * {@link Cluster}のためのファクトリ。
 */
public final class ClusterFactory {

    private static final AtomicLong order = new AtomicLong(1L);

    private ClusterFactory() {
    }

    /**
     * {@link Cluster}を生成する。
     *
     * @param identity {@link ClusterId}
     * @param masterDataSourceId {@link DataSourceId}
     * @param slaveDataSourceIds {@link DataSourceId}の集合
     * @return {@link Cluster}
     */
    public static Cluster create(ClusterId identity, DataSourceId masterDataSourceId, List<DataSourceId> slaveDataSourceIds) {
        return new ClusterImpl(identity, masterDataSourceId, slaveDataSourceIds, order.getAndIncrement());
    }

}
