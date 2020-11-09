package automaton.control

import chisel3._
import chisel3.util._

import datapath.Operation._

class MainControl extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))

    val aluOp = Output(UInt(3.W))
  })

  val out = WireDefault(0.U(3.W))

  switch(io.opcode) {
    is(new fromBigIntToLiteral(RegOp.id).asUInt) {
      out := 0.U
    }
    is(new fromBigIntToLiteral(ImmeOp.id).asUInt) {
      out := 1.U
    }
    is(new fromBigIntToLiteral(BranchOp.id).asUInt) {
      out := 2.U
    }
    is(new fromBigIntToLiteral(LdOp.id).asUInt) {
      out := 3.U
    }
    is(new fromBigIntToLiteral(StrOp.id).asUInt) {
      out := 4.U
    }
    is(new fromBigIntToLiteral(JalOp.id).asUInt) {
      out := 5.U
    }
    is(new fromBigIntToLiteral(JalrOp.id).asUInt) {
      out := 6.U
    }
  }

  io.aluOp := out
}
