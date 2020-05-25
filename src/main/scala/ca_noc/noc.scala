package noc
import chisel3._
import NetworkInterface.NetworkInterface
import router.router
import OcpInterface._
import ocp.OcpCoreSlavePort

class noc_M extends Bundle{
  val Cmd  = Input(UInt(3.W))
  val Addr = Input(UInt(16.W))
  val Data = Input(UInt(32.W))
}
class noc_S extends Bundle{
  val Resp = Output(UInt(2.W))
  val Data = Output(UInt(32.W))
}

class noc(depth:Int,size:Int) extends Module {
  val io = IO(new Bundle {
//    val ocpIO_CORE1_IN = new noc_M
//    val ocpIO_CORE2_IN = new noc_M
//    val ocpIO_CORE3_IN = new noc_M
//    val ocpIO_CORE4_IN = new noc_M
//    val ocpIO_CORE5_IN = new noc_M
//    val ocpIO_CORE6_IN = new noc_M
//    val ocpIO_CORE7_IN = new noc_M
//    val ocpIO_CORE8_IN = new noc_M
//    val ocpIO_CORE9_IN = new noc_M
//
//    val ocpIO_CORE1_OUT = new noc_S
//    val ocpIO_CORE2_OUT = new noc_S
//    val ocpIO_CORE3_OUT = new noc_S
//    val ocpIO_CORE4_OUT = new noc_S
//    val ocpIO_CORE5_OUT = new noc_S
//    val ocpIO_CORE6_OUT = new noc_S
//    val ocpIO_CORE7_OUT = new noc_S
//    val ocpIO_CORE8_OUT = new noc_S
//    val ocpIO_CORE9_OUT = new noc_S
    val CorePort1 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val CorePort2 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val CorePort3 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val CorePort4 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val CorePort5 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val CorePort6 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val CorePort7 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val CorePort8 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
    val CorePort9 = new OcpCoreSlavePort(addrWidth = 16,dataWidth = 32)
  })
printf("Noc CorePort1 input is %x\n",io.CorePort1.M.Data)
  printf("Noc CorePort1 inAddr is %x\n",io.CorePort1.M.Addr)

  val ocpIO_CORE1 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))
  val ocpIO_CORE2 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))
  val ocpIO_CORE3 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))
  val ocpIO_CORE4 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))
  val ocpIO_CORE5 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))
  val ocpIO_CORE6 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))
  val ocpIO_CORE7 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))
  val ocpIO_CORE8 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))
  val ocpIO_CORE9 = Module(new OcpInterface(ADDR_WIDTH = 16,DATA_WIDTH = 32))

  printf("NocCorePort1 out data is %x\n",ocpIO_CORE1.io.OcpOut.dout)
  printf("NocCorePort1 out addr is %x\n",ocpIO_CORE1.io.addr)
  printf("NocCorePort1 out empty is %b\n",ocpIO_CORE1.io.OcpOut.empty)


  val NI_CORE1 = Module(new NetworkInterface(depth, size, slot = 1))
  val NI_CORE2 = Module(new NetworkInterface(depth, size, slot = 2))
  val NI_CORE3 = Module(new NetworkInterface(depth, size, slot = 3))
  val NI_CORE4 = Module(new NetworkInterface(depth, size, slot = 4))
  val NI_CORE5 = Module(new NetworkInterface(depth, size, slot = 5))
  val NI_CORE6 = Module(new NetworkInterface(depth, size, slot = 6))
  val NI_CORE7 = Module(new NetworkInterface(depth, size, slot = 7))
  val NI_CORE8 = Module(new NetworkInterface(depth, size, slot = 8))
  val NI_CORE9 = Module(new NetworkInterface(depth, size, slot = 9))

  val Router_Core1 = Module(new router(size))
  val Router_Core2 = Module(new router(size))
  val Router_Core3 = Module(new router(size))
  val Router_Core4 = Module(new router(size))
  val Router_Core5 = Module(new router(size))
  val Router_Core6 = Module(new router(size))
  val Router_Core7 = Module(new router(size))
  val Router_Core8 = Module(new router(size))
  val Router_Core9 = Module(new router(size))
