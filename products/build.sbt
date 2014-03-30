name := "products"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  "net.sf.barcode4j" % "barcode4j" % "2.0",
  "com.h2database" % "h2" % "1.3.+",
  jdbc,
  anorm,
  cache
)     

play.Project.playScalaSettings
