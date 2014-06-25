package net.gree.aurora.domain.datasource;

import org.sisioh.dddbase.utils.Option;

final class DataSourceImpl extends AbstractDataSource {

    DataSourceImpl(
            DataSourceId id,
            DataSourceType dataSourceType,
            String host,
            Option<Integer> port,
            Long order
    ) {
        super(id, dataSourceType, host, port, order);
    }

    @Override
    public int compareTo(DataSource o) {
        return getOrder().compareTo(o.getOrder());
    }

    @Override
    public DataSourceImpl clone() {
        return (DataSourceImpl) super.clone();
    }

    @Override
    public String toString() {
        return String.format(
                "DataSource(identity = %s, dataSourceType = %s, host = %s, port = %s)",
                getIdentity(), getDataSourceType(), getHost(), getPort()
        );
    }


}
