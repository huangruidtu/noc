package router
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.util.{Cat, Enum}

class router(size:Int) extends Module {
  val io = IO(new Bundle {
    //--------------E Port----------------
    val router_in_E = new RX.WriterIo(size)
    val router_out_E = new RX.ReadIo(size)

    //--------------S Port----------------
    val router_in_S = new RX.WriterIo(size)
    val router_out_S = new RX.ReadIo(size)

    //--------------W Port----------------
    val router_in_W = new RX.WriterIo(size)
    val router_out_W = new RX.ReadIo(size)

    //--------------N Port----------------
    val router_in_N = new RX.WriterIo(size)
    val router_out_N = new RX.ReadIo(size)

    //--------------Local Port----------------
    val router_in_L = new RX.WriterIo(size)
    val router_out_L = new RX.ReadIo(size)
  })

  //--------------HPU-------------------------
  val data_in = RegInit(0.U(size.W))
  val data_after_mux = RegInit(0.U(size.W))
  val routeReg = RegInit(0.U(16.W))
  val dest = RegInit(0.U(4.W))

  val idle :: dataIn :: parsing :: dataOut :: Nil = Enum(4)
  val stateReg = RegInit(idle)
  val write = Input(Bool())
  val xBar_in = RegInit(0.U(size.W))
  write := io.router_in_E.write | io.router_in_S.write |
    io.router_in_W.write | io.router_in_N.write | io.router_in_L.write

  when(stateReg === idle) {
    when(write) {
      stateReg := dataIn
    }.elsewhen(stateReg === dataIn) {
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
      stateReg := parsing
    }.elsewhen(stateReg === parsing) {
      when(xBar_in(33) === 0.U) {
        data_after_mux := xBar_in
      }.otherwise {
        dest := routeReg(3, 0) //???
        routeReg := routeReg >> 4.U
        data_after_mux := Cat(data_in(size - 1, 16), routeReg)
      }
      stateReg := dataOut
    }.elsewhen(stateReg === dataOut) {
      when(dest === "b0001".U) { //East Port
        io.router_out_E.empty := false.B
        io.router_out_E.dout := data_after_mux
      }.elsewhen(dest === "b0010".U) { //South Port
        io.router_out_S.empty := false.B
        io.router_out_S.dout := data_after_mux
      }.elsewhen(dest === "b0100".U) { // West Port
        io.router_out_W.empty := false.B
        io.router_out_W.dout := data_after_mux
      }.elsewhen(dest === "b1000".U) { // North Port
        io.router_out_N.empty := false.B
        io.router_out_N.dout := data_after_mux
      }.elsewhen(dest === "b0000".U) { // Local Port
        io.router_out_L.empty := false.B
        io.router_out_L.dout := data_after_mux
      }.otherwise {
        // do nothing
      }
      stateReg := idle
    }
  }
}




























//package router
//import chisel3._
//import RX.{ReadIo, WriterIo}
//import chisel3.util.Cat
//
//class router(size:Int) extends Module{
//  val io = IO(new Bundle{
//    val router_in_N = new WriterIo(size)
//    val router_out_N = new ReadIo(size)
//  })
//  //--------------HPU-------------------------
//  val data_in = RegInit(0.U(size.W))
//  val HPU_N = new HPU(size)
//  //--------------Cross Bar--------------------
//  when(dest ==== "b00".U){
//
//  }
//
//
//  io.router_in_N.full := true.B
//}
//class HPU(size : Int) extends Module{
//  val io = IO(new Bundle{
//    val data_in = Input(UInt(size.W))
//    val data_out = Output(UInt(size.W))
//    val sele = Output(UInt(2.W))
//  })
//  val data_after_mux = WireInit(0.U(size.W))
//  val routeReg = WireInit(0.U(16.W))
//  val dest = WireInit(0.U(4.W))
//  val data_in = WireInit(0.U(size.U))
//  data_in := io.data_in
//
//  routeReg := data_in(15,0)
//  when(data_in(33) === 0.U){
//    data_after_mux := data_in
//  }.otherwise{
//    dest := routeReg(3,0) //???
//    routeReg := routeReg>>4.U
//    data_after_mux := Cat(data_in(size-1,16),routeReg)
//  }
//}