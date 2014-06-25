package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.domain.cluster.ClusterIdFactory
import net.gree.aurora.domain.cluster.{ClusterId => JClusterId}
import org.sisioh.dddbase.core.model.Identity
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.scala.domain.cluster.Cluster]]のための識別子。
 */
trait ClusterId extends Identity[String] {

  override def toString =
    Seq(s"value = $value").mkString("ClusterId(", ", ", ")")

}

/**
 * コンパニオンオブジェクト。
 */
object ClusterId {

  private[scala] def apply(underlying: JClusterId): ClusterId =
    new ClusterIdImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param value 識別子の値
   * @return [[net.gree.aurora.scala.domain.cluster.ClusterId]]
   */
  def apply(value: String): ClusterId =
    apply(ClusterIdFactory.create(value))

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.cluster.ClusterId]]
   * @return [[net.gree.aurora.domain.cluster.ClusterId]]
   */
  implicit def toJava(self: ClusterId) = self match {
    case s: ClusterIdImpl => s.underlying
  }

}
