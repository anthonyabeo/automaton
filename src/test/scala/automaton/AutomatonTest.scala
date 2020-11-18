package automaton

import org.scalatest._

import firrtl.transforms.NoDCEAnnotation

import chisel3._
import chiseltest._
import chiseltest.internal.WriteVcdAnnotation
import chiseltest.experimental.TestOptionBuilder._

class AutomatonTest extends FlatSpec with ChiselScalatestTester {
  behavior.of("Automaton")

  it should "compute fib of 8" in {
    test(new Automaton).withAnnotations(Seq(WriteVcdAnnotation, NoDCEAnnotation)) { auto =>
      auto.clock.step(150)
    }
  }
}
