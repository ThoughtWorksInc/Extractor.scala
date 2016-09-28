package com.thoughtworks

private[thoughtworks] sealed trait LowPrirorityExtractor {

  sealed trait SeqExtractor[-A, +B] {
    def unapplySeq(a: A): Option[Seq[B]]
  }

  implicit final class OptionFunctionToSeqExtractor[-A, +B] private[LowPrirorityExtractor](underlying: A => Option[Seq[B]]) {
    def extractSeq = new SeqExtractor[A, B] {
      def unapplySeq(a: A) = underlying(a)
    }
  }

  implicit final class OptionFunctionToExtractor[-A, +B] private[LowPrirorityExtractor](underlying: A => Option[B]) {
    def extract = new Extractor[A, B] {
      def unapply(a: A) = underlying(a)
    }
  }

}

/**
  * A pattern that can be used in `match` / `case` expressions.
  */
sealed trait Extractor[-A, +B] {
  def unapply(a: A): Option[B]
}

/**
  * Utilities to convert between `A => Option[B]`, `PartialFunction[A, B]` and [[Extractor]].
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
    val pf2: PartialFunction[Int, String] = f.unlift
    
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
object Extractor extends LowPrirorityExtractor {

  implicit final class PartialFunctionToSeqExtractor[-A, +B] private[Extractor](underlying: PartialFunction[A, Seq[B]]) {
    def extractSeq = new SeqExtractor[A, B] {
      def unapplySeq(a: A) = underlying.lift(a)
    }
  }

  implicit final class PartialFunctionToExtractor[-A, +B] private[Extractor](underlying: PartialFunction[A, B]) {
    def extract = new Extractor[A, B] {
      def unapply(a: A) = underlying.lift(a)
    }
  }

  implicit final class OptionFunctionToPartialFunction[-A, +B] private[Extractor](underlying: A => Option[B]) {
    def unlift: PartialFunction[A, B] = {
      case underlying.extract(b) => b
    }
  }

}