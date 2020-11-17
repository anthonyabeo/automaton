package automaton.control

import chisel3._
import chisel3.util._

import datapath.Operation._

class MainControl extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))
    val funct3 = Input(UInt(3.W))

    val aluOp = Output(UInt(3.W))
    val regWrite = Output(Bool())
    val memWrite = Output(Bool())
    val aluSrcB = Output(UInt(2.W))
    val aluSrcA = Output(UInt(2.W))
    val toReg = Output(UInt(3.W))
    val branch = Output(Bool())
    val bType = Output(UInt(2.W))
    val jmp = Output(Bool())
    val size = Output(UInt(3.W))
  })

  val op = WireDefault(0.U(3.W))
  val regW = WireDefault(false.B)
  val mWrt = WireDefault(false.B)
  val srcB = WireDefault(0.U(2.W))
  val srcA = WireDefault(0.U(2.W))
  val tReg = WireDefault(0.U(2.W))
  val br = WireDefault(false.B)
  val jp = WireDefault(false.B)
  val bt = WireDefault(0.U(2.W))
  val sz = WireDefault(0.U(3.W))

  switch(io.opcode) {
    is(new fromBigIntToLiteral(RegOp.id).asUInt) {
      op := 0.U
      regW := true.B
      mWrt := false.B
      srcB := 0.U
      srcA := 0.U
      when(io.funct3 === "b010".U || io.funct3 === "b011".U) {
        tReg := 2.U
      }.otherwise {
        tReg := 0.U
      }
      br := false.B
      jp := false.B
    }
    is(new fromBigIntToLiteral(ImmeOp.id).asUInt) {
      op := 1.U
      regW := true.B
      mWrt := false.B
      srcB := 1.U
      srcA := 0.U
      when(io.funct3 === "b010".U || io.funct3 === "b011".U) {
        tReg := 2.U
      }.otherwise {
        tReg := 0.U
      }
      br := false.B
      jp := false.B
    }
    is(new fromBigIntToLiteral(BranchOp.id).asUInt) {
      op := 2.U
      regW := false.B
      mWrt := false.B
      srcB := 0.U
      srcA := 0.U
      tReg := 0.U
      br := true.B
      jp := false.B

      when(io.funct3 === "b000".U) {
        bt := "b00".U
      }.elsewhen(io.funct3 === "b001".U) {
        bt := "b01".U
      }.elsewhen(io.funct3 === "b100".U || io.funct3 === "b110".U) {
        bt := "b10".U
      }.otherwise {
        bt := "b11".U
      }
    }
    is(new fromBigIntToLiteral(LdOp.id).asUInt) {
      op := 3.U
      regW := true.B
      mWrt := false.B
      srcB := 1.U
      srcA := 0.U
      tReg := 1.U
      br := false.B
      jp := false.B
      sz := io.funct3
    }
    is(new fromBigIntToLiteral(StrOp.id).asUInt) {
      op := 4.U
      regW := false.B
      mWrt := true.B
      srcB := 1.U
      srcA := 0.U
      tReg := 0.U
      br := false.B
      jp := false.B
      sz := io.funct3
    }
    is(new fromBigIntToLiteral(JalOp.id).asUInt) {
      op := 5.U
      regW := true.B
      mWrt := false.B
      srcB := 2.U
      srcA := 1.U
      tReg := 3.U
      br := false.B
      jp := true.B
    }
    is(new fromBigIntToLiteral(JalrOp.id).asUInt) {
      op := 6.U
      regW := true.B
      mWrt := false.B
      srcB := 1.U
      srcA := 1.U
      tReg := 3.U
      br := false.B
      jp := true.B
    }
  }

  io.aluOp := op
  io.regWrite := regW
  io.memWrite := mWrt
  io.aluSrcB := srcB
  io.aluSrcA := srcA
  io.toReg := tReg
  io.branch := br
  io.jmp := jp
  io.bType := bt
  io.size := sz
}
