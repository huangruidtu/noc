import Chisel._
import NetworkInterface._
import Router._

/**
 * @author patmos
 */
class Network_on_Chip extends Module {
  val io = new Bundle {
    
    /*Inputs for testing*/
    val valid = Bool(INPUT)
    val data          = UInt(INPUT,width=32)
    val addr = UInt(INPUT,width=4)
  }

  /*Router and Network Interface instantiation*/
  val RX_0 = Module(new RX(UInt(0)))
  val TX_0 = Module(new TX(UInt(0)))
  val Router_0 = Module(new Router)
  
  val RX_1 = Module(new RX(UInt(0)))
  val TX_1 = Module(new TX(UInt(0)))
  val Router_1 = Module(new Router)
  
  
  /*CONNECTIONS*/
  
  //Router 0 - RX/TX 0
  //Output - Router Input
  Router_0.io.Input_Processor_Input                       := TX_0.io.Output
  Router_0.io.Input_Processor_Data_Valid               := TX_0.io.Output_Data_Valid
  TX_0.io.Input_ACK                                       := Router_0.io.Input_Processor_Input_ACK
  TX_0.io.Input_Ready                                   := Router_0.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  _0.io.Router_Input                                    := Router_0.io.Output_Port_P_Output
  RX_0.io.Router_Input_Data_Valid                       := Router_0.io.Output_Port_P_Data_Valid
  Router_0.io.Output_Port_P_ACK                          := RX_0.io.Router_Input_ACK
  Router_0.io.Output_Port_P_Ready                       := RX_0.io.Router_Input_Ready

  //Router 1 - RX/TX 1
  Router_1.io.Input_Processor_Input                       := TX_1.io.Output
  Router_1.io.Input_Processor_Data_Valid               := TX_1.io.Output_Data_Valid
  TX_1.io.Input_ACK                                       := Router_1.io.Input_Processor_Input_ACK
  TX_1.io.Input_Ready                                   := Router_1.io.Input_Processor_Input_Ready
  //NI Input - Router Output
  RX_0.io.Router_Input                     := Router_0.io.Output_Port_P_Output
  RX_0.io.Router_Input_Data_Valid   := Router_0.io.Output_Port_P_Data_Valid
  Router_0.io.Output_Port_P_ACK                          := RX_0.io.Router_Input_ACK
  Router_0.io.Output_Port_P_Ready                       := TX_0.io.Router_Input_Ready

  /*
  *                    
  *                                 Port 2
  *                                   __
  *                     Port 1    |    |  Port 3
  *                                  |__|
  *                                  Port 4
  *
  *                     0 --- 1
  * */
  
 
  //Router 0 Port 3 OUT - Router 1 Port 1 IN
  Router_1.io.Input_Port_1_Data_Valid   :=    Router_0.io.Output_Port_3_Data_Valid
  Router_1.io.Input_Port_1_Input           :=    Router_0.io.Output_Port_3_Output
  Router_0.io.Output_Port_3_ACK          :=    Router_1.io.Input_Port_1_Input_ACK
  Router_0.io.Output_Port_3_Ready       :=    Router_1.io.Input_Port_1_Input_Ready  
  //Router 1 Port 1 OUT - Router 0 Port 3 IN
  Router_0.io.Input_Port_3_Data_Valid   :=    Router_1.io.Output_Port_1_Data_Valid
  Router_0.io.Input_Port_3_Input           :=    Router_1.io.Output_Port_1_Output
  Router_1.io.Output_Port_1_ACK          :=    Router_0.io.Input_Port_3_Input_ACK
  Router_1.io.Output_Port_1_Ready       :=    Router_0.io.Input_Port_3_Input_Ready

}
