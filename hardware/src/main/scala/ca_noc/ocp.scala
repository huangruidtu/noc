package ocp

import Chisel.Enum
import chisel3._
import ocp._
import RX.{ReadIo, WriteIo}
import com.sun.org.apache.bcel.internal.generic.NEW

class OcpInterface() extends Module {
    val io = new Bundle{
        width = 32
        val OcpIn = new RX.WriteIo(width)
        val OcpOut = new RX.ReadIo(width)
        val corePort = new OcpCoreSlavePort(16, 32)
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
     io.OcpOut.empty := false.B
            coreReg := io.CorePort.M
            stateReg := idle

    }

}
