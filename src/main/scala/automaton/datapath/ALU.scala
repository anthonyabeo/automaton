package automaton.datapath

import chisel3._
import chisel3.util._

class ALU extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(32.W))
    val b = Input(UInt(32.W))
    val aluCtl = Input(UInt(4.W))

    val zero = Output(Bool())
    val result = Output(UInt(32.W))
  })

  io.result := io.a + io.b
  io.zero := (io.result === 0.U)
}
