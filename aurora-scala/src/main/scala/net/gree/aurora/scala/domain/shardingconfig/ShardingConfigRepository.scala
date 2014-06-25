package net.gree.aurora.scala.domain.shardingconfig

import scala.language.implicitConversions
import net.gree.aurora.domain.shardingconfig.{ShardingConfigRepository => JShardingConfigRepository, ShardingConfigRepositoryFactory}
import org.sisioh.dddbase.core.lifecycle.sync.SyncRepository

/**
 * [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfig]]のためのリポジトリ。
 */
trait ShardingConfigRepository extends SyncRepository[ShardingConfigId, ShardingConfig]

/**
 * コンパニオンオブジェクト。
 */
object ShardingConfigRepository {

  /**
   * JavaオブジェクトからScalaオブジェクトを生成する。
   *
   * @param underlying [[net.gree.aurora.domain.shardingconfig.ShardingConfigRepository]]
   * @return [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigRepository]]
   */
  private[scala] def apply(underlying: JShardingConfigRepository): ShardingConfigRepository =
    new ShardingConfigRepositoryImpl(underlying)

  /**
   * ファクトリメソッド。
   *
   * @return [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigRepository]]
   */
  def apply(): ShardingConfigRepository =
    apply(ShardingConfigRepositoryFactory.create())

  /**
   * ScalaオブジェクトからJavaオブジェクトに変換する。
   *
   * @param self [[net.gree.aurora.scala.domain.shardingconfig.ShardingConfigRepository]]
   * @return [[net.gree.aurora.domain.shardingconfig.ShardingConfigRepository]]
   */
  implicit def toJava(self: ShardingConfigRepository) = self match {
    case s: ShardingConfigRepositoryImpl => s.underlying
  }

}
