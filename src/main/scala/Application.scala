import java.io.File

import service.Matcher

object Application {
    def main(args: Array[String]): Unit = {
//        val results = new Matcher("sbt", new File(".").getCanonicalPath, true).execute()
        val results = Matcher("sbt", new File(".").getCanonicalPath, true, Some("scalaProject")).execute()
        assert(results == List("build.sbt"))
    }
}
