package NI_with_Router
import chisel3._
import chisel3.iotesters.PeekPokeTests
import chisel3.iotesters.PeekPokeTester

class NI_with_Router_Test(dut:NI_with_Router) extends PeekPokeTester (dut) {
//  poke(dut.io.NI2Ocp_In.write,true.B)
//  //poke(dut.io.NI2Ocp_In.din,value = 0x1111)
//  poke(dut.io.addr,value = 0x0003)
//  step(4)
//  poke(dut.io.router_out_W.read,true.B)
//  expect(dut.io.router_out_W.dout,0x600030000L)

  //----------------  HEADER 1  ----------------------

  poke(dut.io.NI2Ocp_In.write,true.B)
//  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x0003)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  step(3)
  poke(dut.io.router_out_W.read,true.B)
  expect(dut.io.router_out_W.dout,expected = 0x600030000L)
  expect(dut.io.router_out_W.empty,true.B)
  println(peek(dut.io.router_out_W.dout).toString())

  //---------------  PAYLOAD 1 ------------------------
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  step(3)
  poke(dut.io.router_out_W.read,true.B)
  expect(dut.io.router_out_W.dout,expected = 0x400001111L)
  expect(dut.io.router_out_W.empty,true.B)
  println(peek(dut.io.router_out_W.dout).toString())

  //---------------  PAYLOAD 2 ------------------------
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1112)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  step(3)
  poke(dut.io.router_out_W.read,true.B)
  expect(dut.io.router_out_W.dout,expected = 0x500001112L)
  expect(dut.io.router_out_W.empty,true.B)
  println(peek(dut.io.router_out_W.dout).toString())

  //------------   Second Package -----------------
  //----------------  HEADER 1  ----------------------

  poke(dut.io.NI2Ocp_In.write,true.B)
  //poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x0009)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  step(3)
  poke(dut.io.router_out_W.read,true.B)
  expect(dut.io.router_out_W.dout,expected = 0x600090001L)
  expect(dut.io.router_out_W.empty,true.B)
  println(peek(dut.io.router_out_W.dout).toString())

  //---------------  PAYLOAD 1 ------------------------
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  //poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  step(3)
  poke(dut.io.router_out_W.read,true.B)
  expect(dut.io.router_out_W.dout,expected = 0x400001111L)
  expect(dut.io.router_out_W.empty,true.B)
  println(peek(dut.io.router_out_W.dout).toString())

  //---------------  PAYLOAD 2 ------------------------
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1112)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  step(3)
  poke(dut.io.router_out_W.read,true.B)
  expect(dut.io.router_out_W.dout,expected = 0x500001112L)
  expect(dut.io.router_out_W.empty,true.B)
  println(peek(dut.io.router_out_W.dout).toString())


}

object NI_with_Router_Test extends App {
  chisel3.iotesters.Driver(() => new NI_with_Router(depth = 3,size = 35, slot = 1)) {
    m => new NI_with_Router_Test(m)
  }
}

class Router_2_NI_Test(dut:NI_with_Router) extends PeekPokeTester (dut) {
  //  poke(dut.io.NI2Ocp_In.write,true.B)
  //  //poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  //  poke(dut.io.addr,value = 0x0003)
  //  step(4)
  //  poke(dut.io.router_out_W.read,true.B)
  //  expect(dut.io.router_out_W.dout,0x600030000L)

