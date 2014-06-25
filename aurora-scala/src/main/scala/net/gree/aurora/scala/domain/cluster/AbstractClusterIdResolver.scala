package net.gree.aurora.scala.domain.cluster

import net.gree.aurora.scala.domain.hint.Hint

/**
 * [[net.gree.aurora.scala.domain.cluster.ClusterIdResolver]]のための骨格実装。
 *
 * @param idBaseName 基準となる識別子名
 * @tparam T ヒントの型
 */
abstract class AbstractClusterIdResolver[T](idBaseName: String) extends ClusterIdResolver[T] {

  /**
   * ヒントから識別子の末尾(サフィックス)となる名前を取得する。
   *
   * @param hint ヒント
   * @return サフィックス
   */
  protected def getSuffixName(hint: Hint[T], clusterSize: Int): String

  def apply(hint: Hint[T], clusterSize: Int): ClusterId =
    ClusterId(idBaseName + getSuffixName(hint, clusterSize))

}
