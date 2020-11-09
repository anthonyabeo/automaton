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

  it should "generate correct ALU OP for Register-Immediate Operations" in {
    test(new ALUControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      /////////////////////
      // ADDI
      /////////////////////
      ctl.io.aluOp.poke(1.U)
      ctl.io.funct3.poke(0.U)

      ctl.io.aluCtl.expect(0.U)

      /////////////////////
      // SLLI
      /////////////////////
      ctl.io.aluOp.poke(1.U)
      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b001".U)

      ctl.io.aluCtl.expect(5.U)

      /////////////////////
      // SRLI
      /////////////////////
      ctl.io.aluOp.poke(1.U)
      ctl.io.funct7.poke("b0000000".U)
      ctl.io.funct3.poke("b101".U)

      ctl.io.aluCtl.expect(6.U)

      /////////////////////
      // SRAI
      /////////////////////
      ctl.io.aluOp.poke(1.U)
      ctl.io.funct7.poke("b0100000".U)
      ctl.io.funct3.poke("b101".U)

      ctl.io.aluCtl.expect(7.U)

      /////////////////////
      // SLTI[U]
      /////////////////////
      ctl.io.aluOp.poke(1.U)

      ctl.io.funct3.poke("b010".U)
      ctl.io.aluCtl.expect(1.U)

      ctl.io.funct3.poke("b011".U)
      ctl.io.aluCtl.expect(1.U)

      /////////////////////
      // XOR
      /////////////////////
      ctl.io.aluOp.poke(1.U)
      ctl.io.funct3.poke("b100".U)

      ctl.io.aluCtl.expect(4.U)

      /////////////////////
      // ORI
      /////////////////////
      ctl.io.aluOp.poke(1.U)
      ctl.io.funct3.poke("b110".U)

      ctl.io.aluCtl.expect(3.U)

      /////////////////////
      // ANDI
      /////////////////////
      ctl.io.aluOp.poke(1.U)
      ctl.io.funct3.poke("b111".U)

      ctl.io.aluCtl.expect(2.U)

      ////////////////////////
      // BRANCH
      ////////////////////////
      ctl.io.aluOp.poke(2.U)

      ctl.io.aluCtl.expect(1.U)

      ////////////////////////
      // LOAD & STORE
      ////////////////////////
      ctl.io.aluOp.poke(3.U)
      ctl.io.aluCtl.expect(0.U)

      ctl.io.aluOp.poke(4.U)
      ctl.io.aluCtl.expect(0.U)

      ////////////////////////
      // JAL & JALR
      ////////////////////////
      ctl.io.aluOp.poke(5.U)
      ctl.io.aluCtl.expect(0.U)

      ctl.io.aluOp.poke(6.U)
      ctl.io.aluCtl.expect(0.U)
    }
  }
}
