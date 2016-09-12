libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % Test

organization in ThisBuild := "com.thoughtworks.extractor"

name := "extractor"

crossScalaVersions in ThisBuild := Seq("2.10.6", "2.11.8", "2.12.0-RC1")
