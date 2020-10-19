package object datapath {
  import chisel3.util.Enum

  val add :: sub :: and :: or :: xor :: Nil = Enum(5)
}
