package de.halcony.pbm

import de.halcony.pbm.PbmImage.defineByte

import java.io.{File, FileOutputStream}
import java.nio.charset.StandardCharsets
import scala.language.postfixOps

/** Simple implementation of creating plain and compressed pbm images
  *
  * based on http://netpbm.sourceforge.net/doc/pbm.html
  */
class PbmImage private (var imageBuffer: Array[Array[Boolean]]) {

  def xDim: Int = imageBuffer.head.length
  def yDim: Int = imageBuffer.length

  private def checkDimensionWithinBoundaries(x: Int, y: Int): Unit = {
    assert(
      x < xDim && y < yDim && x > 0 && y > 0,
      s"the pixel ($x,$y) is not within the image boundaries ($xDim,$yDim)")
  }

  def overwriteBuffer(newBuffer: Array[Array[Boolean]]): Unit = {
    imageBuffer = newBuffer
  }

  def setPixel(x: Int, y: Int, value: Boolean): Unit = {
    checkDimensionWithinBoundaries(x, y)
    imageBuffer(y)(x) = value
  }

  def getPixel(x: Int, y: Int): Boolean = {
    checkDimensionWithinBoundaries(x, y)
    imageBuffer(y)(x)
  }

  def getImageBuffer: Array[Array[Boolean]] = imageBuffer

  private def writePlainPbm(path: String): Unit = {
    val outStream = new FileOutputStream(new File(path))
    try {
      def boolToString(b: Boolean): String = {
        if (b) {
          "1"
        } else {
          "0"
        }
      }
      val encoding = StandardCharsets.US_ASCII
      val header = String.format("P1\n%d %d", xDim, yDim)
      outStream.write(header.getBytes(encoding))
      imageBuffer.flatten.grouped(70).foreach { segment =>
        outStream.write(
          segment.map(boolToString).mkString("\n", "", "\n").getBytes(encoding))
      }
    } finally {
      outStream.close()
    }
  }

  private def writeCompressedPbm(path: String): Unit = {
    val outStream = new FileOutputStream(new File(path))
    try {
      val encoding = StandardCharsets.US_ASCII
      val header = String.format("P4\n%d %d\n", xDim, yDim)
      outStream.write(header.getBytes(encoding))
      imageBuffer.foreach { row =>
        val rowBytes = row.grouped(8).map(defineByte).toArray
        outStream.write(rowBytes)
      }
    } finally {
      outStream.close()
    }
  }

  def writeToFile(path: String, compressed: Boolean = true): Unit = {
    if (compressed) {
      writeCompressedPbm(path)
    } else {
      writePlainPbm(path)
    }
  }

}

object PbmImage {

  def apply(imageBuffer: Array[Array[Boolean]]): PbmImage = {
    new PbmImage(imageBuffer)
  }

  def apply(imageBuffer: Array[Array[Int]]): PbmImage = {
    new PbmImage(imageBuffer.map(intToBool))
  }

  private[pbm] def defineByte(bitSeq: Array[Boolean]): Byte = {
    val fullByte = if (bitSeq.length < 8) {
      bitSeq ++ Array.fill(8 - bitSeq.length)(false)
    } else {
      bitSeq
    }
    fullByte
      .foldLeft(0) { (i, b) =>
        (i << 1) + (if (b) 1 else 0)
      }
      .toByte
  }

  private[pbm] def intToBool(x: Array[Int]): Array[Boolean] = {
    x.map(elem => elem == 1)
  }

}
