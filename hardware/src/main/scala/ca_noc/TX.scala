package TX
import Chisel.Enum
import chisel3._
import chisel3.util.{is, switch}
import RX.{WriterIo,ReadIo,fifoblock}

class TX(/*width:Int,*/depth:Int) extends Module {
  val width = 35
  val io = IO(new Bundle {
      val txIn = new RX.WriterIo(width-3)
      val txOut = new RX.ReadIo(width)
  })

  val slotnumber = Int
  val buffer: Array[fifoblock] = Array.fill(depth){
    Module(new fifoblock(width))
  }
  for (i <- 0 until depth-1){
    buffer (i + 1).io.fifoIN.din := buffer (i).io.fifoOUT.dout
    buffer (i + 1).io.fifoIN.write := buffer (i).io.fifoOUT.empty
    buffer (i).io.fifoOUT.read := buffer (i + 1).io.fifoIN.full
  }
  buffer(0).io.fifoOUT.dout := 5.U + buffer(0).io.fifoIN.din  //101
  buffer(depth-1).io.fifoOUT.dout := 6.U + buffer(depth).io.fifoIN.din //110
  for(i <- 1 until depth-2){
    buffer (i).io.fifoOUT.dout := 4.U + buffer(i).io.fifoIN.din //100
  }

  val dataready2router: Bool = Wire(false.B)
  when(buffer(depth-1).io.fifoIN.full){
    dataready2router := true.B
  }
  io.txIn <> buffer(0).io.fifoIN
  io.txOut.dout <> buffer(depth-1).io.fifoOUT.dout
  io.txOut.empty <> (buffer(depth-1).io.fifoOUT.empty & dataready2router)
  io.txOut.read <> buffer(depth-1).io.fifoOUT.read
}
//val data_in = Input(UInt(96.W))
//val TXAck = Output(Bool())
//val ReqfromOcp = Input(Bool())
//val data_out = Output(UInt(96.W))
//val TXreq = Output(Bool())
//val AckfromRouter = Input(Bool())
//val idle :: s1 :: s2 :: s3 :: s4 :: s5 :: Nil = Enum(6)
//  val stateReg = RegInit(idle)
//  val dataReg = RegInit(0.U(96.W))
//  val countReg = RegInit(0.U(2.W))
//
//  val dataHead = RegInit(0.U(35.W))
//  val dataPayload1 = RegInit(0.U(35.W))
//  val dataPayload2 = RegInit(0.U(35.W))
//
//  countReg := Mux(countReg === 3.U, 0.U, countReg + 1.U)
//
//
//  switch(stateReg) {
//    is(idle) {
//      when (countReg === 0.U ){
//        stateReg := s1
//      }
//    }
//    is(s1){
//      when (io.ReqfromOcp){
//        dataReg := io.data_in
//        if(sizeof(dataReg) === 96.U) {
//          dataHead := dataReg(31, 0)
//          dataPayload1 := dataReg(63,32)
//          dataPayload2 := dataReg(95,64)
//          countReg := 3.U
//          stateReg := s2
//          io.TXAck = true.B
//        }
//        else{
//          stateReg := s1
//        }
//      }
//    }
//    is(s2){
//      io.TXAck = 0.U
//      //how to detect valid
//      dataHead(34,32) := 110
//      dataPayload1(34,32) := 100
//      dataPayload2(34,32) := 101
//    }
//    is(s3) {
//      countReg := 0.U
//      io.TXreq := 1
//      switch(countReg) {
//        is(0) {
//          io.data_out := dataHead
//        }
//        is(1) {
//          io.data_out := dataPayload1
//        }
//        is(2) {
//          io.data_out := dataPayload2
//        }
//      }
//      when(countReg === 3 && NextReg(countReg) === 0) {
//        stateReg := s0
//      }
//    }
//  }