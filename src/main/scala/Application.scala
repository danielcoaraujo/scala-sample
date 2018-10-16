import java.io.File

import service.{FilterChecker, Matcher}

object Application {
    def main(args: Array[String]): Unit = {
//        val results = new Matcher("sbt", new File(".").getCanonicalPath, true).execute()
//        val results = Matcher("sbt", new File(".").getCanonicalPath, true, Some("scalaProject")).execute()
//        assert(results == List("build.sbt"))
        val testMatch = FilterChecker("test").findMatchedContentCount(new File("./testfiles/readme.txt"))
        assert(testMatch == 6)
        val testingMatch = FilterChecker("testing").findMatchedContentCount(new File("./testfiles/readme.txt"))
        assert(testingMatch == 3)
    }
}
