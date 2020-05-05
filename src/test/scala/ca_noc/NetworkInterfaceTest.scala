package NetworkInterfaceTest
import NetworkInterface._
import chisel3._
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class NetworkInterfaceTestTrans(dut:NetworkInterface) extends PeekPokeTester (dut){
  //***************    test path OCP -> NI -> ROUTER **************
  //---------HEADER 1

  poke(dut.io.NI2Ocp_In.write,true.B)
  poke(dut.io.NI2Ocp_In.din,value = 0x1111)
  poke(dut.io.addr,value = 0x6bc1)
  step(1)
  poke(dut.io.NI2Ocp_In.write, false.B)
  poke(dut.io.NI2Router_Out.read,true.B)
  step(3)
  expect(dut.io.NI2Router_Out.dout,expected = 0xabc1)
  expect(dut.io.NI2Router_Out.empty,true.B)
  println(peek(dut.io.NI2Router_Out.dout).toString())

//  //  while(peek(dut.io.txOut.empty) != 1){
//  //    step(1)
//  //  }
//  //---------PAYLOAD 2
//  poke(dut.io.NI2Ocp_In.write, true.B)
//  poke(dut.io.NI2Ocp_In.din, value = 0xbbc2)
//  step(1)
//  poke(dut.io.NI2Ocp_In.write, false.B)
//  step(2)
//  poke(dut.io.NI2Router_Out.read,true.B)
//
//  expect(dut.io.NI2Router_Out.dout,expected = 0xbbc2)
//  expect(dut.io.NI2Router_Out.empty,true.B)
//  println(peek(dut.io.NI2Router_Out.dout).toString())
//  //
//  //  while(peek(dut.io.txOut.empty) != 1){
//  //    step(1)
//  //  }
//  //-----------------PAYLOAD 3
//  poke(dut.io.NI2Ocp_In.write, true.B)
//  poke(dut.io.NI2Ocp_In.din, value = 0xfda3)
//  step(1)
//  poke(dut.io.NI2Ocp_In.write, false.B)
//  step(2)
//  poke(dut.io.NI2Router_Out.read,true.B)
//
//  expect(dut.io.NI2Router_Out.dout,expected = 0xfda3)
//  expect(dut.io.NI2Router_Out.empty,true.B)
//  println(peek(dut.io.NI2Router_Out.dout).toString())

}

object NetworkInterfaceTestTrans extends App {
  chisel3.iotesters.Driver(() => new NetworkInterface(depth = 3, size = 35)) {
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
  chisel3.iotesters.Driver(() => new NetworkInterface(depth = 3, size = 35)) {
    m => new NetworkInterfaceTestReciv(m)
  }
}


class RouteTest(dut:route) extends PeekPokeTester(dut){
  poke(dut.io.en,true.B)
  step(5)
  println(peek(dut.io.route).toString())
}

object RouteTest extends App {
  chisel3.iotesters.Driver(() => new route) {
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
  expect(dut.io.NI2TX_OUT.dout,expected = 0x6bc10001)
  println(peek(dut.io.NI2TX_OUT.dout).toString())
}

object RoutelinkTest extends App {
  chisel3.iotesters.Driver(() => new routelink(size = 35)) {
    m => new RoutelinkTest(m)
  }
}