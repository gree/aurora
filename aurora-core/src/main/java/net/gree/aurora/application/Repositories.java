package net.gree.aurora.application;

import net.gree.aurora.domain.datasource.DataSourceRepository;
import net.gree.aurora.domain.shardingconfig.ShardingConfigRepository;

public interface Repositories {

    DataSourceRepository getDataSourceRepository();

    ShardingConfigRepository getShardingConfigRepository();

}
