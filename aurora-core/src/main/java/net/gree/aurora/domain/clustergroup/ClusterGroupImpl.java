package net.gree.aurora.domain.clustergroup;

import net.gree.aurora.domain.cluster.Cluster;
import net.gree.aurora.domain.cluster.ClusterId;
import net.gree.aurora.domain.cluster.ClusterRepository;
import net.gree.aurora.domain.cluster.ClusterRepositoryFactory;
import org.apache.commons.lang.Validate;
import org.sisioh.dddbase.model.impl.AbstractEntity;
import org.sisioh.dddbase.utils.Try;

import java.util.List;

final class ClusterGroupImpl
        extends AbstractEntity<ClusterGroupId>
        implements ClusterGroup {

    private final Long order;
    private final ClusterRepository shardRepository = ClusterRepositoryFactory.create();

    /**
     * インスタンスを生成する。
     *
     * @param identity {@link org.sisioh.dddbase.model.Identity}
     * @param clusters {@link Cluster}のリスト
     * @param order    オーダー
     */
    public ClusterGroupImpl(ClusterGroupId identity, List<Cluster> clusters, Long order) {
        super(identity);
        Validate.notNull(clusters);
        Validate.notNull(order);
        for (Cluster shard : clusters) {
            shardRepository.store(shard);
        }
        this.order = order;
    }

    public Try<Cluster> resolveCluster(ClusterId shardId) {
        return shardRepository.resolve(shardId);
    }

    @Override
    public Try<List<Cluster>> getClusters() {
        return Try.ofSuccess(shardRepository.toList());
    }

    @Override
    public Long getOrder() {
        return order;
    }

    @Override
    public int compareTo(ClusterGroup o) {
        return order.compareTo(o.getOrder());
    }

    @Override
    public String toString() {
        return String.format("ClusterGroup(identity = %s, clusters = %s)", getIdentity(), getClusters());
    }

    @Override
    public ClusterGroupImpl clone() {
        return (ClusterGroupImpl) super.clone();
    }
}
