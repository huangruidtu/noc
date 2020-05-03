package TX
import TX._
import chisel3._
import chisel3.iotesters.PeekPokeTester
import org.scalatest._
object TXTester {
  val param = Array("--target-dir", "generated", "--generate-vcd-output", "on")
}
class TXTester(dut : TX) extends PeekPokeTester(dut) {
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, 1)
  step(1)
  poke(dut.io.txIn.write, true.B)
  poke(dut.io.txIn.din, 6)
  step(1)
  poke(dut.io.txIn.write, false.B)
  poke(dut.io.txIn.din, 2)
  step(1)
  //println(peek(dut.io.txOut.dout).toString())
}
class TXTest extends FlatSpec with Matchers {
  "TX" should "pass" in {
    iotesters.Driver.execute(/*TXTester.param*/Array("--target-dir", "generated", "--generate-vcd-output", "on"),
      () => new TX(depth = 3)) { c =>
      new TXTester(c)
    } should be(true)
  }
}

//package TXTest
//import TX._
//import chisel3._
//import chisel3.iotesters.{PeekPokeTester, Driver, chiselMainTest}
//import org.scalatest._
//
//class TXTest(dut : TX) extends PeekPokeTester(dut) {
//  poke(dut.io.txIn.write, true.B)
//  poke(dut.io.txIn.din, 1)
//  step(1)
//  poke(dut.io.txIn.write, true.B)
//  poke(dut.io.txIn.din, 6)
//  step(1)
//  poke(dut.io.txIn.write, false.B)
//  poke(dut.io.txIn.din, 2)
//  step(1)
//
//  println("the result is"+peek(dut.io.txOut.dout).toString())
//}
////class WaveformSpec extends FlatSpec with Matchers{
////  "Waveform" should "pass" in {
////    Driver.execute(Array("--target-dir","generated","--generate-vcd-output","on"), ()=>
////        new TX(depth = 3)) { c=>
////      new TXTest(c)
////    }should be(true)
////  }
////}
//
//object TXTest extends App {
//  chisel3.iotesters.Driver(() => new TX(depth = 3)) {
//    m => new TXTest(m)
//  }
//}