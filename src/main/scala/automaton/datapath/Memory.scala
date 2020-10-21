package automaton.datapath

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile

class DataCache(size: Int, addrWidth: Int) extends Module {
  val NUM_REGISTERS = 32

  val io = IO(new Bundle {
    val addr = Input(UInt(addrWidth.W))
    val wrEna = Input(Bool())
    val dataIN = Input(UInt(size.W))

    val dataOUT = Output(UInt(size.W))
  })

  val mem = SyncReadMem(NUM_REGISTERS, UInt(size.W))

  when(io.wrEna) {
    mem.write(io.addr, io.dataIN)
  }

  io.dataOUT := mem.read(io.addr)
}

class InstrCache(size: Int, addrWidth: Int) extends Module {
  val NUM_REGISTERS = 32

  val io = IO(new Bundle {
    val addr = Input(UInt(addrWidth.W))
    val dataOUT = Output(UInt(size.W))
  })

  val mem = SyncReadMem(NUM_REGISTERS, UInt(size.W))

  loadMemoryFromFile(mem, "src/main/resources/data/instr.txt")

  io.dataOUT := mem.read(io.addr)
}
