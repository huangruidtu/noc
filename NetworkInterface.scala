package NetworkInterface
import Chisel.Enum
import chisel3._
import chisel3.util.{Cat, is, switch}
import RX._
import TX._
import routeTable._

class NetworkInterface(depth:Int,size:Int, slot:Int) extends Module{
  val io = IO(new Bundle{
    val NI2Ocp_In = new WriterIo(size-3)
    val NI2Ocp_Out = new ReadIo((size-3))
    val NI2Router_Out = new ReadIo(size)
    val NI2Router_In = new WriterIo(size)
    val addr = Input(UInt(16.W))
  })
  val Tx = Module(new TX(depth))
  val Rx = Module(new RX(depth))
  val routelink = Module(new routelink(size,slot))
  printf("Data in to Ni is %x\n",io.NI2Ocp_In.din)
  io.NI2Ocp_In <> routelink.io.NI2Ocp_In
  routelink.io.addr := io.addr
  Tx.io.txIn.din := routelink.io.NI2TX_OUT.dout
  routelink.io.NI2TX_OUT.read := ~Tx.io.txIn.full
  Tx.io.txIn.write := ~routelink.io.NI2TX_OUT.empty
  io.NI2Router_Out <> Tx.io.txOut
  io.NI2Router_In <> Rx.io.rxIn
  io.NI2Ocp_Out <> Rx.io.rxOut

}

class route(slot:Int) extends Module{
  val io = IO(new Bundle{
    val route = Output(UInt(16.W))
    val en = Input(Bool())
    val addr = Input(UInt(16.W))
  })
  val route = WireInit(0.U(16.W))
  val cnt = RegInit(0.U(4.W))
  var addr = 0.U
  addr = io.addr

  when(io.en){
    cnt := cnt + 1.U
  }
  when(cnt === 3.U){
    cnt := 0.U
  }
  printf("ROUTE COUNTER IS %d\n",cnt)

  if(slot == 1){
    val routeTable = Module(new routeTable1(addr))
    route := routeTable.io.route
  }
  else if(slot == 2){
    val routeTable = Module(new routeTable2(addr))
    route := routeTable.io.route
  }
  else if(slot == 3){
    val routeTable = Module(new routeTable3(addr))
    route := routeTable.io.route
  }
  else if(slot == 4){
    val routeTable = Module(new routeTable4(addr))
    route := routeTable.io.route
  }
  else if(slot == 5){
    val routeTable = Module(new routeTable5(addr))
    route := routeTable.io.route
  }
  else if(slot == 6){
    val routeTable = Module(new routeTable6(addr))
    route := routeTable.io.route
  }
  else if(slot == 7){
    val routeTable = Module(new routeTable7(addr))
    route := routeTable.io.route
  }
  else if(slot == 8){
    val routeTable = Module(new routeTable8(addr))
    route := routeTable.io.route
  }
  else if(slot == 9){
    val routeTable = Module(new routeTable9(addr))
    route := routeTable.io.route
  }



  io.route := route
//  when(cnt === 1.U) {
//    route := 0x8822.U
//    //route := 0x0001.U
//  }.elsewhen(cnt === 4.U) {
//    route := 0x0022.U
//    //route := 0x0002.U
//  }.elsewhen(cnt === 7.U) {
//    route := 0x0041.U
//    //route := 0x0004.U
//  }
  printf("INput enable is %b\n",io.en)
  printf("route is %x\n",route)
  printf("route out is %x\n",io.route)
}

class routelink(size:Int,slot:Int) extends Module {
  val io=IO(new Bundle() {
    val NI2Ocp_In = new RX.WriterIo(size-3)
    val NI2TX_OUT = new ReadIo(size-3)
    val addr = Input(UInt(16.W))
  })

  val route = Module(new route(slot))
  val route_enable = WireInit(false.B)
  val route_out = WireInit(0.U(16.W))
  route.io.en := io.NI2Ocp_In.write
  route_out := route.io.route

  val addr = WireInit(0.U(16.W))
  val header = WireInit(0.U(32.W))
  addr := io.addr
  route.io.addr := addr

  header := Cat(addr,route_out)
  printf("Header is %b\n",header)
  printf("Header is %x\n",header)
  val cnt = RegInit(0.U(3.W))
  when(io.NI2Ocp_In.write){
    cnt := cnt + 1.U
  }
  when(cnt === 3.U){
    cnt := 1.U
  }
  printf("NetworkINterface COUNTER IS %d\n",cnt)
  val  NI2TX_OUT = WireInit(0.U(32.W))
  val  NI2Ocp_In = WireInit(0.U(32.W))
  NI2Ocp_In := io.NI2Ocp_In.din
  io.NI2TX_OUT.dout := NI2TX_OUT
  when(cnt === 1.U){
    //route.io.en := true.B
    NI2TX_OUT := header
  }.otherwise{
    //route.io.en := false.B
    NI2TX_OUT := NI2Ocp_In
  }
  printf("routelink output is %x\n",NI2TX_OUT)
  //-----------FSM------------------
  val empty :: full :: Nil = Enum(2)
  val stateReg = RegInit(empty)
  val dataReg = RegInit(0.U(32.W))

  io.NI2Ocp_In.full := (stateReg === full)
  io.NI2TX_OUT.empty := (stateReg === empty)
  printf("FULL SIGNAL IS %b\n",io.NI2Ocp_In.full)
  when( stateReg === empty) {
    when(io.NI2Ocp_In.write) {
      stateReg := full
      dataReg := io.NI2Ocp_In.din
//      printf("data input is: %d\n",dataReg)
    }
  }. elsewhen ( stateReg === full) {
    when(io.NI2TX_OUT.read) {
      stateReg := empty
      dataReg := 0.U // just to better see empty slots in the waveform
    }
  }. otherwise {
    // There should not be an otherwise state
  }
}