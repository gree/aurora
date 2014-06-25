package net.gree.aurora.domain.cluster;

import org.sisioh.dddbase.model.Entity;

public interface TaggedClusterIdResolver<HINT> extends Entity<TaggedClusterIdResolverId> {

    ClusterIdResolver<HINT>  getClusterIdResolver();

}
