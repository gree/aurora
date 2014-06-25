package net.gree.aurora.scala.domain.datasource

import java.util.UUID
import net.gree.aurora.domain.datasource.DataSourceType
import org.specs2.mutable.Specification
import org.sisioh.dddbase.core.lifecycle.sync.SyncEntityIOContext

class DataSourceSelectorSpec extends Specification {

  "dataSourceSelector" should {

    "select datasource" in {
      implicit val dataSourceRepository = DataSourceRepository()
      implicit val ctx = SyncEntityIOContext

      val uuids = Seq(UUID.randomUUID(), UUID.randomUUID())
      val dataSource1 = DataSource(DataSourceId(uuids(0)), DataSourceType.SLAVE, "localhost", None)
      val dataSource2 = DataSource(DataSourceId(uuids(1)), DataSourceType.SLAVE, "localhost", None)
      dataSourceRepository.store(dataSource1)
      dataSourceRepository.store(dataSource2)

      val dataSourceIdResolver = new DataSourceIdResolver[Int] {
        def apply(value: Option[Int]): DataSourceId = {
          DataSourceId(uuids(value.getOrElse(0)))
        }
      }

      val dss = DataSourceSelector(dataSourceRepository, dataSourceIdResolver)

      dss.selectDataSource(Some(0)).isSuccess must_== true
      dss.selectDataSource(Some(0)).get must_== dataSource1
    }

    "select datasource as random" in {
      implicit val dataSourceRepository = DataSourceRepository()
      implicit val ctx = SyncEntityIOContext

      val uuids = Seq(UUID.randomUUID(), UUID.randomUUID())
      val dataSource1 = DataSource(DataSourceId(uuids(0)), DataSourceType.SLAVE, "localhost", None)
      val dataSource2 = DataSource(DataSourceId(uuids(1)), DataSourceType.SLAVE, "localhost", None)
      dataSourceRepository.store(dataSource1)
      dataSourceRepository.store(dataSource2)

      val dataSourceIdResolver = DataSourceIdRandomResolver(Seq(dataSource1.identity, dataSource2.identity))
      val dss = DataSourceSelector(dataSourceRepository, dataSourceIdResolver)

      dss.selectDataSource(Some(0)).isSuccess must_== true
      dss.selectDataSource(Some(0)).get must beOneOf(dataSource1, dataSource2)
    }

  }

}
