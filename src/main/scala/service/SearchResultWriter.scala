package service

import java.io.{FileWriter, PrintWriter}

object SearchResultWriter {
    def writeToFile(filePath: String, searchResults: List[(String, Option[String])] = List()) = {
        val fileWriter = new FileWriter(filePath)
        val printWriter = new PrintWriter(fileWriter)
        try{
            for((fileName, countOption) <- searchResults) {
                printWriter.println(
                    countOption match {
                        case Some(count) => s"$fileName -> $count"
                        case None => s"$fileName"
                    }
                )
            }
        }finally {
            fileWriter.close()
            printWriter.close()
        }
    }
}
