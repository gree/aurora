package net.gree.aurora.domain.cluster;

import org.sisioh.dddbase.lifecycle.sync.SyncEntityIterableReader;
import org.sisioh.dddbase.lifecycle.sync.SyncRepository;

import java.util.List;

/**
 * {@link Cluster}のためのリポジトリ。
 */
public interface ClusterRepository
        extends SyncRepository<ClusterRepository, ClusterId, Cluster>,
        SyncEntityIterableReader<ClusterId, Cluster> {

    /**
     * エンティティをリストとして取得する。
     *
     * @return {@link Cluster}のリスト
     */
    List<Cluster> toList();

}
