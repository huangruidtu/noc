package router
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.util.Cat

class router(size:Int) extends Module{
  val io = IO(new Bundle{
    //--------------E Port----------------
    val router_in_E = new WriterIo(size)
    val router_out_E = new ReadIo(size)
   
   //--------------S Port----------------
    val router_in_S = new WriterIo(size)
    val router_out_S = new ReadIo(size)
   
   //--------------W Port----------------
    val router_in_W = new WriterIo(size)
    val router_out_W = new ReadIo(size)
    
    //--------------N Port----------------
    val router_in_N = new WriterIo(size)
    val router_out_N = new ReadIo(size)
    
    //--------------Local Port----------------
    val router_in_L = new WriterIo(size)
    val router_out_L = new ReadIo(size)
  })
  
  //--------------HPU-------------------------
  val data_in = RegInit(0.U(size.W))
  val data_after_mux = RegInit(0.U(size.W))
  val routeReg = RegInit(0.U(16.W))
  val dest = RegInit(0.U(4.W))

//  routeReg := data_in(15,0)
//  when(data_in(33) === 0.U){
//    data_after_mux := data_in
//  }.otherwise{
//    dest := routeReg(3,0) //???
//    routeReg := routeReg>>4.U
//    data_after_mux := Cat(data_in(size-1,16),routeReg)
//  }
//  when(dest === "b0001".U){ //East Port
//    io.router_out_E.dout := data_after_mux
//  }.elsewhen(dest === "b0010".U){ //South Port
//    io.router_out_S.dout := data_after_mux
//  }.elsewhen(dest === "b0100".U){ // West Port
//    io.router_out_W.dout := data_after_mux
//  }.elsewhen(dest === "b1000".U){ // North Port
//    io.router_out_N.dout := data_after_mux
//  }.otherwise{
    // do nothing
//  }
    
//  io.router_in_N.full := true.B


  //--------------Cross Bar--------------------
  val idle :: in :: parsing :: out :: Nil = Enum(4)
  val stateReg = RegInit(idle)
  val in = Input(Bool())
  val xBar_in = RegInit(0.U(size.W))

  when( stateReg === idle ) {
      when(io.router_in_E.write) {
        xBar_in := io.router_in_E.din
      }.elsewhen(io.router_in_S.write) {
        xBar_in := io.router_in_S.din
      }.elsewhen(io.router_in_W.write) {
        xBar_in := io.router_in_W.din
      }.elsewhen(io.router_in_N.write) {
        xBar_in := io.router_in_N.din
      }.elsewhen(io.router_in_L.write) {
        xBar_in := io.router_in_L.din
      }.otherwise {
        //do nothing
      }
        stateReg := in
  }.elsewhen (stateReg === parsing){
        when(xBar_in(33) === 0.U){
        data_after_mux := xBar_in
        }.otherwise{
            dest := routeReg(3,0) //???
            routeReg := routeReg>>4.U
            data_after_mux := Cat(data_in(size-1,16),routeReg)
        }
        stateReg := out
  }.elsewhen (stateReg === out) {
      when(dest === "b0001".U){ //East Port
          io.router_out_E.dout := data_after_mux
      }.elsewhen(dest === "b0010".U){ //South Port
          io.router_out_S.dout := data_after_mux
      }.elsewhen(dest === "b0100".U){ // West Port
          io.router_out_W.dout := data_after_mux
      }.elsewhen(dest === "b1000".U){ // North Port
          io.router_out_N.dout := data_after_mux
      }.elsewhen(dest === "b0000".U){ // Local Port
          io.router_out_L.dout := data_after_mux
      }.otherwise{
          // do nothing
      }
      stateReg := idle
  }



  


}
