package automaton.datapath

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile

class DataCache(size: Int, addrWidth: Int) extends Module {
  val io = IO(new Bundle {
    val addr = Input(UInt(addrWidth.W))
    val wrEna = Input(Bool())
    val dataIN = Input(UInt(size.W))

    val dataOUT = Output(UInt(size.W))
  })

  val Mem = SyncReadMem(size, UInt(size.W))

  when(io.wrEna) {
    Mem.write(io.addr, io.dataIN)
  }

  io.dataOUT := Mem.read(io.addr)
}

class InstrCache(size: Int, addrWidth: Int) extends Module {
  val io = IO(new Bundle {
    val addr = Input(UInt(addrWidth.W))
    val dataOUT = Output(UInt(size.W))
  })

  val Mem = SyncReadMem(size, UInt(size.W))

  loadMemoryFromFile(Mem, "src/main/resources/data/instr.txt")

  io.dataOUT := Mem.read(io.addr)
}
