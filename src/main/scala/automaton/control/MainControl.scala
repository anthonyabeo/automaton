package automaton.control

import chisel3._
import chisel3.util._

import datapath.Operation._

class MainControl extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))

    val aluOp = Output(UInt(3.W))
    val regWrite = Output(Bool())
    val memWrite = Output(Bool())
  })

  val op = WireDefault(0.U(3.W))
  val regW = WireDefault(false.B)
  val mWrt = WireDefault(false.B)

  switch(io.opcode) {
    is(new fromBigIntToLiteral(RegOp.id).asUInt) {
      op := 0.U
      regW := true.B
      mWrt := false.B
    }
    is(new fromBigIntToLiteral(ImmeOp.id).asUInt) {
      op := 1.U
      regW := true.B
      mWrt := false.B
    }
    is(new fromBigIntToLiteral(BranchOp.id).asUInt) {
      op := 2.U
      regW := false.B
      mWrt := false.B
    }
    is(new fromBigIntToLiteral(LdOp.id).asUInt) {
      op := 3.U
      regW := true.B
      mWrt := false.B
    }
    is(new fromBigIntToLiteral(StrOp.id).asUInt) {
      op := 4.U
      regW := false.B
      mWrt := true.B
    }
    is(new fromBigIntToLiteral(JalOp.id).asUInt) {
      op := 5.U
      regW := true.B
      mWrt := false.B
    }
    is(new fromBigIntToLiteral(JalrOp.id).asUInt) {
      op := 6.U
      regW := true.B
      mWrt := false.B
    }
  }

  io.aluOp := op
  io.regWrite := regW
  io.memWrite := mWrt
}
