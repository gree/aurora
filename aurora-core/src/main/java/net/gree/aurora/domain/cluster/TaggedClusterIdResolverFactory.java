package net.gree.aurora.domain.cluster;

public class TaggedClusterIdResolverFactory {

    private TaggedClusterIdResolverFactory() {

    }

    public static <T> TaggedClusterIdResolver<T> create(TaggedClusterIdResolverId identity, ClusterIdResolver<T> clusterIdResolver) {
        return new TaggedClusterIdResolverImpl<>(identity, clusterIdResolver);
    }


}
