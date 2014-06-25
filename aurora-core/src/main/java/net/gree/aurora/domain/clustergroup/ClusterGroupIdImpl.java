package net.gree.aurora.domain.clustergroup;

import org.sisioh.dddbase.model.impl.AbstractIdentity;


final class ClusterGroupIdImpl extends AbstractIdentity<String> implements ClusterGroupId {

    /**
     * インスタンスを生成する。
     *
     * @param value 識別子の値
     */
    public ClusterGroupIdImpl(String value) {
        super(value);
    }

}
