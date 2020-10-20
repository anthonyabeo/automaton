package automaton.datapath

import chisel3._
import chisel3.util._

class Datapath(size: Int, regWidth: Int) extends Module {
  val io = IO(new Bundle {
    val regWrite = Input(Bool())
    val aluCtl = Input(UInt(4.W))
    val memWrite = Input(Bool())

    val reg = Output(SInt(size.W))
    val pc = Output(UInt(size.W))
  })

  val PC = RegInit(0.U(size.W))
  val InstrMem = Module(new InstrCache(size, regWidth))
  // val DataMem = Module(new DataCache(size, regWidth))
  val RegFile = Module(new RegisterFile(size, regWidth))
  val Alu = Module(new ALU(size))

  InstrMem.io.addr := PC
  val instr = InstrMem.io.dataOUT

  RegFile.io.readReg1 := instr(19, 15)
  RegFile.io.readReg2 := instr(24, 20)
  RegFile.io.writeReg := instr(11, 7)
  RegFile.io.wrEna := io.regWrite
  RegFile.io.writeData := Alu.io.result

  Alu.io.a := RegFile.io.readData1
  Alu.io.b := RegFile.io.readData2
  Alu.io.aluCtl := io.aluCtl

  // DataMem.io.addr := Alu.io.result
  // DataMem.io.wrEna := io.memWrite
  // DataMem.io.dataIN :=

  PC := PC + 4.U

  io.reg := Alu.io.result
  io.pc := PC
}
