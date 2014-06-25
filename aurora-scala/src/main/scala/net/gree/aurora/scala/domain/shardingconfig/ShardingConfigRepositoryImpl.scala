package net.gree.aurora.scala.domain.shardingconfig

import net.gree.aurora.domain.shardingconfig.{ShardingConfigRepository => JShardingConfigRepository}
import net.gree.aurora.scala.domain.RepositorySupport
import org.sisioh.dddbase.core.lifecycle.EntityIOContext
import org.sisioh.dddbase.core.lifecycle.sync.SyncResultWithEntity
import org.sisioh.dddbase.lifecycle.sync.{SyncResultWithEntity => JSyncResultWithEntity}
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.language.existentials
import scala.util.{Failure, Success, Try}


class ShardingConfigRepositoryImpl(val underlying: JShardingConfigRepository)
  extends ShardingConfigRepository with RepositorySupport {

  type This = ShardingConfigRepository

  def store(entity: ShardingConfig)
           (implicit ctx: EntityIOContext[Try]): Try[SyncResultWithEntity[This, ShardingConfigId, ShardingConfig]] = {
    val result = underlying.store(entity)
    result match {
      case s: JTry.Success[_] =>
        val resultWithEntity = result.get.asInstanceOf[JSyncResultWithEntity[_, _, _]]
        val entity = resultWithEntity.getEntity.asInstanceOf[ShardingConfig]
        Success(SyncResultWithEntity[This, ShardingConfigId, ShardingConfig](this, ShardingConfig(entity)))
      case f: JTry.Failure[_] =>
        Failure(f.getCause)
    }
  }

  def deleteByIdentity(identity: ShardingConfigId)
                      (implicit ctx: EntityIOContext[Try]): Try[SyncResultWithEntity[This, ShardingConfigId, ShardingConfig]] = {
    val result = underlying.delete(identity)
    result match {
      case s: JTry.Success[_] =>
        val resultWithEntity = result.get.asInstanceOf[JSyncResultWithEntity[_, _, _]]
        val entity = resultWithEntity.getEntity.asInstanceOf[ShardingConfig]
        Success(SyncResultWithEntity[This, ShardingConfigId, ShardingConfig](this, ShardingConfig(entity)))
      case f: JTry.Failure[_] =>
        val result = recoverFailure(f)
        Failure(result)
    }
  }

  def resolve(identity: ShardingConfigId)
             (implicit ctx: EntityIOContext[Try]): Try[ShardingConfig] = {
    val result = underlying.resolve(identity)
    result match {
      case s: JTry.Success[_] =>
        val entity = s.get.asInstanceOf[ShardingConfig]
        Success(ShardingConfig(entity))
      case f: JTry.Failure[_] =>
        val result = recoverFailure(f)
        Failure(result)
    }
  }

  def containsByIdentity(identity: ShardingConfigId)
                        (implicit ctx: EntityIOContext[Try]): Try[Boolean] = {
    val result = underlying.contains(identity)
    result match {
      case s: JTry.Success[_] =>
        Success(s.get.asInstanceOf[Boolean])
      case f: JTry.Failure[_] =>
        val result = recoverFailure(f)
        Failure(result)
    }
  }

}
