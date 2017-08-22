import java.io.File

import sys.process._

object CsvHandler {

  val moduleCall = "python /Users/andrea.birotti/hack-and-play/NER/NER/src/main/python/stdin_to_stdout.py"

  def main(args: Array[String]): Unit = {

    val inputFileLocation = args.head
    new File(inputFileLocation) #> moduleCall #>> new File(inputFileLocation+".output") !

  }
}
