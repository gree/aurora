package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.domain.cluster.{ClusterRepository => JClusterRepository}
import org.sisioh.dddbase.core.lifecycle.{EntityNotFoundException, EntityIOContext}
import org.sisioh.dddbase.core.lifecycle.sync.SyncResultWithEntity
import org.sisioh.dddbase.lifecycle.sync.{SyncResultWithEntity => JSyncResultWithEntity}
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.util.{Failure, Success, Try}
import org.sisioh.dddbase.lifecycle.exception.EntityNotFoundRuntimeException
import net.gree.aurora.scala.domain.RepositorySupport
import scala.language.existentials

private[domain]
class ClusterRepositoryImpl(val underlying: JClusterRepository)
  extends ClusterRepository with RepositorySupport {

  type This = ClusterRepository

  def store(entity: Cluster)(implicit ctx: EntityIOContext[Try]): Try[SyncResultWithEntity[This, ClusterId, Cluster]] = {
    val result = underlying.store(entity)
    result match {
      case s: JTry.Success[_] =>
        val resultWithEntity = s.get.asInstanceOf[JSyncResultWithEntity[_, _, _]]
        val entity = resultWithEntity.getEntity.asInstanceOf[Cluster]
        Success(SyncResultWithEntity[This, ClusterId, Cluster](this, Cluster(entity)))
      case f: JTry.Failure[_] =>
        Failure(f.getCause)
    }
  }



  def deleteByIdentity(identity: ClusterId)(implicit ctx: EntityIOContext[Try]): Try[SyncResultWithEntity[This, ClusterId, Cluster]] = {
    val result = underlying.delete(identity)
    result match {
      case s: JTry.Success[_] =>
        val resultWithEntity = s.get.asInstanceOf[JSyncResultWithEntity[_, _, _]]
        val entity = resultWithEntity.getEntity.asInstanceOf[Cluster]
        Success(SyncResultWithEntity[This, ClusterId, Cluster](this, Cluster(entity)))
      case f: JTry.Failure[_] =>
        val result = recoverFailure(f)
        Failure(result)
    }
  }

  def resolve(identity: ClusterId)(implicit ctx: EntityIOContext[Try]): Try[Cluster] = {
    val result = underlying.resolve(identity)
    result match {
      case s: JTry.Success[_] =>
        val entity = s.get.asInstanceOf[Cluster]
        Success(Cluster(entity))
      case f: JTry.Failure[_] =>
        val result = recoverFailure(f)
        Failure(result)
    }
  }

  def containsByIdentity(identity: ClusterId)(implicit ctx: EntityIOContext[Try]): Try[Boolean] = {
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
