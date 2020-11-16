package automaton.datapath

import chisel3._
import chisel3.util._
import chisel3.util.experimental.loadMemoryFromFile

import datapath.signExt

class DataCache(XLEN: Int) extends Module {
  val CAPACITY = 1024

  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(CAPACITY).W))
    val wrEna = Input(Bool())
    val dataIN = Input(SInt(XLEN.W))
    val size = Input(UInt(3.W))

    val dataOUT = Output(SInt(XLEN.W))
  })

  val mem = Mem(CAPACITY, UInt(8.W))
  io.dataOUT := DontCare

  loadMemoryFromFile(mem, "src/test/resources/data/data.txt")

  when(io.wrEna) {
    switch(io.size) {
      is(0.U) {
        mem.write(io.addr, io.dataIN(7, 0))
      }

      is(1.U) {
        mem.write(io.addr, io.dataIN(7, 0))
        mem.write(io.addr + 1.U, io.dataIN(15, 8))
      }

      is(2.U) {
        mem.write(io.addr, io.dataIN(7, 0))
        mem.write(io.addr + 1.U, io.dataIN(15, 8))
        mem.write(io.addr + 2.U, io.dataIN(23, 16))
        mem.write(io.addr + 3.U, io.dataIN(31, 24))
      }

      is(3.U) {
        mem.write(io.addr, io.dataIN(7, 0))
        mem.write(io.addr + 1.U, io.dataIN(15, 8))
        mem.write(io.addr + 2.U, io.dataIN(23, 16))
        mem.write(io.addr + 3.U, io.dataIN(31, 24))
        mem.write(io.addr + 4.U, io.dataIN(39, 32))
        mem.write(io.addr + 3.U, io.dataIN(47, 40))
        mem.write(io.addr + 3.U, io.dataIN(55, 48))
        mem.write(io.addr + 3.U, io.dataIN(63, 56))
      }
    }

  }.otherwise {
    switch(io.size) {
      is(0.U) { // load byte
        io.dataOUT := Cat(
          signExt(mem.read(io.addr).asSInt, 56),
          mem.read(io.addr)
        ).asSInt
      }

      is(1.U) { // load halfword
        val hword = Cat(
          mem.read(io.addr + 1.U),
          mem.read(io.addr)
        )

        io.dataOUT := Cat(signExt(hword.asSInt, 48), hword).asSInt
      }

      is(2.U) { // load word
        val word = Cat(
          mem.read(io.addr + 3.U),
          mem.read(io.addr + 2.U),
          mem.read(io.addr + 1.U),
          mem.read(io.addr)
        )

        io.dataOUT := Cat(signExt(word.asSInt, 32), word).asSInt
      }

      is(3.U) { // load DoubleWord
        io.dataOUT := Cat(
          mem.read(io.addr + 7.U),
          mem.read(io.addr + 6.U),
          mem.read(io.addr + 5.U),
          mem.read(io.addr + 4.U),
          mem.read(io.addr + 3.U),
          mem.read(io.addr + 2.U),
          mem.read(io.addr + 1.U),
          mem.read(io.addr)
        ).asSInt
      }

      is(4.U) { // load byte unsigned
        io.dataOUT := Cat(Fill(56, "b0".U), mem.read(io.addr)).asSInt
      }

      is(5.U) { // load HalfWord unsigned
        io.dataOUT := Cat(
          Fill(48, "b0".U),
          mem.read(io.addr + 1.U),
          mem.read(io.addr)
        ).asSInt
      }

      is(6.U) { // load Word unsigned
        io.dataOUT := Cat(
          Fill(32, "b0".U),
          mem.read(io.addr + 3.U),
          mem.read(io.addr + 2.U),
          mem.read(io.addr + 1.U),
          mem.read(io.addr)
        ).asSInt
      }
    }
  }
}

class InstrCache(XLEN: Int) extends Module {
  val CAPACITY = 1024

  val io = IO(new Bundle {
    val addr = Input(UInt(log2Ceil(CAPACITY).W))
    val dataOUT = Output(UInt(32.W))
  })

  val mem = Mem(CAPACITY, UInt(8.W))

  loadMemoryFromFile(mem, "src/test/resources/data/instr.txt")

  io.dataOUT := Cat(
    mem(io.addr + 3.U),
    mem(io.addr + 2.U),
    mem(io.addr + 1.U),
    mem(io.addr)
  )
}
