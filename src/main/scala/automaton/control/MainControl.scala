package automaton.control

import chisel3._
import chisel3.util._

class MainControl extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))

    val aluOp = Output(UInt(3.W))
  })

  switch(io.opcode) {
    is("b0110011".U) {
      io.aluOp := 0.U
    }

    // is("b0010011".U) {}
  }
}
