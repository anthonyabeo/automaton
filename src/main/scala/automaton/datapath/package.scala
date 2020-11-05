package object datapath {
  import chisel3._
  import chisel3.util._

  val add :: sub :: and :: or :: xor :: sll :: srl :: sra :: Nil = Enum(8)

  def signExt(n: UInt, extAmt: Int): SInt = {
    val bitString = "b0".U
    when(isPos(n)) {
      val bitString = "b0".U
    }.otherwise {
      val bitString = "b1".U
    }

    Cat(Fill(extAmt, bitString), n).asSInt
  }

  def oneTo32Sext(n: Bool): SInt = {
    val bitString = "b0".U
    Cat(Fill(31, bitString), n).asSInt
  }

  def isPos(n: UInt): Bool = ((1.U << 31) & n) === 0.U
}
