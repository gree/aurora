package net.gree.aurora.scala.domain.cluster

import scala.language.implicitConversions
import net.gree.aurora.domain.cluster.{ClusterIdResolver => JClusterIdResolver}
import net.gree.aurora.domain.hint.{Hint => JHint}
import net.gree.aurora.scala.domain.hint.Hint
trait ClusterIdResolver[T] extends ((Hint[T], Int) => ClusterId) {

  def apply(value: Hint[T], clusterSize: Int): ClusterId

}


object ClusterIdResolver {

  def apply[T](f: JClusterIdResolver[T]): ClusterIdResolver[T] = {
    new ClusterIdResolver[T] {
      def apply(hint: Hint[T], clusterSize: Int): ClusterId = ClusterId(f.apply(hint, clusterSize))
    }
  }


  implicit def toJava[T](shardIdResolver: ClusterIdResolver[T]) = {
    new JClusterIdResolver[T] {
      def apply(hint: JHint[T], clusterSize: Int) = shardIdResolver(Hint(hint), clusterSize)
    }
  }

}
