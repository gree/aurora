package net.gree.aurora.scala.domain.clustergroup

import net.gree.aurora.domain.clustergroup.ClusterGroupRepositoryFactory
import net.gree.aurora.domain.clustergroup.{ClusterGroupRepository => JClusterGroupRepository}
import org.sisioh.dddbase.core.lifecycle.sync.SyncRepository
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.scala.domain.clustergroup.ClusterGroup]]のためのリポジトリ。
 */
trait ClusterGroupRepository extends SyncRepository[ClusterGroupId, ClusterGroup]

/**
 * コンパニオンオブジェクト。
 */
object ClusterGroupRepository {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.clustergroup.ClusterGroupRepository]]
   * @return [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupRepository]]
   */
  private[scala] def apply(underlying: JClusterGroupRepository): ClusterGroupRepository =
    new ClusterGroupRepositoryImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @return [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupRepository]]
   */
  def apply(): ClusterGroupRepository =
    apply(ClusterGroupRepositoryFactory.create())

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupRepository]]
   * @return [[net.gree.aurora.domain.clustergroup.ClusterGroupRepository]]
   */
  implicit def toJava(self: ClusterGroupRepository) = self match {
    case s: ClusterGroupRepositoryImpl => s.underlying
  }

}
