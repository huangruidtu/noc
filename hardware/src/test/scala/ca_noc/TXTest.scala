package TXTest
import TX._
import RX.{ReadIo,WriterIo}
import chisel3._
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class TX(/*width:Int,*/depth:Int) extends Module {
  val width = 35
  val io = IO(new Bundle {
    val txIn = new RX.WriterIo(width - 3)
    val txOut = new RX.ReadIo(width)
  })
}
class TXTest(dut : TX) extends PeekPokeTester(dut) {
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value=0xdeadbeef)
  step(1)
  println(peek(dut.io.txOut.dout).toString())
}


object TXTest extends App {
      chisel3.iotesters.Driver(() => new TX(depth = 3)) {
      m => new TXTest(m)
    }
}
