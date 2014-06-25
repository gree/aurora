package net.gree.aurora.scala.application

import net.gree.aurora.application.{AuroraTableNameService => JAuroraTableNameService}
import net.gree.aurora.domain.tablename.{TableName => JTableName}
import org.sisioh.dddbase.utils.{Try => JTry}
import scala.util.{Failure, Success, Try}
import net.gree.aurora.scala.domain.tablename.TableName

private[application]
class AuroraTableNameServiceImpl[T](underlying: JAuroraTableNameService[T])
  extends AuroraTableNameService[T] {

  def resolveByHint(hint: T): Try[TableName] = {
    val result = underlying.resolveTableNameByHint(hint)
    result match {
      case s: JTry.Success[_] =>
        val tableName = s.get().asInstanceOf[JTableName]
        Success(TableName(tableName))
      case f: JTry.Failure[_] =>
        val cause = f.getCause
        Failure(cause)
    }
  }

}