  //----------------  HEADER 1  ----------------------

//  poke(dut.io.router_in_N.write,true.B)
//  poke(dut.io.router_in_N.din, value = 0x630090000L )
//  poke(dut.io.router_in_E.din, value = 0x000000000L)
//  poke(dut.io.router_in_S.din, value = 0x000000000L)
//  poke(dut.io.router_in_W.din, value = 0x000000000L)
//  poke(dut.io.NI2Ocp_In.din  , value = 0x000000000L)
//  step(4)
//  poke(dut.io.NI2Ocp_Out.read,true.B)
//  expect(dut.io.NI2Ocp_Out.dout,expected = 0x30090000L)
//
//  expect(dut.io.NI2Ocp_Out.empty,true.B)
//  println(peek(dut.io.router_out_W.dout).toString())

//  //---------------  PAYLOAD 1 ------------------------
//  poke(dut.io.NI2Ocp_In.write,true.B)
//  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
//  poke(dut.io.addr,value = 0x6bc1)
//  step(1)
//  poke(dut.io.NI2Ocp_In.write, false.B)
//  step(3)
//  poke(dut.io.router_out_W.read,true.B)
//  expect(dut.io.router_out_W.dout,expected = 0x400001111L)
//  expect(dut.io.router_out_W.empty,true.B)
//  println(peek(dut.io.router_out_W.dout).toString())
//
//  //---------------  PAYLOAD 2 ------------------------
//  poke(dut.io.NI2Ocp_In.write,true.B)
//  poke(dut.io.NI2Ocp_In.din,value = 0x1112)
//  poke(dut.io.addr,value = 0x6bc1)
//  step(1)
//  poke(dut.io.NI2Ocp_In.write, false.B)
//  step(3)
//  poke(dut.io.router_out_W.read,true.B)
//  expect(dut.io.router_out_W.dout,expected = 0x500001112L)
//  expect(dut.io.router_out_W.empty,true.B)
//  println(peek(dut.io.router_out_W.dout).toString())
//
//  //------------   Second Package -----------------
//  //----------------  HEADER 1  ----------------------
//
//  poke(dut.io.NI2Ocp_In.write,true.B)
//  //poke(dut.io.NI2Ocp_In.din,value = 0x1111)
//  poke(dut.io.addr,value = 0x0009)
//  step(1)
//  poke(dut.io.NI2Ocp_In.write, false.B)
//  step(3)
//  poke(dut.io.router_out_W.read,true.B)
//  expect(dut.io.router_out_W.dout,expected = 0x600090001L)
//  expect(dut.io.router_out_W.empty,true.B)
//  println(peek(dut.io.router_out_W.dout).toString())
//
//  //---------------  PAYLOAD 1 ------------------------
//  poke(dut.io.NI2Ocp_In.write,true.B)
//  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
//  //poke(dut.io.addr,value = 0x6bc1)
//  step(1)
//  poke(dut.io.NI2Ocp_In.write, false.B)
//  step(3)
//  poke(dut.io.router_out_W.read,true.B)
//  expect(dut.io.router_out_W.dout,expected = 0x400001111L)
//  expect(dut.io.router_out_W.empty,true.B)
//  println(peek(dut.io.router_out_W.dout).toString())
//
//  //---------------  PAYLOAD 2 ------------------------
//  poke(dut.io.NI2Ocp_In.write,true.B)
//  poke(dut.io.NI2Ocp_In.din,value = 0x1112)
//  poke(dut.io.addr,value = 0x6bc1)
//  step(1)
//  poke(dut.io.NI2Ocp_In.write, false.B)
//  step(3)
//  poke(dut.io.router_out_W.read,true.B)
//  expect(dut.io.router_out_W.dout,expected = 0x500001112L)
//  expect(dut.io.router_out_W.empty,true.B)
//  println(peek(dut.io.router_out_W.dout).toString())
//--------------------------------------------
  poke(dut.io.router_in_N.write,true.B)
  poke(dut.io.router_in_N.din, value = 0x630090000L )
  poke(dut.io.router_in_E.din, value = 0x000000000L)
  poke(dut.io.router_in_S.din, value = 0x000000000L)
  poke(dut.io.router_in_W.din, value = 0x000000000L)
  poke(dut.io.NI2Ocp_In.din  , value = 0x000000000L)
  step(4)
  poke(dut.io.NI2Ocp_Out.read,true.B)
  expect(dut.io.NI2Ocp_Out.dout,expected = 0x30090000L)

  expect(dut.io.NI2Ocp_Out.empty,true.B)
  println(peek(dut.io.router_out_W.dout).toString())

}

object Router_2_NI_Test extends App {
  chisel3.iotesters.Driver(() => new NI_with_Router(depth = 3, size = 35, slot = 1)) {
    m => new Router_2_NI_Test(m)
  }
}