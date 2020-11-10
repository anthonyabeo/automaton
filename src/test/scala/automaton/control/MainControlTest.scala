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
      ///////////////////////
      // Reguster-Register
      //////////////////////
      ctl.io.opcode.poke("x33".U)
      ctl.io.aluOp.expect(0.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ctl.io.funct3.poke("b010".U)
      ctl.io.toReg.expect(2.U)
    }

    test(new MainControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ////////////////////////
      // Register-Immediate
      ///////////////////////
      ctl.io.opcode.poke("b0010011".U)

      ctl.io.aluOp.expect(1.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)

      ctl.io.funct3.poke("b010".U)
      ctl.io.toReg.expect(2.U)
    }

    test(new MainControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ////////////
      // Branch
      ////////////
      ctl.io.opcode.poke("b1100011".U)
      ctl.io.aluOp.expect(2.U)
      ctl.io.regWrite.expect(false.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(0.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(true.B)
      ctl.io.jmp.expect(false.B)
    }

    test(new MainControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ///////////
      // LOAD
      ///////////
      ctl.io.opcode.poke("b0000011".U)

      ctl.io.aluOp.expect(3.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(1.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)
    }

    test(new MainControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      ////////////
      // STORE
      ////////////
      ctl.io.opcode.poke("b0100011".U)

      ctl.io.aluOp.expect(4.U)
      ctl.io.regWrite.expect(false.B)
      ctl.io.memWrite.expect(true.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(0.U)
      ctl.io.toReg.expect(0.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(false.B)
    }

    test(new MainControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      //////////
      // JAL
      //////////
      ctl.io.opcode.poke("b1101111".U)

      ctl.io.aluOp.expect(5.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(2.U)
      ctl.io.aluSrcA.expect(1.U)
      ctl.io.toReg.expect(3.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(true.B)
    }

    test(new MainControl).withAnnotations(Seq(WriteVcdAnnotation)) { ctl =>
      //////////
      // JALR
      //////////
      ctl.io.opcode.poke("b1100111".U)

      ctl.io.aluOp.expect(6.U)
      ctl.io.regWrite.expect(true.B)
      ctl.io.memWrite.expect(false.B)
      ctl.io.aluSrcB.expect(1.U)
      ctl.io.aluSrcA.expect(1.U)
      ctl.io.toReg.expect(3.U)
      ctl.io.branch.expect(false.B)
      ctl.io.jmp.expect(true.B)
    }
  }
}
