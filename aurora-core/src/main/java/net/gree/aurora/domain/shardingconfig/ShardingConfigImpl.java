package net.gree.aurora.domain.shardingconfig;

import net.gree.aurora.domain.clustergroup.ClusterGroup;
import net.gree.aurora.domain.clustergroup.ClusterGroupId;
import net.gree.aurora.domain.clustergroup.ClusterGroupRepository;
import net.gree.aurora.domain.clustergroup.ClusterGroupRepositoryFactory;
import org.sisioh.dddbase.model.impl.AbstractEntity;
import org.sisioh.dddbase.utils.Try;

import java.util.Set;

final class ShardingConfigImpl extends AbstractEntity<ShardingConfigId> implements ShardingConfig {

    private final ShardingConfigType configType;
    private final ClusterGroupRepository shardGroupRepository = ClusterGroupRepositoryFactory.create();
    private final Long order;

    public ShardingConfigImpl(ShardingConfigId id, ShardingConfigType configType, Set<ClusterGroup> shardGroups, Long order) {
        super(id);
        this.configType = configType;
        for (ClusterGroup shardGroup : shardGroups) {
            shardGroupRepository.store(shardGroup);
        }
        this.order = order;
    }

    @Override
    public ShardingConfigType getType() {
        return configType;
    }

    @Override
    public Set<ClusterGroup> getClusterGroups() {
        return shardGroupRepository.toSet();
    }

    @Override
    public Long getOrder() {
        return order;
    }

    @Override
    public Try<ClusterGroup> resolveClusterGroup(ClusterGroupId clusterGroupId) {
        return shardGroupRepository.resolve(clusterGroupId);
    }

    @Override
    public int compareTo(ShardingConfig o) {
        return getOrder().compareTo(o.getOrder());
    }

    @Override
    public ShardingConfigImpl clone() {
        return (ShardingConfigImpl) super.clone();
    }
}
