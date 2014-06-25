package net.gree.aurora.scala.domain.hint

import scala.language.implicitConversions
import net.gree.aurora.domain.hint.{Hint => JHint, HintFactory}

trait Hint[T] {

  val tag: Option[Any]

  val value: T

}

object Hint {

  def apply[T](underlying: JHint[T]) = new HintImpl[T](underlying)

  def apply[T](value: T, tag: Option[Any] = None): Hint[T] = apply(HintFactory.create(value, tag.orNull))

  implicit def toJava[T](self: Hint[T]): JHint[T] = self match {
    case s: HintImpl[T] => s.underlying
  }

}