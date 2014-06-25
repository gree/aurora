package net.gree.aurora.domain.datasource;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.sisioh.dddbase.utils.Option;

final class RedisDataSourceImpl
        extends AbstractDataSource implements RedisDataSource {

    private final Integer databaseNumber;
    private final String password;

    RedisDataSourceImpl(
            DataSourceId id,
            DataSourceType dataSourceType,
            String host,
            Option<Integer> port,
            Integer databaseNumber,
            String password,
            Long order
    ) {
        super(id, dataSourceType, host, port, order);
        this.databaseNumber = databaseNumber;
        this.password = password;
    }

    @Override
    public Integer getDatabaseNumber() {
        return databaseNumber;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public RedisDataSourceImpl clone() {
        return (RedisDataSourceImpl) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        RedisDataSourceImpl that = (RedisDataSourceImpl) o;

        if (databaseNumber != null ? !databaseNumber.equals(that.databaseNumber) : that.databaseNumber != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (databaseNumber != null ? databaseNumber.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "RedisDataSource(identity = %s, dataSourceType = %s, host = %s, port = %s, databaseNumber = %s, password = %s)",
                getIdentity(),
                getDataSourceType(),
                getHost(),
                getPort(),
                getDatabaseNumber(),
                getPassword()
        );
    }
}
