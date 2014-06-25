package net.gree.aurora.domain.clustergroup;

import org.sisioh.dddbase.lifecycle.sync.SyncEntityIterableReader;
import org.sisioh.dddbase.lifecycle.sync.SyncRepository;

import java.util.List;

/**
 * {@link ClusterGroup}のためのリポジトリ。
 */
public interface ClusterGroupRepository
        extends SyncRepository<ClusterGroupRepository, ClusterGroupId, ClusterGroup>,
        SyncEntityIterableReader<ClusterGroupId, ClusterGroup> {

    /**
     * エンティティをリストとして取得する。
     *
     * @return {@link ClusterGroup}のリスト
     */
    List<ClusterGroup> toList();

}
