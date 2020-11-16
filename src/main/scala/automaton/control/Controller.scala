package automaton.control

import chisel3._

class Controller extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))
    val funct3 = Input(UInt(3.W))
    val funct7 = Input(UInt(7.W))

    val aluCtl = Output(UInt(3.W))
    val regWrite = Output(Bool())
    val memWrite = Output(Bool())
    val aluSrcB = Output(UInt(2.W))
    val aluSrcA = Output(UInt(2.W))
    val toReg = Output(UInt(2.W))
    val branch = Output(Bool())
    val bType = Output(UInt(2.W))
    val jmp = Output(Bool())
    val size = Output(UInt(3.W))
  })

  val ALUCtl = Module(new ALUControl)
  val MainCtl = Module(new MainControl)

  ///////////////
  // Plumbing //
  /////////////
  MainCtl.io.opcode := io.opcode
  MainCtl.io.funct3 := io.funct3

  ALUCtl.io.funct3 := io.funct3
  ALUCtl.io.funct7 := io.funct7
  ALUCtl.io.aluOp := MainCtl.io.aluOp

  ////////////////////
  // Output Signals //
  ////////////////////
  io.aluCtl := ALUCtl.io.aluCtl
  io.regWrite := MainCtl.io.regWrite
  io.memWrite := MainCtl.io.memWrite
  io.aluSrcB := MainCtl.io.aluSrcB
  io.aluSrcA := MainCtl.io.aluSrcA
  io.toReg := MainCtl.io.toReg
  io.branch := MainCtl.io.branch
  io.jmp := MainCtl.io.jmp
  io.bType := MainCtl.io.bType
  io.size := MainCtl.io.size
}
