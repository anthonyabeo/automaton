package automaton.datapath

import chisel3._
import chisel3.util._

import datapath._

class ALU(size: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(Bits(size.W))
    val b = Input(Bits(size.W))
    val aluCtl = Input(UInt(4.W))

    val zero = Output(Bool())
    val overflow = Output(Bool())
    val negative = Output(Bool())
    val result = Output(Bits(size.W))
  })

  val a = io.a
  val b = io.b
  val res = WireDefault(0.U(size.W))

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
  }

  io.result := res
  io.overflow := false.B
  io.negative := (res < 0.U)
  io.zero := (res === 0.U)
}
