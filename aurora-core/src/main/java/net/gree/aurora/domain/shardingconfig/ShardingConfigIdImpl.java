package net.gree.aurora.domain.shardingconfig;

import org.sisioh.dddbase.model.impl.AbstractIdentity;

final class ShardingConfigIdImpl extends AbstractIdentity<String> implements ShardingConfigId {

    /**
     * インスタンスを生成する。
     *
     * @param value 識別子の値
     */
    protected ShardingConfigIdImpl(String value) {
        super(value);
    }

}
