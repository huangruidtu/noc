package TX
import Chisel.Enum
import chisel3._
import chisel3.util.{Cat, is, switch}
import RX.{ReadIo, WriterIo, fifoblock}

class TX_fifo(/*width:Int,*/depth:Int) extends Module {
  val width = 35
  val io = IO(new Bundle {
    val txIn = new WriterIo(width - 3)
    val txOut = new ReadIo(width-3)
  })
  //----------- Build fifoblocks
  val buffer: Array[fifoblock] = Array.fill(depth) {
    Module(new fifoblock(width))
  }

  for (i <- 0 until depth - 1) {
    buffer(i + 1).io.fifoIN.din := buffer(i).io.fifoOUT.dout
    buffer(i + 1).io.fifoIN.write := ~buffer(i).io.fifoOUT.empty
    buffer(i).io.fifoOUT.read := ~buffer(i + 1).io.fifoIN.full
  }
  //---------------END BUILD-------------------------------------------
  io.txIn <> buffer(0).io.fifoIN
  io.txOut <> buffer(depth-1).io.fifoOUT
}
  //---------------Start to add phit type------------------------------
class TXProcess(depth:Int) extends Module{
  val width = 35
    val io = IO(new Bundle {
      val txIn = new RX.WriterIo(width - 3)
      val txOut = new RX.ReadIo(width)
    })

  //counter 1 to 3
  val cnt: UInt = RegInit(1.U(3.W))
  val result: UInt = WireInit(0.U(3.W))
  when(io.txIn.write === true.B){
    cnt := cnt + 1.U
    printf("cnt is %d\n",cnt)
  }

  //Counting from the beginning, 3 data form a complete format/package
  when(cnt === 3.U & io.txIn.write ){
    cnt := 1.U
  }
  when(cnt === 1.U){
    result := "b110".U
  }.elsewhen(cnt === depth.U){
    result := "b101".U
  }.otherwise{
    result := "b100".U
  }
 printf("result is %d\n",result)
/* Merge the result with data from fifo out
   VLD SOP EOP + 32 Bits data | 16bits addr + 16 bits route
*/
  val merge: UInt = Wire(UInt())
  val data2mux = Wire(UInt())
  data2mux := io.txIn.din
    printf("data2mux is %d\n",data2mux)
  merge := Cat(result,data2mux)
  io.txOut.dout := merge
  printf("merge %b\n",merge)
  //-----------FSM------------------
  val empty :: full :: Nil = Enum(2)
  val stateReg = RegInit(empty)
  val dataReg = RegInit(0.U(width.W))

  io.txIn.full := (stateReg === full)
  io.txOut.empty := (stateReg === empty)

  when( stateReg === empty) {
    when(io.txIn.write) {
      stateReg := full
      dataReg := io.txIn.din
      printf("data input is: %d\n",dataReg)
    }
  }. elsewhen ( stateReg === full) {
    when(io.txOut.read) {
      stateReg := empty
      dataReg := 0.U // just to better see empty slots in the waveform
    }
  }. otherwise {
    // There should not be an otherwise state
  }

}

class TX(depth:Int) extends Module{
  val width = 35
  val io = IO(new Bundle {
    val txIn = new RX.WriterIo(width - 3)
    val txOut = new RX.ReadIo(width)
  })
  val TX_fifo = Module(new TX_fifo(depth))
  val TXProcess = Module(new TXProcess(depth))

  io.txIn <> TX_fifo.io.txIn
  io.txOut <> TXProcess.io.txOut
  TX_fifo.io.txOut.read := ~TXProcess.io.txIn.full
  TXProcess.io.txIn.din := TX_fifo.io.txOut.dout
  TXProcess.io.txIn.write := ~TX_fifo.io.txOut.empty
}
