package net.gree.aurora.scala.application

import net.gree.aurora.SpecSupport
import net.gree.aurora.scala.domain.tablename.AbstractTableNameResolver
import org.specs2.mutable.Specification

class AuroraTableNameServiceImplSpec extends Specification with SpecSupport {

  "auroraTableNameService" should {
    val tableNameResolver = new AbstractTableNameResolver[Int]("user") {
      protected def getSuffixName(userId: Int) = (userId % 3).toString
    }
    "be able to resolve tableName" in {
      val atns = AuroraTableNameService(tableNameResolver, loadConfig("test1"))
      val tableName = atns.resolveByHint(0).get
      tableName.name must_== "user0"
    }
  }
}
