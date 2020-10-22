package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class DatapathTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Datapath")

  "Datapath" should "perform register ADD instruction" in {
    test(new Datapath(size = 32)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      {
        dp.io.regWrite.poke(true.B)
        dp.io.aluCtl.poke(0.U)
        dp.io.memWrite.poke(false.B)
        dp.io.aluSrcB.poke(0.U)

        dp.clock.step(1)

        dp.io.reg.expect(11.S)
        dp.io.pc.expect(4.U)
      }
    }
  }

  // it should "perform register ADDI operation" in {
  //   test(new Datapath(size = 32, regWidth = 5)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
  //     {
  //       dp.clock.step(1)

  //       dp.io.regWrite.poke(true.B)
  //       dp.io.aluCtl.poke(0.U)
  //       dp.io.memWrite.poke(false.B)
  //       dp.io.aluSrcB.poke(1.U)

  //       dp.clock.step(1)

  //       dp.io.reg.expect(17.S)
  //       dp.io.pc.expect(8.U)
  //     }
  //   }
  // }
}
