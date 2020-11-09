package automaton.control

import chisel3._
import chisel3.util._

class MainControl extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))

    val aluOp = Output(UInt(3.W))
  })

  val out = WireDefault(0.U(3.W))

  switch(io.opcode) {
    is("b0110011".U) {
      out := 0.U
    }
    is("b0010011".U) {
      out := 1.U
    }
    is("b1100011".U) {
      out := 2.U
    }
    is("b0000011".U) {
      out := 3.U
    }
    is("b0100011".U) {
      out := 4.U
    }
    is("b1101111".U) {
      out := 5.U
    }
    is("b1100111".U) {
      out := 6.U
    }
  }

  io.aluOp := out
}
