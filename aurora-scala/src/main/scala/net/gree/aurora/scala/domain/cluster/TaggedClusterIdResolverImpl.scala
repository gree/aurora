package net.gree.aurora.scala.domain.cluster


import net.gree.aurora.domain.cluster.{TaggedClusterIdResolver => JTaggedClusterIdResolver}

class TaggedClusterIdResolverImpl[HINT](val underlying: JTaggedClusterIdResolver[HINT])
  extends TaggedClusterIdResolver[HINT] {
  val identity: TaggedClusterIdResolverId = TaggedClusterIdResolverId(underlying.getIdentity.getValue)
  val clusterIdResolver: ClusterIdResolver[HINT] = ClusterIdResolver(underlying.getClusterIdResolver)
}
