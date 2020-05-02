/*
 * Definitions for OCP as generated by the Patmos pipeline
 *
 * Authors: Wolfgang Puffitsch (wpuffitsch@gmail.com)
 *
 */

package ocp

import Chisel._

// Masters include a byte-enable signal
class OcpCoreMasterSignals(addrWidth : Int, dataWidth : Int)
  extends OcpMasterSignals(addrWidth, dataWidth) {
  val ByteEn = UInt(width = dataWidth/8)

  // This does not really clone, but Data.clone doesn't either
  override def clone() = {
    val res = new OcpCoreMasterSignals(addrWidth, dataWidth)
    res.asInstanceOf[this.type]
  }
}

// Master port
class OcpCoreMasterPort(addrWidth : Int, dataWidth : Int) extends Bundle() {
  val M = new OcpCoreMasterSignals(addrWidth, dataWidth).asOutput
  val S = new OcpSlaveSignals(dataWidth).asInput
}

// Slave port is reverse of master port
class OcpCoreSlavePort(addrWidth : Int, dataWidth : Int) extends Bundle() {
  val M = new OcpCoreMasterSignals(addrWidth, dataWidth).asInput
  val S = new OcpSlaveSignals(dataWidth).asOutput

  // This does not really clone, but Data.clone doesn't either
  override def clone() = {
    val res = new OcpCoreSlavePort(addrWidth, dataWidth)
    res.asInstanceOf[this.type]
  }

}

// Provide a "bus" with a master port and a slave port to simplify plumbing
class OcpCoreBus(addrWidth : Int, dataWidth : Int) extends Module {
  val io = new Bundle {
    val slave = new OcpCoreSlavePort(addrWidth, dataWidth)
    val master = new OcpCoreMasterPort(addrWidth, dataWidth)
  }
  io.master <> io.slave
}
