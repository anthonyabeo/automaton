package automaton.datapath

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile

class RegisterFile(size: Int, regWidth: Int) extends Module {
  val NUM_REGISTERS = 32

  val io = IO(new Bundle {
    val readReg1 = Input(UInt(regWidth.W))
    val readReg2 = Input(UInt(regWidth.W))
    val writeReg = Input(UInt(regWidth.W))
    val wrEna = Input(Bool())
    val writeData = Input(SInt(size.W))

    val readData1 = Output(SInt(size.W))
    val readData2 = Output(SInt(size.W))
  })

  // Create Register File
  val regFile = Mem(NUM_REGISTERS, SInt(size.W))
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
