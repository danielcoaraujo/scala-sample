import java.io.File

import service.Matcher

object Application {
    def main(args: Array[String]): Unit = {
        val results = Matcher("sbt", new File(".").getCanonicalPath, true).execute()
        assert(results == List("build.sbt"))
    }
}
