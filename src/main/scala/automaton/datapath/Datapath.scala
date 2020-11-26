package automaton.datapath

import chisel3._
import chisel3.util._

import datapath.{oneTo32Sext, signExt}

class Datapath(XLEN: Int) extends Module {
  val io = IO(new Bundle {
    val regWrite = Input(Bool())
    val aluCtl = Input(UInt(3.W))
    val memWrite = Input(Bool())
    val aluSrcB = Input(UInt(2.W))
    val aluSrcA = Input(UInt(2.W))
    val toReg = Input(UInt(3.W))
    val branch = Input(Bool())
    val bType = Input(UInt(2.W))
    val jmp = Input(Bool())
    val size = Input(UInt(3.W))
    val wOp = Input(Bool())

    val opcode = Output(UInt(7.W))
    val funct3 = Output(UInt(3.W))
    val funct7 = Output(UInt(7.W))
    val wrData = Output(SInt(XLEN.W))
    val addr = Output(UInt(XLEN.W))
  })

  // Datapath Components
  val PC_IF = RegInit(0.U(XLEN.W))
  val InstrMem = Module(new InstrCache(XLEN))
  val DataMem = Module(new DataCache(XLEN))
  val RegFile = Module(new RegisterFile(XLEN))
  val Alu = Module(new ALU(XLEN))

  /////////////////////////////////////////////
  // STAGE REGISTERS
  /////////////////////////////////////////////
  val IF_ID = Module(new IFIDStageReg)
  val ID_EX = Module(new IDEXStageReg)
  val EX_MEM = Module(new EXMEMStageReg)
  val MEM_WB = Module(new MEMWBStageReg)

  /////////////////////////////////////////////
  // FETCH STAGE
  /////////////////////////////////////////////
  InstrMem.io.addr := PC_IF
  IF_ID.io.instrIF := InstrMem.io.dataOUT

  PC_IF := PC_IF + 4.U

  /////////////////////////////////////////////
  // DECODE STAGE
  /////////////////////////////////////////////
  val OPCODE = IF_ID.io.instrID(6, 0)
  val RD = IF_ID.io.instrID(11, 7)
  val RS1 = IF_ID.io.instrID(19, 15)
  val RS2 = IF_ID.io.instrID(24, 20)
  val FUNCT3 = IF_ID.io.instrID(14, 12)
  val FUNCT7 = IF_ID.io.instrID(31, 25)

  // Immediate Constants
  val iTypeImme = WireInit(signExt(IF_ID.io.instrID(31, 20).asSInt, 52))
  val sTypeImme = WireInit(signExt(Cat(IF_ID.io.instrID(31, 25), IF_ID.io.instrID(11, 7)).asSInt, 52))
  val jTypeImme = WireInit(
    signExt(
      Cat(
        IF_ID.io.instrID(31),
        IF_ID.io.instrID(19, 12),
        IF_ID.io.instrID(20),
        IF_ID.io.instrID(30, 21),
        "b0".U
      ).asSInt,
      43
    )
  )
  val bTypeImme = WireInit(
    signExt(
      Cat(IF_ID.io.instrID(31), IF_ID.io.instrID(7), IF_ID.io.instrID(30, 25), IF_ID.io.instrID(11, 8), "b0".U).asSInt,
      51
    )
  )
  val uTypeImme = WireInit(signExt(Cat(IF_ID.io.instrID(31, 12), Fill(12, "b0".U)).asSInt, 32))

  // Register File
  RegFile.io.readReg1 := RS1
  RegFile.io.readReg2 := RS2
  RegFile.io.wrEna := io.regWrite

  ID_EX.io.rd1ID := RegFile.io.readData1
  ID_EX.io.rd2ID := RegFile.io.readData2
  ID_EX.io.writeRegID := RD

  /////////////////////////////////////////////
  // EXECUTE STAGE
  /////////////////////////////////////////////
  Alu.io.a := ID_EX.io.rd1EX
  Alu.io.b := ID_EX.io.rd2EX
  Alu.io.aluCtl := io.aluCtl
  Alu.io.wOp := io.wOp

  val regWriteDataEX = Alu.io.b
  EX_MEM.io.aluResEX := Alu.io.result
  EX_MEM.io.aluZeroEX := Alu.io.zero
  EX_MEM.io.writeDataEX := regWriteDataEX
  EX_MEM.io.writeRegEX := ID_EX.io.writeRegEX

  /////////////////////////////////////////////
  // MEMORY STAGE
  /////////////////////////////////////////////
  DataMem.io.addr := EX_MEM.io.aluResMEM.asUInt
  DataMem.io.dataIN := EX_MEM.io.writeDataMEM
  DataMem.io.wrEna := io.memWrite
  DataMem.io.size := io.size

