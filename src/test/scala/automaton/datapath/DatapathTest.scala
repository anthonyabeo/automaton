package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._

class DatapathTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Datapath")

  "Datapath" should "perform register add instruction" in {
    test(new Datapath(size = 32, regWidth = 5)) { dp =>
      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.clock.step(1)

      dp.io.reg.expect(11.S)
      dp.io.pc.expect(4.U)

    }
  }
}
