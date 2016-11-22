name := "scala_scraping"

version := "1.0"

scalaVersion := "2.11.8"

javaOptions in run += "-Djava.library.path=/Users/takuya_st/scala_scraping/lib"

libraryDependencies ++= Seq(
  "org.seleniumhq.webdriver" % "webdriver-htmlunit" % "0.9.7376",
  "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.3-1",
  "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3-1",
  "net.ruippeixotog" %% "scala-scraper" % "1.1.0",
  "com.typesafe.akka"   %% "akka-stream" % "2.4.11",
  "com.typesafe.akka"   %% "akka-actor"  % "2.4.11"
)