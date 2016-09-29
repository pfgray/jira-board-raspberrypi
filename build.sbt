lazy val `raspberry` = (project in file(".")).
  settings(
    organization := "net.paulgray",
    name := "rasp-java",
    version := "1.0.0",
    scalaVersion := "2.11.2"
  )

resolvers += Resolver.sonatypeRepo("public")

libraryDependencies ++= Seq(
  "com.github.scopt" %% "scopt" % "3.5.0",
  "com.pi4j" % "pi4j-core" % "1.1" % "compile",
  "com.pi4j" % "pi4j-device" % "1.1" % "compile",
  "com.pi4j" % "pi4j-gpio-extension" % "1.1" % "compile",
  "com.pi4j" % "pi4j-service" % "1.1" % "compile"
)

mainClass in assembly := Some("net.paulgray.raspberry.Main")

assemblyJarName in assembly := s"${name.value}-${version.value}.jar"

sourceDirectories in Compile += new File("source")

//assemblyExcludedJars in assembly := {
//  val cp = (fullClasspath in assembly).value
//  cp filter {_.data.getName.contains("pi4j")}
//}
