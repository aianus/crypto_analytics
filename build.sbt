name := "crypto_analytics"

version := "1.0"

scalaVersion := "2.10.6"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.6.1" % "provided"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "2.7.2" % "provided"
libraryDependencies ++= Seq(

)

libraryDependencies += "org.json4s" % "json4s-jackson_2.10" % "3.2.11"
libraryDependencies += "org.scalactic" %% "scalactic" % "2.2.6"
libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % "test"

resolvers += Resolver.mavenLocal
resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"
