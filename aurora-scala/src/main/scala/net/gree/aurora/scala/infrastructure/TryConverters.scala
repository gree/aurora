package net.gree.aurora.scala.infrastructure

import org.sisioh.dddbase.utils.{Try => JTry}
import scala.util.{Failure, Success, Try}

object TryConverters {

  implicit class JTryOps[T](val tryA: JTry[T]) extends AnyVal {
    def toScala: Try[T] = {
      tryA match {
        case s: JTry.Success[T] => Success(s.get)
        case f: JTry.Failure[T] => Failure(f.getCause)
      }
    }
  }

  implicit class STryOps[T](val tryA: Try[T]) extends AnyVal {
    def toJava: JTry[T] = {
      tryA match {
        case Success(v) => JTry.ofSuccess(v)
        case Failure(e) => JTry.ofFailure(e.asInstanceOf[RuntimeException])
      }
    }
  }

}
