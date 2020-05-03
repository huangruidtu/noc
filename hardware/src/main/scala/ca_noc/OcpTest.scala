package OcpTest

import Chisel.Enum
import chisel3._
import ocp._
import RX.{ReadIo, WriterIo}
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class OcpInterface() extends Module {
    val io = new Bundle{
    val ADDR_WIDTH = 16
    val DATA_WIDTH = 32
        val OcpIn = new RX.WriterIo(32)
        val OcpOut = new RX.ReadIo(32)
        val CorePort = new OcpCoreSlavePort(ADDR_WIDTH, DATA_WIDTH)
        val addr = Output(UInt(16.W))
    }

    val idle :: sendAddr :: readData :: writeData :: Nil = Enum(UInt(), 4)
        val stateReg = RegInit(idle)
        val rdAddrReg = RegInit(0.U(16.W)) 
        val dataOutReg = RegInit(0.U(32.W)) 
        val coreReg = RegInit(io.CorePort.M)

        io.CorePort.S.Data := io.OcpIn.din
        io.CorePort.S.Resp := OcpResp.NULL
        io.addr := rdAddrReg
        io.OcpOut.dout := dataOutReg

        when (stateReg === idle){
            when(coreReg.Cmd === OcpCmd.RD){
                stateReg := sendAddr
                rdAddrReg := coreReg.Addr(15, 0)

            }.elsewhen(coreReg.Cmd === OcpCmd.WR){
                stateReg := writeData
                rdAddrReg := coreReg.Addr(15, 0)
                dataOutReg := coreReg.Data

            }.otherwise{
                coreReg := io.CorePort.M
            }

        }.elsewhen(stateReg === sendAddr){
        stateReg := readData

    }.elsewhen(stateReg === readData){
        io.CorePort.S.Resp := OcpResp.DVA
        coreReg := io.CorePort.M
        stateReg := idle

    }.elsewhen(stateReg === writeData){
        io.CorePort.S.Resp := OcpResp.DVA
        io.OcpOut.empty := false.B
        coreReg := io.CorePort.M
        stateReg := idle

    }.otherwise{
        //do nothing
    }
}
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
