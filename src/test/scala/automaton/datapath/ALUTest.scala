package automaton.datapath

import org.scalatest._
import chiseltest._
import chisel3._

class ALUTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior of "ALU"
  it should "be able to add two register numbers" in {
    test(new ALU(8)) { c =>
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

  // it should "detect overflow after addition" in {
  //   test(new ALU(5)) { c =>
  //     {
  //       c.io.a.poke("b10100".asUInt(5.W))
  //       c.io.b.poke("b11010".asUInt(5.W))
  //       c.io.aluCtl.poke(0.U)

  //       c.io.result.expect("b01110".U)
  //       c.io.zero.expect(false.B)
  //       c.io.overflow.expect(true.B)
  //       c.io.negative.expect(false.B)
  //     }
  //   }
  // }
}
