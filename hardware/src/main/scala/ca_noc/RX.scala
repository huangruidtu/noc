package RX
import NetworkInterface._

class RX() extends Module {
    val io = IO(new Bundle{
      val data_from_router = Input(UInt(105.W))
      val reqFromRouter = Input(Bool())
      val ack = Output(Bool())
      val data_to_OCP = Output(UInt(96.W))
      val req = Output(Bool())
      val ackFromOcp = Input(Bool())
    })
  val idle :: s1 :: s2 :: s3 :: s4 :: s5 :: Nil = Enum(6)
  val stateReg = RegInit(idle)
  val dataReg = RegInit(0.U(105.W))
  val countReg = RegInit(0.U(2.W))

  val dataHead = RegInit(0.U(32.W))
  val dataPayload1 = RegInit(0.U(32.W))
  val dataPayload2 = RegInit(0.U(32.W))
  val data  = RegInit(0.U(96.W))
  data = dataHead + dataPayload1 + dataPayload2
  dataReg = data_from_router

  val phitType0 = dataReg(32,34)
  val phitType1 = dataReg(69,67)
  val phitType2 = dataReg(104,102)

  switch(stateReg){
    is(idle){
      when(reqFromRouter){
        stateReg := s1
      }
    }
    is(s1){
        if(phitType2 === '101'){
          if(phitType1 === '100'){
            if(phitType0 === '110'){
                dataHead := dataReg(101,70)
                dataPayload1 := dataReg(66,35)
                dataPayload2 := dataReg(31,0)
            stateReg := s2
            }
          }
        }
      ack = '1'
    }
    is(s2){
      req = '1'
      ack = '0'
      data_to_OCP := data
      stateReg := s3
    }
    is(s3){
      if(ackFromOcp === '1'){
        req = '0'
        stateReg := idle
      }
    }
  }
}
