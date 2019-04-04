publish / skip  := true

organization in ThisBuild := "com.thoughtworks.extractor"

lazy val extractor = crossProject in file(".")

lazy val extractorJVM = extractor.jvm

lazy val extractorJS = extractor.js

// Workaround for randomly Travis CI fail
parallelExecution in Global := false
