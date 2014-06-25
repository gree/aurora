package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.domain.cluster.{Cluster => JCluster}
import net.gree.aurora.domain.datasource.{DataSourceSelector => JDataSourceSelector}
import net.gree.aurora.scala.domain.datasource._
import org.sisioh.dddbase.core.lifecycle.EntityIOContext
import scala.collection.JavaConverters._
import scala.util._

private[domain]
class ClusterImpl(val underlying: JCluster) extends Cluster {

  val identity = ClusterId(underlying.getIdentity)

  val masterDataSourceId = DataSourceId(underlying.getMasterDataSourceId)

  val slaveDataSourceIds = underlying.getSlaveDataSourceIds.asScala.toList.map(DataSourceId(_))

  def createDataSourceSelector[T](dataSourceResolver: DataSourceIdResolver[T])
                                 (implicit dataSourceRepository: DataSourceRepository): DataSourceSelector[T] =
    DataSourceSelector(underlying.createDataSourceSelector(dataSourceRepository, dataSourceResolver))

  def createDataSourceSelectorAsRandom(implicit dataSourceRepository: DataSourceRepository) = {
    DataSourceSelector(dataSourceRepository, DataSourceIdRandomResolver(slaveDataSourceIds))
  }

  def getMasterDataSource(implicit repository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[DataSource] = {
    repository.resolve(masterDataSourceId)
  }

  def getMasterDataSourceAsJDBC(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[JDBCDataSource] = {
    getMasterDataSource.flatMap {
      case ds: JDBCDataSource =>
        Success(ds)
      case ds =>
        Failure(new MatchError(ds))
    }
  }

  def getMasterDataSourceAsRedis(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[RedisDataSource] = {
    getMasterDataSource.flatMap {
      case ds: RedisDataSource =>
        Success(ds)
      case ds =>
        Failure(new MatchError(ds))
    }
  }

  def getSlaveDataSources(implicit repository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[Seq[DataSource]] =
    repository.resolves(slaveDataSourceIds)

  def getSlaveDataSourcesAsJDBC(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[Seq[DataSource]] = {
    getSlaveDataSources.map {
      dss =>
        dss.map {
          case ds: JDBCDataSource => ds
          case ds => throw new MatchError(ds)
        }
    }
  }

  def getSlaveDataSourcesAsRedis(implicit dataSourceRepository: DataSourceRepository, ctx: EntityIOContext[Try]): Try[Seq[DataSource]] = ???
}

