package router
import chisel3._
import RX.{ReadIo, WriterIo}
import chisel3.util.{Cat, Enum, is, switch}
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

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
  val empty :: full :: Nil = Enum(2)
  val stateReg = RegInit(empty)
  val dataReg_N = RegInit(0.U(size.W))
  val dataReg_E = RegInit(0.U(size.W))
  val dataReg_S = RegInit(0.U(size.W))
  val dataReg_W = RegInit(0.U(size.W))
  val dataReg_L = RegInit(0.U(size.W))
  //--------------HPU-------------------------
  val HPU_N = Module(new HPU(size))
  HPU_N.io.data_in := dataReg_N
  val HPU_S = Module(new HPU(size))
  HPU_S.io.data_in := dataReg_S
  val HPU_W = Module(new HPU(size))
  HPU_W.io.data_in := dataReg_W
  val HPU_E = Module(new HPU(size))
  HPU_E.io.data_in := dataReg_E
  val HPU_L = Module(new HPU(size))
  HPU_L.io.data_in := dataReg_L
  //--------------Cross Bar--------------------
  val XBar_N = Module(new XBar(size))
  XBar_N.io.xbar_sele := HPU_N.io.sele
  XBar_N.io.xbar_data_in := HPU_N.io.data_out

  io.router_out_S.dout := XBar_N.io.xbar_data_out_S
  io.router_out_E.dout := XBar_N.io.xbar_data_out_E
  io.router_out_W.dout := XBar_N.io.xbar_data_out_W
  io.router_out_L.dout := XBar_N.io.xbar_data_out_L

  val XBar_S = Module(new XBar(size))
  XBar_S.io.xbar_sele := HPU_S.io.sele
  XBar_S.io.xbar_data_in := HPU_S.io.data_out
  io.router_out_N.dout := XBar_S.io.xbar_data_out_N
  io.router_out_E.dout := XBar_S.io.xbar_data_out_E
  io.router_out_W.dout := XBar_S.io.xbar_data_out_W
  io.router_out_L.dout := XBar_S.io.xbar_data_out_L

  val XBar_W = Module(new XBar(size))
  XBar_W.io.xbar_sele := HPU_W.io.sele
  XBar_W.io.xbar_data_in := HPU_W.io.data_out
  io.router_out_N.dout := XBar_W.io.xbar_data_out_N
  io.router_out_S.dout := XBar_W.io.xbar_data_out_S
  io.router_out_E.dout := XBar_W.io.xbar_data_out_E
  io.router_out_L.dout := XBar_W.io.xbar_data_out_L

  val XBar_E = Module(new XBar(size))
  XBar_E.io.xbar_sele := HPU_E.io.sele
  XBar_E.io.xbar_data_in := HPU_E.io.data_out
  io.router_out_N.dout := XBar_E.io.xbar_data_out_N
  io.router_out_S.dout := XBar_E.io.xbar_data_out_S
  io.router_out_W.dout := XBar_E.io.xbar_data_out_W
  io.router_out_L.dout := XBar_E.io.xbar_data_out_L

  val XBar_L = Module(new XBar(size))
  XBar_L.io.xbar_sele := HPU_L.io.sele
  XBar_L.io.xbar_data_in := HPU_L.io.data_out
  io.router_out_N.dout := XBar_L.io.xbar_data_out_N
  io.router_out_S.dout := XBar_L.io.xbar_data_out_S
  io.router_out_E.dout := XBar_L.io.xbar_data_out_E
  io.router_out_W.dout := XBar_L.io.xbar_data_out_W

  io.router_in_L.full := ( stateReg === full)
  io.router_in_N.full := ( stateReg === full)
  io.router_in_E.full := ( stateReg === full)
  io.router_in_S.full := ( stateReg === full)
  io.router_in_W.full := ( stateReg === full)
  io.router_out_L.empty := ( stateReg === empty)
  io.router_out_N.empty := ( stateReg === empty)
  io.router_out_E.empty := ( stateReg === empty)
  io.router_out_S.empty := ( stateReg === empty)
  io.router_out_W.empty := ( stateReg === empty)


  when( stateReg === empty) {
    when(io.router_in_L.write) {
      stateReg := full
      dataReg_L := io.router_in_L.din
    }.elsewhen(io.router_in_N.write){
      stateReg := full
      dataReg_N := io.router_in_N.din
    }.elsewhen(io.router_in_E.write){
      stateReg := full
      dataReg_E := io.router_in_E.din
    }.elsewhen(io.router_in_S.write){
      stateReg := full
      dataReg_S := io.router_in_S.din
    }.elsewhen(io.router_in_W.write){
      stateReg := full
      dataReg_W := io.router_in_W.din
    }
  }. elsewhen ( stateReg === full) {
    when(io.router_out_L.read) {
      stateReg := empty
      dataReg_L := 0.U // just to better see empty slots in the waveform
    }.elsewhen(io.router_out_N.read){
      stateReg := empty
      dataReg_N := 0.U
    }.elsewhen(io.router_out_E.read){
      stateReg := empty
      dataReg_E := 0.U
    }.elsewhen(io.router_out_S.read){
      stateReg := empty
      dataReg_S := 0.U
    }.elsewhen(io.router_out_W.read){
      stateReg := empty
      dataReg_W := 0.U
    }
  }. otherwise {
    // There should not be an otherwise state
  }
}

