package net.gree.aurora.scala.application

import net.gree.aurora.SpecSupport
import net.gree.aurora.scala.domain.cluster.{TaggedClusterIdResolverId, TaggedClusterIdResolver, AbstractClusterIdResolver}
import net.gree.aurora.scala.domain.hint.Hint
import org.sisioh.dddbase.core.lifecycle.sync.SyncEntityIOContext
import org.specs2.mutable.Specification

class AuroraShardingServiceImplSpec extends Specification with SpecSupport {

  "AuroraShardingServiceImpl" should {
    "be able to resolve cluster" in {
      val clusterIdResolver = new AbstractClusterIdResolver[Int]("cluster") {
        protected def getSuffixName(userIdHint: Hint[Int], clusterSize: Int) = {
          (((userIdHint.value + 1) % 2) + 1).toString
        }
      }
      def resolveClusterByHint(service: AuroraShardingService[Int], userId: Hint[Int]) = {
        service.resolveClusterByHint("database", "main", userId)
      }

      val service = AuroraShardingService(clusterIdResolver, loadConfig("forJDBC"))

      implicit val dataSourceRepository = service.dataSourceRepository.get
      implicit val ctx = SyncEntityIOContext

      resolveClusterByHint(service, Hint(1)) must beSuccessfulTry.like {
        case cluster =>
          cluster.identity.value must_== "cluster1"
      }
      resolveClusterByHint(service, Hint(2)) must beSuccessfulTry.like {
        case cluster =>
          cluster.identity.value must_== "cluster2"
      }
      resolveClusterByHint(service, Hint(3)) must beSuccessfulTry.like {
        case cluster =>
          cluster.identity.value must_== "cluster1"
      }
    }
    "be able to resolve cluster by tag" in {
      val clusterIdResolver = new AbstractClusterIdResolver[Int]("cluster") {
        protected def getSuffixName(userIdHint: Hint[Int], clusterSize: Int) = {
          (((userIdHint.value + 1) % 2) + 1).toString
        }
      }

      val tcir = TaggedClusterIdResolver[Int](TaggedClusterIdResolverId("test"), clusterIdResolver)

      def resolveClusterByHint(service: AuroraShardingService[Int], userId: Hint[Int]) = {
        service.resolveClusterByHint("database", "main", userId)
      }

      val service = AuroraShardingService(Set(tcir), loadConfig("forJDBC"))

      implicit val dataSourceRepository = service.dataSourceRepository.get
      implicit val ctx = SyncEntityIOContext

      resolveClusterByHint(service, Hint(1, Some("test"))) must beSuccessfulTry.like {
        case cluster =>
          cluster.identity.value must_== "cluster1"
      }
      resolveClusterByHint(service, Hint(2, Some("test"))) must beSuccessfulTry.like {
        case cluster =>
          cluster.identity.value must_== "cluster2"
      }
      resolveClusterByHint(service, Hint(3, Some("test"))) must beSuccessfulTry.like {
        case cluster =>
          cluster.identity.value must_== "cluster1"
      }
    }
    "be able to resolve cluster as multiple instance" in {
      def resolveClusterByHint[T](service: AuroraShardingService[T], userId: Hint[T]) = {
        service.resolveClusterByHint("database", "main", userId)
      }

      {
        val clusterIdResolver1 = new AbstractClusterIdResolver[Int]("cluster") {
          protected def getSuffixName(userIdHint: Hint[Int], clusterSize: Int) = {
            (((userIdHint.value + 1) % 2) + 1).toString
          }
        }
        val service1 = AuroraShardingService(clusterIdResolver1, loadConfig("forJDBC"))
        implicit val dataSourceRepository = service1.dataSourceRepository.get
        implicit val ctx = SyncEntityIOContext

        resolveClusterByHint(service1, Hint(1)) must beSuccessfulTry.like {
          case cluster =>
            cluster.identity.value must_== "cluster1"
        }
        resolveClusterByHint(service1, Hint(2)) must beSuccessfulTry.like {
          case cluster =>
            cluster.identity.value must_== "cluster2"
        }
        resolveClusterByHint(service1, Hint(3)) must beSuccessfulTry.like {
          case cluster =>
            cluster.identity.value must_== "cluster1"
        }
      }
      {
        val clusterIdResolver2 = new AbstractClusterIdResolver[String]("cluster") {
          protected def getSuffixName(userIdHint: Hint[String], clusterSize: Int) = {
            (((userIdHint.value.toInt + 1) % 2) + 1).toString
          }
        }
        val service2 = AuroraShardingService(clusterIdResolver2, loadConfig("forJDBC"))
        implicit val dataSourceRepository = service2.dataSourceRepository.get
        implicit val ctx = SyncEntityIOContext

        resolveClusterByHint(service2, Hint("1")) must beSuccessfulTry.like {
          case cluster =>
            cluster.identity.value must_== "cluster1"
        }
        resolveClusterByHint(service2, Hint("2")) must beSuccessfulTry.like {
          case cluster =>
            cluster.identity.value must_== "cluster2"
        }
        resolveClusterByHint(service2, Hint("3")) must beSuccessfulTry.like {
          case cluster =>
            cluster.identity.value must_== "cluster1"
        }
      }

    }
  }

}
