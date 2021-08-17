import scoverage.ScoverageKeys

name := """capmovie-backend"""
organization := "uk.gov.hmrc.capmovie-backend"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)
  .settings(routesGenerator := InjectedRoutesGenerator)
  .configs(ITest)
  .settings( inConfig(ITest)(Defaults.testSettings) : _*)
  .settings(
    fork in IntegrationTest := false,
    unmanagedSourceDirectories in IntegrationTest := (baseDirectory in IntegrationTest)(base => Seq(base / "it")).value,
    unmanagedResourceDirectories in IntegrationTest := (baseDirectory in IntegrationTest)(base => Seq(base / "it" / "resources")).value)
  .settings(scoverageSettings)
lazy val ITest = config("it") extend(Test)

lazy val scoverageSettings = Seq(
  ScoverageKeys.coverageExcludedPackages := "<empty>;Reverse.*;config.*;.*(AuthService|BuildInfo|Routes).*",
  ScoverageKeys.coverageMinimumStmtTotal := 95,
  ScoverageKeys.coverageFailOnMinimum := true,
  ScoverageKeys.coverageHighlighting := true
)

scalaVersion := "2.12.13"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
resolvers += "HMRC-open-artefacts-maven2" at "https://open.artefacts.tax.service.gov.uk/maven2"
resolvers += Resolver.bintrayRepo("hmrc", "releases")
libraryDependencies += "uk.gov.hmrc.mongo" %% "hmrc-mongo-play-28" % "0.50.0"
PlayKeys.devSettings := Seq("play.server.http.port" -> "9001")
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.9"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"
libraryDependencies += "org.scalatestplus" %% "mockito-3-4" % "3.2.9.0" % "test"
libraryDependencies += "uk.gov.hmrc.mongo" %% "hmrc-mongo-test-play-28" % "0.50.0"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "uk.gov.hmrc.agentsBackend.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "uk.gov.hmrc.agentsBackend.binders._"

PlayKeys.devSettings := Seq("play.server.http.port" -> "9009")
