package net.gree.aurora.domain.cluster;

import net.gree.aurora.domain.AbstractSyncRepositoryOnMemory;

final class TaggedClusterIdResolverRepositoryImpl<HINT>
        extends AbstractSyncRepositoryOnMemory<TaggedClusterIdResolverRepository<HINT>, TaggedClusterIdResolverId, TaggedClusterIdResolver<HINT>>
        implements TaggedClusterIdResolverRepository<HINT> {
}
