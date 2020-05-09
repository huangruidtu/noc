package OcpInterface

import Chisel.Enum
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}
import ocp._

class OcpInterface(ADDR_WIDTH :Int,DATA_WIDTH : Int) extends Module {
  val io = new Bundle{
    val OcpIn = new RX.WriterIo(32)
    val OcpOut = new RX.ReadIo(32)
    val CorePort = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val addr = Output(UInt(16.W))
  }

  val idle :: sendAddr :: readData :: writeData :: Nil = Enum(UInt(), 4)
  val stateReg = RegInit(idle)
  val rdAddrReg = RegInit(0.U(16.W))
  val dataOutReg = RegInit(0.U(32.W))
  //val coreReg = RegInit(io.CorePort.M)
  val coreReg = Wire(io.CorePort.M)

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
