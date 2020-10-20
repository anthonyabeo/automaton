package automaton.datapath

import org.scalatest._
import chiseltest._
import chisel3._

class ALUTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("ALU")
  it should "be able to add two register numbers" in {
    test(new ALU(8)) { c =>
      {
        c.io.a.poke(12.S)
        c.io.b.poke(30.S)
        c.io.aluCtl.poke(0.U)

        c.clock.step(1)

        c.io.result.expect(42.S)
        c.io.zero.expect(false.B)
        c.io.negative.expect(false.B)
      }
    }
  }

  // it should "detect overflow after addition" in {
  //   test(new ALU(5)) { c =>
  //     {
  //       c.io.a.poke(-12.S)
  //       c.io.b.poke(-6.S)
  //       c.io.aluCtl.poke(0.U)

  //       c.io.result.expect(14.S)
  //       c.io.zero.expect(false.B)
  //       c.io.overflow.expect(true.B)
  //       c.io.negative.expect(false.B)
  //     }
  //   }
  // }
}
