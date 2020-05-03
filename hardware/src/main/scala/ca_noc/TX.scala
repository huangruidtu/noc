package TX
import Chisel.Enum
import chisel3._
import chisel3.util.{Cat, is, switch}
import RX.{ReadIo, WriterIo, fifoblock}

class TX(/*width:Int,*/depth:Int) extends Module {
  val width = 35
  val io = IO(new Bundle {
      val txIn = new RX.WriterIo(width-3)
      val txOut = new RX.ReadIo(width)
  })
//----------- Build fifoblocks
  val buffer: Array[fifoblock] = Array.fill(depth){
    Module(new fifoblock(width))
  }
  for (i <- 0 until depth-1){
    buffer (i + 1).io.fifoIN.din := buffer (i).io.fifoOUT.dout
    buffer (i + 1).io.fifoIN.write := ~buffer (i).io.fifoOUT.empty
    buffer (i).io.fifoOUT.read := ~buffer (i + 1).io.fifoIN.full
  }
  //---------------END BUILD-------------------------------------------
  //---------------Start to add phit type------------------------------
  val cnt: UInt = Reg(UInt())
  val result: UInt = Wire(UInt())
  cnt := 0.U
  result := "b000".U
  when(buffer(depth-1).io.fifoOUT.empty === true.B){
      cnt := cnt + 1.U
  }
  //Counting from the beginning, 3 data form a complete format/package
  when(cnt === 3.U){
    cnt := 0.U
  }
  when(cnt === 1.U){
    result := "b110".U
  }.elsewhen(cnt === depth.U){
    result := "b101".U
  }.otherwise{
    result := "b100".U
  }
/*----------------End----------Merge the result with data from fifo out
            VLD SOP EOP + 32 Bits data | 16bits addr + 16 bits route
 */
  val merge: UInt = Wire(UInt())
  merge := Cat(result,buffer(depth-1).io.fifoOUT.dout)
//----------------Wait until 3 fifoblocks are filled
//  val dataready2router: Bool = Wire(false.B)
//  when(buffer(depth-1).io.fifoIN.full){
//    dataready2router := true.B
//  }

  io.txIn <> buffer(0).io.fifoIN
  io.txOut.dout := merge /*buffer(depth-1).io.fifoOUT.dout*/
  io.txOut.empty := (buffer(depth-1).io.fifoOUT.empty /*& dataready2router*/)
  //io.txOut.read := buffer(depth-1).io.fifoOUT.read
  val read = WireInit(false.B)
  read := io.txOut.read
  buffer(depth-1).io.fifoOUT.read := read
}
