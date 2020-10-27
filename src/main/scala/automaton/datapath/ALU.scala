package automaton.datapath

import chisel3._
import chisel3.util._

import datapath._

class ALU(XLEN: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(SInt(XLEN.W))
    val b = Input(SInt(XLEN.W))
    val aluCtl = Input(UInt(4.W))

    val zero = Output(Bool())
    val negative = Output(Bool())
    val result = Output(SInt(XLEN.W))
  })

  val a = io.a
  val b = io.b
  val res = WireDefault(0.S(XLEN.W))

  switch(io.aluCtl) {
    is(add) {
      res := a + b
    }
    is(sub) {
      res := a - b
    }
    is(and) {
      res := a & b
    }
    is(or) {
      res := a | b
    }
    is(xor) {
      res := a ^ b
    }
    is(sll) {
      res := a << b(4, 0).asUInt
    }
    is(srl) {
      res := a >> b(4, 0).asUInt
    }
    is(sra) {}
  }

  io.result := res
  io.negative := (res < 0.S)
  io.zero := (res === 0.S)
}
