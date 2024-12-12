
ThisBuild / scalaVersion := "2.13.12"
ThisBuild / organization := "com.cesar"


ThisBuild / scalafixScalaBinaryVersion := "2.13"

val AkkaVersion = "2.10.0"
val AkkaHttpVersion = "10.7.0"
val swaggerVersion = "2.2.21"
val jacksonVersion = "2.17.2"

Compile/mainClass := Some("com.cesar.MainApp")
lazy val commonSettings = Seq(
  resolvers += "Akka library repository".at("https://repo.akka.io/maven"),
  libraryDependencies ++= commonDependencies
)

lazy val commonDependencies = Seq(
  "com.fasterxml.uuid" % "java-uuid-generator" % "5.1.0",
  "io.scalaland" %% "chimney" % "1.5.0"
)


lazy val business = (project in file("business")).settings(
  name := "csv-api",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "com.github.tototoshi" %% "scala-csv" % "2.0.0",
    "com.lightbend.akka" %% "akka-stream-alpakka-csv" % "9.0.0"
  ) ++ swaggerDependencies,
  commonSettings
)

lazy val data = (project in file("data"))
  .dependsOn(business)
  .settings(
    name := "example-data",
    resolvers += "typesafe".at("https://repo1.maven.org/maven2/"),
    libraryDependencies ++= Seq(
      "com.typesafe.slick" %% "slick" % "3.5.0",
      "org.slf4j" % "slf4j-nop" % "2.0.13",
      "org.liquibase" % "liquibase-core" % "4.29.0",
      "io.github.liquibase4s" %% "liquibase4s-core" % "1.0.0",
      "org.postgresql" % "postgresql" % "42.7.3"
    ),
    commonSettings
  )

val swaggerDependencies = Seq(
  "jakarta.ws.rs" % "jakarta.ws.rs-api" % "4.0.0",
  "com.github.swagger-akka-http" %% "swagger-akka-http" % "2.11.0",
  "com.github.swagger-akka-http" %% "swagger-scala-module" % "2.12.3",
  "com.github.swagger-akka-http" %% "swagger-enumeratum-module" % "2.9.0",
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "io.swagger.core.v3" % "swagger-jaxrs2-jakarta" % swaggerVersion,
  "net.codingwell" %% "scala-guice" % "7.0.0",
)

lazy val api = (project in file("api"))
  .settings(
    name := "example-api",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    ) ++ swaggerDependencies,
    commonSettings
  ).dependsOn(business, data)


lazy val root = (project in file(".")).
  settings(
    name := "ejemplo_api",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
      "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
      "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
      "ch.qos.logback" % "logback-classic" % "1.5.6",
      "org.scalactic" %% "scalactic" % "3.2.19",
      "org.scalatest" %% "scalatest" % "3.2.19" % "test"
    )
  ).dependsOn(business, api, data)
  .aggregate(business, api, data)
