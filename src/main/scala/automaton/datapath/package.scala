package object datapath {
  import chisel3._
  import chisel3.util._

  val add :: sub :: and :: or :: xor :: sll :: srl :: sra :: Nil = Enum(8)

  def signExt(n: SInt, extAmt: Int): SInt = {
    val width = (64 - extAmt - 1)
    val out = Wire(Bits(64.W))

    when((n(width) === 0.U)) {
      val bitString = "b0".U
      out := Cat(Fill(extAmt, bitString), n)
    }.otherwise {
      val bitString = "b1".U
      out := Cat(Fill(extAmt, bitString), n)
    }

    out.asSInt
  }

  def oneTo32Sext(n: Bool): SInt = {
    val bitString = "b0".U
    Cat(Fill(63, bitString), n).asSInt
  }

  object Operation extends Enumeration {
    type Operation = Value
    val BranchOp = Value(0x63)
    val ImmeOp = Value(0x13)
    val RegOp = Value(0x33)
    val JalOp = Value(0x6f)
    val JalrOp = Value(0x67)
    val LdOp = Value(0x3)
    val StrOp = Value(0x23)
    val WOp = Value(0x3b)
  }
}
