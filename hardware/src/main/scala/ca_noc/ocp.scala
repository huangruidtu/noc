package noc.ocp

import io._
import ocp._
import Chisel._

class OcpInterface() extends Module {
  val io = new Bundle{
    val addr = Output(UInt(width = 16))
    val dataIn = Input(UInt(width = 32))
    val dataOut = Output(UInt(width = 32))
    val corePort = new OcpCoreSlavePort(ADDR_WIDTH, DATA_WIDTH)
  }
  
  val idle :: sendAddr :: readData :: writeData :: Nil = Enum(UInt(), 4)
  val stateReg = Reg(init = idle)
  val rdAddrReg = Reg(UInt(width = 16)) 
  val dataOutReg = Reg(UInt(width = 32)) 
  val coreReg = Reg(init = io.CorePort.M)

  io.CorePort.S.Data := io.dataIn
  io.CorePort.S.Resp := OcpResp.NULL
  io.addr := rdAddrReg
  io.dataOut := dataOutReg

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
  }
  when(stateReg === sendAddr){
     stateReg := readData
  }
  when(stateReg === readData){
     io.CorePort.S.Resp := OcpResp.DVA
     coreReg := io.CorePort.M
     stateReg := idle
  }
  when(stateReg === writeData){
     io.CorePort.S.Resp := OcpResp.DVA
     coreReg := io.CorePort.M
     stateReg := idle
  }
}
