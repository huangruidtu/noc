package noc
import chisel3._
import NetworkInterface.NetworkInterface
import router.router
import RX._
import TX.TX
import OcpInterface._

class noc(depth:Int,width:Int,size:Int) extends Module {
  val io = IO(new Bundle {
    val ocpIO_CORE1 = new OcpInterface()
    val ocpIO_CORE2 = new OcpInterface()
    val ocpIO_CORE3 = new OcpInterface()
    val ocpIO_CORE4 = new OcpInterface()
    val ocpIO_CORE5 = new OcpInterface()
    val ocpIO_CORE6 = new OcpInterface()
    val ocpIO_CORE7 = new OcpInterface()
    val ocpIO_CORE8 = new OcpInterface()
    val ocpIO_CORE9 = new OcpInterface()
  })

  val NI_CORE1 = Module(new NetworkInterface(depth, size))
  val NI_CORE2 = Module(new NetworkInterface(depth, size))
  val NI_CORE3 = Module(new NetworkInterface(depth, size))
  val NI_CORE4 = Module(new NetworkInterface(depth, size))
  val NI_CORE5 = Module(new NetworkInterface(depth, size))
  val NI_CORE6 = Module(new NetworkInterface(depth, size))
  val NI_CORE7 = Module(new NetworkInterface(depth, size))
  val NI_CORE8 = Module(new NetworkInterface(depth, size))
  val NI_CORE9 = Module(new NetworkInterface(depth, size))

  val Router_Core1 = Module(new router(size))
  val Router_Core2 = Module(new router(size))
  val Router_Core3 = Module(new router(size))
  val Router_Core4 = Module(new router(size))
  val Router_Core5 = Module(new router(size))
  val Router_Core6 = Module(new router(size))
  val Router_Core7 = Module(new router(size))
  val Router_Core8 = Module(new router(size))
  val Router_Core9 = Module(new router(size))

  /**
   * -------------Core 1 ------------------ 
   */
  //------------OCP CONNECTION WITH NI
  NI_CORE1.io.NI2Ocp_In.write := ~io.ocpIO_CORE1.io.OcpOut.empty
  io.ocpIO_CORE1.io.OcpOut.read := ~NI_CORE1.io.NI2Ocp_In.full
  NI_CORE1.io.NI2Ocp_In.din := io.ocpIO_CORE1.io.OcpOut.dout

  NI_CORE1.io.NI2Ocp_Out.read := ~io.ocpIO_CORE1.io.OcpIn.full
  io.ocpIO_CORE1.io.OcpIn.write := ~NI_CORE1.io.NI2Ocp_Out.empty
  io.ocpIO_CORE1.io.OcpIn.din := NI_CORE1.io.NI2Ocp_Out.dout
  
  NI_CORE1.io.addr := io.ocpIO_CORE1.io.addr
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
  NI_CORE2.io.NI2Ocp_In.write := ~io.ocpIO_CORE2.io.OcpOut.empty
  io.ocpIO_CORE2.io.OcpOut.read := ~NI_CORE2.io.NI2Ocp_In.full
  NI_CORE2.io.NI2Ocp_In.din := io.ocpIO_CORE2.io.OcpOut.dout

  NI_CORE2.io.NI2Ocp_Out.read := ~io.ocpIO_CORE2.io.OcpIn.full
  io.ocpIO_CORE2.io.OcpIn.write := ~NI_CORE2.io.NI2Ocp_Out.empty
  io.ocpIO_CORE2.io.OcpIn.din := NI_CORE2.io.NI2Ocp_Out.dout

  NI_CORE2.io.addr := io.ocpIO_CORE2.io.addr
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
  NI_CORE3.io.NI2Ocp_In.write := ~io.ocpIO_CORE3.io.OcpOut.empty
  io.ocpIO_CORE3.io.OcpOut.read := ~NI_CORE3.io.NI2Ocp_In.full
  NI_CORE3.io.NI2Ocp_In.din := io.ocpIO_CORE3.io.OcpOut.dout

  NI_CORE3.io.NI2Ocp_Out.read := ~io.ocpIO_CORE3.io.OcpIn.full
  io.ocpIO_CORE3.io.OcpIn.write := ~NI_CORE3.io.NI2Ocp_Out.empty
  io.ocpIO_CORE3.io.OcpIn.din := NI_CORE3.io.NI2Ocp_Out.dout

  NI_CORE3.io.addr := io.ocpIO_CORE3.io.addr
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
  NI_CORE4.io.NI2Ocp_In.write := ~io.ocpIO_CORE4.io.OcpOut.empty
  io.ocpIO_CORE4.io.OcpOut.read := ~NI_CORE4.io.NI2Ocp_In.full
  NI_CORE4.io.NI2Ocp_In.din := io.ocpIO_CORE4.io.OcpOut.dout

  NI_CORE4.io.NI2Ocp_Out.read := ~io.ocpIO_CORE4.io.OcpIn.full
  io.ocpIO_CORE4.io.OcpIn.write := ~NI_CORE4.io.NI2Ocp_Out.empty
  io.ocpIO_CORE4.io.OcpIn.din := NI_CORE4.io.NI2Ocp_Out.dout
  
