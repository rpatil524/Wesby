name := "wesby"

version := "0.1.0-M4-SNAPSHOT"

play.Project.playScalaSettings

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  filters,
  "org.apache.jena" % "jena-arq" % "2.11.0",
  "commons-configuration" % "commons-configuration" % "1.9",
  "com.github.mumoshu" %% "play2-memcached" % "0.3.0.2"
)

seq(jasmineSettings : _*)

resolvers += "Spy Repository" at "http://files.couchbase.com/maven2"

templatesImport ++= Seq(
  "es.weso.wesby.models.OptionsHelper",
  "es.weso.wesby.models.OptionsHelper._",
  "es.weso.wesby.models._",
  "es.weso.wesby.sparql.Handlers._",
  "es.weso.wesby.utils.CommonURIS._",
  "views.helpers.Utils._",
  "views.html.helpers._",
  "views.html.helpers.utils._"
)

templatesImport ++= Seq()

scalacOptions in Compile in doc += "-diagrams"

// jasmine configuration, overridden as we don't follow the default project structure sbt-jasmine expects
appJsDir <+= baseDirectory  / "public/javascripts"

appJsLibDir <+= baseDirectory  / "public/javascripts"

jasmineTestDir <+= baseDirectory  / "test/jasmine"

jasmineConfFile <+= baseDirectory  / "test/jasmine/test.dependencies.js"

// link jasmine to the standard 'sbt test' action. Now when running 'test' jasmine tests will be run, and if they pass
// then other Play tests will be executed.
(test in Test) <<= (test in Test) dependsOn (jasmine)
