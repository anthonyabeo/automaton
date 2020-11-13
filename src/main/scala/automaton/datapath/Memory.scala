package automaton.datapath

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

class DataCache(XLEN: Int) extends Module {
  val CAPACITY = 1024

  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(CAPACITY).W))
    val wrEna = Input(Bool())
    val dataIN = Input(SInt(XLEN.W))

    val dataOUT = Output(SInt(XLEN.W))
  })

  val mem = Mem(CAPACITY, SInt(XLEN.W))
  io.dataOUT := DontCare

  loadMemoryFromFile(mem, "src/test/resources/data/data.txt")

  when(io.wrEna) {
    mem.write(io.addr, io.dataIN)
  }.otherwise {
    io.dataOUT := mem.read(io.addr)
  }
}

class InstrCache(XLEN: Int) extends Module {
  val CAPACITY = 1024

  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(CAPACITY).W))
    val dataOUT = Output(UInt(XLEN.W))
  })

  val mem = Mem(CAPACITY, UInt(8.W))

  loadMemoryFromFile(mem, "src/test/resources/data/instr.txt")

  io.dataOUT := Cat(mem.read(io.addr + 3.U), mem.read(io.addr + 2.U), mem.read(io.addr + 1.U), mem.read(io.addr))
}
