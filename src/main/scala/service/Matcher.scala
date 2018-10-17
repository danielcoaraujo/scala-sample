package service

import java.io.File
import models.{DirectoryObject, FileObject, IOObject}
import scala.annotation.tailrec

class Matcher (filter: String,
               rootLocation: String = new File(".").getCanonicalPath,
               checkSubFolders: Boolean = false,
               contentFilter: Option[String] = None){

    val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

    def execute() = {
        @tailrec
        def recursiveMatch(files: List[IOObject], currentList: List[FileObject]): List[FileObject] = {
            files match {
                case List() => currentList
                case iOObject :: rest =>
                    iOObject match {
                        case file: FileObject if FilterChecker(filter).matches(file.name) =>
                            recursiveMatch(rest, file::currentList)
                        case directory: DirectoryObject =>
                            recursiveMatch(rest ::: directory.children(), currentList)
                        case _ =>
                            recursiveMatch(rest, currentList)
                    }
            }
        }

        val matchedFiles = rootIOObject match {
            case file : FileObject if FilterChecker(filter).matches(file.name) =>
                List(file)
            case directory : DirectoryObject =>
                if (checkSubFolders) recursiveMatch(directory.children(), List())
                else FilterChecker(filter).findMatchedFiles(directory.children())
            case _ =>
                List()
        }

        val contentFilterValues = contentFilter match {
          case Some(dataFilter) =>
              matchedFiles.filter(iOObject => FilterChecker(dataFilter).findMatchedFileContent(iOObject.file))
          case None =>
              matchedFiles
        }

        val contentFilterValuesWithTuple = contentFilter match {
            case Some(dataFilter) => {
                matchedFiles.map(iOObject => (iOObject, Some(FilterChecker(dataFilter).findMatchedContentCount(iOObject.file))))
                            .filter(matchTuple => matchTuple._2.get > 0)
            }
            case None => matchedFiles.map(iOObject=>(iOObject, None))
        }

//        contentFilterValues.map(iOObject => iOObject.name)
        contentFilterValuesWithTuple.map{
            case (iOObject, count) => (iOObject.name, count match {
                case Some(value) => Some(value.toString)
                case None => None
            })
        }
    }
}

//object Matcher{
//    def apply(filter: String, rootLocation: String, checkSubFolders: Boolean, contentFilter: Option[String]): Matcher =
//      new Matcher(filter, rootLocation, checkSubFolders, contentFilter)
//}