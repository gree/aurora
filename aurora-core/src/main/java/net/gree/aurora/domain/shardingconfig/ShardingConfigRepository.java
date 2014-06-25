package net.gree.aurora.domain.shardingconfig;

import org.sisioh.dddbase.lifecycle.sync.SyncEntityIterableReader;
import org.sisioh.dddbase.lifecycle.sync.SyncRepository;

import java.util.List;

/**
 * {@link ShardingConfig}のためのリポジトリ。
 */
public interface ShardingConfigRepository
        extends SyncRepository<ShardingConfigRepository, ShardingConfigId, ShardingConfig>,
        SyncEntityIterableReader<ShardingConfigId, ShardingConfig> {

    /**
     * エンティティをリストとして取得する。
     *
     * @return {@link ShardingConfig}のリスト
     */
    List<ShardingConfig> toList();

}
