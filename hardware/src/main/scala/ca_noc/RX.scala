package RX
import Chisel.Enum
import chisel3._
import com.sun.org.apache.bcel.internal.generic.NEW

class WriterIo(size : Int) extends Bundle{
  val write = Input(Bool())
  val full = Output(Bool())
  val din = Input(UInt(size.W))
}
class ReadIo(size : Int) extends Bundle{
  val read = Input(Bool())
  val empty = Output(Bool())
  val dout = Output(UInt(size.W))
}
/*
* fifoIN    -> dataReg ->Mux -> fifoOut
*         width   ^           |
*                 |___________v
*
* */
class fifoblock(width:Int) extends Module{
  val io = IO(new Bundle {
    val fifoIN = new WriterIo(width)
    val fifoOUT = new ReadIo((width))
  })
  val empty :: full :: Nil = Enum(2)
  val stateReg = RegInit(empty)
  val dataReg = RegInit(0.U(width.W))
  val datamux = Wire(UInt())
  when(io.fifoOUT.read){
    datamux := dataReg
  }
  io.fifoIN.full := ( stateReg === full)
  io.fifoOUT. empty := ( stateReg === empty)
  //io.fifoOUT.dout := dataReg
  io.fifoOUT.dout := datamux

  when( stateReg === empty) {
    when(io.fifoIN.write) {
      stateReg := full
      dataReg := io.fifoIN.din
    }
  }. elsewhen ( stateReg === full) {
    when(io.fifoOUT.read) {
      stateReg := empty
      dataReg := 0.U // just to better see empty slots in the waveform
    }
  }. otherwise {
    // There should not be an otherwise state
  }

}

/*rx---------------------------------------------------
* |  Router -> IN -> BLOCK1 -> BLOCK2 -> .... -> BLOCK_N -> OUT -> OCP INTERFACE  |
* (32+3)*3  abandon the 3 bits phit type store the 32*3 into fifo and ready to transmit into Patmos through OCP IO
* */
class RX(/*width: Int,*/depth: Int) extends Module {
  val width = 35
  val io = IO(new Bundle{
      val rxIn = new WriterIo(width-3)
      val rxOut = new ReadIo(width-3)
      })

  val buffer = Array.fill(depth){
    Module(new fifoblock(width-3))
  }
  for (i <- 0 until depth-1){
    buffer (i + 1).io.fifoIN.din := buffer (i).io.fifoOUT.dout
    buffer (i + 1).io.fifoIN.write := buffer (i).io.fifoOUT.empty
    buffer (i).io.fifoOUT.read := buffer (i + 1).io.fifoIN.full
  }
  io.rxIn.din(width-4,0) <> buffer(0).io.fifoIN.din
  io.rxOut.dout <> buffer(depth-1).io.fifoOUT.dout
}
