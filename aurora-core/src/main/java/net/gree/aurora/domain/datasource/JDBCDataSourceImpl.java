package net.gree.aurora.domain.datasource;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.sisioh.dddbase.utils.Option;

final class JDBCDataSourceImpl extends AbstractDataSource implements JDBCDataSource {

    private final String driverClassName;
    private final String url;
    private final String userName;
    private final String password;

    JDBCDataSourceImpl(
            DataSourceId id,
            DataSourceType dataSourceType,
            String driverClassName,
            String host,
            Option<Integer> port,
            String url,
            String userName,
            String password,
            Long order
    ) {
        super(id, dataSourceType, host, port, order);
        Validate.notNull(driverClassName);
        Validate.notNull(url);
        Validate.notNull(userName);
        Validate.notNull(password);
        this.driverClassName = driverClassName;
        this.url = url;
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public JDBCDataSourceImpl clone() {
        return (JDBCDataSourceImpl) super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        JDBCDataSourceImpl that = (JDBCDataSourceImpl) o;

        if (driverClassName != null ? !driverClassName.equals(that.driverClassName) : that.driverClassName != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (driverClassName != null ? driverClassName.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format(
                "JDBCDataSource(identity = %s, dataSourceType = %s, host = %s, port = %s, driverClassName = %s, url = %s, userName = %s, password = %s)",
                getIdentity(),
                getDataSourceType(),
                getHost(),
                getPort(),
                getDriverClassName(),
                getUrl(),
                getUserName(),
                getPassword()
        );
    }

}
