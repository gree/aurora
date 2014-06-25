package net.gree.aurora.domain.cluster;

public class TaggedClusterIdResolverIdFactory {

    private TaggedClusterIdResolverIdFactory() {
    }

    public static TaggedClusterIdResolverId create(Object value) {
        return new TaggedClusterIdResolverIdImpl(value);
    }

}
