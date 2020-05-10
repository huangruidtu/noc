package NetworkInterfaceTest
import NetworkInterface._
import chisel3._
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class NetworkInterfaceTestTrans(dut:NetworkInterface) extends PeekPokeTester (dut){
  //***************    test path OCP -> NI -> ROUTER **************
  //----------------  HEADER 1  ----------------------

  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x0003)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  poke(dut.io.NI2Router_Out.read,true.B)
  step(3)
  expect(dut.io.NI2Router_Out.dout,expected = 0x600030004L)
  expect(dut.io.NI2Router_Out.empty,true.B)
  println(peek(dut.io.NI2Router_Out.dout).toString())

  //---------------  PAYLOAD 1 ------------------------
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  poke(dut.io.NI2Router_Out.read,true.B)
  step(3)
  expect(dut.io.NI2Router_Out.dout,expected = 0x400001111L)
  expect(dut.io.NI2Router_Out.empty,true.B)
  println(peek(dut.io.NI2Router_Out.dout).toString())

  //---------------  PAYLOAD 2 ------------------------
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1112)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  poke(dut.io.NI2Router_Out.read,true.B)
  step(3)
  expect(dut.io.NI2Router_Out.dout,expected = 0x500001112L)
  expect(dut.io.NI2Router_Out.empty,true.B)
  println(peek(dut.io.NI2Router_Out.dout).toString())

  //------------   Second Package -----------------
  //----------------  HEADER 1  ----------------------

  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x0009)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  poke(dut.io.NI2Router_Out.read,true.B)
  step(3)
  expect(dut.io.NI2Router_Out.dout,expected = 0x600090014L)
  expect(dut.io.NI2Router_Out.empty,true.B)
  println(peek(dut.io.NI2Router_Out.dout).toString())

  //---------------  PAYLOAD 1 ------------------------
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  poke(dut.io.NI2Router_Out.read,true.B)
  step(3)
  expect(dut.io.NI2Router_Out.dout,expected = 0x400001111L)
  expect(dut.io.NI2Router_Out.empty,true.B)
  println(peek(dut.io.NI2Router_Out.dout).toString())

  //---------------  PAYLOAD 2 ------------------------
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1112)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  poke(dut.io.NI2Router_Out.read,true.B)
  step(3)
  expect(dut.io.NI2Router_Out.dout,expected = 0x500001112L)
  expect(dut.io.NI2Router_Out.empty,true.B)
  println(peek(dut.io.NI2Router_Out.dout).toString())

}

object NetworkInterfaceTestTrans extends App {
  chisel3.iotesters.Driver(() => new NetworkInterface(depth = 3, size = 35, slot = 1)) {
    m => new NetworkInterfaceTestTrans(m)
  }
}

class NetworkInterfaceTestReciv(dut:NetworkInterface) extends PeekPokeTester (dut){
  //*****************, test path OCP <- NI <- ROUTER ****************
  //---------HEADER 1
  poke(dut.io.NI2Router_In.write,true.B)
  poke(dut.io.NI2Router_In.din,value = 0x600010010L)
  step(1)
//  while(dut.io.NI2Router_In.full != true.B){
//    step(1)
//  }
  poke(dut.io.NI2Router_In.write,false.B)
  step(2)
  println("the result = "+peek(dut.io.NI2Ocp_Out.dout).toString())
  expect(dut.io.NI2Ocp_Out.dout,0x00010010)

}

object NetworkInterfaceTestReciv extends App {
  chisel3.iotesters.Driver(() => new NetworkInterface(depth = 3, size = 35,slot = 1)) {
    m => new NetworkInterfaceTestReciv(m)
  }
}


class RouteTest(dut:route) extends PeekPokeTester(dut){
  poke(dut.io.en,true.B)
  step(5)
  println(peek(dut.io.route).toString())
}

object RouteTest extends App {
  chisel3.iotesters.Driver(() => new route(slot = 1)) {
    m => new RouteTest(m)
  }
}

class RoutelinkTest(dut:routelink) extends PeekPokeTester(dut){
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2TX_OUT.read,value = true)
  poke(dut.io.NI2Ocp_In.write,false.B)
  expect(dut.io.NI2TX_OUT.dout,expected = 0x6bc10001)
  println(peek(dut.io.NI2TX_OUT.dout).toString())
// step(1)
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2TX_OUT.read,value = true)
  poke(dut.io.NI2Ocp_In.write,false.B)
  expect(dut.io.NI2TX_OUT.dout,expected = 0x1111)
  println(peek(dut.io.NI2TX_OUT.dout).toString())
//step(1)
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1112)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2TX_OUT.read,value = true)
  expect(dut.io.NI2TX_OUT.dout,expected = 0x1112)
  println(peek(dut.io.NI2TX_OUT.dout).toString())
 //step(1)
  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2TX_OUT.read,value = true)
  expect(dut.io.NI2TX_OUT.dout,expected = 0x6bc10002)
  println(peek(dut.io.NI2TX_OUT.dout).toString())
}

object RoutelinkTest extends App {
  chisel3.iotesters.Driver(() => new routelink(size = 35, slot = 1)) {
    m => new RoutelinkTest(m)
  }
}