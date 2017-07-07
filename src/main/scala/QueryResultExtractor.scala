import java.io.{File, FileInputStream, PrintWriter}

import org.json4s
import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write

case class Result(doi: String, Abstract: String)

object QueryResultExtractor {

  def main(args: Array[String]): Unit = {
    implicit val formats = DefaultFormats

    val inputFileLocation = args.head

    val inputContent = fileContent(inputFileLocation)

    val bindings = (inputContent \\ "bindings").children.distinct
    val results = asResults(bindings)

    toOutput(inputFileLocation, results)
  }

  private def fileContent(inputFileLocation: String) = {
    val fileInputStream = new FileInputStream(new File(inputFileLocation))
    val fileContent = parse(fileInputStream)
    fileContent
  }

  private def asResults(bindings: List[json4s.JValue]): List[Result] = {
    bindings
      .map(res =>
        Result(
          (res \\ "doi" \ "value").values.toString,
          (res \\ "abs" \ "value").values.toString
        )
      )
  }

  private def toOutput(inputFileLocation: String, results: List[Result]) = {
    val outputFile = new File(inputFileLocation + ".output")
    new PrintWriter(outputFile).write(write(results))
  }
}

