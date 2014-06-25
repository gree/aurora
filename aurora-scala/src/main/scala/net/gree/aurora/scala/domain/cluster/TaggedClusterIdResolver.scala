package net.gree.aurora.scala.domain.cluster

import scala.language.implicitConversions
import net.gree.aurora.domain.cluster.{TaggedClusterIdResolver => JTaggedClusterIdResolver, TaggedClusterIdResolverFactory, TaggedClusterIdResolverIdFactory}
import org.sisioh.dddbase.core.model.Entity

trait TaggedClusterIdResolver[HINT] extends Entity[TaggedClusterIdResolverId] {

  val clusterIdResolver: ClusterIdResolver[HINT]

}

object TaggedClusterIdResolver {

  def apply[HINT](underlying: JTaggedClusterIdResolver[HINT]): TaggedClusterIdResolver[HINT] =
    new TaggedClusterIdResolverImpl(underlying)

  def apply[HINT](identity: TaggedClusterIdResolverId, clusterIdResolver: ClusterIdResolver[HINT]): TaggedClusterIdResolver[HINT] = {
    val id = TaggedClusterIdResolverIdFactory.create(identity.value)
    val java = TaggedClusterIdResolverFactory.create(id, clusterIdResolver)
    apply(java)
  }

  implicit def toJava[HINT](self: TaggedClusterIdResolver[HINT]): JTaggedClusterIdResolver[HINT] = self match {
    case s: TaggedClusterIdResolverImpl[HINT] => s.underlying
  }
}
