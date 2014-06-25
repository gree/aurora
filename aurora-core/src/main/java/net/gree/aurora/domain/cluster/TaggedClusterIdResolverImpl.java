package net.gree.aurora.domain.cluster;

import org.sisioh.dddbase.model.impl.AbstractEntity;

final class TaggedClusterIdResolverImpl<HINT>  extends AbstractEntity<TaggedClusterIdResolverId> implements TaggedClusterIdResolver<HINT> {

    private final ClusterIdResolver<HINT> clusterIdResolver;

    /**
     * インスタンスを生成する。
     *
     * @param identity {@link org.sisioh.dddbase.model.Identity}
     * @param clusterIdResolver
     */
    public TaggedClusterIdResolverImpl(TaggedClusterIdResolverId identity, ClusterIdResolver<HINT> clusterIdResolver) {
        super(identity);
        this.clusterIdResolver = clusterIdResolver;
    }

    @Override
    public ClusterIdResolver<HINT> getClusterIdResolver() {
        return clusterIdResolver;
    }

}
