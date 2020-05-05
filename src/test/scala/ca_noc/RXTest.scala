package RX
import RX._
import chisel3._
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class RXTest(dut:RX) extends PeekPokeTester (dut){
  poke(dut.io.rxIn.write, true.B)
  poke(dut.io.rxIn.din, 0x0000abc1)
  poke(dut.io.rxOut.read,true)
  step(3)
  println("the result = "+peek(dut.io.rxOut.dout).toString())
  expect(dut.io.rxOut.dout,0x000abc1)
}

object RXTest extends App {
  chisel3.iotesters.Driver(() => new RX(depth = 3)) {
    m => new RXTest(m)
  }
}
class FIFOBLOCKTest(dut: fifoblock) extends PeekPokeTester(dut){
  poke(dut.io.fifoIN.write,true)
  poke(dut.io.fifoIN.din, 0x1423AAAA)
  poke(dut.io.fifoOUT.read,true)
  step(1)
  println("the result = " + peek(dut.io.fifoOUT.dout).toString() + ", " + peek(dut.io.fifoOUT.empty).toString()  )
  expect(dut.io.fifoOUT.dout,0x1423AAAA)

  poke(dut.io.fifoIN.write,true)
  poke(dut.io.fifoIN.din, 0x0000AAAA)
  poke(dut.io.fifoOUT.read,true)
  step(1)
  println(peek(dut.io.fifoOUT.dout).toString())
  expect(dut.io.fifoOUT.dout,0x0000AAAA)
}

object FIFOBLOCKTest extends App {
  chisel3.iotesters.Driver(() => new fifoblock(width = 32)) {
    m => new FIFOBLOCKTest(m)
  }
}