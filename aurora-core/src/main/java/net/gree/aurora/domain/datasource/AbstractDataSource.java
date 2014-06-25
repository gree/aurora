package net.gree.aurora.domain.datasource;

import org.apache.commons.lang.Validate;
import org.sisioh.dddbase.model.impl.AbstractEntity;
import org.sisioh.dddbase.utils.Option;

/**
 * {@link DataSource}のための骨格実装。
 */
abstract class AbstractDataSource extends AbstractEntity<DataSourceId> implements DataSource {

    private final DataSourceType dataSourceType;
    private final String host;
    private final Option<Integer> port;
    private final Long order;

    /**
     * インスタンスを生成する。
     *
     * @param identity       {@link org.sisioh.dddbase.model.Identity}
     * @param dataSourceType {@link DataSourceType}
     * @param host           ホスト名
     * @param port           ポート番号
     * @param order          オーダー
     */
    protected AbstractDataSource(
            DataSourceId identity,
            DataSourceType dataSourceType,
            String host,
            Option<Integer> port,
            Long order
    ) {
        super(identity);
        Validate.notNull(dataSourceType);
        Validate.notNull(host);
        Validate.notNull(port);
        Validate.notNull(order);
        this.dataSourceType = dataSourceType;
        this.host = host;
        this.port = port;
        this.order = order;
    }

    @Override
    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public Option<Integer> getPort() {
        return port;
    }

    @Override
    public Long getOrder() {
        return order;
    }

    @Override
    public AbstractDataSource clone() {
        return (AbstractDataSource) super.clone();
    }

    @Override
    public int compareTo(DataSource o) {
        return getOrder().compareTo(o.getOrder());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AbstractDataSource that = (AbstractDataSource) o;

        if (dataSourceType != that.dataSourceType) return false;
        if (host != null ? !host.equals(that.host) : that.host != null) return false;
        if (order != null ? !order.equals(that.order) : that.order != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (dataSourceType != null ? dataSourceType.hashCode() : 0);
        result = 31 * result + (host != null ? host.hashCode() : 0);
        result = 31 * result + (port != null ? port.hashCode() : 0);
        result = 31 * result + (order != null ? order.hashCode() : 0);
        return result;
    }

}
