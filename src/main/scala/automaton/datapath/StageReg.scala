package automaton.datapath

import chisel3._

class IFIDStageReg extends Module {
  val io = IO(new Bundle {
    val instrIF = Input(UInt(32.W))

    val instrID = Output(UInt(32.W))
  })

  io.instrID := RegNext(io.instrIF)
}

class IDEXStageReg extends Module {
  val io = IO(new Bundle {
    val rd1ID = Input(SInt(64.W))
    val rd2ID = Input(SInt(64.W))
    val writeRegID = Input(UInt(5.W))

    val rd1EX = Output(SInt(64.W))
    val rd2EX = Output(SInt(64.W))
    val writeRegEX = Output(UInt(5.W))
  })

  io.rd1EX := RegNext(io.rd1ID)
  io.rd2EX := RegNext(io.rd2ID)
  io.writeRegEX := RegNext(io.writeRegID)
}

class EXMEMStageReg extends Module {
  val io = IO(new Bundle {
    val aluResEX = Input(SInt(64.W))
    val aluZeroEX = Input(Bool())
    val writeDataEX = Input(SInt(64.W))
    val writeRegEX = Input(UInt(5.W))

    val aluResMEM = Output(SInt(64.W))
    val aluZeroMEM = Output(Bool())
    val writeDataMEM = Output(SInt(64.W))
    val writeRegMEM = Output(UInt(5.W))
  })

  io.aluResMEM := RegNext(io.aluResEX)
  io.aluZeroMEM := RegNext(io.aluZeroEX)
  io.writeDataMEM := RegNext(io.writeDataEX)
  io.writeRegMEM := RegNext(io.writeRegEX)
}

class MEMWBStageReg extends Module {
  val io = IO(new Bundle {
    val aluResMEM = Input(SInt(64.W))
    val writeRegMEM = Input(UInt(5.W))

    val aluResWB = Output(SInt(64.W))
    val writeRegWB = Output(UInt(5.W))
  })

  io.aluResWB := RegNext(io.aluResMEM)
  io.writeRegWB := RegNext(io.writeRegMEM)
}
