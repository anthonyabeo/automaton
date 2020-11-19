package automaton.memory

import chisel3.Bundle
import chisel3._

import memory._

class Cache(Capacity: Int) extends Module {
  val io = IO(new Bundle {
    val cpuReq = Input(CPURequest)
    val memResp = Input(MemResponse)

    val cpuResp = Output(CPUResponse)
    val memReq = Output(MemRequest)
  })

  val TagTable = Module(new Tag(Capacity))
  val DataTable = Module(new Data(Capacity))

}
