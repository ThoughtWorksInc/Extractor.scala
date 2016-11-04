# Extractor.scala <a href="http://thoughtworks.com/"><img align="right" src="https://www.thoughtworks.com/imgs/tw-logo.png" title="ThoughtWorks" height="15"/></a>

[![Build Status](https://travis-ci.org/ThoughtWorksInc/Extractor.scala.svg?branch=master)](https://travis-ci.org/ThoughtWorksInc/Extractor.scala)

**Extractor.scala** is a library to convert between `A => Option[B]`, `PartialFunction[A, B]` and extractor objects for pattern matching.

## Usage
``` sbt
// Add this line in your build.sbt
libraryDependencies += "com.thoughtworks.extractor" %% "extractor" % "latest.release"
```

``` scala
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
```
## Links

 * [API Documentation](https://oss.sonatype.org/service/local/repositories/releases/archive/com/thoughtworks/extractor/extractor_2.12/1.1.1/extractor_2.12-1.1.1-javadoc.jar/!/com/thoughtworks/Extractor$.html#SeqExtractor[-A,+B]extendsAnyRef)