  NI_CORE4.io.addr := io.ocpIO_CORE4.io.addr
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
  NI_CORE5.io.NI2Ocp_In.write := ~io.ocpIO_CORE5.io.OcpOut.empty
  io.ocpIO_CORE5.io.OcpOut.read := ~NI_CORE5.io.NI2Ocp_In.full
  NI_CORE5.io.NI2Ocp_In.din := io.ocpIO_CORE5.io.OcpOut.dout

  NI_CORE5.io.NI2Ocp_Out.read := ~io.ocpIO_CORE5.io.OcpIn.full
  io.ocpIO_CORE5.io.OcpIn.write := ~NI_CORE5.io.NI2Ocp_Out.empty
  io.ocpIO_CORE5.io.OcpIn.din := NI_CORE5.io.NI2Ocp_Out.dout

  NI_CORE5.io.addr := io.ocpIO_CORE5.io.addr
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
  NI_CORE6.io.NI2Ocp_In.write := ~io.ocpIO_CORE6.io.OcpOut.empty
  io.ocpIO_CORE6.io.OcpOut.read := ~NI_CORE6.io.NI2Ocp_In.full
  NI_CORE6.io.NI2Ocp_In.din := io.ocpIO_CORE6.io.OcpOut.dout

  NI_CORE6.io.NI2Ocp_Out.read := ~io.ocpIO_CORE6.io.OcpIn.full
  io.ocpIO_CORE6.io.OcpIn.write := ~NI_CORE6.io.NI2Ocp_Out.empty
  io.ocpIO_CORE6.io.OcpIn.din := NI_CORE6.io.NI2Ocp_Out.dout

  NI_CORE6.io.addr := io.ocpIO_CORE6.io.addr
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
  NI_CORE7.io.NI2Ocp_In.write := ~io.ocpIO_CORE7.io.OcpOut.empty
  io.ocpIO_CORE7.io.OcpOut.read := ~NI_CORE7.io.NI2Ocp_In.full
  NI_CORE7.io.NI2Ocp_In.din := io.ocpIO_CORE7.io.OcpOut.dout

  NI_CORE7.io.NI2Ocp_Out.read := ~io.ocpIO_CORE7.io.OcpIn.full
  io.ocpIO_CORE7.io.OcpIn.write := ~NI_CORE7.io.NI2Ocp_Out.empty
  io.ocpIO_CORE7.io.OcpIn.din := NI_CORE7.io.NI2Ocp_Out.dout

  NI_CORE7.io.addr := io.ocpIO_CORE7.io.addr
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
  NI_CORE8.io.NI2Ocp_In.write := ~io.ocpIO_CORE8.io.OcpOut.empty
  io.ocpIO_CORE8.io.OcpOut.read := ~NI_CORE8.io.NI2Ocp_In.full
  NI_CORE8.io.NI2Ocp_In.din := io.ocpIO_CORE8.io.OcpOut.dout

  NI_CORE8.io.NI2Ocp_Out.read := ~io.ocpIO_CORE8.io.OcpIn.full
  io.ocpIO_CORE8.io.OcpIn.write := ~NI_CORE8.io.NI2Ocp_Out.empty
  io.ocpIO_CORE8.io.OcpIn.din := NI_CORE8.io.NI2Ocp_Out.dout

  NI_CORE8.io.addr := io.ocpIO_CORE8.io.addr
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
  NI_CORE9.io.NI2Ocp_In.write := ~io.ocpIO_CORE9.io.OcpOut.empty
  io.ocpIO_CORE9.io.OcpOut.read := ~NI_CORE9.io.NI2Ocp_In.full
  NI_CORE9.io.NI2Ocp_In.din := io.ocpIO_CORE9.io.OcpOut.dout

  NI_CORE9.io.NI2Ocp_Out.read := ~io.ocpIO_CORE9.io.OcpIn.full
  io.ocpIO_CORE9.io.OcpIn.write := ~NI_CORE9.io.NI2Ocp_Out.empty
  io.ocpIO_CORE9.io.OcpIn.din := NI_CORE9.io.NI2Ocp_Out.dout

  NI_CORE9.io.addr := io.ocpIO_CORE9.io.addr
  //-------------NI CONNECTION WITH ROUTER 
  
  Router_Core9.io.router_in_L.din := NI_CORE9.io.NI2Router_Out.dout
  Router_Core9.io.router_in_L.write := ~NI_CORE9.io.NI2Router_Out.empty
  NI_CORE9.io.NI2Router_Out.read := ~Router_Core9.io.router_in_L.full

  NI_CORE1.io.NI2Router_In.din := Router_Core1.io.router_out_L.dout
  NI_CORE1.io.NI2Router_In.write := ~Router_Core1.io.router_out_L.empty
  Router_Core1.io.router_out_L.read := ~NI_CORE1.io.NI2Router_In.full
  
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
