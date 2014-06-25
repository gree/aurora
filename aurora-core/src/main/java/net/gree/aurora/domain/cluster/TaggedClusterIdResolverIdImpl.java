package net.gree.aurora.domain.cluster;

import org.sisioh.dddbase.model.impl.AbstractIdentity;

final class TaggedClusterIdResolverIdImpl extends AbstractIdentity<Object> implements TaggedClusterIdResolverId {

    public TaggedClusterIdResolverIdImpl(Object value) {
        super(value);
    }

    @Override
    public String toString() {
        return String.format("TaggedClusterIdResolverId(value = %s)", value);
    }

}
