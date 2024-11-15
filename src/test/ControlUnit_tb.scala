import chisel3._
import chiseltest._
import org.scalatest.flatspec.AnyFlatSpec

class CUTest extends AnyFlatSpec with ChiselScalatestTester {
  "CU" should "generate correct control signals for each opcode" in {
    test(new CU) { dut =>
      // Test case for R-type (opcode = 0110011)
      dut.io.opcode.poke("b0110011".U)
      dut.io.stall.poke(false.B)
      dut.clock.step(1)
      dut.io.regWrite.expect(true.B)
      dut.io.aluOp.expect("b10".U)
      dut.io.aluSrc.expect(false.B)
      dut.io.memRead.expect(false.B)
      dut.io.memWrite.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.memToReg.expect(false.B)

      // Test case for I-type (opcode = 0010011)
      dut.io.opcode.poke("b0010011".U)
      dut.io.stall.poke(false.B)
      dut.clock.step(1)
      dut.io.regWrite.expect(true.B)
      dut.io.aluOp.expect("b00".U)
      dut.io.aluSrc.expect(true.B)
      dut.io.memRead.expect(false.B)
      dut.io.memWrite.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.memToReg.expect(false.B)

      // Test case for Load (opcode = 0000011)
      dut.io.opcode.poke("b0000011".U)
      dut.io.stall.poke(false.B)
      dut.clock.step(1)
      dut.io.regWrite.expect(true.B)
      dut.io.memRead.expect(true.B)
      dut.io.memToReg.expect(true.B)
      dut.io.aluSrc.expect(true.B)
      dut.io.aluOp.expect("b00".U)
      dut.io.memWrite.expect(false.B)
      dut.io.branch.expect(false.B)

      // Test case for Store (opcode = 0100011)
      dut.io.opcode.poke("b0100011".U)
      dut.io.stall.poke(false.B)
      dut.clock.step(1)
      dut.io.memWrite.expect(true.B)
      dut.io.aluSrc.expect(true.B)
      dut.io.aluOp.expect("b00".U)
      dut.io.regWrite.expect(false.B)
      dut.io.memRead.expect(false.B)
      dut.io.branch.expect(false.B)
      dut.io.memToReg.expect(false.B)

      // Test case for Branch (opcode = 1100011)
      dut.io.opcode.poke("b1100011".U)
      dut.io.stall.poke(false.B)
      dut.clock.step(1)
      dut.io.branch.expect(true.B)
      dut.io.aluOp.expect("b01".U)
      dut.io.regWrite.expect(false.B)
      dut.io.memRead.expect(false.B)
      dut.io.memWrite.expect(false.B)
      dut.io.aluSrc.expect(false.B)
      dut.io.memToReg.expect(false.B)

      // Test case for stall
      dut.io.opcode.poke("b0110011".U) // R-type
      dut.io.stall.poke(true.B)
      dut.clock.step(1)
      dut.io.branch.expect(false.B)
      dut.io.memRead.expect(false.B)
      dut.io.memToReg.expect(false.B)
      dut.io.memWrite.expect(false.B)
      dut.io.aluSrc.expect(false.B)
      dut.io.regWrite.expect(false.B)
      dut.io.aluOp.expect("b00".U)
    }
  }
}
