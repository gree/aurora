package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{DataSourceId => JDataSourceId, DataSource => JDataSource, JDBCDataSource => JJDBCDataSource, RedisDataSource => JRedisDataSource, DataSourceRepository => JDataSourceRepository}
import org.sisioh.dddbase.core.lifecycle.EntityIOContext
import org.sisioh.dddbase.core.lifecycle.sync.SyncResultWithEntity
import org.sisioh.dddbase.lifecycle.sync.{SyncResultWithEntity => JSyncResultWithEntity}
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.util.{Failure, Success, Try}
import scala.collection.JavaConverters._
import net.gree.aurora.scala.domain.RepositorySupport

private[domain]
class DataSourceRepositoryImpl(val underlying: JDataSourceRepository)
  extends DataSourceRepository with RepositorySupport {

  type This = DataSourceRepository

  def store(entity: DataSource)(implicit ctx: EntityIOContext[Try]) = {
    val result = underlying.store(entity)
    result match {
      case s: JTry.Success[_] =>
        val resultWithEntity = result.get.asInstanceOf[JSyncResultWithEntity[_, JDataSourceId, JDataSource]]
        val entity = resultWithEntity.getEntity
        Success(SyncResultWithEntity[This, DataSourceId, DataSource](this, DataSource(entity)))
      case f: JTry.Failure[_] =>
        Failure(f.getCause)
    }
  }

  def deleteByIdentity(identity: DataSourceId)(implicit ctx: EntityIOContext[Try]) = {
    val result = underlying.delete(identity)
    result match {
      case s: JTry.Success[_] =>
        val resultWithEntity = result.get.asInstanceOf[JSyncResultWithEntity[_, JDataSourceId, JDataSource]]
        val entity = resultWithEntity.getEntity
        Success(SyncResultWithEntity[This, DataSourceId, DataSource](this, DataSource(entity)))
      case f: JTry.Failure[_] =>
        val result = recoverFailure(f)
        Failure(result)
    }
  }

  def resolve(identity: DataSourceId)(implicit ctx: EntityIOContext[Try]) = {
    val result = underlying.resolve(identity)
    result match {
      case s: JTry.Success[_] =>
        s.get match {
          case ds: JJDBCDataSource => Success(JDBCDataSource(ds))
          case ds: JRedisDataSource => Success(RedisDataSource(ds))
          case ds: JDataSource => Success(DataSource(ds))
        }
      case f: JTry.Failure[_] =>
        val result = recoverFailure(f)
        Failure(result)
    }
  }

  def iterator: Iterator[DataSource] = {
    underlying.toList.asScala.map {
      case ds: JJDBCDataSource => JDBCDataSource(ds)
      case ds: JRedisDataSource => RedisDataSource(ds)
      case ds: JDataSource => DataSource(ds)
    }.iterator
  }

}
