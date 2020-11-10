package automaton.control

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

import Automaton.Operation._

class ControllerTest extends FlatSpec with ChiselScalatestTester {
  behavior.of("Controller")

  it should "generate control signals for Register-Register" in {
    test(new Controller).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ctl.io.opcode.poke(new fromBigIntToLiteral(RegOp.id).asUInt)

      //////////
      // ADD
      //////////
      ctl.io.funct3.poke("b000".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(0.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////
      // SUB
      //////////
      ctl.io.funct3.poke("b000".U)
      ctl.io.funct7.poke("b0100000".U)

      ctl.io.aluCtl.expect(1.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////
      // SLL
      //////////
      ctl.io.funct3.poke("b001".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(5.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////
      // SLT[U]
      //////////
      ctl.io.funct3.poke("b010".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(1.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(2.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ctl.io.funct3.poke("b011".U)

      ctl.io.aluCtl.expect(1.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(2.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////
      // XOR
      //////////
      ctl.io.funct3.poke("b100".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(4.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      /////////////
      // SR[L|A] //
      /////////////
      ctl.io.funct3.poke("b101".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(6.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ctl.io.funct7.poke("b0100000".U)

      ctl.io.aluCtl.expect(7.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////////
      // OR | AND
      /////////////
      ctl.io.funct3.poke("b110".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(3.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ctl.io.funct3.poke("b111".U)

      ctl.io.aluCtl.expect(2.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)
    }
  }

  it should "generate control signals for Register-Intermediate" in {
    test(new Controller).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ctl.io.opcode.poke(new fromBigIntToLiteral(ImmeOp.id).asUInt)
      //////////
      // ADDI
      //////////
      ctl.io.funct3.poke("b000".U)

      ctl.io.aluCtl.expect(0.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////
      // SLLI
      //////////
      ctl.io.funct3.poke("b001".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(5.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////
      // SLTI[U]
      //////////
      ctl.io.funct3.poke("b010".U)

      ctl.io.aluCtl.expect(1.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(2.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ctl.io.funct3.poke("b011".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(1.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(2.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////
      // XOR
      //////////
      ctl.io.funct3.poke("b100".U)

      ctl.io.aluCtl.expect(4.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      /////////////
      // SR[L|A]I //
      /////////////
      ctl.io.funct3.poke("b101".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(6.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ctl.io.funct7.poke("b0100000".U)

      ctl.io.aluCtl.expect(7.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      //////////////
      // ORI | ANDI
      //////////////
      ctl.io.funct3.poke("b110".U)

      ctl.io.aluCtl.expect(3.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ctl.io.funct3.poke("b111".U)

      ctl.io.aluCtl.expect(2.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)
    }
  }

  it should "generate control signals for Branch Instructions" in {
    test(new Controller).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ctl.io.opcode.poke(new fromBigIntToLiteral(BranchOp.id).asUInt)

      ctl.io.aluCtl.expect(1.U)
      ctl.io.regWrite.expect(false.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(true.B)
      ctl.io.jmp.expect(false.B)
    }
  }

  it should "generate control signals for Jump Instructions" in {
    test(new Controller).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ///////////////
      // JALR
      ///////////////
      ctl.io.opcode.poke(new fromBigIntToLiteral(JalrOp.id).asUInt)

      ctl.io.aluCtl.expect(0.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(1.U)
      ctl.io.toReg.expect(3.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(true.B)

      ///////////////
      // JAL
      ///////////////
      ctl.io.opcode.poke(new fromBigIntToLiteral(JalOp.id).asUInt)

      ctl.io.aluCtl.expect(0.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(2.U)
      ctl.io.aluSrcA.expect(1.U)
      ctl.io.toReg.expect(3.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(true.B)
    }
  }

  it should "generate control signals for Load AND Store Instructions" in {
    test(new Controller).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ///////////////
      // Load
      ///////////////
      ctl.io.opcode.poke((new fromBigIntToLiteral(LdOp.id).asUInt))

      ctl.io.aluCtl.expect(0.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(1.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ///////////////
      // Store
      ///////////////
      ctl.io.opcode.poke((new fromBigIntToLiteral(StrOp.id).asUInt))

      ctl.io.aluCtl.expect(0.U)
      ctl.io.regWrite.expect(false.B)
      ctl.io.memWrite.expect(true.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)
    }
  }
}
