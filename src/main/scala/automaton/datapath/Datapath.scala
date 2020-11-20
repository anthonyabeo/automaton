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

  val PC = RegInit(0.U(XLEN.W))
  val InstrMem = Module(new InstrCache(XLEN))
  val DataMem = Module(new DataCache(XLEN))
  val RegFile = Module(new RegisterFile(XLEN))
  val Alu = Module(new ALU(XLEN))

  InstrMem.io.addr := PC
  val instr = InstrMem.io.dataOUT

  val iTypeImme = WireInit(signExt(instr(31, 20).asSInt, 52))
  val sTypeImme = WireInit(signExt(Cat(instr(31, 25), instr(11, 7)).asSInt, 52))
  val jTypeImme = WireInit(signExt(Cat(instr(31), instr(19, 12), instr(20), instr(30, 21), "b0".U).asSInt, 43))
  val bTypeImme = WireInit(signExt(Cat(instr(31), instr(7), instr(30, 25), instr(11, 8), "b0".U).asSInt, 51))
  val uTypeImme = WireInit(signExt(Cat(instr(31, 12), Fill(12, "b0".U)).asSInt, 32))

  val OPCODE = instr(6, 0)
  val RD = instr(11, 7)
  val RS1 = instr(19, 15)
  val RS2 = instr(24, 20)
  val FUNCT3 = instr(14, 12)
  val FUNCT7 = instr(31, 25)

  /////////////////////////////
  // Register File
  /////////////////////////////
  RegFile.io.readReg1 := RS1
  RegFile.io.readReg2 := RS2
  RegFile.io.writeReg := RD
  RegFile.io.wrEna := io.regWrite
  when(io.toReg === 0.U) {
    when(io.wOp) {
      RegFile.io.writeData := signExt(Alu.io.result(31, 0).asSInt, 32)
    }.otherwise {
      RegFile.io.writeData := Alu.io.result
    }
  }.elsewhen(io.toReg === 1.U) {
    RegFile.io.writeData := DataMem.io.dataOUT
  }.elsewhen(io.toReg === 2.U) {
    RegFile.io.writeData := oneTo32Sext(Alu.io.negative)
  }.elsewhen(io.toReg === 3.U) {
    RegFile.io.writeData := (PC + 4.U).asSInt
  }.otherwise {
    RegFile.io.writeData := uTypeImme
  }

  ///////////////////////////
  // selecting ALU Operand A
  ///////////////////////////
  when(io.aluSrcA === 0.U) {
    when(io.jmp) {
      Alu.io.a := RegFile.io.readData1 << 2
    }.elsewhen(io.wOp) {
      Alu.io.a := signExt(RegFile.io.readData1(31, 0).asSInt, 32)
    }.otherwise {
      Alu.io.a := RegFile.io.readData1
    }
  }.otherwise {
    Alu.io.a := (PC).asSInt
  }

  ///////////////////////////
  // selecting ALU Operand B
  ///////////////////////////
  when(io.aluSrcB === 0.U) {
    when(io.wOp) {
      Alu.io.b := signExt(RegFile.io.readData2(31, 0).asSInt, 32)
    }.otherwise {
      Alu.io.b := RegFile.io.readData2
    }
  }.elsewhen(io.aluSrcB === 1.U) {
    when(io.jmp) {
      Alu.io.b := iTypeImme << 2
    }.elsewhen(io.memWrite) {
      Alu.io.b := sTypeImme
    }.otherwise {
      Alu.io.b := iTypeImme
    }
  }.elsewhen(io.aluSrcB === 2.U) {
    Alu.io.b := (jTypeImme.asSInt << 2)
  }.otherwise {
    Alu.io.b := uTypeImme
  }
  Alu.io.aluCtl := io.aluCtl
  Alu.io.wOp := io.wOp

  DataMem.io.addr := Alu.io.result.asUInt
  DataMem.io.wrEna := io.memWrite
  DataMem.io.dataIN := RegFile.io.readData2
  DataMem.io.size := io.size

  ///////////////////////////////
  // Next PC
  //////////////////////////////
  when(io.branch) {
    switch(io.bType) {
      is("b00".U) { // BEQ
        when(Alu.io.zero) {
          PC := PC + (bTypeImme.asUInt << 2)
        }.otherwise {
          PC := PC + 4.U
        }
      }
      is("b01".U) { // BNE
        when(!Alu.io.zero) {
          PC := PC + (bTypeImme.asUInt << 2)
        }.otherwise {
          PC := PC + 4.U
        }
      }
      is("b10".U) { // BLT[U]
        when(Alu.io.negative || Alu.io.zero) {
          PC := PC + (bTypeImme.asUInt << 2)
        }.otherwise {
          PC := PC + 4.U
        }
      }
      is("b11".U) { // BGE[U]
        when(Alu.io.zero || Alu.io.positive) {
          PC := PC + (bTypeImme.asUInt << 2)
        }.otherwise {
          PC := PC + 4.U
        }
      }
    }
  }.elsewhen(io.jmp) {
    PC := Alu.io.result.asUInt
  }.otherwise {
    PC := PC + 4.U
  }

  ////////////////////
  // Outputs
  ////////////////////
  io.opcode := OPCODE
  io.funct3 := FUNCT3
  io.funct7 := FUNCT7

  io.wrData := RegFile.io.readData2
  io.addr := Alu.io.result.asUInt
}