class HPU(size : Int) extends Module{
  val io = IO(new Bundle{
    val data_in = Input(UInt(size.W))
    val data_out = Output(UInt(size.W))
    val sele = Output(UInt(4.W))
  })

  val routeReg = RegInit(0.U(16.W))
  val dest = RegInit(0.U(4.W))
  val data_in = RegInit(0.U(size.W))

  data_in := io.data_in
  routeReg := io.data_in(15,0)
  dest := io.data_in(3,0)
  io.sele := dest

  printf("data_in is %x\n",io.data_in)
  printf("data_in is %x\n",data_in)
  printf("Route Reg is %x\n",routeReg)
  printf("Dest Reg is %x\n",dest)

  val data_after_mux = RegInit(0.U(size.W))
  io.data_out := data_after_mux
  when(data_in(33) === 0.U){
    data_after_mux := data_in
  }.elsewhen(data_in(33) === 1.U){
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
  val seleReg = RegInit(0.U(4.W))
  val dataReg = WireInit(0.U(size.W))
  val xbar_data_out_L = WireInit(0.U(size.W))
  val xbar_data_out_N = WireInit(0.U(size.W))
  val xbar_data_out_E = WireInit(0.U(size.W))
  val xbar_data_out_S = WireInit(0.U(size.W))
  val xbar_data_out_W = WireInit(0.U(size.W))
  io.xbar_data_out_E := xbar_data_out_E
  io.xbar_data_out_S := xbar_data_out_S
  io.xbar_data_out_W := xbar_data_out_W
  io.xbar_data_out_N := xbar_data_out_N
  io.xbar_data_out_L := xbar_data_out_L

  dataReg := io.xbar_data_in
  seleReg := io.xbar_sele

  printf("Data in is %x\n",io.xbar_data_in)
  printf("select signal is %x\n",io.xbar_sele)
  printf("DataReg is %x\n",dataReg)
  printf("seleReg is %x\n",seleReg)
  printf("xbar_data_out_W is %x\n",xbar_data_out_W)
  when(seleReg === "b0001".U){
    xbar_data_out_N :=  dataReg
  }.elsewhen(seleReg === "b0010".U){
    xbar_data_out_S :=  dataReg
  }.elsewhen(seleReg === "b0100".U){
    xbar_data_out_W :=  dataReg
  }.elsewhen(seleReg === "b1000".U){
    xbar_data_out_E :=  dataReg
  }.elsewhen(seleReg === "b0000".U){
    xbar_data_out_L :=  dataReg
  }

}
