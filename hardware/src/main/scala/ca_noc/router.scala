package noc.router

import Chisel._

class RouterEmulator() extends Module(){
	val io = IO(new Bundle{
    val start = Input(Bool())
    val readyIn = Input(Bool())
    val packetOut = Output(UInt(width = 96))
    val validOut = Output(Bool())
    val readyOut = Output(Bool())
    val packetIn = Input(UInt(width = 96))
    val validIn = Input(Bool())
	})

  val countReg = Reg(init = UInt(0, 32))
  io.packetOut := UInt(0)
  io.validOut := Bool(false)
  io.readyOut := Bool(false)

  when(io.start === Bool(true)){
    countReg := countReg + UInt(1)
    io.packetOut(31, 0) := countReg
    io.packetOut(63, 32) := countReg
    io.validOut := Bool(true)
    io.readyOut := Bool(true)
  }

}
