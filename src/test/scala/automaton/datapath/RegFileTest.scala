package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._

class RegFileTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Register File")
  "Register File" should "write to valid memory location" in {
    test(new RegisterFile(XLEN = 32)) { reg =>
      // Writing to Register files
      reg.io.wrEna.poke(true.B)
      reg.io.writeReg.poke(5.U)
      reg.io.writeData.poke(238.S)

      reg.clock.step(1)

      reg.io.wrEna.poke(false.B)
      reg.io.readReg1.poke(5.U)
      reg.io.readData1.expect(238.S)
    }
  }

  "Register File" should "read from both read ports asynchronously" in {
    test(new RegisterFile(XLEN = 32)) { reg =>
      reg.io.wrEna.poke(true.B)
      reg.io.writeReg.poke(2.U)
      reg.io.writeData.poke(100.S)

      reg.clock.step(1)

      reg.io.wrEna.poke(true.B)
      reg.io.writeReg.poke(9.U)
      reg.io.writeData.poke(87.S)

      reg.clock.step(1)

      reg.io.readReg1.poke(2.U)
      reg.io.readReg2.poke(9.U)
      reg.io.readData1.expect(100.S)
      reg.io.readData2.expect(87.S)
    }
  }
}
