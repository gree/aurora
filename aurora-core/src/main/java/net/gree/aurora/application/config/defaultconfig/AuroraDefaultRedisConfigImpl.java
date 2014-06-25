package net.gree.aurora.application.config.defaultconfig;

class AuroraDefaultRedisConfigImpl
        extends AbstractAuroraDefaultConfig
        implements AuroraDefaultRedisConfig {

    private final Integer databaseNumber;

    private final String password;

    public AuroraDefaultRedisConfigImpl(
            String masterHost,
            String slaveHost,
            Integer port,
            Integer databaseNumber,
            String password
    ) {
        super(masterHost, slaveHost, port);
        this.databaseNumber = databaseNumber;
        this.password = password;
    }

    public AuroraDefaultRedisConfigImpl() {
        this(null, null, null, null, null);
    }

    @Override
    public Integer getDatabaseNumber() {
        return databaseNumber;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
