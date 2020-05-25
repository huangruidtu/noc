package Router2Rx
import chisel3._
import NetworkInterface.NetworkInterface
import RX.{ReadIo, WriterIo}
import router.router

class Router2Rx (depth:Int,size:Int, slot:Int) extends Module{
  val io = IO(new Bundle() {
    val NI2Ocp_In = new WriterIo(size-3)
    val NI2Ocp_Out = new ReadIo((size-3))
    val addr = Input(UInt(16.W))
    val router_in_N = new WriterIo(size)
    val router_out_N = new ReadIo(size)
    val router_in_S = new WriterIo(size)
    val router_out_S = new ReadIo(size)
    val router_in_W = new WriterIo(size)
    val router_out_W = new ReadIo(size)
    val router_in_E = new WriterIo(size)
    val router_out_E = new ReadIo(size)
  })

  val NI = Module(new NetworkInterface(depth,size,slot))
  val Router = Module(new router(size))

  io.NI2Ocp_In  <> NI.io.NI2Ocp_In
  io.NI2Ocp_Out <> NI.io.NI2Ocp_Out
  io.addr       <> NI.io.addr
  Router.io.router_in_L.din := NI.io.NI2Router_Out.dout
  Router.io.router_in_L.write := ~NI.io.NI2Router_Out.empty
  NI.io.NI2Router_Out.read := ~Router.io.router_in_L.full

  NI.io.NI2Router_In.din :=Router.io.router_out_L.dout
  NI.io.NI2Router_In.write := ~Router.io.router_out_L.empty
  Router.io.router_out_L.read := ~NI.io.NI2Router_In.full
  io.router_in_W <> Router.io.router_in_W
  io.router_in_S <> Router.io.router_in_S
  io.router_in_E <> Router.io.router_in_E
  io.router_in_N <> Router.io.router_in_N
  io.router_out_S <> Router.io.router_out_S
  io.router_out_W <> Router.io.router_out_W
  io.router_out_E <> Router.io.router_out_E
  io.router_out_N <> Router.io.router_out_N

}
