package RXTest
import RX._
import chisel3._
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class RXTest(dut:RX) extends PeekPokeTester (dut){
  poke(dut.io.rxIn.write, true.B)
  poke(dut.io.rxIn.din, 0x000abc1)
  step(1)
  println(peek(dut.io.rxOut.dout).toString())
}

object RXTest extends App {
  chisel3.iotesters.Driver(() => new RX(depth = 3)) {
    m => new RXTest(m)
  }
}