  MEM_WB.io.aluResMEM := EX_MEM.io.aluResMEM
  MEM_WB.io.writeRegMEM := EX_MEM.io.writeRegMEM

  /////////////////////////////////////////////
  // WRITE_BACK STAGE
  /////////////////////////////////////////////
  RegFile.io.writeData := MEM_WB.io.aluResWB
  RegFile.io.writeReg := MEM_WB.io.writeRegMEM

  // /////////////////////////////
  // // Register File
  // /////////////////////////////
  // RegFile.io.readReg1 := RS1
  // RegFile.io.readReg2 := RS2
  // RegFile.io.writeReg := RD
  // RegFile.io.wrEna := io.regWrite
  // when(io.toReg === 0.U) {
  //   when(io.wOp) {
  //     RegFile.io.writeData := signExt(Alu.io.result(31, 0).asSInt, 32)
  //   }.otherwise {
  //     RegFile.io.writeData := Alu.io.result
  //   }
  // }.elsewhen(io.toReg === 1.U) {
  //   RegFile.io.writeData := DataMem.io.dataOUT
  // }.elsewhen(io.toReg === 2.U) {
  //   RegFile.io.writeData := oneTo32Sext(Alu.io.negative)
  // }.elsewhen(io.toReg === 3.U) {
  //   RegFile.io.writeData := (PC + 4.U).asSInt
  // }.otherwise {
  //   RegFile.io.writeData := uTypeImme
  // }

  // ///////////////////////////
  // // selecting ALU Operand A
  // ///////////////////////////
  // when(io.aluSrcA === 0.U) {
  //   when(io.jmp) {
  //     Alu.io.a := RegFile.io.readData1 << 2
  //   }.elsewhen(io.wOp) {
  //     Alu.io.a := signExt(RegFile.io.readData1(31, 0).asSInt, 32)
  //   }.otherwise {
  //     Alu.io.a := RegFile.io.readData1
  //   }
  // }.otherwise {
  //   Alu.io.a := (PC).asSInt
  // }

  // ///////////////////////////
  // // selecting ALU Operand B
  // ///////////////////////////
  // when(io.aluSrcB === 0.U) {
  //   when(io.wOp) {
  //     Alu.io.b := signExt(RegFile.io.readData2(31, 0).asSInt, 32)
  //   }.otherwise {
  //     Alu.io.b := RegFile.io.readData2
  //   }
  // }.elsewhen(io.aluSrcB === 1.U) {
  //   when(io.jmp) {
  //     Alu.io.b := iTypeImme << 2
  //   }.elsewhen(io.memWrite) {
  //     Alu.io.b := sTypeImme
  //   }.otherwise {
  //     Alu.io.b := iTypeImme
  //   }
  // }.elsewhen(io.aluSrcB === 2.U) {
  //   Alu.io.b := (jTypeImme.asSInt << 2)
  // }.otherwise {
  //   Alu.io.b := uTypeImme
  // }
  // Alu.io.aluCtl := io.aluCtl
  // Alu.io.wOp := io.wOp

  // DataMem.io.addr := Alu.io.result.asUInt
  // DataMem.io.wrEna := io.memWrite
  // DataMem.io.dataIN := RegFile.io.readData2
  // DataMem.io.size := io.size

  ///////////////////////////////
  // Next PC
  //////////////////////////////
  // when(io.branch) {
  //   switch(io.bType) {
  //     is("b00".U) { // BEQ
  //       when(Alu.io.zero) {
  //         PC := PC + (bTypeImme.asUInt << 2)
  //       }.otherwise {
  //         PC := PC + 4.U
  //       }
  //     }
  //     is("b01".U) { // BNE
  //       when(!Alu.io.zero) {
  //         PC := PC + (bTypeImme.asUInt << 2)
  //       }.otherwise {
  //         PC := PC + 4.U
  //       }
  //     }
  //     is("b10".U) { // BLT[U]
  //       when(Alu.io.negative) {
  //         PC := PC + (bTypeImme.asUInt << 2)
  //       }.otherwise {
  //         PC := PC + 4.U
  //       }
  //     }
  //     is("b11".U) { // BGE[U]
  //       when(Alu.io.zero || Alu.io.positive) {
  //         PC := PC + (bTypeImme.asUInt << 2)
  //       }.otherwise {
  //         PC := PC + 4.U
  //       }
  //     }
  //   }
  // }.elsewhen(io.jmp) {
  //   PC := Alu.io.result.asUInt
  // }.otherwise {
  //   PC := PC + 4.U
  // }

  ////////////////////
  // Outputs
  ////////////////////
  io.opcode := OPCODE
  io.funct3 := FUNCT3
  io.funct7 := FUNCT7

  io.wrData := RegFile.io.readData2
  io.addr := Alu.io.result.asUInt
}
