# Extractor.scala <a href="http://thoughtworks.com/"><img align="right" src="https://www.thoughtworks.com/imgs/tw-logo.png" title="ThoughtWorks" height="15"/></a>

[![Build Status](https://travis-ci.org/ThoughtWorksInc/Extractor.scala.svg?branch=master)](https://travis-ci.org/ThoughtWorksInc/Extractor.scala)

**Extractor.scala** is a library to convert between `A => Option[B]`, `PartialFunction[A, B]` and extractor objects for pattern matching.

``` scala
import com.thoughtworks.Extractor._

// Convert a PartialFunction to a pattern
val pf: PartialFunction[Int, String] = {
  case 1 => "matched by PartialFunction"
}

// Convert an optional function to a pattern
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
  case pf.extract(m) =>
    println(m)
  case f.extract(m) =>
    println(m)
  case pf2.extract(m) =>
    throw new AssertionError("This case should never occur because it has the same condition as `f.extract`.")
  case _ =>
    println("Not matched")
}
```
