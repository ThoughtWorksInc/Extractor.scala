publishArtifact := false

organization in ThisBuild := "com.thoughtworks.extractor"

crossScalaVersions in ThisBuild := Seq("2.10.6", "2.11.8", "2.12.0-RC1")

lazy val extractor = crossProject in file(".")

lazy val extractorJVM = extractor.jvm.addSbtFiles(file("../shared/build.sbt"))

lazy val extractorJS = extractor.js.addSbtFiles(file("../shared/build.sbt"))

// Workaround for randomly Travis CI fail
parallelExecution in Global := false
