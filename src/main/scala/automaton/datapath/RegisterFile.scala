package automaton.datapath

import chisel3._

class RegisterFile(size: Int, regWidth: Int) extends Module {
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
  val regFile = Mem(size, SInt(size.W))

  // Asynchronous reads
  io.readData1 := regFile.read(io.readReg1)
  io.readData2 := regFile.read(io.readReg2)

  // synchronous writes
  when(io.wrEna) {
    regFile.write(idx = io.writeReg, data = io.writeData)
  }
}
