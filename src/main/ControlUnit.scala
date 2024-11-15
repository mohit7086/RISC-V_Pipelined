import chisel3._
import chisel3.util._

class CU extends Module {
  val io = IO(new Bundle {
    val opcode = Input(UInt(7.W))
    val stall = Input(Bool())
    
    val branch = Output(Bool())
    val memread = Output(Bool())
    val memtoreg = Output(Bool())
    val memwrite = Output(Bool())
    val aluSrc = Output(Bool())
    val regwrite = Output(Bool())
    val Aluop = Output(UInt(2.W))
  })

  // Default values
  io.branch := false.B
  io.memread := false.B
  io.memtoreg := false.B
  io.memwrite := false.B
  io.aluSrc := false.B
  io.regwrite := false.B
  io.Aluop := "b00".U

  when(io.stall) {
    // If stall is active, set all control signals to 0
    io.branch := false.B
    io.memread := false.B
    io.memtoreg := false.B
    io.memwrite := false.B
    io.aluSrc := false.B
    io.regwrite := false.B
    io.Aluop := "b00".U
  } .otherwise {
    // Control signals based on opcode
    switch(io.opcode) {
      is("b0000011".U) { // Load instruction
        io.aluSrc := true.B
        io.memtoreg := true.B
        io.regwrite := true.B
        io.memread := true.B
        io.memwrite := false.B
        io.branch := false.B
        io.Aluop := "b00".U
      }
      is("b0100011".U) { // Store instruction
        io.aluSrc := true.B
        io.memtoreg := false.B
        io.regwrite := false.B
        io.memread := false.B
        io.memwrite := true.B
        io.branch := false.B
        io.Aluop := "b00".U
      }
      is("b0110011".U) { // R-type instruction
        io.aluSrc := false.B
        io.memtoreg := false.B
        io.regwrite := true.B
        io.memread := false.B
        io.memwrite := false.B
        io.branch := false.B
        io.Aluop := "b10".U
      }
      is("b1100011".U) { // Branch instruction
        io.aluSrc := false.B
        io.memtoreg := false.B
        io.regwrite := false.B
        io.memread := false.B
        io.memwrite := false.B
        io.branch := true.B
        io.Aluop := "b01".U
      }
      is("b0010011".U) { // I-type instruction
        io.aluSrc := true.B
        io.memtoreg := false.B
        io.regwrite := true.B
        io.memread := false.B
        io.memwrite := false.B
        io.branch := false.B
        io.Aluop := "b00".U
      }
    }
  }
}
