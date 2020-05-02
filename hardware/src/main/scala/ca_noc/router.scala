package router
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.util.Cat

class router(size:Int) extends Module{
  val io = IO(new Bundle{
    val router_in_N = new WriterIo(size)
    val router_out_N = new ReadIo(size)
  })
  //--------------HPU-------------------------
  val data_in = RegInit(0.U(size.W))
  val data_after_mux = RegInit(0.U(size.W))
  val routeReg = RegInit(0.U(16.W))
  val dest = RegInit(0.U(4.W))

  routeReg := data_in(15,0)
  when(data_in(33) === 0.U){
    data_after_mux := data_in
  }.otherwise{
    dest := routeReg(3,0) //???
    routeReg := routeReg>>4.U
    data_after_mux := Cat(data_in(size-1,16),routeReg)
  }
  //--------------Cross Bar--------------------
  when(dest ==== "b00".U){

  }


  io.router_in_N.full := true.B
}
