package router
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.util.Cat

class router(size:Int) extends Module{
  val io = IO(new Bundle{
    val router_in_N = new WriterIo(size)
    val router_out_N = new ReadIo(size)
    val router_in_S = new WriterIo(size)
    val router_out_S = new ReadIo(size)
    val router_in_W = new WriterIo(size)
    val router_out_W = new ReadIo(size)
    val router_in_E = new WriterIo(size)
    val router_out_E = new ReadIo(size)
    val router_in_L = new WriterIo(size)
    val router_out_L = new ReadIo(size)
  })
  //--------------HPU-------------------------
  val HPU_N = Module(new HPU(size))
  HPU_N.data_in := io.router_in_N.din
  val HPU_S = Module(new HPU(size))
  HPU_S.data_in := io.router_in_S.din
  val HPU_W = Module(new HPU(size))
  HPU_W.data_in := io.router_in_W.din
  val HPU_E = Module(new HPU(size))
  HPU_E.data_in := io.router_in_E.din
  val HPU_L = Module(new HPU(size))
  HPU_L.data_in := io.router_in_L.din
  //--------------Cross Bar--------------------
  val XBar_N = Module(new XBar(size))
  XBar_N.xbar_sele := HPU_N.sele
  XBar_N.xbar_data_in := HPU_N.data_out
  io.router_out_N.dout := XBar_N.xbar_data_out_N
  io.router_out_S.dout := XBar_N.xbar_data_out_S
  io.router_out_E.dout := XBar_N.xbar_data_out_E
  io.router_out_W.dout := XBar_N.xbar_data_out_W
  io.router_out_L.dout := XBar_N.xbar_data_out_L
  
  val XBar_S = Module(new XBar(size))
  XBar_S.xbar_sele := HPU_S.sele
  XBar_S.xbar_data_in := HPU_S.data_out
  io.router_out_N.dout := XBar_S.xbar_data_out_N
  io.router_out_S.dout := XBar_S.xbar_data_out_S
  io.router_out_E.dout := XBar_S.xbar_data_out_E
  io.router_out_W.dout := XBar_S.xbar_data_out_W
  io.router_out_L.dout := XBar_S.xbar_data_out_L
  
  val XBar_W = Module(new XBar(size))
  XBar_W.xbar_sele := HPU_W.sele
  XBar_W.xbar_data_in := HPU_W.data_out
  io.router_out_N.dout := XBar_W.xbar_data_out_N
  io.router_out_S.dout := XBar_W.xbar_data_out_S
  io.router_out_E.dout := XBar_W.xbar_data_out_E
  io.router_out_W.dout := XBar_W.xbar_data_out_W
  io.router_out_L.dout := XBar_W.xbar_data_out_L
  
  val XBar_E = Module(new XBar(size))
  XBar_E.xbar_sele := HPU_E.sele
  XBar_E.xbar_data_in := HPU_E.data_out
  io.router_out_N.dout := XBar_E.xbar_data_out_N
  io.router_out_S.dout := XBar_E.xbar_data_out_S
  io.router_out_E.dout := XBar_E.xbar_data_out_E
  io.router_out_W.dout := XBar_E.xbar_data_out_W
  io.router_out_L.dout := XBar_E.xbar_data_out_L
  
  val XBar_L = Module(new XBar(size))
  XBar_L.xbar_sele := HPU_L.sele
  XBar_L.xbar_data_in := HPU_L.data_out
  io.router_out_N.dout := XBar_L.xbar_data_out_N
  io.router_out_S.dout := XBar_L.xbar_data_out_S
  io.router_out_E.dout := XBar_L.xbar_data_out_E
  io.router_out_W.dout := XBar_L.xbar_data_out_W
  io.router_out_L.dout := XBar_L.xbar_data_out_L


  io.router_in_N.full := true.B
}

class HPU(size : Int) extends Module{
  val io = IO(new Bundle{
    val data_in = Input(UInt(size.W))
    val data_out = Output(UInt(size.W))
    val sele = Output(UInt(4.W))
  })
  val data_after_mux = WireInit(0.U(size.W))
  val routeReg = WireInit(0.U(16.W))
  val dest = WireInit(0.U(4.W))
  val data_in = WireInit(0.U(size.W))
  data_in := io.data_in

  routeReg := data_in(15,0)
  when(data_in(33) === 0.U){
    data_after_mux := data_in
  }.otherwise{
    dest := routeReg(3,0) //???
    routeReg := routeReg>>4.U
    data_after_mux := Cat(data_in(size-1,16),routeReg)
  }
}

class XBar(size : Int) extends Module{
    val io = IO(new Bundle{
        val xbar_sele = Input(UInt(4.W))
        val xbar_data_in = Input(UInt(size.W))
        val xbar_data_out_N = Output(UInt(size.W))
        val xbar_data_out_S = Output(UInt(size.W))
        val xbar_data_out_E = Output(UInt(size.W))
        val xbar_data_out_W = Output(UInt(size.W))
        val xbar_data_out_L = Output(UInt(size.W))

    })
    val seleReg = WireInit(0.U(4.W))
    val dataReg = WireInit(0.U(size.W))
    dataReg := io.xbar_data_in
    seleReg := io.xbar_sele
    when(seleReg === "b0001".U){
        io.xbar_data_out_N :=  dataReg
    }.elsewhen(seleReg === "b0010".U){
        io.xbar_data_out_S :=  dataReg
    }.elsewhen(seleReg === "b0100".U){
        io.xbar_data_out_W :=  dataReg
    }.elsewhen(seleReg === "b1000".U){
        io.xbar_data_out_E :=  dataReg
    }.elsewhen(seleReg === "b0000".U){
        io.xbar_data_out_L :=  dataReg
    }.otherwise{//do nothing}
