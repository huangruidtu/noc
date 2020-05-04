package OcpTest

import Chisel.Enum
import chisel3._
import OcpInterface._
import RX.{ReadIo, WriterIo}
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class OcpTest(dut : OcpInterface) extends PeekPokeTester(dut) {
    //poke(dut.io.txIn.write, true.B)
    //poke(dut.io.txIn.din, 1)
    //step(1)
    //println(peek(dut.io.txOut.dout).toString())

    //---------------Patmos->Ocp->OcpOutput--------
    poke(dut.io.CorePort.M.Cmd, 0x1)
        poke(dut.io.CorePort.M.Addr, 0x0001)
        poke(dut.io.CorePort.M.Data, 0xdeadbeef)
        println(peek(dut.io.addr).toString())
        println(peek(dut.io.OcpOut.dout).toString())

        //---------------OccpInput->Ocp->Patmos--------

}

object OcpTest extends App {
    chisel3.iotesters.Driver(() => new OcpInterface()) {
        m => new OcpTest(m)

    }

}
