package net.gree.aurora.domain.datasource;

import org.sisioh.dddbase.lifecycle.sync.SyncEntityIterableReader;
import org.sisioh.dddbase.lifecycle.sync.SyncRepository;

import java.util.List;

/**
 * {@link DataSource}のためのリポジトリ。
 */
public interface DataSourceRepository
        extends SyncRepository<DataSourceRepository, DataSourceId, DataSource>,
        SyncEntityIterableReader<DataSourceId, DataSource> {

    /**
     * エンティティをリストとして取得する。
     *
     * @return {@link DataSource}のリスト
     */
    List<DataSource> toList();

}
