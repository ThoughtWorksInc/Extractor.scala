import sbtcrossproject.CrossPlugin.autoImport.crossProject

publish / skip  := true

organization in ThisBuild := "com.thoughtworks.extractor"

lazy val extractor = crossProject(JSPlatform, JVMPlatform) in file(".")

// Workaround for randomly Travis CI fail
parallelExecution in Global := false
