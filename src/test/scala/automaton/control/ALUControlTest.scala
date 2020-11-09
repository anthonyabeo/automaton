package automaton.control

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class ALUControlTest extends FlatSpec with ChiselScalatestTester {
  behavior.of("ALU Controller")

  it should "generate correct ALU OP for Register-Register Operations" in {
    test(new ALUControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      /////////////////////
      // ADD
      /////////////////////
      ctl.io.aluOp.poke(0.U)
      ctl.io.funct7.poke(0.U)
      ctl.io.funct3.poke(0.U)

      ctl.io.aluCtl.expect(0.U)

      /////////////////////
      // SUB
      /////////////////////
      ctl.io.aluOp.poke(0.U)
      ctl.io.funct7.poke("b0100000".U)
      ctl.io.funct3.poke("b000".U)

      ctl.io.aluCtl.expect(1.U)

      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b010".U)

      ctl.io.aluCtl.expect(1.U)

      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b011".U)

      ctl.io.aluCtl.expect(1.U)

      /////////////////////
      // AND
      /////////////////////
      ctl.io.aluOp.poke(0.U)
      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b111".U)

      ctl.io.aluCtl.expect(2.U)

      /////////////////////
      // OR
      /////////////////////
      ctl.io.aluOp.poke(0.U)
      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b110".U)

      ctl.io.aluCtl.expect(3.U)

      /////////////////////
      // XOR
      /////////////////////
      ctl.io.aluOp.poke(0.U)
      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b100".U)

      ctl.io.aluCtl.expect(4.U)

      /////////////////////
      // SLL
      /////////////////////
      ctl.io.aluOp.poke(0.U)
      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b001".U)

      ctl.io.aluCtl.expect(5.U)

      /////////////////////
      // SRL
      /////////////////////
      ctl.io.aluOp.poke(0.U)
      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b101".U)

      ctl.io.aluCtl.expect(6.U)

      /////////////////////
      // SRA
      /////////////////////
      ctl.io.aluOp.poke(0.U)
      ctl.io.funct7.poke("b0100000".U)
      ctl.io.funct3.poke("b101".U)

      ctl.io.aluCtl.expect(7.U)
    }
  }
}
