package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class DatapathTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Datapath")

  it should "Run All the Tests" in {
    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      dp.io.pc.expect(0.U)

      // ADD
      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(0.U)

      dp.io.reg.expect(11.S)

      // AND
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(1.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(2.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(0.U)

      dp.io.reg.expect(4.S)

      // OR
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(2.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(3.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(7.S)

      // XOR
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(3.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(4.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(3.S)

      // SUB
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(1.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(-1.S)

      // SLL- shift left logical
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(5.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(5.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(320.S)

      // SRL- shift right logical
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(6.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(6.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(0.S)

      // SRA- shift right arithmetic
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(7.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(7.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(-3.S)

      // SLT - Reg[rd] <= 1 if Reg[rs1] < Reg[rs2] else Reg[rd] <= 0
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(8.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(2.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.io.reg.expect(-3.S)
      dp.io.neg.expect(true.B)

      // SLTU - Reg[rd] <= 1 if Reg[rs1] < Reg[rs2] else Reg[rd] <= 0
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(9.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.io.reg.expect(-1.S)
      dp.io.neg.expect(true.B)

      // ADDI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(10.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.io.reg.expect(20.S)

      // ANDI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(11.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(2.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.io.reg.expect(5.S)

      // ORI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(12.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(3.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.io.reg.expect(15.S)

      // XORI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(13.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(4.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.io.reg.expect(10.S)

      // SLLI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(14.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(5.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(24.S)

      // SRLI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(15.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(6.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(3.S)

      // SRAI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(16.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(7.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(-5.S)

      // SLTI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(17.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.io.reg.expect(-4.S)
      dp.io.neg.expect(true.B)

      // SLTIU
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(18.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(2.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.io.reg.expect(3.S)
      dp.io.neg.expect(false.B)

      // SW - store word
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(19.U)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)

      dp.io.reg.expect(6.S)

      // LW - load word
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(20.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)
      dp.io.branch.poke(false.B)

      dp.io.reg.expect(10.S)

      // BEQ - branch if equal
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(21.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)

      dp.io.reg.expect(0.S)
      dp.io.zero.expect(true.B)
      dp.io.neg.expect(false.B)

      // BNE - branch if not equals
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(23.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)

      dp.io.reg.expect(-3.S)
      dp.io.neg.expect(true.B)
      dp.io.zero.expect(false.B)

      // BLT - branch if less than
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(25.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)

      dp.io.reg.expect(-3.S)
      dp.io.zero.expect(false.B)
      dp.io.neg.expect(true.B)

      // BGE - branch if less than
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(27.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)

      dp.io.reg.expect(3.S)
      dp.io.zero.expect(false.B)
      dp.io.neg.expect(false.B)

      ///////////////////////
      // JAL- Jump and Link
      ///////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(29.U)

      dp.io.aluSrcB.poke(2.U)
      dp.io.aluSrcA.poke(1.U)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(3.U)
      dp.io.regWrite.poke(true.B)
      dp.io.jmp.poke(true.B)

      dp.io.reg.expect(31.S)

      ////////////////////////////////
      // JALR- Jump and Link Register
      ////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(31.U)

      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(3.U)
      dp.io.regWrite.poke(true.B)
      dp.io.jmp.poke(true.B)

      dp.io.reg.expect(34.S)
    }
  }
}
