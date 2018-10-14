package service

import java.io.File

import models.{DirectoryObject, FileObject}

class Matcher (filter: String, rootLocation: String = new File(".").getCanonicalPath){
    val rootIOObject = FileConverter.convertToIOObject(new File(rootLocation))

    def execute() = {
        val matchedFiles = rootIOObject match {
            case file : FileObject
                if FilterChecker(filter).matches(file.name) => List(file)

            case directory : DirectoryObject =>
                FilterChecker(filter).findMatchedFiles(directory.children)

            case _ => List()
        }
        matchedFiles.map(iOObject => iOObject.name)
    }
}

object Matcher{
    def apply(filter: String, rootLocation: String): Matcher = new Matcher(filter, rootLocation)
}