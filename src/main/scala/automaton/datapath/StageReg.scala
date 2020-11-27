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
    val regWriteID = Input(Bool())
    val aluCtlID = Input(UInt(3.W))
    val wOpID = Input(Bool())
    val toRegID = Input(UInt(3.W))
    val iTypeImmeID = Input(SInt(64.W))
    val aluSrcBID = Input(UInt(2.W))

    val rd1EX = Output(SInt(64.W))
    val rd2EX = Output(SInt(64.W))
    val writeRegEX = Output(UInt(5.W))
    val regWriteEX = Output(Bool())
    val aluCtlEX = Output(UInt(3.W))
    val wOpEX = Output(Bool())
    val toRegEX = Output(UInt(3.W))
    val iTypeImmeEX = Output(SInt(64.W))
    val aluSrcBEX = Output(UInt(2.W))
  })

  io.rd1EX := RegNext(io.rd1ID)
  io.rd2EX := RegNext(io.rd2ID)
  io.writeRegEX := RegNext(io.writeRegID)
  io.iTypeImmeEX := RegNext(io.iTypeImmeID)

  // Control signals
  io.regWriteEX := RegNext(io.regWriteID)
  io.aluCtlEX := RegNext(io.aluCtlID)
  io.wOpEX := RegNext(io.wOpID)
  io.toRegEX := RegNext(io.toRegID)
  io.aluSrcBEX := RegNext(io.aluSrcBID)
}

class EXMEMStageReg extends Module {
  val io = IO(new Bundle {
    val aluResEX = Input(SInt(64.W))
    val aluZeroEX = Input(Bool())
    val aluNegEX = Input(Bool())
    val writeDataEX = Input(SInt(64.W))
    val writeRegEX = Input(UInt(5.W))
    val regWriteEX = Input(Bool())
    val toRegEX = Input(UInt(3.W))

    val aluResMEM = Output(SInt(64.W))
    val aluZeroMEM = Output(Bool())
    val aluNegMEM = Output(Bool())
    val writeDataMEM = Output(SInt(64.W))
    val writeRegMEM = Output(UInt(5.W))
    val regWriteMEM = Output(Bool())
    val toRegMEM = Output(UInt(3.W))
  })

  io.aluResMEM := RegNext(io.aluResEX)
  io.aluZeroMEM := RegNext(io.aluZeroEX)
  io.aluNegMEM := RegNext(io.aluNegEX)
  io.writeDataMEM := RegNext(io.writeDataEX)
  io.writeRegMEM := RegNext(io.writeRegEX)
  io.regWriteMEM := RegNext(io.regWriteEX)
  io.toRegMEM := RegNext(io.toRegEX)
}

class MEMWBStageReg extends Module {
  val io = IO(new Bundle {
    val aluResMEM = Input(SInt(64.W))
    val writeRegMEM = Input(UInt(5.W))
    val regWriteMEM = Input(Bool())
    val toRegMEM = Input(UInt(3.W))
    val aluNegMEM = Input(Bool())

    val aluResWB = Output(SInt(64.W))
    val writeRegWB = Output(UInt(5.W))
    val regWriteWB = Output(Bool())
    val toRegWB = Output(UInt(3.W))
    val aluNegWB = Output(Bool())
  })

  io.aluResWB := RegNext(io.aluResMEM)
  io.writeRegWB := RegNext(io.writeRegMEM)
  io.regWriteWB := RegNext(io.regWriteMEM)
  io.toRegWB := RegNext(io.toRegMEM)
  io.aluNegWB := RegNext(io.aluNegMEM)
}
