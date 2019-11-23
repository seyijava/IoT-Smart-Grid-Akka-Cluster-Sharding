import com.typesafe.sbt.SbtMultiJvm.multiJvmSettings
import com.typesafe.sbt.SbtMultiJvm.MultiJvmKeys.MultiJvm

enablePlugins(AkkaGrpcPlugin)

name := "iot-smart-grid-monitoring"

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case PathList("reference.conf") => MergeStrategy.concat
 case x => MergeStrategy.first
 }

lazy val akkaVersion = "2.6.0"
lazy val akkaGrpcVersion = "0.7.2"
lazy val `iot-smart-grid-monitoring` = project
  .in(file("."))
  .settings(multiJvmSettings: _*)
  .settings(
    organization := "iot-smart-grid-monitoring",
    scalaVersion := "2.12.8",
    scalacOptions in Compile ++= Seq("-deprecation", "-feature", "-unchecked", "-Xlog-reflective-calls", "-Xlint"),
    javacOptions in Compile ++= Seq("-Xlint:unchecked", "-Xlint:deprecation"),
    javaOptions in run ++= Seq("-Xms128m", "-Xmx1024m", "-Djava.library.path=./target/native"),
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-remote" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
      "com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion,
      "com.lightbend.akka" %% "akka-stream-alpakka-mqtt" % "1.1.2",
      "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
      "com.typesafe.akka" %% "akka-persistence-query" % akkaVersion,
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-serialization-jackson" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream-kafka" % "0.18",
      "com.typesafe.akka" %% "akka-slf4j" % "2.6.0",
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.google.code.gson" % "gson" % "2.8.5",
      "org.scalatest" %% "scalatest" % "3.0.7" % Test,
       "com.thesamet.scalapb" %% "compilerplugin" % "0.9.4",
       "com.lightbend.akka.grpc" %% "akka-grpc-runtime" % "0.7.0",
       "com.typesafe.akka" %% "akka-discovery" % akkaVersion,
       "com.typesafe.akka" %% "akka-persistence-cassandra" % "0.99" exclude("com.typesafe.akka", "*"),
       "com.google.guava"  % "guava" % "23.0",
       "com.fasterxml.jackson.core" % "jackson-databind" % "2.9.5",
       "com.datastax.cassandra" % "cassandra-driver-extras" % "3.1.4",
       "io.kamon" % "sigar-loader" % "1.6.6-rev002"),
    fork in run := true,
    mainClass in (Compile, run) := Some("com.bigdataconcept.akka.distributed.iot.smart.grid.IoTSmartGridCluster"),
    // disable parallel tests
    parallelExecution in Test := false
  )
  .configs (MultiJvm)