printf("NI NI2Ocp_In DATA IS %x\n",NI_CORE1.io.NI2Ocp_In.din)
  printf("Router data in is %x\n", Router_Core1.io.router_in_L.din)
  printf("ROuter data out is %x\n",Router_Core1.io.router_out_W.dout)
  printf("NI NI2Router_In DATA IS %x\n",NI_CORE1.io.NI2Router_In.din)
  printf("NI NI2Ocp_In DATA IS %x\n",NI_CORE1.io.NI2Ocp_Out.dout)
  // printf("NI TX OUT DATA IS %x\n",NI_CORE1.Tx.io.txOut.dout)
  /*
  *               Noc IO WITH OUTSIDE ENVIRONMENT
  *
  * */
//  io.ocpIO_CORE1_IN <> ocpIO_CORE1.io.CorePort.M
//  io.ocpIO_CORE2_IN <> ocpIO_CORE2.io.CorePort.M
//  io.ocpIO_CORE3_IN <> ocpIO_CORE3.io.CorePort.M
//  io.ocpIO_CORE4_IN <> ocpIO_CORE4.io.CorePort.M
//  io.ocpIO_CORE5_IN <> ocpIO_CORE5.io.CorePort.M
//  io.ocpIO_CORE6_IN <> ocpIO_CORE6.io.CorePort.M
//  io.ocpIO_CORE7_IN <> ocpIO_CORE7.io.CorePort.M
//  io.ocpIO_CORE8_IN <> ocpIO_CORE8.io.CorePort.M
//  io.ocpIO_CORE9_IN <> ocpIO_CORE9.io.CorePort.M
//
//  io.ocpIO_CORE1_OUT <> ocpIO_CORE1.io.CorePort.S
//  io.ocpIO_CORE2_OUT <> ocpIO_CORE2.io.CorePort.S
//  io.ocpIO_CORE3_OUT <> ocpIO_CORE3.io.CorePort.S
//  io.ocpIO_CORE4_OUT <> ocpIO_CORE4.io.CorePort.S
//  io.ocpIO_CORE5_OUT <> ocpIO_CORE5.io.CorePort.S
//  io.ocpIO_CORE6_OUT <> ocpIO_CORE6.io.CorePort.S
//  io.ocpIO_CORE7_OUT <> ocpIO_CORE7.io.CorePort.S
//  io.ocpIO_CORE8_OUT <> ocpIO_CORE8.io.CorePort.S
//  io.ocpIO_CORE9_OUT <> ocpIO_CORE9.io.CorePort.S
  io.CorePort1 <> ocpIO_CORE1.io.CorePort
  io.CorePort2 <> ocpIO_CORE2.io.CorePort
  io.CorePort3 <> ocpIO_CORE3.io.CorePort
  io.CorePort4 <> ocpIO_CORE4.io.CorePort
  io.CorePort5 <> ocpIO_CORE5.io.CorePort
  io.CorePort6 <> ocpIO_CORE6.io.CorePort
  io.CorePort7 <> ocpIO_CORE7.io.CorePort
  io.CorePort8 <> ocpIO_CORE8.io.CorePort
  io.CorePort9 <> ocpIO_CORE9.io.CorePort
  /**
   * -------------Core 1 ------------------ 
   */
  //------------OCP CONNECTION WITH NI
  NI_CORE1.io.NI2Ocp_In.write := ~ocpIO_CORE1.io.OcpOut.empty
  ocpIO_CORE1.io.OcpOut.read := ~NI_CORE1.io.NI2Ocp_In.full
  NI_CORE1.io.NI2Ocp_In.din := ocpIO_CORE1.io.OcpOut.dout

  NI_CORE1.io.NI2Ocp_Out.read := ~ocpIO_CORE1.io.OcpIn.full
  ocpIO_CORE1.io.OcpIn.write := ~NI_CORE1.io.NI2Ocp_Out.empty
  ocpIO_CORE1.io.OcpIn.din := NI_CORE1.io.NI2Ocp_Out.dout
  
  NI_CORE1.io.addr := ocpIO_CORE1.io.addr
  //-------------NI CONNECTION WITH ROUTER 
  Router_Core1.io.router_in_L.din := NI_CORE1.io.NI2Router_Out.dout
  Router_Core1.io.router_in_L.write := ~NI_CORE1.io.NI2Router_Out.empty
  NI_CORE1.io.NI2Router_Out.read := ~Router_Core1.io.router_in_L.full
  
  NI_CORE1.io.NI2Router_In.din := Router_Core1.io.router_out_L.dout
  NI_CORE1.io.NI2Router_In.write := ~Router_Core1.io.router_out_L.empty
  Router_Core1.io.router_out_L.read := ~NI_CORE1.io.NI2Router_In.full
  
  
  /*
  * ---------------Core 2 -----------------------------
  * */
  NI_CORE2.io.NI2Ocp_In.write := ~ocpIO_CORE2.io.OcpOut.empty
  ocpIO_CORE2.io.OcpOut.read := ~NI_CORE2.io.NI2Ocp_In.full
  NI_CORE2.io.NI2Ocp_In.din := ocpIO_CORE2.io.OcpOut.dout

  NI_CORE2.io.NI2Ocp_Out.read := ~ocpIO_CORE2.io.OcpIn.full
  ocpIO_CORE2.io.OcpIn.write := ~NI_CORE2.io.NI2Ocp_Out.empty
  ocpIO_CORE2.io.OcpIn.din := NI_CORE2.io.NI2Ocp_Out.dout

  NI_CORE2.io.addr := ocpIO_CORE2.io.addr
  //-------------NI CONNECTION WITH ROUTER
  Router_Core2.io.router_in_L.din := NI_CORE2.io.NI2Router_Out.dout
  Router_Core2.io.router_in_L.write := ~NI_CORE2.io.NI2Router_Out.empty
  NI_CORE2.io.NI2Router_Out.read := ~Router_Core2.io.router_in_L.full

  NI_CORE2.io.NI2Router_In.din := Router_Core2.io.router_out_L.dout
  NI_CORE2.io.NI2Router_In.write := ~Router_Core2.io.router_out_L.empty
  Router_Core2.io.router_out_L.read := ~NI_CORE2.io.NI2Router_In.full
  
  /*
    * ---------------Core 3 -----------------------------
    * */
  NI_CORE3.io.NI2Ocp_In.write := ~ocpIO_CORE3.io.OcpOut.empty
  ocpIO_CORE3.io.OcpOut.read := ~NI_CORE3.io.NI2Ocp_In.full
  NI_CORE3.io.NI2Ocp_In.din := ocpIO_CORE3.io.OcpOut.dout

  NI_CORE3.io.NI2Ocp_Out.read := ~ocpIO_CORE3.io.OcpIn.full
  ocpIO_CORE3.io.OcpIn.write := ~NI_CORE3.io.NI2Ocp_Out.empty
  ocpIO_CORE3.io.OcpIn.din := NI_CORE3.io.NI2Ocp_Out.dout

  NI_CORE3.io.addr := ocpIO_CORE3.io.addr
  //-------------NI CONNECTION WITH ROUTER 
  Router_Core3.io.router_in_L.din := NI_CORE3.io.NI2Router_Out.dout
  Router_Core3.io.router_in_L.write := ~NI_CORE3.io.NI2Router_Out.empty
  NI_CORE3.io.NI2Router_Out.read := ~Router_Core3.io.router_in_L.full

  NI_CORE3.io.NI2Router_In.din := Router_Core3.io.router_out_L.dout
  NI_CORE3.io.NI2Router_In.write := ~Router_Core3.io.router_out_L.empty
  Router_Core3.io.router_out_L.read := ~NI_CORE3.io.NI2Router_In.full
  
  /*
  * ---------------Core 4 -----------------------------
  * */
  NI_CORE4.io.NI2Ocp_In.write := ~ocpIO_CORE4.io.OcpOut.empty
  ocpIO_CORE4.io.OcpOut.read := ~NI_CORE4.io.NI2Ocp_In.full
  NI_CORE4.io.NI2Ocp_In.din := ocpIO_CORE4.io.OcpOut.dout

  NI_CORE4.io.NI2Ocp_Out.read := ~ocpIO_CORE4.io.OcpIn.full
  ocpIO_CORE4.io.OcpIn.write := ~NI_CORE4.io.NI2Ocp_Out.empty
  ocpIO_CORE4.io.OcpIn.din := NI_CORE4.io.NI2Ocp_Out.dout
  
  NI_CORE4.io.addr := ocpIO_CORE4.io.addr
  //-------------NI CONNECTION WITH ROUTER 
  Router_Core4.io.router_in_L.din := NI_CORE4.io.NI2Router_Out.dout
  Router_Core4.io.router_in_L.write := ~NI_CORE4.io.NI2Router_Out.empty
  NI_CORE4.io.NI2Router_Out.read := ~Router_Core4.io.router_in_L.full

  NI_CORE4.io.NI2Router_In.din := Router_Core4.io.router_out_L.dout
  NI_CORE4.io.NI2Router_In.write := ~Router_Core4.io.router_out_L.empty
  Router_Core4.io.router_out_L.read := ~NI_CORE4.io.NI2Router_In.full
  
  /*
  * ---------------Core 5 -----------------------------
  * */
  NI_CORE5.io.NI2Ocp_In.write := ~ocpIO_CORE5.io.OcpOut.empty
  ocpIO_CORE5.io.OcpOut.read := ~NI_CORE5.io.NI2Ocp_In.full
  NI_CORE5.io.NI2Ocp_In.din := ocpIO_CORE5.io.OcpOut.dout

  NI_CORE5.io.NI2Ocp_Out.read := ~ocpIO_CORE5.io.OcpIn.full
  ocpIO_CORE5.io.OcpIn.write := ~NI_CORE5.io.NI2Ocp_Out.empty
  ocpIO_CORE5.io.OcpIn.din := NI_CORE5.io.NI2Ocp_Out.dout

  NI_CORE5.io.addr := ocpIO_CORE5.io.addr
  //-------------NI CONNECTION WITH ROUTER 
  Router_Core5.io.router_in_L.din := NI_CORE5.io.NI2Router_Out.dout
  Router_Core5.io.router_in_L.write := ~NI_CORE5.io.NI2Router_Out.empty
  NI_CORE5.io.NI2Router_Out.read := ~Router_Core5.io.router_in_L.full

  NI_CORE5.io.NI2Router_In.din := Router_Core5.io.router_out_L.dout
  NI_CORE5.io.NI2Router_In.write := ~Router_Core5.io.router_out_L.empty
  Router_Core5.io.router_out_L.read := ~NI_CORE5.io.NI2Router_In.full
  
  /*
  * ---------------Core 6 -----------------------------
  * */
  NI_CORE6.io.NI2Ocp_In.write := ~ocpIO_CORE6.io.OcpOut.empty
  ocpIO_CORE6.io.OcpOut.read := ~NI_CORE6.io.NI2Ocp_In.full
  NI_CORE6.io.NI2Ocp_In.din := ocpIO_CORE6.io.OcpOut.dout

  NI_CORE6.io.NI2Ocp_Out.read := ~ocpIO_CORE6.io.OcpIn.full
  ocpIO_CORE6.io.OcpIn.write := ~NI_CORE6.io.NI2Ocp_Out.empty
  ocpIO_CORE6.io.OcpIn.din := NI_CORE6.io.NI2Ocp_Out.dout

  NI_CORE6.io.addr := ocpIO_CORE6.io.addr
  //-------------NI CONNECTION WITH ROUTER 
  Router_Core6.io.router_in_L.din := NI_CORE6.io.NI2Router_Out.dout
  Router_Core6.io.router_in_L.write := ~NI_CORE6.io.NI2Router_Out.empty
  NI_CORE6.io.NI2Router_Out.read := ~Router_Core6.io.router_in_L.full

  NI_CORE6.io.NI2Router_In.din := Router_Core6.io.router_out_L.dout
  NI_CORE6.io.NI2Router_In.write := ~Router_Core6.io.router_out_L.empty
  Router_Core6.io.router_out_L.read := ~NI_CORE6.io.NI2Router_In.full
  /*
  * ---------------Core 7 -----------------------------
  * */
  NI_CORE7.io.NI2Ocp_In.write := ~ocpIO_CORE7.io.OcpOut.empty
  ocpIO_CORE7.io.OcpOut.read := ~NI_CORE7.io.NI2Ocp_In.full
  NI_CORE7.io.NI2Ocp_In.din := ocpIO_CORE7.io.OcpOut.dout

  NI_CORE7.io.NI2Ocp_Out.read := ~ocpIO_CORE7.io.OcpIn.full
  ocpIO_CORE7.io.OcpIn.write := ~NI_CORE7.io.NI2Ocp_Out.empty
  ocpIO_CORE7.io.OcpIn.din := NI_CORE7.io.NI2Ocp_Out.dout

  NI_CORE7.io.addr := ocpIO_CORE7.io.addr
  //-------------NI CONNECTION WITH ROUTER 
  Router_Core7.io.router_in_L.din := NI_CORE7.io.NI2Router_Out.dout
  Router_Core7.io.router_in_L.write := ~NI_CORE7.io.NI2Router_Out.empty
  NI_CORE7.io.NI2Router_Out.read := ~Router_Core7.io.router_in_L.full

  NI_CORE7.io.NI2Router_In.din := Router_Core7.io.router_out_L.dout
  NI_CORE7.io.NI2Router_In.write := ~Router_Core7.io.router_out_L.empty
  Router_Core7.io.router_out_L.read := ~NI_CORE7.io.NI2Router_In.full
  /*
  * ---------------Core 8 -----------------------------
  * */
  NI_CORE8.io.NI2Ocp_In.write := ~ocpIO_CORE8.io.OcpOut.empty
  ocpIO_CORE8.io.OcpOut.read := ~NI_CORE8.io.NI2Ocp_In.full
  NI_CORE8.io.NI2Ocp_In.din := ocpIO_CORE8.io.OcpOut.dout

  NI_CORE8.io.NI2Ocp_Out.read := ~ocpIO_CORE8.io.OcpIn.full
  ocpIO_CORE8.io.OcpIn.write := ~NI_CORE8.io.NI2Ocp_Out.empty
  ocpIO_CORE8.io.OcpIn.din := NI_CORE8.io.NI2Ocp_Out.dout

  NI_CORE8.io.addr := ocpIO_CORE8.io.addr
  //-------------NI CONNECTION WITH ROUTER 

  Router_Core8.io.router_in_L.din := NI_CORE8.io.NI2Router_Out.dout
  Router_Core8.io.router_in_L.write := ~NI_CORE8.io.NI2Router_Out.empty
  NI_CORE8.io.NI2Router_Out.read := ~Router_Core8.io.router_in_L.full

  NI_CORE8.io.NI2Router_In.din := Router_Core8.io.router_out_L.dout
  NI_CORE8.io.NI2Router_In.write := ~Router_Core8.io.router_out_L.empty
  Router_Core8.io.router_out_L.read := ~NI_CORE8.io.NI2Router_In.full

  /*
  * ---------------Core 9 -----------------------------
  * */
  NI_CORE9.io.NI2Ocp_In.write := ~ocpIO_CORE9.io.OcpOut.empty
  ocpIO_CORE9.io.OcpOut.read := ~NI_CORE9.io.NI2Ocp_In.full
  NI_CORE9.io.NI2Ocp_In.din := ocpIO_CORE9.io.OcpOut.dout

  NI_CORE9.io.NI2Ocp_Out.read := ~ocpIO_CORE9.io.OcpIn.full
  ocpIO_CORE9.io.OcpIn.write := ~NI_CORE9.io.NI2Ocp_Out.empty
  ocpIO_CORE9.io.OcpIn.din := NI_CORE9.io.NI2Ocp_Out.dout

  NI_CORE9.io.addr := ocpIO_CORE9.io.addr
  //-------------NI CONNECTION WITH ROUTER 
  
  Router_Core9.io.router_in_L.din := NI_CORE9.io.NI2Router_Out.dout
  Router_Core9.io.router_in_L.write := ~NI_CORE9.io.NI2Router_Out.empty
  NI_CORE9.io.NI2Router_Out.read := ~Router_Core9.io.router_in_L.full

  NI_CORE9.io.NI2Router_In.din := Router_Core9.io.router_out_L.dout
  NI_CORE9.io.NI2Router_In.write := ~Router_Core9.io.router_out_L.empty
  Router_Core9.io.router_out_L.read := ~NI_CORE9.io.NI2Router_In.full
  
