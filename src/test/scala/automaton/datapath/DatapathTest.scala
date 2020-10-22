package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class DatapathTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Datapath")

  "Datapath" should "perform register ADD instruction" in {
    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.aluSrcB.poke(0.U)

        dp.clock.step(1)

        dp.io.reg.expect(11.S)
        dp.io.pc.expect(1.U)
      }
    }
  }

  it should "perform register ADDI operation" in {
    test(new Datapath(XLEN = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        // Delay by 1 clock cycle to execute first instruction
        dp.clock.step(1)

        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.aluSrcB.poke(1.U)

        dp.clock.step(1)

        dp.io.reg.expect(20.S)
        dp.io.pc.expect(2.U)
      }
    }
  }
}
