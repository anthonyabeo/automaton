package automaton.memory

import chisel3._
import chisel3.util.Enum

package object memory {
  /////////////////////////////////////
  // Structure for tag and data tables
  /////////////////////////////////////
  val TAG_MSB = 31
  val TAG_LSB = 14
  val INDEX_LSB = 4
  val INDEX_MSB = 13

  val Block = UInt(32.W)

  val CacheRequest = new Bundle {
    val index = UInt(10.W)
    val we = Bool()
  }

  val TagEntry = new Bundle {
    val valid = Bool()
    val dirty = Bool()
    val tag = UInt(18.W)
  }

  ////////////////////////////////////////////////////////
  // Structures for the CPU <-> Cache Controler interface.
  ////////////////////////////////////////////////////////

  // CPU Reqest (CPU->cache controller)
  val CPURequest = new Bundle {
    val addr = UInt(32.W)
    val valid = Bool()
    val dataWR = SInt(32.W)
    val wr = Bool()
  }

  // Cache result(cache controller->cpu)
  val CPUResponse = new Bundle {
    val ready = Bool()
    val data = SInt(32.W)
  }

  ///////////////////////////////////////////////////////
  // Structres for cache controller <-> Memory interface
  ///////////////////////////////////////////////////////

  // Memory request (cache controller -> memory)
  val MemRequest = new Bundle {
    val addr = UInt(32.W)
    val valid = Bool()
    val dataWR = SInt(32.W)
    val wr = Bool()
  }

  // Memory controller response (memory -> cache controller)
  val MemResponse = new Bundle {
    val data = Block
    val ready = Bool()
  }

  ///////////////////////////////////////////
  // FSM Controller specific structures
  ///////////////////////////////////////////
  val idle :: compare_tag :: allocate :: write_back :: Nil = Enum(4)
}
