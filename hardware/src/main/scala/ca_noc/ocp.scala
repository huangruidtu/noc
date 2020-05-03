package ocp

import Chisel.Enum
import chisel3._
import ocp._
import RX.{ReadIo, WriterIo}
import com.sun.org.apache.bcel.internal.generic.NEW

class OcpInterface() extends Module {
  var width = 32
  val io = new Bundle{
    val OcpIn = new RX.WriterIo(width)
    val OcpOut = new RX.ReadIo(width)
    val corePort = new OcpCoreSlavePort(16, 32)
    val addr = Output(UInt(16.W))
  }

  val idle :: sendAddr :: readData :: writeData :: Nil = Enum(UInt(), 4)
  val stateReg = RegInit(idle)
  val rdAddrReg = RegInit(0.U(16.W))
  val dataOutReg = RegInit(0.U(32.W))
  val coreReg = RegInit(io.corePort.M)

  io.corePort.S.Data := io.OcpIn.din
  io.corePort.S.Resp := OcpResp.NULL
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
      coreReg := io.corePort.M
    }

  }.elsewhen(stateReg === sendAddr){
    stateReg := readData

  }.elsewhen(stateReg === readData){
    io.corePort.S.Resp := OcpResp.DVA
    coreReg := io.corePort.M
    stateReg := idle

  }.elsewhen(stateReg === writeData){
    io.corePort.S.Resp := OcpResp.DVA
    io.OcpOut.empty := false.B
    coreReg := io.corePort.M
    stateReg := idle

  }.otherwise{
    //do nothing
  }
}