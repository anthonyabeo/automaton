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
        dp.clock.step(1)
        dp.clock.step(1)

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
      dp.clock.step(1)
      dp.clock.step(1)
      dp.clock.step(1)

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
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)

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
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)

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
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(6.U)
        dp.io.aluSrcB.poke(0.U)
        dp.io.memWrite.poke(false.B)

        dp.clock.step(1)

        dp.io.reg.expect(0.S)
        dp.io.pc.expect(7.U)
      }
    }
  }

  it should "perform Integer Register-Immediate Instructions" in {
    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // ADDI

        // Delay for a few clock cycle to execute prior instructions
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)
        dp.clock.step(1)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.aluSrcB.poke(1.U)

        dp.clock.step(1)

        dp.io.reg.expect(20.S)
        dp.io.pc.expect(8.U)
      }
    }
  }
}
