package routeTable
import chisel3._
import chisel3.experimental.IO
import chisel3.util._

/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
*
* */
class routeTable1(/*addr:UInt*/) extends Module{
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
  val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 2.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 3.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 4.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 5.U){
    route := 0x0082.U
  }
  .elsewhen(addr === 6.U){
    route := 0x0882.U
  }
  .elsewhen(addr === 7.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 8.U){
    route := 0x0081.U
  }
  .elsewhen(addr === 9.U){
    route := 0x0014.U
  }
}
/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
*
* */
class routeTable2(/*addr:UInt*/) extends Module {
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
 val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 1.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 3.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 4.U){
    route := 0x0042.U
  }
  .elsewhen(addr === 5.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 6.U){
    route := 0x0082.U
  }
  .elsewhen(addr === 7.U){
    route := 0x0041.U
  }
  .elsewhen(addr === 8.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 9.U){
    route := 0x0081.U
  }
}
/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
*
* */
class routeTable3(/*addr:UInt*/) extends Module{
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
 val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 2.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 1.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 6.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 9.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 5.U){
    route := 0x0042.U
  }
  .elsewhen(addr === 4.U){
    route := 0x0082.U
  }
  .elsewhen(addr === 8.U){
    route := 0x0041.U
  }
  .elsewhen(addr === 7.U){
    route := 0x0018.U
  }
}
/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
*
* */
class routeTable4(/*addr:UInt*/) extends Module{
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
 val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 6.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 5.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 7.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 1.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 2.U){
    route := 0x0081.U
  }
  .elsewhen(addr === 3.U){
    route := 0x0014.U
  }
  .elsewhen(addr === 8.U){
    route := 0x0082.U
  }
  .elsewhen(addr === 9.U){
    route := 0x0042.U
  }
}
/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
* NSWE
* */
class routeTable5(/*addr:UInt*/) extends Module{
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
 val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 2.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 8.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 4.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 6.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 1.U){
    route := 0x0041.U
  }
  .elsewhen(addr === 3.U){
    route := 0x0081.U
  }
  .elsewhen(addr === 7.U){
    route := 0x0042.U
  }
  .elsewhen(addr === 9.U){
    route := 0x0082.U
  }
}
/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
*
* */
class routeTable6(/*addr:UInt*/) extends Module{
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
 val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 3.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 9.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 5.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 4.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 2.U){
    route := 0x0041.U
  }
  .elsewhen(addr === 1.U){
    route := 0x0081.U
  }
  .elsewhen(addr === 8.U){
    route := 0x0042.U
  }
  .elsewhen(addr === 7.U){
    route := 0x0082.U
  }
}
/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
*
* */
class routeTable7(/*addr:UInt*/) extends Module{
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
 val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 4.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 1.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 9.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 8.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 6.U){
    route := 0x0041.U
  }
  .elsewhen(addr === 5.U){
    route := 0x0081.U
  }
  .elsewhen(addr === 3.U){
    route := 0x0042.U
  }
  .elsewhen(addr === 2.U){
    route := 0x0082.U
  }
}
/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
*
* */
class routeTable8(/*addr:UInt*/) extends Module{
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
 val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 5.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 2.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 7.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 9.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 4.U){
    route := 0x0041.U
  }
  .elsewhen(addr === 6.U){
    route := 0x0081.U
  }
  .elsewhen(addr === 1.U){
    route := 0x0042.U
  }
  .elsewhen(addr === 3.U){
    route := 0x0082.U
  }
}
/*
*
* North: 0001 0x1
* South: 0010 0x2
* West : 0100 0x4
* East : 1000 0x8
* Local: 0000 0x0
*
* */
class routeTable9(/*addr:UInt*/) extends Module{
  val io = IO(new Bundle() {
    val route = Output(UInt(16.W))
    val addr = Input(UInt(16.W))
  })
 val route = WireInit(0.U(16.W))
  val addr = Wire(UInt(16.W))
  addr := io.addr
  io.route := route

  when(addr === 6.U){
    route := 0x0001.U
  }
  .elsewhen(addr === 3.U){
    route := 0x0002.U
  }
  .elsewhen(addr === 8.U){
    route := 0x0004.U
  }
  .elsewhen(addr === 7.U){
    route := 0x0008.U
  }
  .elsewhen(addr === 5.U){
    route := 0x0041.U
  }
  .elsewhen(addr === 4.U){
    route := 0x0081.U
  }
  .elsewhen(addr === 2.U){
    route := 0x0042.U
  }
  .elsewhen(addr === 1.U){
    route := 0x0082.U
  }
}