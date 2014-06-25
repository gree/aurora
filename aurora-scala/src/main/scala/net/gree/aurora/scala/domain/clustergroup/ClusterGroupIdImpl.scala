package net.gree.aurora.scala.domain.clustergroup

import net.gree.aurora.domain.clustergroup.{ClusterGroupId => JClusterGroupId}

private[domain]
class ClusterGroupIdImpl(val underlying: JClusterGroupId) extends ClusterGroupId {

  def value: String = underlying.getValue

}
