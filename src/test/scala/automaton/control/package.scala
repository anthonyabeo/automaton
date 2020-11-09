package object Automaton {
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
