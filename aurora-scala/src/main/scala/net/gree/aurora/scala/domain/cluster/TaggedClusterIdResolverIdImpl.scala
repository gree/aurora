package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.domain.cluster.{TaggedClusterIdResolverId => JTaggedClusterIdResolverId}

class TaggedClusterIdResolverIdImpl(val underlying: JTaggedClusterIdResolverId) extends TaggedClusterIdResolverId {
  def value: Any = underlying.getValue
}
