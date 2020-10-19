package automaton.datapath

import org.scalatest._
import chiseltest._
import chisel3._

class ALUTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior of "ALU"
  it should "be able to add two register numbers" in {
    test(new ALU) { c =>
      {
        c.io.a.poke(12.U)
        c.io.b.poke(30.U)
        c.io.aluCtl.poke(0.U)

        c.io.result.expect(42.U)
        c.io.zero.expect(false.B)
        c.io.overflow.expect(false.B)
        c.io.negative.expect(false.B)
      }
    }
  }
}
