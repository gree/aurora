package net.gree.aurora.application.config.clusterconfig;

import java.util.List;

final class AuroraClusterJDBCConfigImpl
        extends AbstractAuroraClusterConfig
        implements AuroraClusterJDBCConfig {

    private final String driverClassName;
    private final String databaseName;
    private final String userName;
    private final String password;

    AuroraClusterJDBCConfigImpl(
            String id,
            String masterHost,
            List<String> slaveHosts,
            String standbyHost,
            String driverClassName,
            String databaseName,
            String userName,
            String password
    ) {
        super(id, masterHost, slaveHosts, standbyHost);
        this.driverClassName = driverClassName;
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }

    AuroraClusterJDBCConfigImpl(
            String id
    ) {
        this(id, null, null, null, null, null, null, null);
    }

    @Override
    public String getDriverClassName() {
        return driverClassName;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