//-----------------------------------------------------------------------
//                      Router Connection
  //
 /*          7             8             9
             |             |             |
          3--1 ------------2-------------3--1
             |             |             |
          6--4-------------5-------------6--4
             |             |             |
          9--7-------------8-------------9--7
             |             |             |
             1             2             3
             DIN <= DOUT
             WRITE <= ~EMPTY
             ~FULL => READ
 * ---------------------------------------------------------
 * */
  
  /*
  * ----------------------------------
  *                7
  *                |
  *             3- 1 - 2
  *                |
  *                4
  * */
  /*ROUTER 1 N   <---> S ROUTER 7 */
  //ROUTER 1   <- ROUTER 7
  Router_Core1.io.router_in_N.din := Router_Core7.io.router_out_S.dout
  Router_Core1.io.router_in_N.write := ~Router_Core7.io.router_out_S.empty
  Router_Core7.io.router_out_S.read := ~Router_Core1.io.router_in_N.full
  //ROUTER 1 -> ROUTER 7
  Router_Core7.io.router_in_S.din := Router_Core1.io.router_out_N.dout
  Router_Core7.io.router_in_S.write := ~Router_Core1.io.router_out_N.empty
  Router_Core1.io.router_out_N.read := ~Router_Core7.io.router_in_S.full
  
  /*ROUTER 1   E  <------>  W ROUTER 2*/
  //ROUTER 1   <- ROUTER 2
  Router_Core1.io.router_in_E.din := Router_Core2.io.router_out_W.dout
  Router_Core1.io.router_in_E.write := ~Router_Core2.io.router_out_W.empty
  Router_Core2.io.router_out_W.read := ~Router_Core1.io.router_in_E.full
  //ROUTER 1 -> ROUTER 2
  Router_Core2.io.router_in_W.din := Router_Core1.io.router_out_E.dout
  Router_Core2.io.router_in_W.write := ~Router_Core1.io.router_out_E.empty
  Router_Core1.io.router_out_E.read := ~Router_Core2.io.router_in_W.full

  /*ROUTER 1  W  <---> E ROUTER 3 */
  //ROUTER 1   <- ROUTER 3
  Router_Core1.io.router_in_W.din := Router_Core3.io.router_out_E.dout
  Router_Core1.io.router_in_W.write := ~Router_Core3.io.router_out_E.empty
  Router_Core3.io.router_out_E.read := ~Router_Core1.io.router_in_W.full
  //ROUTER 1 -> ROUTER 3
  Router_Core3.io.router_in_E.din := Router_Core1.io.router_out_W.dout
  Router_Core3.io.router_in_E.write := ~Router_Core1.io.router_out_W.empty
  Router_Core1.io.router_out_W.read := ~Router_Core3.io.router_in_E.full
  
  /*ROUTER 1  S  <---> N ROUTER 4 */
  //ROUTER 1   <- ROUTER 4
  Router_Core1.io.router_in_S.din := Router_Core4.io.router_out_N.dout
  Router_Core1.io.router_in_S.write := ~Router_Core4.io.router_out_N.empty
  Router_Core4.io.router_out_N.read := ~Router_Core1.io.router_in_S.full
  //ROUTER 1 -> ROUTER 4
  Router_Core4.io.router_in_N.din := Router_Core1.io.router_out_S.dout
  Router_Core4.io.router_in_N.write := ~Router_Core1.io.router_out_S.empty
  Router_Core1.io.router_out_S.read := ~Router_Core4.io.router_in_N.full
  
  /*
  * ----------------------------------
  *                8
  *                |
  *             1- 2 - 3
  *                |
  *                5
  * */
  /*ROUTER 2    <--->  ROUTER 8 */
  //ROUTER 2   <- ROUTER 8
  Router_Core2.io.router_in_N.din := Router_Core8.io.router_out_S.dout
  Router_Core2.io.router_in_N.write := ~Router_Core8.io.router_out_S.empty
  Router_Core8.io.router_out_S.read := ~Router_Core2.io.router_in_N.full
  //ROUTER 2 -> ROUTER 8
  Router_Core8.io.router_in_S.din := Router_Core2.io.router_out_N.dout
  Router_Core8.io.router_in_S.write := ~Router_Core2.io.router_out_N.empty
  Router_Core2.io.router_out_N.read := ~Router_Core8.io.router_in_S.full
 
  /*ROUTER 2   E  <------>  W ROUTER 3*/
  //ROUTER 2   <- ROUTER 3
  Router_Core2.io.router_in_E.din := Router_Core3.io.router_out_W.dout
  Router_Core2.io.router_in_E.write := ~Router_Core3.io.router_out_W.empty
  Router_Core3.io.router_out_W.read := ~Router_Core2.io.router_in_E.full
  //ROUTER 2 -> ROUTER 3
  Router_Core3.io.router_in_W.din := Router_Core2.io.router_out_E.dout
  Router_Core3.io.router_in_W.write := ~Router_Core2.io.router_out_E.empty
  Router_Core2.io.router_out_E.read := ~Router_Core3.io.router_in_W.full

  /*ROUTER 2  S  <---> N ROUTER 5 */
  //ROUTER 2   <- ROUTER 5
  Router_Core2.io.router_in_S.din := Router_Core5.io.router_out_N.dout
  Router_Core2.io.router_in_S.write := ~Router_Core5.io.router_out_N.empty
  Router_Core5.io.router_out_N.read := ~Router_Core2.io.router_in_S.full
  //ROUTER 2 -> ROUTER 5
  Router_Core5.io.router_in_N.din := Router_Core2.io.router_out_S.dout
  Router_Core5.io.router_in_N.write := ~Router_Core2.io.router_out_S.empty
  Router_Core2.io.router_out_S.read := ~Router_Core5.io.router_in_N.full
  
  /*
 * ----------------------------------
 *                9
 *                |
 *             2- 3 - 1
 *                |
 *                6
 * */
  /*ROUTER 3    <--->  ROUTER 9 */
  //ROUTER 3   <- ROUTER 9
  Router_Core3.io.router_in_N.din := Router_Core9.io.router_out_S.dout
  Router_Core3.io.router_in_N.write := ~Router_Core9.io.router_out_S.empty
  Router_Core9.io.router_out_S.read := ~Router_Core3.io.router_in_N.full
  //ROUTER 3 -> ROUTER 9
  Router_Core9.io.router_in_S.din := Router_Core3.io.router_out_N.dout
  Router_Core9.io.router_in_S.write := ~Router_Core3.io.router_out_N.empty
  Router_Core3.io.router_out_N.read := ~Router_Core9.io.router_in_S.full

  /*ROUTER 3  S  <---> N ROUTER 6 */
  //ROUTER 3   <- ROUTER 6
  Router_Core3.io.router_in_S.din := Router_Core6.io.router_out_N.dout
  Router_Core3.io.router_in_S.write := ~Router_Core6.io.router_out_N.empty
  Router_Core6.io.router_out_N.read := ~Router_Core3.io.router_in_S.full
  //ROUTER 3 -> ROUTER 6
  Router_Core6.io.router_in_N.din := Router_Core3.io.router_out_S.dout
  Router_Core6.io.router_in_N.write := ~Router_Core3.io.router_out_S.empty
  Router_Core3.io.router_out_S.read := ~Router_Core6.io.router_in_N.full
  
  /*
* ----------------------------------
*                1
*                |/
*             6- 4 - 5
*                |
*                7
* */
  /*ROUTER 4   E  <------>  W ROUTER 5*/
  //ROUTER 4   <- ROUTER 5
  Router_Core4.io.router_in_E.din := Router_Core5.io.router_out_W.dout
  Router_Core4.io.router_in_E.write := ~Router_Core5.io.router_out_W.empty
  Router_Core5.io.router_out_W.read := ~Router_Core4.io.router_in_E.full
  //ROUTER 4 -> ROUTER 5
  Router_Core5.io.router_in_W.din := Router_Core4.io.router_out_E.dout
  Router_Core5.io.router_in_W.write := ~Router_Core4.io.router_out_E.empty
  Router_Core4.io.router_out_E.read := ~Router_Core5.io.router_in_W.full

  /*ROUTER 4  W  <---> E ROUTER 6 */
  //ROUTER 4   <- ROUTER 6
  Router_Core4.io.router_in_W.din := Router_Core6.io.router_out_E.dout
  Router_Core4.io.router_in_W.write := ~Router_Core6.io.router_out_E.empty
  Router_Core6.io.router_out_E.read := ~Router_Core4.io.router_in_W.full
  //ROUTER 4 -> ROUTER 6
  Router_Core6.io.router_in_E.din := Router_Core4.io.router_out_W.dout
  Router_Core6.io.router_in_E.write := ~Router_Core4.io.router_out_W.empty
  Router_Core4.io.router_out_W.read := ~Router_Core6.io.router_in_E.full

  /*ROUTER 4  S  <---> N ROUTER 7 */
  //ROUTER 4   <- ROUTER 7
  Router_Core4.io.router_in_S.din := Router_Core7.io.router_out_N.dout
  Router_Core4.io.router_in_S.write := ~Router_Core7.io.router_out_N.empty
  Router_Core7.io.router_out_N.read := ~Router_Core4.io.router_in_S.full
  //ROUTER 4 -> ROUTER 7
  Router_Core7.io.router_in_N.din := Router_Core4.io.router_out_S.dout
  Router_Core7.io.router_in_N.write := ~Router_Core4.io.router_out_S.empty
  Router_Core4.io.router_out_S.read := ~Router_Core7.io.router_in_N.full
  
  /*
* ----------------------------------
*                2
*                |/
*             4- /5 - 6
*                |
*                8
* */
  /*ROUTER 5   E  <------>  W ROUTER 6*/
  //ROUTER 5   <- ROUTER 6
  Router_Core5.io.router_in_E.din := Router_Core6.io.router_out_W.dout
  Router_Core5.io.router_in_E.write := ~Router_Core6.io.router_out_W.empty
  Router_Core6.io.router_out_W.read := ~Router_Core5.io.router_in_E.full
  //ROUTER 5 -> ROUTER 6
  Router_Core6.io.router_in_W.din := Router_Core5.io.router_out_E.dout
  Router_Core6.io.router_in_W.write := ~Router_Core5.io.router_out_E.empty
  Router_Core5.io.router_out_E.read := ~Router_Core6.io.router_in_W.full

  /*ROUTER 5  S  <---> N ROUTER 8 */
  //ROUTER 5   <- ROUTER 8
  Router_Core5.io.router_in_S.din := Router_Core8.io.router_out_N.dout
  Router_Core5.io.router_in_S.write := ~Router_Core8.io.router_out_N.empty
  Router_Core8.io.router_out_N.read := ~Router_Core5.io.router_in_S.full
  //ROUTER 5 -> ROUTER 8
  Router_Core8.io.router_in_N.din := Router_Core5.io.router_out_S.dout
  Router_Core8.io.router_in_N.write := ~Router_Core5.io.router_out_S.empty
  Router_Core5.io.router_out_S.read := ~Router_Core8.io.router_in_N.full
  
  /*
* ----------------------------------
*                3
*                |/
*             5-/ 6 /- 4
*                |
*                9
* */
  /*ROUTER 6  S  <---> N ROUTER 9 */
  //ROUTER 6   <- ROUTER 9
  Router_Core6.io.router_in_S.din := Router_Core9.io.router_out_N.dout
  Router_Core6.io.router_in_S.write := ~Router_Core9.io.router_out_N.empty
  Router_Core9.io.router_out_N.read := ~Router_Core6.io.router_in_S.full
  //ROUTER 6 -> ROUTER 9
  Router_Core9.io.router_in_N.din := Router_Core6.io.router_out_S.dout
  Router_Core9.io.router_in_N.write := ~Router_Core6.io.router_out_S.empty
  Router_Core6.io.router_out_S.read := ~Router_Core9.io.router_in_N.full
  
  /*
* ----------------------------------
*                4
*                |
*             9- 7 - 8
*                |
*                1
* */
  /*ROUTER 7   E  <------>  W ROUTER 8*/
  //ROUTER 7   <- ROUTER 8
  Router_Core7.io.router_in_E.din := Router_Core8.io.router_out_W.dout
  Router_Core7.io.router_in_E.write := ~Router_Core8.io.router_out_W.empty
  Router_Core8.io.router_out_W.read := ~Router_Core7.io.router_in_E.full
  //ROUTER 7 -> ROUTER 8
  Router_Core8.io.router_in_W.din := Router_Core7.io.router_out_E.dout
  Router_Core8.io.router_in_W.write := ~Router_Core7.io.router_out_E.empty
  Router_Core7.io.router_out_E.read := ~Router_Core8.io.router_in_W.full

  /*ROUTER 7  W  <---> E ROUTER 9 */
  //ROUTER 7   <- ROUTER 9
  Router_Core7.io.router_in_W.din := Router_Core9.io.router_out_E.dout
  Router_Core7.io.router_in_W.write := ~Router_Core9.io.router_out_E.empty
  Router_Core9.io.router_out_E.read := ~Router_Core7.io.router_in_W.full
  //ROUTER 7 -> ROUTER 9
  Router_Core9.io.router_in_E.din := Router_Core7.io.router_out_W.dout
  Router_Core9.io.router_in_E.write := ~Router_Core7.io.router_out_W.empty
  Router_Core7.io.router_out_W.read := ~Router_Core9.io.router_in_E.full

  /*
* ----------------------------------
*                5
*                |/
*             7- /8 - 9
*                |/
*                2
* */
  /*ROUTER 8   E  <------>  W ROUTER 9*/
  //ROUTER 8   <- ROUTER 9
  Router_Core8.io.router_in_E.din := Router_Core9.io.router_out_W.dout
  Router_Core8.io.router_in_E.write := ~Router_Core9.io.router_out_W.empty
  Router_Core9.io.router_out_W.read := ~Router_Core8.io.router_in_E.full
  //ROUTER 8 -> ROUTER 9
  Router_Core9.io.router_in_W.din := Router_Core8.io.router_out_E.dout
  Router_Core9.io.router_in_W.write := ~Router_Core8.io.router_out_E.empty
  Router_Core8.io.router_out_E.read := ~Router_Core9.io.router_in_W.full
}

object noc extends App {
  chisel3.Driver.execute(args, () => new noc (depth = 3,size = 35))
}