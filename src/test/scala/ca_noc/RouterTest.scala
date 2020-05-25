package router
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.util.{Cat, Enum, is, switch}
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class RouterTest(dut : router) extends PeekPokeTester(dut) {
//  /*********           Header        ***********/
//  poke(dut.io.router_in_L.din, value = 0x601014482L)
//  poke(dut.io.router_in_L.write,true.B)
//  step(2)
//  poke(dut.io.router_out_S.read,true.B)
//  expect(dut.io.router_out_S.dout,0x601010448L)
//  expect(dut.io.router_out_S.empty,true.B)
//  /*********           PayLoad1        ***********/
//  poke(dut.io.router_in_L.din, value = 0x40bbcabc1L)
//  poke(dut.io.router_in_L.write,true.B)
//  step(2)
//  poke(dut.io.router_out_S.read,true.B)
//  expect(dut.io.router_out_S.dout,0x40bbcabc1L)
//  expect(dut.io.router_out_S.empty,true.B)
//  expect(dut.io.router_out_S.empty,true.B)
//  /*********           PayLoad2        ***********/
//  poke(dut.io.router_in_L.din, value = 0x50bbcabc2L)
//  poke(dut.io.router_in_L.write,true.B)
//  step(2)
//  poke(dut.io.router_out_S.read,true.B)
//  expect(dut.io.router_out_S.dout,0x50bbcabc2L)
//  expect(dut.io.router_out_S.empty,true.B)
  /*********           Header        ***********/
  poke(dut.io.router_in_N.din, value = 0x601010004L)
  poke(dut.io.router_in_E.din, value = 0x000000000L)
  poke(dut.io.router_in_S.din, value = 0x000000000L)
  poke(dut.io.router_in_W.din, value = 0x000000000L)
  poke(dut.io.router_in_L.din, value = 0x000000000L)

  poke(dut.io.router_in_N.write,true.B)
  step(2)
  poke(dut.io.router_out_W.read,true.B)
  expect(dut.io.router_out_W.dout,0x601010000L)
  expect(dut.io.router_out_W.empty,true.B)
//  /*********           PayLoad1        ***********/
//  poke(dut.io.router_in_L.din, value = 0x40bbcabc1L)
//  poke(dut.io.router_in_L.write,true.B)
//  step(2)
//  poke(dut.io.router_out_S.read,true.B)
//  expect(dut.io.router_out_S.dout,0x40bbcabc1L)
//  expect(dut.io.router_out_S.empty,true.B)
//  expect(dut.io.router_out_S.empty,true.B)
//  /*********           PayLoad2        ***********/
//  poke(dut.io.router_in_L.din, value = 0x50bbcabc2L)
//  poke(dut.io.router_in_L.write,true.B)
//  step(2)
//  poke(dut.io.router_out_S.read,true.B)
//  expect(dut.io.router_out_S.dout,0x50bbcabc2L)
//  expect(dut.io.router_out_S.empty,true.B)

}

object RouterTest extends App {
  chisel3.iotesters.Driver(() => new router(size =35)) {
    m => new RouterTest(m)
  }
}


class HPUTest(dut:HPU) extends PeekPokeTester(dut){
  poke(dut.io.data_in,value = 0x600101234L)
  step(1)
  expect(dut.io.sele,expected = 0x4)
  expect(dut.io.data_out,expected = 0x600100123L)
  println(peek(dut.io.data_out).toString())
  println(peek(dut.io.sele).toString())

  poke(dut.io.data_in,value = 0x40010abcdL)
  step(1)
  expect(dut.io.sele,expected = 0x4)
  expect(dut.io.data_out,expected = 0x40010abcdL)
  println(peek(dut.io.data_out).toString())
  println(peek(dut.io.sele).toString())

  poke(dut.io.data_in,value = 0x50010abcdL)
  step(1)
  expect(dut.io.sele,expected = 0x4)
  expect(dut.io.data_out,expected = 0x50010abcdL)
  println(peek(dut.io.data_out).toString())
  println(peek(dut.io.sele).toString())
}

object HPUTest extends App {
  chisel3.iotesters.Driver(() => new HPU(size =35)) {
    m => new HPUTest(m)
  }
}


class crossbarTest(dut:XBar) extends PeekPokeTester(dut){
  poke(dut.io.xbar_sele,value = 4)
  poke(dut.io.xbar_data_in, value = 0x600101234L)
  step(1)
  expect(dut.io.xbar_data_out_W,expected = 0x600101234L)

  poke(dut.io.xbar_sele,value = 1)
  poke(dut.io.xbar_data_in, value = 0x600101234L)
  step(1)
  expect(dut.io.xbar_data_out_N,expected = 0x600101234L)

  poke(dut.io.xbar_sele,value = 2)
  poke(dut.io.xbar_data_in, value = 0x600101234L)
  step(1)
  expect(dut.io.xbar_data_out_S,expected = 0x600101234L)

  poke(dut.io.xbar_sele,value = 8)
  poke(dut.io.xbar_data_in, value = 0x600101234L)
  step(1)
  expect(dut.io.xbar_data_out_E,expected = 0x600101234L)


}

object crossbarTest extends App {
  chisel3.iotesters.Driver(() => new XBar(size =35)) {
    m => new crossbarTest(m)
  }
}

class memoryTest(dut : memory_sele) extends PeekPokeTester(dut){
  // header
  poke(dut.io.sele,value = 0xa)
  poke(dut.io.phit_type,value = "b110".U)
  step(1)
  expect(dut.io.out_sele,expected = 0xa)
  // payload1
  poke(dut.io.phit_type,value = "b100".U)
  step(1)
  expect(dut.io.out_sele,expected = 0xa)
  // payload2
  poke(dut.io.phit_type,value = "b101".U)
  step(1)
  expect(dut.io.out_sele,expected = 0xa)
  // second package
  // header
  poke(dut.io.sele,value = 0xb)
  poke(dut.io.phit_type,value = "b110".U)
  step(1)
  expect(dut.io.out_sele,expected = 0xb)
  // payload1
  poke(dut.io.phit_type,value = "b100".U)
  step(1)
  expect(dut.io.out_sele,expected = 0xb)
  // payload2
  poke(dut.io.phit_type,value = "b101".U)
  step(1)
  expect(dut.io.out_sele,expected = 0xb)

}

object memoryTest extends App {
  chisel3.iotesters.Driver(() => new memory_sele) {
    m => new memoryTest(m)
  }
}