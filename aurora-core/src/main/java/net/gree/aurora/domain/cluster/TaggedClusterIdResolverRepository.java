package net.gree.aurora.domain.cluster;

import org.sisioh.dddbase.lifecycle.sync.SyncEntityIterableReader;
import org.sisioh.dddbase.lifecycle.sync.SyncRepository;

public interface TaggedClusterIdResolverRepository<HINT>
        extends SyncRepository<TaggedClusterIdResolverRepository<HINT>, TaggedClusterIdResolverId, TaggedClusterIdResolver<HINT>>,
        SyncEntityIterableReader<TaggedClusterIdResolverId, TaggedClusterIdResolver<HINT>> {
}
