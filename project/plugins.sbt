// The Play plugin
addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.10")


// provides server side compilation of typescript to ecmascript 5 or 3
addSbtPlugin("name.de-vries" % "sbt-typescript" % "0.3.0-beta.6")


// checks your typescript code for error prone constructions
addSbtPlugin("name.de-vries" % "sbt-tslint" % "3.15.1-1")


addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.1.10")


// web plugins
addSbtPlugin("com.typesafe.sbt" % "sbt-less" % "1.1.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-jshint" % "1.0.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-rjs" % "1.0.8")

addSbtPlugin("com.typesafe.sbt" % "sbt-digest" % "1.1.1")

addSbtPlugin("org.irundaia.sbt" % "sbt-sassify" % "1.4.6")
