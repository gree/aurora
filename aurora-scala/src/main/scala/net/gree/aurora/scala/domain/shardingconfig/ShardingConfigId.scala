package net.gree.aurora.scala.domain.shardingconfig

import scala.language.implicitConversions
import net.gree.aurora.domain.shardingconfig.ShardingConfigIdFactory
import net.gree.aurora.domain.shardingconfig.{ShardingConfigId => JShardingConfigId}
import org.sisioh.dddbase.core.model.Identity

/**
 * [[net.gree.aurora.domain.shardingconfig.ShardingConfigId]]のScala版。
 */
trait ShardingConfigId extends Identity[String]

/**
 * コンパニオンオブジェクト。
 */
object ShardingConfigId {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.shardingconfig.ShardingConfigId]]
   * @return [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigId]]
   */
  private[scala] def apply(underlying: JShardingConfigId): ShardingConfigId =
    new ShardingConfigIdImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param value 識別子の値
   * @return [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigId]]
   */
  def apply(value: String): ShardingConfigId =
    apply(
      ShardingConfigIdFactory.create(value)
    )

  /**
   * ScalaオブジェクトをJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigId]]
   * @return [[net.gree.aurora.domain.shardingconfig.ShardingConfigId]]
   */
  implicit def toJava(self: ShardingConfigId) = self match {
    case s: ShardingConfigIdImpl => s.underlying
  }

}


