mport chisel3._
import chisel3.util._

class MuxCase_Example extends Module {
  // Define the IO interface for the module
  val io = IO(new Bundle {
    val in0 = Input(Bool())    // First input signal
    val in1 = Input(Bool())    // Second input signal
    val in2 = Input(Bool())    // Third input signal
    val in3 = Input(Bool())    // Fourth input signal
    val sel = Input(UInt(2.W)) // 2-bit wide select signal
    val out = Output(Bool())   // Output signal
  })

  // Use MuxCase to select one of the input signals based on the sel signal
  io.out := MuxCase(false.B, Array(
    (io.sel === 0.U) -> io.in0,  // If sel is 0, select in0
    (io.sel === 1.U) -> io.in1,  // If sel is 1, select in1
    (io.sel === 2.U) -> io.in2,  // If sel is 2, select in2
    (io.sel === 3.U) -> io.in3   // If sel is 3, select in3
  ))
}

// Generate the Verilog code for the MuxCase_Example module
println((new chisel3.stage.ChiselStage).emitVerilog(new MuxCase_Example()))
