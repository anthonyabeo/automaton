package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class DatapathTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Datapath")

  it should "perform Integer Register-Register Operations" in {
    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // ADD
        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.aluSrcB.poke(0.U)

        dp.clock.step(1)

        dp.io.reg.expect(11.S)
        dp.io.pc.expect(1.U)
      }
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // AND

        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(1)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(2.U)
        dp.io.memWrite.poke(false.B)
        dp.io.aluSrcB.poke(0.U)

        dp.clock.step(1)

        dp.io.reg.expect(4.S)
        dp.io.pc.expect(2.U)
      }
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // OR

        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(2)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(3.U)
        dp.io.aluSrcB.poke(0.U)
        dp.io.memWrite.poke(false.B)

        dp.clock.step(1)

        dp.io.reg.expect(7.S)
        dp.io.pc.expect(3.U)
      }
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // XOR
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(3)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(4.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.clock.step(1)

      dp.io.reg.expect(3.S)
      dp.io.pc.expect(4.U)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // SUB
        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(4)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(1.U)
        dp.io.aluSrcB.poke(0.U)
        dp.io.memWrite.poke(false.B)

        dp.clock.step(1)

        dp.io.reg.expect(-1.S)
        dp.io.pc.expect(5.U)
      }
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // SLL- shift left logical
        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(5)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(5.U)
        dp.io.aluSrcB.poke(0.U)
        dp.io.memWrite.poke(false.B)

        dp.clock.step(1)

        dp.io.reg.expect(320.S)
        dp.io.pc.expect(6.U)
      }
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // SRL- shift right logical
        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(6)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(6.U)
        dp.io.aluSrcB.poke(0.U)
        dp.io.memWrite.poke(false.B)

        dp.clock.step(1)

        dp.io.reg.expect(0.S)
        dp.io.pc.expect(7.U)
      }
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // SLT - Reg[rd] <= 1 if Reg[rs1] < Reg[rs2] else Reg[rd] <= 0
        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(7)

        dp.io.regWrite.poke(true.B)
        dp.io.aluSrcB.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.toReg.poke(1.U)
        dp.io.aluCtl.poke(1.U) // substraction

        dp.clock.step(1)

        dp.io.reg.expect(-3.S)
        dp.io.pc.expect(8.U)
        dp.io.neg.expect(true.B)
      }
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // SLTU - Reg[rd] <= 1 if Reg[rs1] < Reg[rs2] else Reg[rd] <= 0
        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(8)

        dp.io.regWrite.poke(true.B)
        dp.io.aluSrcB.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.toReg.poke(1.U)
        dp.io.aluCtl.poke(1.U) // substraction

        dp.clock.step(1)

        dp.io.reg.expect(-1.S)
        dp.io.pc.expect(9.U)
        dp.io.neg.expect(true.B)
      }
    }

    // test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
    //   {
    //     // SRA- shift right arithmetic
    //     // Delay for a few clock cycle to execute prior instructions
    //     dp.clock.step(1)
    //     dp.clock.step(1)
    //     dp.clock.step(1)
    //     dp.clock.step(1)
    //     dp.clock.step(1)
    //     dp.clock.step(1)
    //     dp.clock.step(1)

    //     dp.io.regWrite.poke(true.B)
    //     dp.io.aluCtl.poke(7.U)
    //     dp.io.aluSrcB.poke(0.U)
    //     dp.io.memWrite.poke(false.B)

    //     dp.clock.step(1)

    //     dp.io.reg.expect(-38.S)
    //     dp.io.pc.expect(8.U)
    //   }
    // }
  }

  it should "perform Integer Register-Immediate Instructions" in {
    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // ADDI
        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(9)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.aluSrcB.poke(1.U)

        dp.clock.step(1)

        dp.io.reg.expect(20.S)
        dp.io.pc.expect(10.U)
      }
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // ANDI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(10)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(2.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.clock.step(1)

      dp.io.reg.expect(5.S)
      dp.io.pc.expect(11.U)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // ORI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(11)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(3.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.clock.step(1)

      dp.io.reg.expect(15.S)
      dp.io.pc.expect(12.U)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // XORI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(12)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(4.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.clock.step(1)

      dp.io.reg.expect(10.S)
      dp.io.pc.expect(13.U)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // SLLI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(13)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(5.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      dp.clock.step(1)

      dp.io.reg.expect(24.S)
      dp.io.pc.expect(14.U)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // SRLI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(14)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(6.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      dp.clock.step(1)

      dp.io.reg.expect(3.S)
      dp.io.pc.expect(15.U)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // SLTI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(15)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.clock.step(1)

      dp.io.reg.expect(-4.S)
      dp.io.pc.expect(16.U)
      dp.io.neg.expect(true.B)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // SLTI
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(16)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.clock.step(1)

      dp.io.reg.expect(3.S)
      dp.io.pc.expect(17.U)
      dp.io.neg.expect(false.B)
    }
  }

  it should "perform  Load and Store Instructions" in {
    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // SW - store word
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(17)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)

      dp.clock.step(1)

      dp.io.pc.expect(18.U)
      dp.io.reg.expect(6.S)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // LW - load word
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(18)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)

      dp.clock.step(1)

      dp.io.reg.expect(10.S)

      dp.clock.step(1)

      dp.io.pc.expect(20.U)
    }
  }

  it should "perform Conditional Branch Instructions" in {
    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // BNE - branch if not equals
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(19)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)

      dp.clock.step(1)

      dp.io.reg.expect(-1.S)
      dp.io.neg.expect(true.B)
      dp.io.zero.expect(false.B)
      dp.io.pc.expect(20.U)
    }

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      // BEQ - branch if equal
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(20)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)

      dp.clock.step(1)

      dp.io.reg.expect(0.S)
      dp.io.zero.expect(true.B)

      dp.clock.step(1)

      dp.io.pc.expect(23.U)
    }

  }
}
