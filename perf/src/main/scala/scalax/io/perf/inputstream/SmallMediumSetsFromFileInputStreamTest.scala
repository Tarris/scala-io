package scalax.io.perf
package inputstream

import Utils._
import scalax.io._
import sperformance.Keys.WarmupRuns
import sperformance.dsl._
import util.Random._
import Resource._
import Line.Terminators._
import java.io.{ ByteArrayOutputStream, ByteArrayInputStream }
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.io.File
import java.io.FileInputStream

class SmallMediumSetsFromFileInputStreamTest extends Base {

  val MaxSize = 15000
  val Inc = 5000
  val From = 5000
  val WarmUpRuns = 10
  val WarmUpRunsForLines = 10

}

object SmallMediumSetsFromFileInputStreamTest {
  def main(args: Array[String]) {
    Main.runTests(() => new SmallMediumSetsFromFileInputStreamTest)
  }
}
