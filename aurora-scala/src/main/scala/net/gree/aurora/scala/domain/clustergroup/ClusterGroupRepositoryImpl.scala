package net.gree.aurora.scala.domain.clustergroup

import net.gree.aurora.domain.clustergroup.{ClusterGroupRepository => JClusterGroupRepository}
import net.gree.aurora.scala.domain.RepositorySupport
import org.sisioh.dddbase.core.lifecycle.EntityIOContext
import org.sisioh.dddbase.core.lifecycle.sync.SyncResultWithEntity
import org.sisioh.dddbase.lifecycle.sync.{SyncResultWithEntity => JSyncResultWithEntity}
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.language.existentials
import scala.util.{Failure, Success, Try}

private[domain]
class ClusterGroupRepositoryImpl(val underlying: JClusterGroupRepository)
  extends ClusterGroupRepository with RepositorySupport {

  type This = ClusterGroupRepository

  def store(entity: ClusterGroup)
           (implicit ctx: EntityIOContext[Try]): Try[SyncResultWithEntity[This, ClusterGroupId, ClusterGroup]] = {
    val result = underlying.store(entity)
    result match {
      case success: JTry.Success[_] =>
        val resultWithEntity = success.get.asInstanceOf[JSyncResultWithEntity[_, _, _]]
        val entity = resultWithEntity.getEntity.asInstanceOf[ClusterGroup]
        Success(SyncResultWithEntity[This, ClusterGroupId, ClusterGroup](this, ClusterGroup(entity)))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

  def deleteByIdentity(identity: ClusterGroupId)
                      (implicit ctx: EntityIOContext[Try]): Try[SyncResultWithEntity[This, ClusterGroupId, ClusterGroup]] = {
    val result = underlying.delete(identity)
    result match {
      case success: JTry.Success[_] =>
        val resultWithEntity = success.get.asInstanceOf[JSyncResultWithEntity[_, _, _]]
        val entity = resultWithEntity.getEntity.asInstanceOf[ClusterGroup]
        Success(SyncResultWithEntity[This, ClusterGroupId, ClusterGroup](this, ClusterGroup(entity)))
      case failure: JTry.Failure[_] =>
        val result = recoverFailure(failure)
        Failure(result)
    }
  }

  def resolve(identity: ClusterGroupId)
             (implicit ctx: EntityIOContext[Try]): Try[ClusterGroup] = {
    val result = underlying.resolve(identity)
    result match {
      case success: JTry.Success[_] =>
        val entity = success.get.asInstanceOf[ClusterGroup]
        Success(ClusterGroup(entity))
      case failure: JTry.Failure[_] =>
        val result = recoverFailure(failure)
        Failure(result)
    }
  }

  def containsByIdentity(identity: ClusterGroupId)
                        (implicit ctx: EntityIOContext[Try]): Try[Boolean] = {
    val result = underlying.contains(identity)
    result match {
      case success: JTry.Success[_] =>
        Success(success.get.asInstanceOf[Boolean])
      case failure: JTry.Failure[_] =>
        val result = recoverFailure(failure)
        Failure(result)
    }
  }

}
