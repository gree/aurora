package net.gree.aurora.scala.domain.clustergroup

import net.gree.aurora.domain.clustergroup.ClusterGroupFactory
import net.gree.aurora.domain.clustergroup.{ClusterGroup => JClusterGroup}
import net.gree.aurora.scala.domain.cluster.{Cluster, ClusterId}
import org.sisioh.dddbase.core.model.Entity
import scala.collection.JavaConverters._
import scala.language.implicitConversions
import scala.util.Try

/**
 * [[net.gree.aurora.domain.clustergroup.ClusterGroup]]のScala版。
 */
trait ClusterGroup extends Entity[ClusterGroupId] {

  /**
   *
   * @param shardId
   * @return
   */
  def resolveCluster(shardId: ClusterId): Try[Cluster]

  def clusters: Try[Seq[Cluster]]

  override def toString =
    Seq(s"clusters = $clusters").mkString("ClusterGroup(", ", ", ")")

}

/**
 * コンパニオンオブジェクト。
 */
object ClusterGroup {

  private[scala] def apply(underlying: JClusterGroup): ClusterGroup =
    new ClusterGroupImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param identity 識別子
   * @param clusters [[net.gree.aurora.scala.domain.cluster.Cluster]]の集合　
   * @return [[net.gree.aurora.scala.domain.clustergroup.ClusterGroup]]
   */
  def apply(identity: ClusterGroupId, clusters: Seq[Cluster]): ClusterGroup =
    apply(ClusterGroupFactory.create(identity, clusters.map(Cluster.toJava).asJava))

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.clustergroup.ClusterGroup]]
   * @return [[net.gree.aurora.domain.clustergroup.ClusterGroup]]
   */
  implicit def toJava(self: ClusterGroup) = self match {
    case s: ClusterGroupImpl => s.underlying
  }

}