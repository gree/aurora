package net.gree.aurora.scala.domain

import org.sisioh.dddbase.core.lifecycle.EntityNotFoundException
import org.sisioh.dddbase.lifecycle.exception.EntityNotFoundRuntimeException
import org.sisioh.dddbase.utils.{Try => JTry}

trait RepositorySupport {

  protected def recoverFailure(f: JTry.Failure[_]) = {
    val cause = f.getCause
    if (cause.isInstanceOf[EntityNotFoundRuntimeException]) {
      new EntityNotFoundException(None, Some(cause))
    } else {
      cause
    }
  }

}
