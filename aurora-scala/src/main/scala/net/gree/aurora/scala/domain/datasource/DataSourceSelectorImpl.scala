package net.gree.aurora.scala.domain.datasource

import net.gree.aurora.domain.datasource.{DataSource => JDataSource, JDBCDataSource => JJDBCDataSource, RedisDataSource => JRedisDataSource}
import net.gree.aurora.domain.datasource.{DataSourceSelector => JDataSourceSelector}
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.util.{Failure, Success, Try}

private[domain]
class DataSourceSelectorImpl[T](underlying: JDataSourceSelector[T])
  extends DataSourceSelector[T] {

  private def toJava[T](hint: Option[T]):T = hint match {
    case Some(v) => v
    case None => null.asInstanceOf[T]
  }
  def selectDataSource(hint: Option[T]): Try[DataSource] = {
    val result = underlying.selectDataSource(toJava(hint))
    result match {
      case success: JTry.Success[_] =>
        Success(DataSource(success.get().asInstanceOf[JDataSource]))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

  def selectDataSourceAsJDBC(hint: Option[T]): Try[JDBCDataSource] = {
    val result = underlying.selectDataSourceAsJDBC(toJava(hint))
    result match {
      case success: JTry.Success[_] =>
        Success(JDBCDataSource(success.get().asInstanceOf[JJDBCDataSource]))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

  def selectDataSourceAsRedis(hint: Option[T]): Try[RedisDataSource] = {
    val result = underlying.selectDataSourceAsRedis(toJava(hint))
    result match {
      case success: JTry.Success[_] =>
        Success(RedisDataSource(success.get().asInstanceOf[JRedisDataSource]))
      case failure: JTry.Failure[_] =>
        Failure(failure.getCause)
    }
  }

}
