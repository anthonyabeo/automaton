package automaton.memory

import chisel3._
import chisel3.util._

import memory._

class Tag(Capacity: Int) extends Module {
  val io = IO(new Bundle {
    val tagIn = Input(TagEntry)
    val cacheReq = Input(CacheRequest)

    val tagOut = Output(TagEntry)
  })

  val tagMem = Mem(Capacity, TagEntry)

  when(io.cacheReq.we) {
    tagMem.write(io.cacheReq.index, io.tagIn)
  }

  io.tagOut := tagMem.read(io.cacheReq.index)
}

class Data(Capacity: Int) extends Module {
  val io = IO(new Bundle {
    val cacheReq = Input(CacheRequest)
    val dataIn = Input(Block)

    val dataOut = Output(Block)
  })

  val dataMem = Mem(Capacity, Block)

  when(io.cacheReq.we) {
    dataMem.write(io.cacheReq.index, io.dataIn)
  }

  io.dataOut := dataMem.read(io.cacheReq.index)
}
