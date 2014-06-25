package net.gree.aurora.domain.cluster;

public final class TaggedClusterIdResolverRepositoryFactory {
    private TaggedClusterIdResolverRepositoryFactory() {

    }

    public static <HINT> TaggedClusterIdResolverRepository<HINT> create() {
        return new TaggedClusterIdResolverRepositoryImpl<>();
    }

}
