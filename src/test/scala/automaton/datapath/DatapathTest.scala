package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._
import java.net.DatagramPacket

class DatapathTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Datapath")

  "Datapath" should "perform Integer Register-Register Operations" in {
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

    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
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

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.aluSrcB.poke(1.U)

        dp.clock.step(1)

        dp.io.reg.expect(20.S)
        dp.io.pc.expect(5.U)
      }
    }
  }
}