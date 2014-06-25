package net.gree.aurora.scala.domain.hint

import net.gree.aurora.domain.hint.{Hint => JHint}

case class HintImpl[T](underlying: JHint[T]) extends Hint[T] {
  val tag: Option[Any] = Option(underlying.getTag)
  val value: T = underlying.getValue
}

