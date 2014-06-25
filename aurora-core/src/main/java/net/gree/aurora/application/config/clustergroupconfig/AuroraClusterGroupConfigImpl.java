package net.gree.aurora.application.config.clustergroupconfig;

import net.gree.aurora.application.config.clusterconfig.AuroraClusterConfig;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.sisioh.dddbase.utils.CloneUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class AuroraClusterGroupConfigImpl implements AuroraClusterGroupConfig {

    private final String id;
    private final List<AuroraClusterConfig> auroraShardConfigs;

    public AuroraClusterGroupConfigImpl(
            String id,
            List<AuroraClusterConfig> auroraShardConfigs
    ) {
        Validate.notNull(id);
        Validate.notNull(auroraShardConfigs);
        this.id = id;
        this.auroraShardConfigs = CloneUtil.cloneValueArrayList(auroraShardConfigs);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<AuroraClusterConfig> getClusterConfigsAsList() {
        return CloneUtil.cloneValueArrayList(auroraShardConfigs);
    }

    @Override
    public Map<String, AuroraClusterConfig> getClusterConfigsAsMap() {
        HashMap<String, AuroraClusterConfig> result = new HashMap<>();
        for (AuroraClusterConfig e : auroraShardConfigs) {
            result.put(e.getId(), e);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuroraClusterGroupConfigImpl that = (AuroraClusterGroupConfigImpl) o;

        if (!auroraShardConfigs.equals(that.auroraShardConfigs)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + auroraShardConfigs.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).
                append("id", id).
                append("auroraShardConfigs", auroraShardConfigs).
                toString();
    }

}
