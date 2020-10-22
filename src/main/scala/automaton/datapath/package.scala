package object datapath {
  import chisel3._
  import chisel3.util._

  val add :: sub :: and :: or :: xor :: Nil = Enum(5)

  def signExt(n: UInt): SInt = {
    val bitString = "b0".U
    when(isPos(n)) {
      val bitString = "b0".U
    }.otherwise {
      val bitString = "b1".U
    }

    Cat(Fill(20, bitString), n).asSInt
  }

  def isPos(n: UInt): Bool = ((1.U << 31) & n) === 0.U
}
