/*
 * Utility functions for Patmos.
 *
 * Author: Martin Schoeberl (martin@jopdesign.com)
 *
 */

package util

import java.nio.file.{ Files, Paths }

import Chisel._
import scala.math._

import patmos.Constants._

// Borrowed from the Chisel source tree.
// Hacked to now support longs
object log2Up
{
  def apply(in: Long): Int = if(in == 1) 1 else ceil(log(in)/log(2)).toInt
}

object Utility {

  /**
   * Read a binary file into the ROM vector
   */
  def readBin(fileName: String, width: Int): Vec[Bits] = {

    val bytesPerWord = (width+7) / 8

    println("Reading " + fileName)
    val byteArray = Files.readAllBytes(Paths.get(fileName))

    // use an array to convert input
    val arr = new Array[Bits](math.max(1, byteArray.length / bytesPerWord))

    if (byteArray.length == 0) {
      arr(0) = Bits(0, width = width)
    }

    for (i <- 0 until byteArray.length / bytesPerWord) {
      var word = BigInt(0)
      for (j <- 0 until bytesPerWord) {
        word <<= 8
        word += byteArray(i * bytesPerWord + j).toInt & 0xff
      }
      // printf("%08x\n", Bits(word))
      arr(i) = Bits(word, width = width)
    }

    // use vector to represent ROM
    Vec[Bits](arr)
  }

  def sizeToStr(size: Long): String = {
    if (size < (1 << 10)) {
      size + " B"
    } else if (size < (1 << 20)) {
      (size / (1 << 10)) + " KB"
    } else if (size < (1 << 30)) {
      (size / (1 << 20)) + " MB"
    } else {
      (size / (1 << 30)) + " GB"
    }
  }

  def printConfig(configFile: String): Unit = {
    printf("\nPatmos configuration \"" + util.Config.getConfig.description + "\"\n")
    printf("\tFrequency: "+ (CLOCK_FREQ/1000000).toString +" MHz\n")
    printf("\tPipelines: "+ PIPE_COUNT.toString +"\n")
    printf("\tCores: "+ Config.getConfig.coreCount.toString +"\n")
    if (ICACHE_TYPE == ICACHE_TYPE_METHOD) {
      printf("\tMethod cache: "+ sizeToStr(ICACHE_SIZE) +", "+ ICACHE_ASSOC.toString +" methods\n")
    } else {
      printf("\tInstruction cache: "+ sizeToStr(ICACHE_SIZE))
      if (ICACHE_ASSOC == 1) {
        printf(", direct-mapped\n")
      } else {
        printf(", "+ ICACHE_ASSOC.toString +"-way set associative with %s replacement\n")
      }
    }
    printf("\tData cache: "+ sizeToStr(DCACHE_SIZE))
    if (DCACHE_ASSOC == 1) {
      printf(", direct-mapped")
    } else {
      printf(", "+ DCACHE_ASSOC.toString +"-way set associative with "+ DCACHE_REPL +" replacement")
    }
    if (DCACHE_WRITETHROUGH){
      printf(", write through\n")
    }else{
      printf(", write back\n")
    }
    printf("\tStack cache: "+ sizeToStr(SCACHE_SIZE) +"\n")
    printf("\tInstruction SPM: "+ sizeToStr(ISPM_SIZE) +"\n")
    printf("\tData SPM: "+ sizeToStr(DSPM_SIZE) +"\n")
    printf("\tAddressable external memory: "+ sizeToStr(util.Config.getConfig.ExtMem.size) +"\n")
    printf("\tMMU: "+HAS_MMU+"\n")
    printf("\tBurst length: "+ util.Config.getConfig.burstLength +"\n")
    printf("\n")
  }
}
