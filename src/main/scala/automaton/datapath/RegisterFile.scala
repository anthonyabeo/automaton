package automaton.datapath

import chisel3._
import chisel3.util.log2Ceil
import chisel3.util.experimental.loadMemoryFromFile

class RegisterFile(XLEN: Int) extends Module {
  val NUM_REGISTERS = 32

  val io = IO(new Bundle {
    val readReg1 = Input(UInt(log2Ceil(XLEN).W))
    val readReg2 = Input(UInt(log2Ceil(XLEN).W))
    val writeReg = Input(UInt(log2Ceil(XLEN).W))
    val wrEna = Input(Bool())
    val writeData = Input(SInt(XLEN.W))

    val readData1 = Output(SInt(XLEN.W))
    val readData2 = Output(SInt(XLEN.W))
  })

  // Create Register File
  val regFile = Mem(NUM_REGISTERS, SInt(XLEN.W))
  io.readData1 := DontCare
  io.readData2 := DontCare

  loadMemoryFromFile(regFile, "src/main/resources/data/register.txt")

  // Asynchronous reads
  io.readData1 := regFile(io.readReg1)
  io.readData2 := regFile(io.readReg2)

  // synchronous writes
  when(io.wrEna) {
    regFile(io.writeReg) := io.writeData
  }
}
