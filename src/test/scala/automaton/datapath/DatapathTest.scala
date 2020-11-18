package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class DatapathTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Datapath")

  it should "Run All the Tests" in {
    test(new Datapath(XLEN = 64)).withAnnotations(Seq(WriteVcdAnnotation)) { dp =>
      dp.io.pc.expect(0.U)

      /////////////
      // 1. ADD //
      /////////////
      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(0.U)

      dp.io.reg.expect(11.S)

      ////////////
      // 2. AND //
      ////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(4.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(2.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(0.U)

      dp.io.reg.expect(4.S)

      ///////////
      // 3. OR //
      ///////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(8.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(3.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(7.S)

      ////////////
      // 4. XOR //
      ////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(12.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(4.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(3.S)

      ////////////
      // 5. SUB //
      ////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(16.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(1.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(-1.S)

      ////////////////////////////////
      // 6. SLL- shift left logical //
      ////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(20.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(5.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(320.S)

      /////////////////////////////////
      // 7. SRL- shift right logical //
      /////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(24.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(6.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(0.S)

      /////////////////////////////////////
      // 8. SRA- shift right arithmetic //
      ////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(28.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(7.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(-3.S)

      /////////////////////////////////////////////////////////////////////
      // 9. SLT - Reg[rd] <= 1 if Reg[rs1] < Reg[rs2] else Reg[rd] <= 0 //
      ////////////////////////////////////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(32.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(2.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.io.reg.expect(-3.S)
      dp.io.neg.expect(true.B)

      /////////////////////////////////////////////////////////////////////
      // 10. SLTU - Reg[rd] <= 1 if Reg[rs1] < Reg[rs2] else Reg[rd] <= 0 //
      /////////////////////////////////////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(36.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(2.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.io.reg.expect(-1.S)
      dp.io.neg.expect(true.B)

      //////////////
      // 11. ADDI //
      //////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(40.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.io.reg.expect(20.S)

      //////////////
      // 12. ANDI //
      //////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(44.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(2.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.io.reg.expect(5.S)

      //////////////
      // 13. ORI  //
      //////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(48.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(3.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.io.reg.expect(15.S)

      //////////////
      // 14. XORI //
      //////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(52.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(4.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      dp.io.reg.expect(10.S)

      ///////////////
      // 15. SLLI  //
      ///////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(56.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(5.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(24.S)

      ///////////////
      // 16. SRLI  //
      //////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(60.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(6.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(3.S)

      //////////////
      // 17. SRAI //
      //////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(64.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(7.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      dp.io.reg.expect(-5.S)

      ///////////////
      // 18. SLTI  //
      ///////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(68.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.io.reg.expect(-4.S)
      dp.io.neg.expect(true.B)

      ///////////////
      // 19. SLTIU //
      ///////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(72.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(2.U)
      dp.io.aluCtl.poke(1.U) // substraction

      dp.io.reg.expect(3.S)
      dp.io.neg.expect(false.B)

      /////////////////////////////
      // SB- store byte          //
      ////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(76.U)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.size.poke(0.U)

      dp.io.reg.expect(8.S)

      /////////////////////////////
      // SH- store Halfword //
      ////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(80.U)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.size.poke(1.U)

      dp.io.reg.expect(6.S)

      //////////////////////////
      // SW - store word //
      /////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(84.U)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.size.poke(2.U)

      dp.io.reg.expect(6.S)

      //////////////////////////
      // SD - store Double      //
      /////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(88.U)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.size.poke(3.U)

      dp.io.reg.expect(8.S)

      //////////////////////////
      // LB - load byte   //
      //////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(92.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(0.U)

      dp.io.reg.expect(10.S)

      //////////////////////////////
      // LH - load Halfword   //
      //////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(96.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(1.U)

      dp.io.reg.expect(10.S)

      //////////////////////////
      // LW - load word   //
      //////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(100.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(2.U)

      dp.io.reg.expect(10.S)

      ////////////////////////////////
      // LD - load DoubleWord   //
      ////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(104.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(3.U)

      dp.io.reg.expect(10.S)

      ///////////////////////////////////
      // LBU - load byte Unsigned  //
      ///////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(108.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(4.U)

      dp.io.reg.expect(10.S)

      ///////////////////////////////////////
      // LHU - load Halfword Unsigned  //
      ///////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(112.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(5.U)

      dp.io.reg.expect(10.S)

      ///////////////////////////////////////
      // LWU - load Word Unsigned          //
      ///////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(116.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(1.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(6.U)

      dp.io.reg.expect(10.S)

      ///////////////////////////////////////
      // ADDW - Add Word                   //
      ///////////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(120.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(0.U)
      dp.io.wOp.poke(true.B)

      dp.io.reg.expect(11.S)

      ///////////////////////////////////////
      // SUBW - Subtract Word              //
      ///////////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(124.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.wOp.poke(true.B)

      dp.io.reg.expect(-1.S)

      ///////////////////////////////////////
      // SLLW - Shift Left Logical Word    //
      ///////////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(128.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(5.U)
      dp.io.wOp.poke(true.B)

      dp.io.reg.expect(320.S)

      ///////////////////////////////////////
      // SRLW - Shift Right Logical Word   //
      ///////////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(132.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(6.U)
      dp.io.wOp.poke(true.B)

      dp.io.reg.expect(0.S)

      //////////////////////////////////////////
      // SRAW - Shift Right Arithmetic Word   //
      //////////////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(136.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(7.U)
      dp.io.wOp.poke(true.B)

      dp.io.reg.expect(-3.S)

      ////////////////////////////////
      // BEQ - branch if equal //
      ///////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(140.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)
      dp.io.bType.poke("b00".U)

      dp.io.reg.expect(0.S)
      dp.io.zero.expect(true.B)
      dp.io.neg.expect(false.B)
      dp.io.pos.expect(false.B)

      ////////////////////////////////////
      // BNE - branch if not equals //
      ////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(148.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)
      dp.io.bType.poke("b01".U)

      dp.io.reg.expect(-3.S)
      dp.io.neg.expect(true.B)
      dp.io.zero.expect(false.B)

      ////////////////////////////////////
      // BLT - branch if less than  //
      ////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(156.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)
      dp.io.bType.poke("b10".U)

      dp.io.reg.expect(-3.S)
      dp.io.zero.expect(false.B)
      dp.io.neg.expect(true.B)

      ////////////////////////////////////////////////
      // BLTU - branch if less than or equal to //
      ////////////////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(164.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)
      dp.io.bType.poke("b10".U)

      dp.io.reg.expect(0.S)
      dp.io.zero.expect(true.B)
      dp.io.neg.expect(false.B)

      ////////////////////////////////////////
      // BGE - branch if greater than   //
      ///////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(172.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)
      dp.io.bType.poke("b11".U)

      dp.io.reg.expect(3.S)
      dp.io.zero.expect(false.B)
      dp.io.neg.expect(false.B)

      //////////////////////////////////////////////////
      // BGEU - branch if grater than or equal to //
      //////////////////////////////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)
      dp.io.pc.expect(180.U)

      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.branch.poke(true.B)
      dp.io.bType.poke("b11".U)

      dp.io.reg.expect(0.S)
      dp.io.zero.expect(true.B)
      dp.io.neg.expect(false.B)

      ///////////////////////////////////////
      // LUI- Load Upper Immediate         //
      //////////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(188.U)

      dp.io.regWrite.poke(true.B)
      dp.io.toReg.poke(4.U)
      dp.io.branch.poke(false.B)
      dp.io.jmp.poke(false.B)

      ///////////////////////////////////////
      // AUIPC- Load Upper Immediate       //
      //////////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(192.U)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(1.U)
      dp.io.aluSrcB.poke(3.U)
      dp.io.toReg.poke(0.U)
      dp.io.aluCtl.poke(0.U)

      dp.io.reg.expect(377024.S)

      ////////////////////////////
      // JAL- Jump and Link //
      ////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(196.U)

      dp.io.aluSrcB.poke(2.U)
      dp.io.aluSrcA.poke(1.U)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(3.U)
      dp.io.regWrite.poke(true.B)
      dp.io.jmp.poke(true.B)

      dp.io.reg.expect(204.S)

      ///////////////////////////////////////
      // JALR- Jump and Link Register //
      //////////////////////////////////////
      dp.clock.step(1)
      dp.io.pc.expect(204.U)

      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(3.U)
      dp.io.regWrite.poke(true.B)
      dp.io.jmp.poke(true.B)

      dp.io.reg.expect(136.S)
    }
  }
}
