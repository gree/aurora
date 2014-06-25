package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.domain.cluster.{TaggedClusterIdResolverId => JTaggedClusterIdResolverId, TaggedClusterIdResolverIdFactory}
import org.sisioh.dddbase.core.model.Identity

trait TaggedClusterIdResolverId extends Identity[Any]

object TaggedClusterIdResolverId {

  def apply(underlying: JTaggedClusterIdResolverId): TaggedClusterIdResolverId =
    new TaggedClusterIdResolverIdImpl(underlying)

  def apply(value: Any): TaggedClusterIdResolverId =
    apply(TaggedClusterIdResolverIdFactory.create(value))

}
