package RouterTest
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.util.{Cat, Enum, is, switch}
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}
import router._


class RouterTest(dut : router) extends PeekPokeTester(dut) {
  poke(dut.io.router_in_L.din, 0x0001)
  poke(dut.io.router_in_L.write,true.B)
  //poke(dut.io.txIn.din, 1)
  //step(1)
  //println(peek(dut.io.txOut.dout).toString())

}

object RouterTest extends App {
  chisel3.iotesters.Driver(() => new router(size =35)) {
    m => new RouterTest(m)
  }
}


























//package RouterTest
//import router._
//import chisel3._
//import chisel3.iotesters.{PeekPokeTester, chiselMainTest}
//
//class RouterTest(dut:router) extends PeekPokeTester (dut){
//  poke(dut.io.router_in_N.write, true.B)
//  poke(dut.io.router_in_N.din, 0x000abc1)
//  step(1)
//  println(peek(dut.io.router_out_N.dout).toString())
//}
//
//object RouterTest extends App {
//  chisel3.iotesters.Driver(() => new router(size = 35)) {
//    m => new RouterTest(m)
//  }
//}
