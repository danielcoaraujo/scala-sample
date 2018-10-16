package service

import java.io.File
import models.{FileObject, IOObject}
import scala.util.control.NonFatal
import scala.io.Source

class FilterChecker(filter : String) {
    val filterAsRegex = filter.r

//    def matches(content: String) = content.contains(filter)
    def matches(content: String) = {
        filterAsRegex.findFirstMatchIn(content) match {
            case Some(_) => true
            case None => false
        }
    }

    def findMatchedFiles(iOObjects: List[IOObject]) =
        for (iOObject <- iOObjects
             if iOObject.isInstanceOf[FileObject] && matches(iOObject.name)
        ) yield iOObject

    def matchesFileContent(file: File) = {
        import scala.io.Source
        try {
            val fileSource = Source.fromFile(file)
            try {
                fileSource.getLines().exists(line => matches(line))
            } catch {
                case NonFatal(_) => false
            } finally {
                fileSource.close()
            }
        }catch {
            case NonFatal(_) => false
        }
    }

    def findMatchedContentCount(file: File) = {
        def getFilterMatchCount(content: String) = filterAsRegex.findAllIn(content).length
        try {
            val fileSource = Source.fromFile(file)
            try {
                fileSource.getLines().foldLeft(0)((accumulator, line) => accumulator + getFilterMatchCount(line))
            } catch {
                case NonFatal(_) => 0
            } finally {
                fileSource.close()
            }
        }catch {
            case NonFatal(_) => 0
        }
    }
}

object FilterChecker {
    def apply(filter: String): FilterChecker = new FilterChecker(filter)
}