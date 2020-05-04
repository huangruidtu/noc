package TX
import TX._
import chisel3._
import chisel3.iotesters.PeekPokeTester
import org.scalatest._
//object TXTester {
//  val param = Array("--target-dir", "generated", "--generate-vcd-output", "on")
//}
class TXFIFOTester(dut : TX_fifo) extends PeekPokeTester(dut) {
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xabc1)
  poke(dut.io.txOut.read,true.B)
  step(3)
  expect(dut.io.txOut.dout,expected = 0xabc1)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())

  while(peek(dut.io.txOut.empty) != 1){
    step(1)
  }

  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xbbc2)
  poke(dut.io.txOut.read,true.B)
  step(3)
  expect(dut.io.txOut.dout,expected = 0xbbc2)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())

  while(peek(dut.io.txOut.empty) != 1){
    step(1)
  }

  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xfda3)
  poke(dut.io.txOut.read,true.B)
  step(3)
  expect(dut.io.txOut.dout,expected = 0xfda3)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())
}

object TXFIFOTester extends App {
  chisel3.iotesters.Driver(() => new TX_fifo(depth = 3)) {
    m => new TXFIFOTester(m)
  }
}

class TXPROTester(dut : TXProcess) extends PeekPokeTester(dut) {
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xabc1)
  poke(dut.io.txOut.read,true.B)
  step(3)
  expect(dut.io.txOut.dout,expected = 0xabc1)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())

  while(peek(dut.io.txOut.empty) != 1){
    step(1)
  }

  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xbbc2)
  poke(dut.io.txOut.read,true.B)
  step(3)
  expect(dut.io.txOut.dout,expected = 0xbbc2)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())

  while(peek(dut.io.txOut.empty) != 1){
    step(1)
  }

  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xfda3)
  poke(dut.io.txOut.read,true.B)
  step(3)
  expect(dut.io.txOut.dout,expected = 0xfda3)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())
}

object TXPROTester extends App {
  chisel3.iotesters.Driver(() => new TXProcess(depth = 3)) {
    m => new TXPROTester(m)
  }
}