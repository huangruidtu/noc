package noc

import chisel3.iotesters.PeekPokeTester

class nocTest(dut:noc) extends PeekPokeTester (dut) {
//  poke(dut.io.ocpIO_CORE1_IN.Cmd, value = 0x01)
//  poke(dut.io.ocpIO_CORE1_IN.Addr, value = 0x0005)
//  step(2)
//  poke(dut.io.ocpIO_CORE1_IN.Cmd, value = 0x01)
//  poke(dut.io.ocpIO_CORE1_IN.Data, value = 0x0001abc1)
//  step(2)
//  poke(dut.io.ocpIO_CORE1_IN.Cmd, value = 0x01)
//  poke(dut.io.ocpIO_CORE1_IN.Data, value = 0x0002bbc2)
//  step(4)
//  expect(dut.io.ocpIO_CORE5_OUT.Data,expected = 0x600050000L)
  poke(dut.io.CorePort1.M.Cmd, value = 0x1)
 // step(1)
  poke(dut.io.CorePort1.M.Addr, value = 4)
//  step(1)
 // poke(dut.io.CorePort1.M.Data, value = 0x10010010)
  step(6)
  expect(dut.io.CorePort2.S.Data, expected = 0x00020000)
//  step(3)
//  step(3)
 // step(4)
//  poke(dut.io.CorePort1.M.Cmd, value = 0x01)
//  poke(dut.io.CorePort1.M.Data, value = 0x0001abc1)
//  step(2)
//  poke(dut.io.CorePort1.M.Cmd, value = 0x01)
//  poke(dut.io.CorePort1.M.Data, value = 0x0002bbc2)
  //step(4)
  //poke()
 // expect(dut.io.CorePort1.S.Data,expected = 0x600050000L)


}

object nocTest extends App {
  chisel3.iotesters.Driver(() => new noc(depth = 3,size = 35)) {
    m => new nocTest(m)
  }
}
