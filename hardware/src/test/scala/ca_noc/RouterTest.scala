package RouterTest
import router._
import chisel3._
import chisel3.iotesters.{PeekPokeTester, chiselMainTest}

class RouterTest(dut:router) extends PeekPokeTester (dut){
  poke(dut.io.router_in_N.write, true.B)
  poke(dut.io.router_in_N.din, 0x000abc1)
  step(1)
  println(peek(dut.io.router_out_N.dout).toString())
}

object RouterTest extends App {
  chisel3.iotesters.Driver(() => new router(size = 35)) {
    m => new RouterTest(m)
  }
}