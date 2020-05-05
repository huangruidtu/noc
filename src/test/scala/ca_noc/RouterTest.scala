package router
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.util.{Cat, Enum, is, switch}
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class RouterTest(dut : router) extends PeekPokeTester(dut) {
  poke(dut.io.router_in_L.din, value = 0x501014482L)
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


class HPUTest(dut:HPU) extends PeekPokeTester(dut){
  poke(dut.io.data_in,value = 0x600101234L)
  step(1)
  expect(dut.io.sele,expected = 0x4)
  expect(dut.io.data_out,expected = 0x600101234L)
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