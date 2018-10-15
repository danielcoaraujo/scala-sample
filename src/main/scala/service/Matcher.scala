package service

import java.io.File

import models.{DirectoryObject, FileObject, IOObject}

class Matcher (filter: String,
               rootLocation: String = new File(".").getCanonicalPath,
               checkSubFolders: Boolean = false){

    val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

    def recursiveMatch(files: List[IOObject], currentList: List[FileObject]): List[FileObject] = {
        files match {
            case List() => currentList
            case iOObject :: rest =>
                iOObject match {
                    case file: FileObject if FilterChecker(filter).matches(file.name) => recursiveMatch(rest, file::currentList)
                    case directory: DirectoryObject => recursiveMatch(rest ::: directory.children(), currentList)
                    case _ => recursiveMatch(rest, currentList)
                }
        }
    }

    def execute() = {
        val matchedFiles = rootIOObject match {
            case file : FileObject
                if FilterChecker(filter).matches(file.name) => List(file)

            case directory : DirectoryObject =>
                 if (checkSubFolders) recursiveMatch(directory.children(), List())
                else FilterChecker(filter).findMatchedFiles(directory.children())

            case _ => List()
        }
        matchedFiles.map(iOObject => iOObject.name)
    }
}

object Matcher{
    def apply(filter: String, rootLocation: String, checkSubFolders: Boolean): Matcher = new Matcher(filter, rootLocation, checkSubFolders)
}