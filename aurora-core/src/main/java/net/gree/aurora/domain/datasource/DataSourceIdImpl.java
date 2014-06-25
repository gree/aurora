package net.gree.aurora.domain.datasource;

import org.sisioh.dddbase.model.impl.AbstractIdentity;

import java.util.UUID;

final class DataSourceIdImpl extends AbstractIdentity<UUID> implements DataSourceId {

    DataSourceIdImpl(UUID value) {
        super(value);
    }

    public String toString() {
        return String.format("DataSourceId(value = %s)", value);
    }

}
