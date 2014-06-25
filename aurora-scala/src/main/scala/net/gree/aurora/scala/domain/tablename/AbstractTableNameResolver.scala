package net.gree.aurora.scala.domain.tablename

/**
 * [[net.gree.aurora.scala.domain.tablename.TableNameResolver]]の骨格実装。
 *
 * @param tableBaseName テーブル名の基準名
 * @tparam T ヒントの型
 */
abstract class AbstractTableNameResolver[T](tableBaseName: String) extends TableNameResolver[T] {

  /**
   * ヒントから識別子の末尾(サフィックス)となる名前を取得する。
   *
   * @param hint ヒント
   * @return サフィックス
   */
  protected def getSuffixName(hint: T): String

  def apply(hint: T): TableName =
    TableName(tableBaseName + getSuffixName(hint))

}
