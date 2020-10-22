package automaton.datapath

import chisel3._
import chisel3.util.log2Ceil
import chisel3.util.experimental.loadMemoryFromFile

class DataCache(size: Int) extends Module {
  val CAPACITY = 1024

  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(CAPACITY).W))
    val wrEna = Input(Bool())
    val dataIN = Input(UInt(size.W))

    val dataOUT = Output(UInt(size.W))
  })

  val mem = SyncReadMem(CAPACITY, UInt(size.W))

  when(io.wrEna) {
    mem.write(io.addr, io.dataIN)
  }

  io.dataOUT := mem.read(io.addr)
}

class InstrCache(size: Int) extends Module {
  val CAPACITY = 1024

  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(CAPACITY).W))
    val dataOUT = Output(UInt(size.W))
  })

  val mem = SyncReadMem(CAPACITY, UInt(size.W))

  loadMemoryFromFile(mem, "src/main/resources/data/instr.txt")

  io.dataOUT := mem.read(io.addr)
}
