import java.io.File

import service.Matcher

object Application {
    def main(args: Array[String]): Unit = {
        val results = Matcher("txt", new File("readme.txt").getCanonicalPath).execute()
        assert(results == List("readme.txt"))
    }
}
