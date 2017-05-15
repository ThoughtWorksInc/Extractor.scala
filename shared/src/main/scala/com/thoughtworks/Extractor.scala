package com.thoughtworks

private[thoughtworks] sealed trait LowLowPriorityExtractor {

  implicit final class FunctionToExtractor[-A, +B] private[LowLowPriorityExtractor] (underlying: A => B) {
    def extract = new Extractor[A, B] {
      override def unapply(a: A): Some[B] = {
        Some(underlying(a))
      }
    }
  }

}

private[thoughtworks] sealed trait LowPriorityExtractor extends LowLowPriorityExtractor {

  sealed trait SeqExtractor[-A, +B] {
    def unapplySeq(a: A): Option[Seq[B]]
  }

  implicit final class OptionFunctionToSeqExtractor[-A, +B] private[LowPriorityExtractor] (
      underlying: A => Option[Seq[B]]) {
    def seq = new SeqExtractor[A, B] {
      def unapplySeq(a: A) = underlying(a)
    }
  }

  implicit final class OptionFunctionToExtractor[-A, +B] private[LowPriorityExtractor] (underlying: A => Option[B]) {
    def extract = new Extractor[A, B] {
      def unapply(a: A) = underlying(a)
    }

    def forall = new Extractor[Seq[A], Seq[B]] {
      override def unapply(a: Seq[A]): Option[Seq[B]] = {
        Some(a.map {
          case OptionFunctionToExtractor.this.extract(b) => b
          case _ => return None
        })
      }
    }

  }

}

/**
  * A pattern that can be used in `match` / `case` expressions.
  */
sealed trait Extractor[-A, +B] extends (A => Option[B]) {
  def unapply(a: A): Option[B]
  final def apply(a: A) = unapply(a)
}

/**
  * Utilities to convert between `A => Option[B]`, [[scala.PartialFunction]] and [[Extractor]].
  *
  * @example
    {{{
    import com.thoughtworks.Extractor._

    // Define a PartialFunction
    val pf: PartialFunction[Int, String] = {
      case 1 => "matched by PartialFunction"
    }

    // Define an optional function
    val f: Int => Option[String] = { i =>
      if (i == 2) {
        Some("matched by optional function")
      } else {
        None
      }
    }

    // Convert an optional function to a PartialFunction
    val pf2: PartialFunction[Int, String] = Function.unlift(f)

    util.Random.nextInt(4) match {
      case pf.extract(m) => // Convert a PartialFunction to a pattern
        println(m)
      case f.extract(m) => // Convert an optional function to a pattern
        println(m)
      case pf2.extract(m) => // Convert a PartialFunction to a pattern
        throw new AssertionError("This case should never occur because it has the same condition as `f.extract`.")
      case _ =>
        println("Not matched")
    }
    }}}
  *
  */
object Extractor extends LowPriorityExtractor {

  implicit final class PartialFunctionToExtractor[-A, +B] private[Extractor] (underlying: PartialFunction[A, B]) {
    def extract = new Extractor[A, B] {
      def unapply(a: A) = underlying.lift(a)
    }
  }

}
