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
  switch(io.aluOp) {
    is("b000".U) {
      switch(Cat(io.funct7, io.funct3)) {
        is("b0000000000".U) {
          out := 0.U
        }

        is("b0100000000".U) {
          out := 1.U
        }

        is("b0000000001".U) {
          out := 5.U
        }

        is("b0000000010".U) {
          out := 1.U
        }

        is("b0000000011".U) {
          out := 1.U
        }

        is("b0000000100".U) {
          out := 4.U
        }

        is("b0000000101".U) {
          out := 6.U
        }

        is("b0100000101".U) {
          out := 7.U
        }

        is("b0000000110".U) {
          out := 3.U
        }

        is("b0000000111".U) {
          out := 2.U
        }
      }
    }

    is("b001".U) {
      switch(io.funct3) {
        is("b000".U) {
          out := 0.U
        }
        is("b001".U) {
          out := 5.U
        }
        is("b101".U) {
          switch(io.funct7) {
            is("b0000000".U) {
              out := 6.U
            }
            is("b0100000".U) {
              out := 7.U
            }
          }
        }
        is("b010".U, "b011".U) {
          out := 1.U
        }
        is("b100".U) {
          out := 4.U
        }
        is("b110".U) {
          out := 3.U
        }
        is("b111".U) {
          out := 2.U
        }
      }
    }

    is("b010".U) {
      // SB Instruction Type
      out := 1.U
    }

    is("b011".U, "b100".U) {
      // LOAD & STORE
      out := 0.U
    }

    is("b101".U, "b110".U) {
      // JAL & JALR
      out := 0.U
    }
  }

  io.aluCtl := out
}
