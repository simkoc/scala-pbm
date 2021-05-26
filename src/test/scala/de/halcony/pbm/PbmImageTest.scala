package de.halcony.pbm

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PbmImageTest extends AnyWordSpec with Matchers {

  def intToBool(x : Array[Int]) : Array[Boolean] = {
    x.map(elem => elem == 1)
  }

  "defineByte" should {
    "give me 0" in {
      PbmImage.defineByte(intToBool(Array(0,0,0,0,0,0,0,0))) shouldBe 0
    }
    "give me 1" in {
      PbmImage.defineByte(intToBool(Array(0,0,0,0,0,0,0,1))) shouldBe 1
    }
    "give me 2" in {
      PbmImage.defineByte(intToBool(Array(0,0,0,0,0,0,1,0))) shouldBe 2
    }
    "give me 4" in {
      PbmImage.defineByte(intToBool(Array(0,0,0,0,0,1,0,0))) shouldBe 4
    }
    "give me 8" in {
      PbmImage.defineByte(intToBool(Array(0,0,0,0,1,0,0,0))) shouldBe 8
    }
    "give me 16" in {
      PbmImage.defineByte(intToBool(Array(0,0,0,1,0,0,0,0))) shouldBe 16
    }
    "give me 32" in {
      PbmImage.defineByte(intToBool(Array(0,0,1,0,0,0,0,0))) shouldBe 32
    }
    "give me 64" in {
      PbmImage.defineByte(intToBool(Array(0,1,0,0,0,0,0,0))) shouldBe 64
    }
    "give me 96" in {
      PbmImage.defineByte(intToBool(Array(0,1,1,0,0,0,0,0))) shouldBe 96
    }
    "give me 128" in {
      PbmImage.defineByte(intToBool(Array(1,0,0,0,0,0,0,0))) shouldBe -128
    }
    "give me 129" in {
      PbmImage.defineByte(intToBool(Array(1,0,0,0,0,0,0,1))) shouldBe -127
    }
  }

  "create new PbmImage from matrix" should {
    "yield the provided matrix" in {
      PbmImage(
        Array(
          Array(true,false),
          Array(true,false)
        )
      ).getImageBuffer shouldBe Array(
        Array(true,false),
        Array(true,false)
      )
    }
  }

  "writePlainPbm" should {
    "write a proper plain pbm (has to be verified manually)" in {
      PbmImage(
        Array(
          Array(1,0,0,0,0,0,0,1,1),
          Array(1,1,0,0,0,0,1,0,1),
          Array(1,0,1,0,0,1,0,0,1),
          Array(1,0,0,1,1,0,0,0,1),
          Array(1,0,0,1,1,0,0,0,1),
          Array(1,0,1,0,0,1,0,0,1),
          Array(1,1,0,0,0,0,1,0,1),
          Array(1,0,0,0,0,0,0,1,1)
        )
      ).writeToFile("/tmp/uncompressed.pbm",compressed = false)
    }
    "write a proper compressed pbm (has to be verified manually)" in {
      PbmImage(
        Array(
          Array(1,0,0,0,0,0,0,1,1),
          Array(1,1,0,0,0,0,1,0,1),
          Array(1,0,1,0,0,1,0,0,1),
          Array(1,0,0,1,1,0,0,0,1),
          Array(1,0,0,1,1,0,0,0,1),
          Array(1,0,1,0,0,1,0,0,1),
          Array(1,1,0,0,0,0,1,0,1),
          Array(1,0,0,0,0,0,0,1,1)
        )
      ).writeToFile("/tmp/compressed.pbm")
    }
  }


}
