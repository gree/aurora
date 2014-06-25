package net.gree.aurora.scala.domain.tablename

import net.gree.aurora.domain.tablename.{TableName => JTableName}

private[domain]
case class TableNameImpl(underlying: JTableName) extends TableName {

  val name = underlying.getName

  override def toString = Seq(s"name = $name").mkString("TableName(", ", ", ")")

}


