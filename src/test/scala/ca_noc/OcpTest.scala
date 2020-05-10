package OcpTest

import Chisel.Enum
import chisel3._
import OcpInterface._
import RX.{ReadIo, WriterIo}
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class OcpTest(dut : OcpInterface) extends PeekPokeTester(dut) {
    //---------------Patmos->Ocp->OcpOutput--------
    //poke(dut.io.CorePort.M.Cmd, 0x1)
    //poke(dut.io.CorePort.M.Addr, 0x0001)
    //poke(dut.io.CorePort.M.Data, 0x0001)
    //step(2)
    //println(peek(dut.io.addr).toString())
    //println(peek(dut.io.OcpOut.dout).toString())
    step(1)
    //---------------OccpInput->Ocp->Patmos--------
    poke(dut.io.OcpIn.din, 0x0001)
    step(2)
    println(peek(dut.io.CorePort.S.Data).toString())

}

object OcpTest extends App {
    chisel3.iotesters.Driver(() => new OcpInterface(16, 32)) {
        m => new OcpTest(m)

    }

}
