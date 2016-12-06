name := "portfolio-viewer"
version := "1.0-SNAPSHOT"
lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

val reactiveMongoVer = "0.12.0"
val ngVersion="2.2.0"
libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.reactivemongo" %% "play2-reactivemongo" % reactiveMongoVer,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,

  //angular2 dependencies
  "org.webjars.npm" % "angular__common" % ngVersion,
  "org.webjars.npm" % "angular__compiler" % ngVersion,
  "org.webjars.npm" % "angular__core" % ngVersion,
  "org.webjars.npm" % "angular__http" % ngVersion,
  "org.webjars.npm" % "angular__forms" % ngVersion,
  "org.webjars.npm" % "angular__router" % "3.2.0",
  "org.webjars.npm" % "angular__platform-browser-dynamic" % ngVersion,
  "org.webjars.npm" % "angular__platform-browser" % ngVersion,
  "org.webjars.npm" % "systemjs" % "0.19.40",
  "org.webjars.npm" % "rxjs" % "5.0.0-beta.12",
  "org.webjars.npm" % "reflect-metadata" % "0.1.8",
  "org.webjars.npm" % "zone.js" % "0.6.26",
  "org.webjars.npm" % "core-js" % "2.4.1",
  "org.webjars.npm" % "symbol-observable" % "1.0.1",

  "org.webjars.npm" % "typescript" % "2.0.10",

  //tslint dependency
  "org.webjars.npm" % "tslint-eslint-rules" % "2.1.0",
  "org.webjars.npm" % "codelyzer" % "0.0.28",
  "org.webjars.npm" % "types__jasmine" % "2.2.26-alpha" % "test"
)

dependencyOverrides += "org.webjars.npm" % "minimatch" % "3.0.0"

// use the webjars npm directory (target/web/node_modules ) for resolution of module imports of angular2/core etc
resolveFromWebjarsNodeModulesDir := true

// use the combined tslint and eslint rules plus ng2 lint rules
(rulesDirectories in tslint) := Some(List(
  tslintEslintRulesDir.value,
  ng2LintRulesDir.value
))

routesGenerator := InjectedRoutesGenerator

