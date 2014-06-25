package net.gree.aurora.application.config.clusterconfig;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.sisioh.dddbase.utils.CloneUtil;

import java.util.List;

/**
 * {@link AuroraClusterConfig}のための骨格実装。
 */
abstract class AbstractAuroraClusterConfig implements AuroraClusterConfig {

    private final String id;
    private final String masterHost;
    private final List<String> slaveHosts;
    private final String standbyHost;

    /**
     * インスタンスを生成する。
     *
     * @param id  識別子
     * @param masterHost マスター
     * @param slaveHosts スレーブ
     * @param standbyHost スタンバイ
     */
    AbstractAuroraClusterConfig(
            String id,
            String masterHost,
            List<String> slaveHosts,
            String standbyHost
    ) {
        Validate.notNull(id);
        this.id = id;
        this.masterHost = masterHost;
        this.slaveHosts = CloneUtil.cloneValueArrayList(slaveHosts);
        this.standbyHost = standbyHost;
    }

    /**
     * インスタンスを生成する。
     *
     * @param id 識別子
     */
    AbstractAuroraClusterConfig(
            String id) {
        this(id, null, null, null);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getMasterHostAndPort() {
        return masterHost;
    }

    @Override
    public List<String> getSlaveHostAndPorts() {
        return CloneUtil.cloneValueArrayList(slaveHosts);
    }

    @Override
    public String getStandbyHostAndPort() {
        return standbyHost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractAuroraClusterConfig that = (AbstractAuroraClusterConfig) o;

        if (!id.equals(that.id)) return false;
        if (masterHost != null ? !masterHost.equals(that.masterHost) : that.masterHost != null) return false;
        if (slaveHosts != null ? !slaveHosts.equals(that.slaveHosts) : that.slaveHosts != null) return false;
        if (standbyHost != null ? !standbyHost.equals(that.standbyHost) : that.standbyHost != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (masterHost != null ? masterHost.hashCode() : 0);
        result = 31 * result + (slaveHosts != null ? slaveHosts.hashCode() : 0);
        result = 31 * result + (standbyHost != null ? standbyHost.hashCode() : 0);
        return result;
    }

    protected ToStringBuilder getToStringBuilder() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).
                append("id", id).
                append("masterHost", masterHost).
                append("slaveHosts", slaveHosts).
                append("standbyHost", standbyHost);
    }

    @Override
    public String toString() {
        return getToStringBuilder().toString();
    }
}
