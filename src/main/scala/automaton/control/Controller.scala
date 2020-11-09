package automaton.control

import chisel3._

class Controller extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))
    val funct3 = Input(UInt(3.W))
    val funct7 = Input(UInt(7.W))

    val aluCtl = Output(UInt(3.W))
  })

  val ALUCtl = Module(new ALUControl)
  val MainCtl = Module(new MainControl)

  ///////////////
  // Plumbing //
  /////////////
  MainCtl.io.opcode := io.opcode

  ALUCtl.io.funct3 := io.funct3
  ALUCtl.io.funct7 := io.funct7
  ALUCtl.io.aluOp := MainCtl.io.aluOp

  ////////////////////
  // Output Signals //
  ////////////////////
  io.aluCtl := ALUCtl.io.aluCtl
}
