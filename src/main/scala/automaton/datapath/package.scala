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

  object Operation extends Enumeration {
    type Operation = Value
    val BranchOp = Value(0x63)
    val ImmeOp = Value(0x13)
    val RegOp = Value(0x33)
    val JalOp = Value(0x6f)
    val JalrOp = Value(0x67)
    val LdOp = Value(0x3)
    val StrOp = Value(0x23)
  }
}
