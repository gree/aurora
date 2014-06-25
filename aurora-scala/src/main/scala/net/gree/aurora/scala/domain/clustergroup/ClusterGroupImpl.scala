package net.gree.aurora.scala.domain.clustergroup

import java.util.{List => JList}
import net.gree.aurora.domain.clustergroup.{ClusterGroup => JClusterGroup}
import net.gree.aurora.scala.domain.cluster.{Cluster, ClusterId}
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.collection.JavaConverters._
import scala.util.{Failure, Success, Try}

private[domain]
class ClusterGroupImpl
(val underlying: JClusterGroup) extends ClusterGroup {

  val identity = ClusterGroupId(underlying.getIdentity.getValue)

  def resolveCluster(shardId: ClusterId): Try[Cluster] = {
    val result = underlying.resolveCluster(shardId)
    result match {
      case success: JTry.Success[_] =>
        Success(Cluster(success.get().asInstanceOf[Cluster]))
      case failure: JTry[_] =>
        Failure(failure.getCause)
    }
  }

  def clusters: Try[Seq[Cluster]] = {
    if (underlying.getClusters.isSuccess) {
      val clusters = underlying.getClusters.get()
      Success(clusters.asScala.map(Cluster(_)))
    } else {
      Failure(underlying.getClusters.getCause)
    }
  }

}
