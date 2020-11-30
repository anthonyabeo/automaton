package automaton.datapath

import org.scalatest._

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

import firrtl.transforms.NoDCEAnnotation

class DatapathTest extends FlatSpec with ChiselScalatestTester with Matchers {
  behavior.of("Datapath")

  it should "Run All the Tests" in {
    test(new Datapath(XLEN = 64)).withAnnotations(Seq(WriteVcdAnnotation, NoDCEAnnotation)) { dp =>
      /////////////
      // 1. ADD //
      /////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)

      ////////////
      // 2. AND //
      ////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(2.U)

      ///////////
      // 3. OR //
      ///////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(3.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      // ////////////
      // // 4. XOR //
      // ////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(4.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      ////////////
      // 5. SUB //
      ////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(1.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      ////////////////////////////////
      // 6. SLL- shift left logical //
      ////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(5.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      /////////////////////////////////
      // 7. SRL- shift right logical //
      /////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(6.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      /////////////////////////////////////
      // 8. SRA- shift right arithmetic //
      ////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(7.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)

      /////////////////////////////////////////////////////////////////////
      // 9. SLT - Reg[rd] <= 1 if Reg[rs1] < Reg[rs2] else Reg[rd] <= 0 //
      ////////////////////////////////////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      /////////////////////////////////////////////////////////////////////
      // 10. SLTU - Reg[rd] <= 1 if Reg[rs1] < Reg[rs2] else Reg[rd] <= 0 //
      /////////////////////////////////////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      //////////////
      // 11. ADDI //
      //////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.toReg.poke(0.U)

      //////////////
      // 12. ANDI //
      //////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(2.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      //////////////
      // 13. ORI  //
      //////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(3.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      //////////////
      // 14. XORI //
      //////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(4.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)

      ///////////////
      // 15. SLLI  //
      ///////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(5.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      ///////////////
      // 16. SRLI  //
      //////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(6.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      //////////////
      // 17. SRAI //
      //////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluCtl.poke(7.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)

      ///////////////
      // 18. SLTI  //
      ///////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      ///////////////
      // 19. SLTIU //
      ///////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.toReg.poke(1.U)
      dp.io.aluCtl.poke(1.U) // substraction

      /////////////////////////////
      // SB- store byte          //
      /////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.size.poke(0.U)

      /////////////////////////////
      // SH- store Halfword //
      ////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.size.poke(1.U)

      //////////////////////////
      // SW - store word //
      /////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.size.poke(2.U)

      //////////////////////////
      // SD - store Double    //
      //////////////////////////
      // Delay for a few clock cycle to execute prior instructions
      dp.clock.step(1)

      dp.io.regWrite.poke(false.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(true.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.size.poke(3.U)

      //////////////////////////
      // LB - load byte       //
      //////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(2.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(0.U)

      //////////////////////////////
      // LH - load Halfword       //
      //////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(2.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(1.U)

      //////////////////////////
      // LW - load word       //
      //////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(2.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(2.U)

      ////////////////////////////////
      // LD - load DoubleWord   //
      ////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(2.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(3.U)

      ///////////////////////////////////
      // LBU - load byte Unsigned  //
      ///////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(2.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(4.U)

      ///////////////////////////////////////
      // LHU - load Halfword Unsigned  //
      ///////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(2.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(5.U)

      ///////////////////////////////////////
      // LWU - load Word Unsigned          //
      ///////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcB.poke(1.U)
      dp.io.memWrite.poke(false.B)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(2.U)
      dp.io.branch.poke(false.B)
      dp.io.size.poke(6.U)

      ///////////////////////////////////////
      // ADDW - Add Word                   //
      ///////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(0.U)
      dp.io.toReg.poke(0.U)
      dp.io.wOp.poke(true.B)

      ///////////////////////////////////////
      // SUBW - Subtract Word              //
      ///////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(1.U)
      dp.io.wOp.poke(true.B)

      ///////////////////////////////////////
      // SLLW - Shift Left Logical Word    //
      ///////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(5.U)
      dp.io.wOp.poke(true.B)

      ///////////////////////////////////////
      // SRLW - Shift Right Logical Word   //
      ///////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(6.U)
      dp.io.wOp.poke(true.B)

      //////////////////////////////////////////
      // SRAW - Shift Right Arithmetic Word   //
      //////////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(0.U)
      dp.io.aluCtl.poke(7.U)
      dp.io.wOp.poke(true.B)

      //////////////////////////////////////////
      // ADDIW - Add Immediate Word           //
      //////////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.aluCtl.poke(0.U)
      dp.io.wOp.poke(true.B)

      ///////////////////////////////////////////////
      // SLLIW - Shift Left Logical Immediate Word //
      ///////////////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.aluCtl.poke(5.U)
      dp.io.wOp.poke(true.B)

      ///////////////////////////////////////////////
      // SRLIW - Shift Right Logical Immediate Word //
      ///////////////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.aluCtl.poke(6.U)
      dp.io.wOp.poke(true.B)

      ///////////////////////////////////////////////////
      // SRAIW - Shift Right Arithmetic Immediate Word //
      ///////////////////////////////////////////////////
      dp.clock.step(1)

      dp.io.regWrite.poke(true.B)
      dp.io.aluSrcA.poke(0.U)
      dp.io.aluSrcB.poke(1.U)
      dp.io.aluCtl.poke(7.U)
      dp.io.wOp.poke(true.B)

      dp.clock.step(5)
      // ////////////////////////////////
      // // BEQ - branch if equal //
      // ///////////////////////////////
      // // Delay for a few clock cycle to execute prior instructions
      // dp.clock.step(1)

      // dp.io.aluSrcB.poke(0.U)
      // dp.io.aluCtl.poke(1.U)
      // dp.io.branch.poke(true.B)
      // dp.io.bType.poke("b00".U)

      // ////////////////////////////////////
      // // BNE - branch if not equals //
      // ////////////////////////////////////
      // // Delay for a few clock cycle to execute prior instructions
      // dp.clock.step(1)

      // dp.io.aluSrcB.poke(0.U)
      // dp.io.aluCtl.poke(1.U)
      // dp.io.branch.poke(true.B)
      // dp.io.bType.poke("b01".U)

      // ////////////////////////////////////
      // // BLT - branch if less than  //
      // ////////////////////////////////////
      // // Delay for a few clock cycle to execute prior instructions
      // dp.clock.step(1)

      // dp.io.aluSrcB.poke(0.U)
      // dp.io.aluCtl.poke(1.U)
      // dp.io.branch.poke(true.B)
      // dp.io.bType.poke("b10".U)

      // ////////////////////////////////////////////////
      // // BLTU - branch if less than or equal to //
      // ////////////////////////////////////////////////
      // // Delay for a few clock cycle to execute prior instructions
      // dp.clock.step(1)

      // dp.io.aluSrcB.poke(0.U)
      // dp.io.aluCtl.poke(1.U)
      // dp.io.branch.poke(true.B)
      // dp.io.bType.poke("b10".U)

      // ////////////////////////////////////////
      // // BGE - branch if greater than   //
      // ///////////////////////////////////////
      // // Delay for a few clock cycle to execute prior instructions
      // dp.clock.step(1)

      // dp.io.aluSrcB.poke(0.U)
      // dp.io.aluCtl.poke(1.U)
      // dp.io.branch.poke(true.B)
      // dp.io.bType.poke("b11".U)

      // //////////////////////////////////////////////////
      // // BGEU - branch if grater than or equal to //
      // //////////////////////////////////////////////////
      // // Delay for a few clock cycle to execute prior instructions
      // dp.clock.step(1)

      // dp.io.aluSrcB.poke(0.U)
      // dp.io.aluCtl.poke(1.U)
      // dp.io.branch.poke(true.B)
      // dp.io.bType.poke("b11".U)

      // ///////////////////////////////////////
      // // LUI- Load Upper Immediate         //
      // //////////////////////////////////////
      // dp.clock.step(1)

      // dp.io.regWrite.poke(true.B)
      // dp.io.toReg.poke(4.U)
      // dp.io.branch.poke(false.B)
      // dp.io.jmp.poke(false.B)

      // ///////////////////////////////////////
      // // AUIPC- Load Upper Immediate       //
      // //////////////////////////////////////
      // dp.clock.step(1)

      // dp.io.regWrite.poke(true.B)
      // dp.io.aluSrcA.poke(1.U)
      // dp.io.aluSrcB.poke(3.U)
      // dp.io.toReg.poke(0.U)
      // dp.io.aluCtl.poke(0.U)

      // ////////////////////////////
      // // JAL- Jump and Link //
      // ////////////////////////////
      // dp.clock.step(1)

      // dp.io.aluSrcB.poke(2.U)
      // dp.io.aluSrcA.poke(1.U)
      // dp.io.aluCtl.poke(0.U)
      // dp.io.toReg.poke(3.U)
      // dp.io.regWrite.poke(true.B)
      // dp.io.jmp.poke(true.B)

      // ///////////////////////////////////////
      // // JALR- Jump and Link Register      //
      // ///////////////////////////////////////
      // dp.clock.step(1)

      // dp.io.aluSrcA.poke(0.U)
      // dp.io.aluSrcB.poke(1.U)
      // dp.io.aluCtl.poke(0.U)
      // dp.io.toReg.poke(3.U)
      // dp.io.regWrite.poke(true.B)
      // dp.io.jmp.poke(true.B)
    }
  }
}
