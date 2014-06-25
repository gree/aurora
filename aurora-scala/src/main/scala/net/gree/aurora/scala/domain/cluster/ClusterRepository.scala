package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.domain.cluster.ClusterRepositoryFactory
import net.gree.aurora.domain.cluster.{ClusterRepository => JClusterRepository}
import org.sisioh.dddbase.core.lifecycle.sync.SyncRepository
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.domain.cluster.ClusterRepository]]のScala版。
 */
trait ClusterRepository extends SyncRepository[ClusterId, Cluster]

/**
 * コンパニオンオブジェクト。
 */
object ClusterRepository {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.cluster.ClusterRepository]]
   * @return [[net.gree.aurora.scala.domain.cluster.ClusterRepository]]
   */
  private[scala] def apply(underlying: JClusterRepository): ClusterRepository =
    new ClusterRepositoryImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @return [[net.gree.aurora.scala.domain.cluster.ClusterRepository]]
   */
  def apply(): ClusterRepository =
    apply(ClusterRepositoryFactory.create())

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.cluster.ClusterRepository]]
   * @return [[net.gree.aurora.domain.cluster.ClusterRepository]]
   */
  implicit def toJava(self: ClusterRepository) = self match {
    case s: ClusterRepositoryImpl => s.underlying
  }

}
