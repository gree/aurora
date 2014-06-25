package net.gree.aurora.scala.domain.clustergroup

import net.gree.aurora.domain.clustergroup.ClusterGroupIdFactory
import net.gree.aurora.domain.clustergroup.{ClusterGroupId => JClusterGroupId}
import org.sisioh.dddbase.core.model.Identity
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.scala.domain.clustergroup.ClusterGroup]]のための識別子。
 */
trait ClusterGroupId extends Identity[String] {

  override def toString =
    Seq(s"value = $value").mkString("ClusterGroupId(", ", ", ")")

}

/**
 * コンパニオンオブジェクト。
 */
object ClusterGroupId {

  private[scala] def apply(underlying: JClusterGroupId): ClusterGroupId =
    new ClusterGroupIdImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param value 識別子の値
   * @return [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupId]]
   */
  def apply(value: String): ClusterGroupId =
    apply(ClusterGroupIdFactory.create(value))

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.clustergroup.ClusterGroupId]]
   * @return [[net.gree.aurora.domain.clustergroup.ClusterGroupId]]
   */
  implicit def toJava(self: ClusterGroupId) = self match {
    case s: ClusterGroupIdImpl => s.underlying
  }

}
