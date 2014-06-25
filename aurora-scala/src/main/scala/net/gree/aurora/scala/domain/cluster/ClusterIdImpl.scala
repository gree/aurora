package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.domain.cluster.{ClusterId => JClusterId}

private[domain]
case class ClusterIdImpl(underlying: JClusterId) extends ClusterId {

  def value = underlying.getValue

}
