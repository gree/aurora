package net.gree.aurora.application.config.defaultconfig;

import org.apache.commons.lang.Validate;

final class AuroraDefaultJDBCConfigImpl extends AbstractAuroraDefaultConfig
        implements AuroraDefaultJDBCConfig {

    private final String driverClassName;
    private final String prefixUrl;
    private final String userName;
    private final String password;
    private final String readOnlyUserName;
    private final String readOnlyPassword;

    AuroraDefaultJDBCConfigImpl(String driverClassName, String prefixUrl) {
        this(
                driverClassName,
                prefixUrl,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    AuroraDefaultJDBCConfigImpl(String driverClassName,
                                String prefixUrl, String masterHost, String slaveHost,
                                Integer port, String userName, String password,
                                String readOnlyUserName, String readOnlyPassword) {
        super(masterHost, slaveHost, port);
        Validate.notNull(driverClassName);
        Validate.notNull(prefixUrl);
        this.driverClassName = driverClassName;
        this.prefixUrl = prefixUrl;
        this.userName = userName;
        this.password = password;
        this.readOnlyUserName = readOnlyUserName;
        this.readOnlyPassword = readOnlyPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AuroraDefaultJDBCConfigImpl that = (AuroraDefaultJDBCConfigImpl) o;

        if (!driverClassName.equals(that.driverClassName)) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (!prefixUrl.equals(that.prefixUrl)) return false;
        if (readOnlyPassword != null ? !readOnlyPassword.equals(that.readOnlyPassword) : that.readOnlyPassword != null)
            return false;
        if (readOnlyUserName != null ? !readOnlyUserName.equals(that.readOnlyUserName) : that.readOnlyUserName != null)
            return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + driverClassName.hashCode();
        result = 31 * result + prefixUrl.hashCode();
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (readOnlyUserName != null ? readOnlyUserName.hashCode() : 0);
        result = 31 * result + (readOnlyPassword != null ? readOnlyPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getPrefixUrl() {
        return prefixUrl;
    }

    @Override
    public String getReadOnlyPassword() {
        return readOnlyPassword;
    }

    @Override
    public String getReadOnlyUserName() {
        return readOnlyUserName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return getToStringBuilder().
                append("driverClassName", driverClassName).
                append("prefixUrl", prefixUrl).
                append("userName", userName).
                append("password", password).
                append("readOnlyUserName", readOnlyUserName).
                append("readOnlyPassword", readOnlyPassword).
                toString();
    }
}
