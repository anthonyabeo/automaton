package automaton.control

import chisel3._
import chisel3.util._

class ALUControl extends Module {
  val io = IO(new Bundle {
    val aluOp = Input(UInt(3.W))
    val funct7 = Input(UInt(7.W))
    val funct3 = Input(UInt(3.W))

    val aluCtl = Output(UInt(3.W))
  })

  val out = WireDefault(0.U(3.W))
  switch(Cat(io.aluOp, io.funct7, io.funct3)) {
    is("b0000000000000".U) {
      out := 0.U
    }

    is("b0000100000000".U) {
      out := 1.U
    }

    is("b0000000000001".U) {
      out := 5.U
    }

    is("b0000000000010".U) {
      out := 1.U
    }

    is("b0000000000011".U) {
      out := 1.U
    }

    is("b0000000000100".U) {
      out := 4.U
    }

    is("b0000000000101".U) {
      out := 6.U
    }

    is("b0000100000101".U) {
      out := 7.U
    }

    is("b0000000000110".U) {
      out := 3.U
    }

    is("b0000000000111".U) {
      out := 2.U
    }
  }

  io.aluCtl := out
}
