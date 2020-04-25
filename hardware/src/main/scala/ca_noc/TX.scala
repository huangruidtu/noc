package TX
import NetworkInterface._
import chisel3._

class TX() extends Module {
  val io = IO(new Bundle {
    val data_in = Input(UInt(96.W))
    val TXAck = Output(Bool())
    val ReqfromOcp = Input(Bool())
    val data_out = Output(UInt(96.W))
    val TXreq = Output(Bool())
    val AckfromRouter = Input(Bool())
  })
  val idle :: s1 :: s2 :: s3 :: s4 :: s5 :: Nil = Enum(6)
  val stateReg = RegInit(idle)
  val dataReg = RegInit(0.U(96.W))
  val countReg = RegInit(0.U(2.W))

  val dataHead = RegInit(0.U(35.W))
  val dataPayload1 = RegInit(0.U(35.W))
  val dataPayload2 = RegInit(0.U(35.W))

  countReg := Mux(countReg === 3.U, 0.U, countReg + 1)


  switch(stateReg) {
    is(idle) {
      when (countReg === 0 ){
        stateReg := s1
      }
    }
    is(s1){
      when (io.ReqfromOcp){
        dataReg := data_in
        if(sizeof(dataReg) === 96) {
          dataHead := dataReg(31, 0)
          dataPayload1 := dataReg(63,32)
          dataPayload2 := dataReg(95,64)
          countReg := 3
          stateReg := s2
          io.TXAck = 1
        }
        else{
          stateReg := s1
        }
      }
    }
    is(s2){
      io.TXAck = 0
      //how to detect valid
      dataHead(34,32) := 110
      dataPayload1(34,32) := 100
      dataPayload2(34,32) := 101
    }
    is(s3) {
      countReg := 0.U
      io.TXreq := 1
      switch(countReg) {
        is(0) {
          io.data_out := dataHead
        }
        is(1) {
          io.data_out := dataPayload1
        }
        is(2) {
          io.data_out := dataPayload2
        }
      }
      when(countReg === 3 && NextReg(countReg) === 0) {
        stateReg := s0
      }
    }
  }
}
