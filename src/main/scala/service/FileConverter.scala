package service

import java.io.File

import models._


object FileConverter {
    def convertToIOObject(file: File) = {
        if (file.isDirectory) DirectoryObject(file)
        else FileObject(file)
    }
}
