package NetworkInterface
import chisel3._
import chisel3.util.{Cat, is, switch}
import RX._
import TX._
import OCP_INF._
import ocp.OcpCoreBus
class NetworkInterface(ADDR_WIDTH:Int,DATA_WIDTH:Int,depth:Int,size:Int) extends Module{
  val io = IO(new Bundle{
    val NI2Ocp_In = new RX.WriterIo(size)
    val NI2Ocp_Out = new RX.ReadIo((size))
    val NI2Router_Out = new RX.WriterIo(size)
    val NI2Router_In = new RX.ReadIo(size)
    val addr = Input(UInt(16.W))
  })
  val Tx = new TX.TX(depth)
  val Rx = new RX.RX(depth)
  val route = Wire(0.U(16.W))
  val Routetable = new route
  val header = Wire(0.U(32.W))

  header := Cat(io.addr,route)

  val cnt = WireInit(0.U)
  when(io.NI2Ocp_In.write){
    when(cnt =/= 3.U){
      cnt := cnt + 1.U
    }.otherwise{cnt := 0.U}
  }

  when(cnt === 1.U){
    Routetable.io.en := true.B
    Tx.io.txIn.din := header
  }.otherwise{
    Routetable.io.en := false.B
    Tx.io.txIn.din := io.NI2Ocp_In.din
  }

  io.NI2Ocp_In <> Tx.io.txIn
  io.NI2Router_Out <> Tx.io.txOut
  io.NI2Router_In <> Rx.io.rxIn
  io.NI2Ocp_Out <> Rx.io.rxOut

  route := Routetable.io.route
}

class route extends Module{
  val io = IO(new Bundle{
    val route = Output(0.U(16.W))
    val en = Input(Bool())
  })
  val route = Wire(0.U(16.W))
  val cnt = WireInit(0.U)
  io.route := route
  when(io.en){
    cnt := cnt + 1.U
  }
  when(cnt === 5.U){
    cnt := 0.U
  }
  when(cnt === 1.U){
    route := "0x0001".U
  }.elsewhen(cnt === 2.U){
    route := "0x0002".U
  }.elsewhen(cnt === 3.U){
    route := "0x0004".U
  }.elsewhen(cnt === 4.U){
    route := "0x0008".U
  }.elsewhen(cnt === 5.U){
    route := "0x0011".U
  }
}