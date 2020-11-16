package automaton

import chisel3._

import datapath.Datapath
import control.Controller

class Automaton extends Module {
  val XLEN = 64
  val io = IO(new Bundle {
    // val rdData = Input(SInt(XLEN.W))

    // val addr = Output(UInt(XLEN.W))
    // val wrData = Output(SInt(XLEN.W))
    // val memWrite = Output(Bool())
    //val valid = Output(Bool())
  })

  val datapath = Module(new Datapath(XLEN))
  val contoller = Module(new Controller)
  // val L1DataCache = Module(new DataCache)
  // val L1InstrCache = Module(new InstrCache)

  ////////////////////////
  // PLUMBING
  ///////////////////////
  datapath.io.regWrite := contoller.io.regWrite
  datapath.io.aluSrcA := contoller.io.aluSrcA
  datapath.io.aluSrcB := contoller.io.aluSrcB
  datapath.io.branch := contoller.io.branch
  datapath.io.jmp := contoller.io.jmp
  datapath.io.memWrite := contoller.io.memWrite
  datapath.io.toReg := contoller.io.toReg
  datapath.io.aluCtl := contoller.io.aluCtl
  datapath.io.bType := contoller.io.bType
  datapath.io.size := contoller.io.size

  contoller.io.opcode := datapath.io.opcode
  contoller.io.funct3 := datapath.io.funct3
  contoller.io.funct7 := datapath.io.funct7

//   io.memWrite := contoller.io.memWrite
//   io.addr := datapath.io.addr
//   io.wrData := datapath.io.wrData

//   io.rdData :=
}
