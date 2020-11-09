package automaton.control

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class MainControlTest extends FlatSpec with ChiselScalatestTester {
  behavior.of("Main Controller")

  it should "generate correct signals based on opcode" in {
    test(new MainControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      // Reguster-Register
      ctl.io.opcode.poke("b0110011".U)
      ctl.io.aluOp.expect(0.U)

      // Register-Immediate
      ctl.io.opcode.poke("b0010011".U)
      ctl.io.aluOp.expect(1.U)

      // Branch
      ctl.io.opcode.poke("b1100011".U)
      ctl.io.aluOp.expect(2.U)

      // LOAD
      ctl.io.opcode.poke("b0000011".U)
      ctl.io.aluOp.expect(3.U)

      // STORE
      ctl.io.opcode.poke("b0100011".U)
      ctl.io.aluOp.expect(4.U)

      // JAL
      ctl.io.opcode.poke("b1101111".U)
      ctl.io.aluOp.expect(5.U)

      // JAL
      ctl.io.opcode.poke("b1100111".U)
      ctl.io.aluOp.expect(6.U)
    }
  }
}
