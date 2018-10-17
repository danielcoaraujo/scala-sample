import java.io.File

import models.{DirectoryObject, FileObject}
import service.{FilterChecker, Matcher, SearchResultWriter}
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

//To test method contentFilterValues without tuple.
class Application3 extends FlatSpec{
    val results = new Matcher("txt", new File("readme.txt").getCanonicalPath).execute()
    assert(results == List("readme.txt"))
}

//To test method contentFilterValues without tuple.
class Application4 extends FlatSpec{
    val results = new Matcher("txt", new File(".").getCanonicalPath).execute()
    assert(results == List("readme.txt"))
}

class Appication5 extends FlatSpec{
    val testMatch = FilterChecker("test").findMatchedContentCount(new File("./testfiles/readme.txt"))
    assert(testMatch == 6)
}

class Application6 extends FlatSpec{
    val testingMatch = FilterChecker("testing").findMatchedContentCount(new File("./testfiles/readme.txt"))
    assert(testingMatch == 3)
}

class Application7 extends FlatSpec{
    val results = new Matcher("txt", new File("./testfiles").getCanonicalPath).execute()
    assert(results == List(("readme.txt", None)))
}

class Application8 extends FlatSpec{
    val results = new Matcher("txt", new File("./testfiles").getCanonicalPath).execute()
    SearchResultWriter.writeToFile("./testfiles/result.txt", results)
    assert(results == List(("readme.txt", None)))
}

//To test method contentFilterValues without tuple.
class Application9 extends FlatSpec{
    val results = new Matcher("readme.txt", new File(".").getCanonicalPath, true).execute()
    assert(results == List("readme.txt"))
}

class Application10 extends FlatSpec{
    val results = new Matcher("txt", new File("./testfiles").getCanonicalPath, true, Some("test")).execute()
    SearchResultWriter.writeToFile("./testfiles/result.txt", results)
    assert(results == List(("readme.txt", None)))
}