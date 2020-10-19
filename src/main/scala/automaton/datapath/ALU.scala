package automaton.datapath

import chisel3._
import chisel3.util._

import datapath._

class ALU extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(64.W))
    val b = Input(UInt(64.W))
    val aluCtl = Input(UInt(4.W))

    val zero = Output(Bool())
    val overflow = Output(Bool())
    val negative = Output(Bool())
    val result = Output(UInt(64.W))
  })

  val res = WireDefault(0.U(64.W))

  switch(io.aluCtl) {
    is(add) {
      res := io.a + io.b
    }
    is(sub) {
      res := io.a - io.b
    }
    is(and) {
      res := io.a & io.b
    }
    is(or) {
      res := io.a | io.b
    }
    is(xor) {
      res := io.a ^ io.b
    }
  }

  io.result := res
  io.overflow := (io.a(0) === io.b(0)) && (io.result(0) != io.a(0))
  io.negative := (io.result < 0.U)
  io.zero := (io.result === 0.U)
}
