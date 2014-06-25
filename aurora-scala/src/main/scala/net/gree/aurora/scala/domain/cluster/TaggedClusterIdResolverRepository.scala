package net.gree.aurora.scala.domain.cluster

import org.sisioh.dddbase.core.lifecycle.sync.SyncRepository

trait TaggedClusterIdResolverRepository[HINT]
  extends SyncRepository[TaggedClusterIdResolverId, TaggedClusterIdResolver[HINT]] {

}
