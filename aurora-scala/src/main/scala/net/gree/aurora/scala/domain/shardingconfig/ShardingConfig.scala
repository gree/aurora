package net.gree.aurora.scala.domain.shardingconfig

import net.gree.aurora.domain.shardingconfig.{ShardingConfig => JShardingConfig, ShardingConfigType, ShardingConfigFactory}
import net.gree.aurora.scala.domain.clustergroup.ClusterGroup
import org.sisioh.dddbase.core.model.Entity
import scala.collection.JavaConverters._
import scala.language.implicitConversions

/**
 * [[net.gree.aurora.domain.shardingconfig.ShardingConfig]]のScala版。
 */
trait ShardingConfig extends Entity[ShardingConfigId]

/**
 * コンパニオンオブジェクト。
 */
object ShardingConfig {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.shardingconfig.ShardingConfig]]
   * @return [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfig]]
   */
  private[scala] def apply(underlying: JShardingConfig): ShardingConfig =
    new ShardingConfigImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @param shardingConfigId  [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigId]]
   * @param configType [[net.gree.aurora.domain.shardingconfig.ShardingConfigType]]
   * @param clusterGroups [[net.gree.aurora.scala.domain.clustergroup.ClusterGroup]]の集合
   * @return [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfig]]
   */
  def apply(shardingConfigId: ShardingConfigId,
            configType: ShardingConfigType,
            clusterGroups: Seq[ClusterGroup]): ShardingConfig =
    apply(
      ShardingConfigFactory.create(shardingConfigId, configType, clusterGroups.map(ClusterGroup.toJava).toSet.asJava)
    )

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfig]]
   * @return [[net.gree.aurora.domain.shardingconfig.ShardingConfig]]
   */
  implicit def toJava(self: ShardingConfig) = self match {
    case s: ShardingConfigImpl => s.underlying
  }

}

