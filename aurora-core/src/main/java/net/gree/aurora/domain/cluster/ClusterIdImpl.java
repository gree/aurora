package net.gree.aurora.domain.cluster;

import org.sisioh.dddbase.model.impl.AbstractIdentity;

final class ClusterIdImpl extends AbstractIdentity<String> implements ClusterId {

    ClusterIdImpl(String value) {
        super(value);
    }

    @Override
    public String toString() {
        return String.format("ClusterId(value = %s)", value);
    }

}
