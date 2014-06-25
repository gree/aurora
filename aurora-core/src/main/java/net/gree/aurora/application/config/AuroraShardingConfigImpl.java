package net.gree.aurora.application.config;

import net.gree.aurora.application.config.clustergroupconfig.AuroraClusterGroupConfig;
import net.gree.aurora.application.config.defaultconfig.AuroraDefaultConfig;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.sisioh.dddbase.utils.CloneUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

final class AuroraShardingConfigImpl implements AuroraShardingConfig {

    private final String id;
    private final AuroraShardingConfigType type;
    private final AuroraDefaultConfig defaultConfig;
    private final Set<AuroraClusterGroupConfig> shardGroupConfigs;

    AuroraShardingConfigImpl(
            String id,
            AuroraShardingConfigType type,
            AuroraDefaultConfig defaultConfig,
            Set<AuroraClusterGroupConfig> shardGroupConfigs
    ) {
        Validate.notNull(id);
        Validate.notNull(type);
        this.id = id;
        this.type = type;
        this.defaultConfig = defaultConfig;
        this.shardGroupConfigs = shardGroupConfigs;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public AuroraShardingConfigType getType() {
        return type;
    }

    @Override
    public AuroraDefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public Set<AuroraClusterGroupConfig> getClusterGroupsAsSet() {
        return CloneUtil.cloneValueHashSet(shardGroupConfigs);
    }

    @Override
    public Map<String, AuroraClusterGroupConfig> getClusterGroupsAsMap() {
        HashMap<String, AuroraClusterGroupConfig> result = new HashMap<>();
        for (AuroraClusterGroupConfig shardGroupConfig : shardGroupConfigs) {
            result.put(shardGroupConfig.getId(), shardGroupConfig);
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuroraShardingConfigImpl that = (AuroraShardingConfigImpl) o;

        if (!defaultConfig.equals(that.defaultConfig)) return false;
        if (!id.equals(that.id)) return false;
        if (!shardGroupConfigs.equals(that.shardGroupConfigs)) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + defaultConfig.hashCode();
        result = 31 * result + shardGroupConfigs.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).
                append("id", id).
                append("type", type).
                append("default", defaultConfig).
                append("shardGroups", shardGroupConfigs).
                toString();
    }

}
