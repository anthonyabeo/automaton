package automaton.datapath

import chisel3._
import chisel3.util._

import datapath.{oneTo32Sext, signExt}

class Datapath(XLEN: Int) extends Module {
  val io = IO(new Bundle {
    val regWrite = Input(Bool())
    val aluCtl = Input(UInt(3.W))
    val memWrite = Input(Bool())
    val aluSrcB = Input(UInt(1.W))
    val toReg = Input(UInt(2.W))

    val reg = Output(SInt(XLEN.W))
    val pc = Output(UInt(XLEN.W))
    val neg = Output(Bool())
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
  }.otherwise {
    RegFile.io.writeData := oneTo32Sext(Alu.io.negative)
  }

  val offSet = Wire(Bits(12.W))
  when(io.memWrite) {
    offSet := Cat(instr(31, 25), instr(11, 7))
  }.otherwise {
    offSet := instr(31, 20)
  }

  Alu.io.a := RegFile.io.readData1
  when(io.aluSrcB === 0.U) {
    Alu.io.b := RegFile.io.readData2
  }.otherwise {
    Alu.io.b := signExt(offSet)
  }
  Alu.io.aluCtl := io.aluCtl

  DataMem.io.addr := Alu.io.result.asUInt
  DataMem.io.wrEna := io.memWrite
  DataMem.io.dataIN := RegFile.io.readData2

  PC := PC + 1.U

  io.reg := Alu.io.result
  io.pc := PC
  io.neg := Alu.io.negative
}
