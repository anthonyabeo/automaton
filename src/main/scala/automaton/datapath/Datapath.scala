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
    val toReg = Input(UInt(2.W))
    val branch = Input(Bool())
    val jmp = Input(Bool())

    val reg = Output(SInt(XLEN.W))
    val pc = Output(UInt(XLEN.W))
    val neg = Output(Bool())
    val zero = Output(Bool())

    val opcode = Output(UInt(7.W))
    val funct3 = Output(UInt(3.W))
    val funct7 = Output(UInt(7.W))
  })

  val PC = RegInit(0.U(XLEN.W))
  val InstrMem = Module(new InstrCache(XLEN))
  val DataMem = Module(new DataCache(XLEN))
  val RegFile = Module(new RegisterFile(XLEN))
  val Alu = Module(new ALU(XLEN))

  InstrMem.io.addr := PC
  val instr = InstrMem.io.dataOUT

  RegFile.io.readReg1 := instr(19, 15)
  RegFile.io.readReg2 := instr(24, 20)
  RegFile.io.writeReg := instr(11, 7)
  RegFile.io.wrEna := io.regWrite
  when(io.toReg === 0.U) {
    RegFile.io.writeData := Alu.io.result
  }.elsewhen(io.toReg === 1.U) {
    RegFile.io.writeData := DataMem.io.dataOUT
  }.elsewhen(io.toReg === 2.U) {
    RegFile.io.writeData := oneTo32Sext(Alu.io.negative)
  }.otherwise {
    RegFile.io.writeData := (PC + 1.U).asSInt
  }

  val offSet = Wire(Bits(12.W))
  when(io.memWrite) {
    offSet := Cat(instr(31, 25), instr(11, 7))
  }.otherwise {
    offSet := instr(31, 20)
  }

  val jmpOffset = WireInit(signExt(Cat(instr(31), instr(19, 12), instr(20), instr(30, 21)).asSInt, 12))
  val target = WireInit(signExt(Cat(instr(31), instr(7), instr(30, 25), instr(11, 8), "b0".U).asSInt, 19))

  when(io.aluSrcA === 0.U) {
    Alu.io.a := RegFile.io.readData1
  }.otherwise {
    Alu.io.a := (PC).asSInt
  }

  when(io.aluSrcB === 0.U) {
    Alu.io.b := RegFile.io.readData2
  }.elsewhen(io.aluSrcB === 1.U) {
    Alu.io.b := signExt(offSet.asSInt, 20)
  }.otherwise {
    Alu.io.b := jmpOffset.asSInt
  }

  Alu.io.aluCtl := io.aluCtl

  DataMem.io.addr := Alu.io.result.asUInt
  DataMem.io.wrEna := io.memWrite
  DataMem.io.dataIN := RegFile.io.readData2

  when(io.branch & Alu.io.zero) {
    PC := PC + target.asUInt
  }.elsewhen(io.branch & !Alu.io.zero) {
    PC := PC + target.asUInt
  }.otherwise {
    PC := PC + 1.U
  }

  when(io.jmp) {
    PC := Alu.io.result.asUInt
  }

  io.reg := Alu.io.result
  io.pc := PC
  io.neg := Alu.io.negative
  io.zero := Alu.io.zero

  io.opcode := instr(6, 0)
  io.funct3 := instr(14, 12)
  io.funct7 := instr(31, 25)
}
