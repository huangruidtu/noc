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
  step(1)
  expect(dut.io.txOut.dout,expected = 0xabc1)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())

//  while(peek(dut.io.txOut.empty) != 1){
//    step(1)
//  }

  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xbbc2)
  poke(dut.io.txOut.read,true.B)
  step(1)
  expect(dut.io.txOut.dout,expected = 0xbbc2)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())
//
//  while(peek(dut.io.txOut.empty) != 1){
//    step(1)
//  }

  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xfda3)
  poke(dut.io.txOut.read,true.B)
  step(1)
  expect(dut.io.txOut.dout,expected = 0xfda3)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())
}

object TXPROTester extends App {
  chisel3.iotesters.Driver(() => new TXProcess(depth = 3)) {
    m => new TXPROTester(m)
  }
}

class TXTester(dut : TX) extends PeekPokeTester(dut) {
  //---------TEST1
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xabc1)
  step(1)
  poke(dut.io.txIn.write, false.B)
  step(2)
  poke(dut.io.txOut.read,true.B)

  expect(dut.io.txOut.dout,expected = 0xabc1)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())

  //  while(peek(dut.io.txOut.empty) != 1){
  //    step(1)
  //  }
  //---------TEST2


  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xbbc2)
  step(1)
  poke(dut.io.txIn.write, false.B)
  step(2)
  poke(dut.io.txOut.read,true.B)

  expect(dut.io.txOut.dout,expected = 0xbbc2)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())
  //
  //  while(peek(dut.io.txOut.empty) != 1){
  //    step(1)
  //  }
  //-----------------TEST3

  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xfda3)
  step(1)
  poke(dut.io.txIn.write, false.B)
  step(2)
  poke(dut.io.txOut.read,true.B)

  expect(dut.io.txOut.dout,expected = 0xfda3)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())
  //--------TEST4
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xCAD4)
  step(1)
  poke(dut.io.txIn.write, false.B)
  step(2)
  poke(dut.io.txOut.read,true.B)

  expect(dut.io.txOut.dout,expected = 0xCAD4)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())
  //-------------------TEST5
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xCFA5)
  step(1)
  poke(dut.io.txIn.write, false.B)
  step(2)
  poke(dut.io.txOut.read,true.B)

  expect(dut.io.txOut.dout,expected = 0xCFA5)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())
  //---------------TEST6
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, value = 0xBAC6)
  step(1)
  poke(dut.io.txIn.write, false.B)
  step(2)
  poke(dut.io.txOut.read,true.B)

  expect(dut.io.txOut.dout,expected = 0xBAC6)
  expect(dut.io.txOut.empty,true.B)
  println(peek(dut.io.txOut.dout).toString())

}

object TXTester extends App {
  chisel3.iotesters.Driver(() => new TX(depth = 3)) {
    m => new TXTester(m)
  }
}