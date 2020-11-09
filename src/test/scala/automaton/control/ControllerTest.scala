package automaton.control

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class ControllerTest extends FlatSpec with ChiselScalatestTester {
  behavior.of("Controller")

  it should "generate control signals for Register-Register" in {
    test(new Controller).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ctl.io.opcode.poke("b0110011".U)
      //////////
      // ADD
      //////////
      ctl.io.funct3.poke("b000".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(0.U)

      //////////
      // SUB
      //////////
      ctl.io.funct3.poke("b000".U)
      ctl.io.funct7.poke("b0100000".U)

      ctl.io.aluCtl.expect(1.U)

      //////////
      // SLL
      //////////
      ctl.io.funct3.poke("b001".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(5.U)

      //////////
      // SLT[U]
      //////////
      ctl.io.funct3.poke("b010".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(1.U)

      ctl.io.funct3.poke("b011".U)
      ctl.io.aluCtl.expect(1.U)

      //////////
      // XOR
      //////////
      ctl.io.funct3.poke("b100".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(4.U)

      /////////////
      // SR[L|A] //
      /////////////
      ctl.io.funct3.poke("b101".U)
      ctl.io.funct7.poke("b0000000".U)

      ctl.io.aluCtl.expect(6.U)

      ctl.io.funct7.poke("b0100000".U)
      ctl.io.aluCtl.expect(7.U)

      //////////////
      // OR | AND
      /////////////
      ctl.io.funct3.poke("b110".U)
      ctl.io.funct7.poke("b0000000".U)
      ctl.io.aluCtl.expect(3.U)

      ctl.io.funct3.poke("b111".U)
      ctl.io.aluCtl.expect(2.U)

    }
  }

//   it should "generate control signals for Register-Intermediate" in {
//     test(new Controller).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
//       ctl.io.opcode.poke("b0110011".U)
//       //////////
//       // ADD
//       //////////
//       ctl.io.funct3.poke("b000".U)
//       ctl.io.funct7.poke("b0000000".U)

//       ctl.io.aluCtl.expect(0.U)

//       //////////
//       // SUB
//       //////////
//       ctl.io.funct3.poke("b000".U)
//       ctl.io.funct7.poke("b0100000".U)

//       ctl.io.aluCtl.expect(1.U)

//       //////////
//       // SLL
//       //////////
//       ctl.io.funct3.poke("b001".U)
//       ctl.io.funct7.poke("b0000000".U)

//       ctl.io.aluCtl.expect(5.U)

//       //////////
//       // SLT[U]
//       //////////
//       ctl.io.funct3.poke("b010".U)
//       ctl.io.funct7.poke("b0000000".U)

//       ctl.io.aluCtl.expect(1.U)

//       ctl.io.funct3.poke("b011".U)
//       ctl.io.aluCtl.expect(1.U)

//       //////////
//       // XOR
//       //////////
//       ctl.io.funct3.poke("b100".U)
//       ctl.io.funct7.poke("b0000000".U)

//       ctl.io.aluCtl.expect(4.U)

//       /////////////
//       // SR[L|A] //
//       /////////////
//       ctl.io.funct3.poke("b101".U)
//       ctl.io.funct7.poke("b0000000".U)

//       ctl.io.aluCtl.expect(6.U)

//       ctl.io.funct7.poke("b0100000".U)
//       ctl.io.aluCtl.expect(7.U)

//       //////////////
//       // OR | AND
//       /////////////
//       ctl.io.funct3.poke("b110".U)
//       ctl.io.funct7.poke("b0000000".U)
//       ctl.io.aluCtl.expect(3.U)

//       ctl.io.funct3.poke("b111".U)
//       ctl.io.aluCtl.expect(2.U)

//     }
//   }
}
