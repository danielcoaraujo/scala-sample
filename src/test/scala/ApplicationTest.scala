import java.io.File

import models.{DirectoryObject, FileObject}
import service.{FilterChecker, Matcher}
import org.scalatest.FlatSpec

class Application1 extends FlatSpec{
    val matchedFile = FileObject(new File("exampleFile"))
    val listFiles = List(FileObject(new File("anotherFile")), matchedFile)
    val matchedFiles = FilterChecker("exampleFile").findMatchedFiles(listFiles)
    assert(matchedFiles == List(matchedFile))
}

class Application2 extends FlatSpec{
    val matchedFile = FileObject(new File("exampleFile"))
    val directoryObj = DirectoryObject(new File("exampleFile"))
    val listFiles = List(FileObject(new File("anotherFile")), matchedFile, directoryObj)
    val matchedFiles = FilterChecker("exampleFile").findMatchedFiles(listFiles)
    assert(matchedFiles == List(matchedFile))
}

class Application3 extends FlatSpec{
    val results = new Matcher("txt", new File("readme.txt").getCanonicalPath).execute()
    assert(results == List("readme.txt"))
}


class Application4 extends FlatSpec{
    val results = new Matcher("txt", new File(".").getCanonicalPath).execute()
    assert(results == List("readme.txt"))
}
