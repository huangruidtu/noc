package NOC.OcpHandshakeComponent

import Chisel._
import Node._
import patmos.Constants._
import io._
import ocp._

class Handshake() extends Module {
   val io = new Bundle{
      val fromCore = new OcpCoreSlavePort(ADDR_WIDTH, DATA_WIDTH)//data goes from processor
      val toTX = new OcpCoreMasterPort(ADDR_WIDTH, DATA_WIDTH)//data goes to TX
      val TXReady = Input(Bool())//handshake needs to wait until TX component is ready
      val TXValid = Output(Bool())//handshake has valid data
   }
   
   val waitForCmd :: wr :: rd :: fromNIC :: Nil = Enum(UInt(), 4)//states cannot start with capital letters
   val masterReg = Reg(init = io.fromCore.M)
   val stateReg = Reg(init = waitForCmd)
   val dataReg = Reg(init = io.toTX.S.Data)
   
   io.toTX.M <> masterReg
   io.TXValid := Bool(false)
   io.fromCore.S.Data := dataReg   
   io.fromCore.S.Resp := OcpResp.NULL

   when(stateReg === waitForCmd){
     when(masterReg.Cmd === OcpCmd.WR){
        stateReg := wr
     }.elsewhen(masterReg.Cmd === OcpCmd.RD){
        stateReg := rd
     }.otherwise{
        masterReg := io.fromCore.M
     }
     /*
     when(io.toTX.S.Resp === OcpResp.DVA){
        dataReg := io.toTX.S.Data
        stateReg := fromNIC
     }*/
   }
   when(stateReg === wr){
     io.TXValid := Bool(true)
     when(io.TXReady === Bool(true)){
       stateReg := waitForCmd
       io.fromCore.S.Resp := OcpResp.DVA
       masterReg := io.fromCore.M
     }
   }
   when(stateReg === rd){
     io.TXValid := Bool(true)
     when(io.TXReady === Bool(true)){
        stateReg := waitForCmd
        masterReg := io.fromCore.M
        //io.fromCore.S.Resp := OcpResp.DVA
     }
     
   }
   when(stateReg === fromNIC){
      io.fromCore.S.Resp := OcpResp.DVA
      stateReg := waitForCmd
   }
}

class HandshakeTest(dut: Handshake) extends Tester(dut){
   /*
    * first simple test when processor issues write command
    * we issue WR Command and then peek in the tx to see the things
    * SResp and SData is automatically directed to the processor
    */
   poke(dut.io.fromCore.M.Cmd, 0x0)//Null command
   poke(dut.io.TXReady, true)
   step(1)
   poke(dut.io.fromCore.M.Cmd, 0x1)//Write command
   poke(dut.io.fromCore.M.Addr, 0xe8000001)
   poke(dut.io.fromCore.M.Data, 0xdeadbeef)
   poke(dut.io.TXReady, true)
   peek(dut.io.TXValid)
   peek(dut.io.toTX.M)
   step(1)
   poke(dut.io.fromCore.M.Cmd, 0x0)//Write command
   poke(dut.io.TXReady, true)
   peek(dut.io.fromCore.S.Resp)
   peek(dut.io.TXValid)
   peek(dut.io.toTX.M)
   step(1)
   /*
    * In this part we let the handshake component wait for a few steps
    *
    */
   poke(dut.io.fromCore.M.Cmd, 0x0)//Null command
   poke(dut.io.TXReady, true)
   peek(dut.io.TXValid)
   step(1)
   poke(dut.io.fromCore.M.Cmd, 0x1)//Write command
   poke(dut.io.fromCore.M.Addr, 0xe8000001)
   poke(dut.io.fromCore.M.Data, 0xdeadbeef)
   poke(dut.io.TXReady, false)
   peek(dut.io.TXValid)
   step(1)
   poke(dut.io.fromCore.M.Cmd, 0x0)//Write command
   poke(dut.io.TXReady, false)
   peek(dut.io.fromCore.S.Resp)
   peek(dut.io.TXValid)
   peek(dut.io.toTX.M)
   step(1)
   poke(dut.io.fromCore.M.Cmd, 0x0)//Write command
   poke(dut.io.TXReady, false)
   peek(dut.io.fromCore.S.Resp)
   peek(dut.io.TXValid)
   peek(dut.io.toTX.M)
   step(1)
   poke(dut.io.fromCore.M.Cmd, 0x0)//Write command
   poke(dut.io.TXReady, false)
   peek(dut.io.fromCore.S.Resp)
   peek(dut.io.TXValid)
   peek(dut.io.toTX.M)
   step(1)
   poke(dut.io.fromCore.M.Cmd, 0x0)//Write command
   poke(dut.io.TXReady, true)
   peek(dut.io.fromCore.S.Resp)
   peek(dut.io.TXValid)
   peek(dut.io.toTX.M)
   step(1)
   poke(dut.io.fromCore.M.Cmd, 0x0)//Write command
   poke(dut.io.TXReady, true)
   peek(dut.io.fromCore.S.Resp)
   peek(dut.io.TXValid)
   peek(dut.io.toTX.M)
   step(1)
   /*
    * The last part of the test tests read commmand evn though we probably won`t use it
    *
    *
    */
} 

object HandshakeTest{
  def main(args: Array[String]): Unit = {
    chiselMainTest(Array("--genHarness", "--test", "--backend", "c",
      "--compile", "--vcd", "--targetDir", "generated"),
      () => Module(new Handshake())) {
        m => new HandshakeTest(m)
      }
  }
}
