package net.gree.aurora.application;

import net.gree.aurora.domain.cluster.Cluster;
import net.gree.aurora.domain.clustergroup.ClusterGroupId;
import net.gree.aurora.domain.datasource.DataSource;
import net.gree.aurora.domain.datasource.DataSourceId;
import net.gree.aurora.domain.datasource.DataSourceRepository;
import net.gree.aurora.domain.hint.Hint;
import net.gree.aurora.domain.shardingconfig.ShardingConfigId;
import net.gree.aurora.domain.shardingconfig.ShardingConfigRepository;
import org.sisioh.dddbase.utils.Try;

import java.util.List;

/**
 * ヒントから{@link net.gree.aurora.domain.cluster.Cluster}を解決するためのサービス。
 *
 * @param <T> ヒントの型
 */
public interface AuroraShardingService<T> {

    /**
     * {@link DataSourceRepository}を取得する。
     *
     * @return {@link DataSourceRepository}
     */
    Try<DataSourceRepository> getDataSourceRepository();

    /**
     * {@link ShardingConfigRepository}を取得する。
     *
     * @return {@link ShardingConfigRepository}
     */
    Try<ShardingConfigRepository> getShardingConfigRepository();

    /**
     * {@link DataSourceId}から{@link DataSource} を解決する。
     *
     * @param id {@link DataSourceId}
     * @return {@link DataSource}
     */
    Try<DataSource> resolveDataSourceById(DataSourceId id);


    /**
     * {@link DataSourceId}の集合から{@link DataSource}の集合を解決する。
     *
     * @param ids {@link DataSourceId}の集合
     * @return {@link DataSource}の集合
     */
    Try<List<DataSource>> resolveDataSourcesByIds(List<DataSourceId> ids);


    /**
     * ヒントを指定しないで{@link net.gree.aurora.domain.cluster.Cluster}を解決する。
     *
     * @param shardingConfigId {@link ShardingConfigId}
     * @param clusterGroupId   {@link ClusterGroupId}
     * @return {@link net.gree.aurora.domain.cluster.Cluster}
     */
    Try<Cluster> resolveCluster(ShardingConfigId shardingConfigId, ClusterGroupId clusterGroupId);

    /**
     * ヒントを指定しないで{@link net.gree.aurora.domain.cluster.Cluster}を解決する。
     *
     * @param shardingConfigId {@link ShardingConfigId}の文字列表現
     * @param clusterGroupId   {@link ClusterGroupId}の文字列表現
     * @return {@link net.gree.aurora.domain.cluster.Cluster}
     */
    Try<Cluster> resolveCluster(String shardingConfigId, String clusterGroupId);

    /**
     * ヒントから{@link net.gree.aurora.domain.cluster.Cluster}を解決する。
     *
     * @param shardingConfigId {@link ShardingConfigId}
     * @param clusterGroupId   {@link ClusterGroupId}
     * @param hint             ヒント
     * @return {@link net.gree.aurora.domain.cluster.Cluster}
     */
    Try<Cluster> resolveClusterByHint(ShardingConfigId shardingConfigId, ClusterGroupId clusterGroupId, Hint<T> hint);

    /**
     * ヒントから{@link net.gree.aurora.domain.cluster.Cluster}を解決する。
     *
     * @param shardingConfigId {@link ShardingConfigId}の文字列表現
     * @param clusterGroupId   {@link ClusterGroupId}の文字列表現
     * @param hint             ヒント
     * @return {@link net.gree.aurora.domain.cluster.Cluster}
     */
    Try<Cluster> resolveClusterByHint(String shardingConfigId, String clusterGroupId, Hint<T> hint);

}
