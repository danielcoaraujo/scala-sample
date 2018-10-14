package models

import java.io.File

import service.FileConverter

case class DirectoryObject(file: File) extends IOObject{
    def children() =
        try {
            file.listFiles.toList
            .map(file => FileConverter.convertToIOObject(file))
        }catch {
            case _: NullPointerException => List()
        }
}
