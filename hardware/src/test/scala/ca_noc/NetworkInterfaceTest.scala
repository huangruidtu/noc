package NetworkInterfaceTest
import NetworkInterface._
import chisel3._
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class NetworkInterfaceTest(dut:NetworkInterface) extends PeekPokeTester (dut){
  poke(dut.io.NI2Ocp_In.write, true.B)
  poke(dut.io.NI2Ocp_In.din, 0x000abc1)
  step(1)
  println(peek(dut.io.NI2Router_Out.dout).toString())
}

object NetworkInterfaceTest extends App {
  chisel3.iotesters.Driver(() => new NetworkInterface(depth = 3, size = 32)) {
    m => new NetworkInterfaceTest(m)
  }
}