package net.gree.aurora.application.config.defaultconfig;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * {@link AuroraDefaultConfig}の骨格実装。
 */
abstract class AbstractAuroraDefaultConfig implements AuroraDefaultConfig {

    private final Integer port;
    private final String masterHost;
    private final String slaveHost;

    /**
     * インスタンスを生成する。
     */
    protected AbstractAuroraDefaultConfig() {
        this(null, null, null);
    }

    /**
     * インスタンスを生成する。
     *
     * @param masterHost マスターホスト名
     * @param slaveHost スレーブホスト名
     * @param port ポート番号
     */
    protected AbstractAuroraDefaultConfig(
            String masterHost,
            String slaveHost,
            Integer port
    ) {
        this.masterHost = masterHost;
        this.slaveHost = slaveHost;
        this.port = port;
    }

    @Override
    public String getMasterHost() {
        return masterHost;
    }

    @Override
    public Integer getPort() {
        return port;
    }

    @Override
    public String getSlaveHost() {
        return slaveHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAuroraDefaultConfig that = (AbstractAuroraDefaultConfig) o;

        if (masterHost != null ? !masterHost.equals(that.masterHost) : that.masterHost != null) return false;
        if (port != null ? !port.equals(that.port) : that.port != null) return false;
        if (slaveHost != null ? !slaveHost.equals(that.slaveHost) : that.slaveHost != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = port != null ? port.hashCode() : 0;
        result = 31 * result + (masterHost != null ? masterHost.hashCode() : 0);
        result = 31 * result + (slaveHost != null ? slaveHost.hashCode() : 0);
        return result;
    }

    protected ToStringBuilder getToStringBuilder() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("masterHost", masterHost).append("slaveHost", slaveHost).append("port", port);
    }

    @Override
    public String toString() {
        return getToStringBuilder().toString();
    }

}
