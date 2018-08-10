publishArtifact := false

organization in ThisBuild := "com.thoughtworks.extractor"

crossScalaVersions in ThisBuild := Seq("2.10.7", "2.11.12", "2.12.6", "2.13.0-M4")

lazy val extractor = crossProject in file(".")

lazy val extractorJVM = extractor.jvm.addSbtFiles(file("../shared/build.sbt"))

lazy val extractorJS = extractor.js.addSbtFiles(file("../shared/build.sbt"))

// Workaround for randomly Travis CI fail
parallelExecution in Global := false
