import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String filePath = "asm.bin";

        ArrayList<Integer> list = readFile(filePath);
        System.out.println(list.get(0));
        decode(list.get(0));
    }

    private static ArrayList<Integer> readFile(String filePath) {

        ArrayList<Integer> a = new ArrayList<Integer>();

        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)))) {
            // Reading 4 bytes at a time
            while (dis.available() >= 4) {
                a.add(dis.readInt());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return a;

    }

    private static Instruction decode(int i) {
        int i11 = i >>> 21;
        int i10 = i >>> 22;
        int i8 = i >>> 24;
        int i6 = i >>> 26;

        switch (i6) {
            case 0b000101:
                return new Instruction("B", 'B');
            case 0b100101:
                return new Instruction("BL", 'B');
        }

        switch (i8) {
            case 0b10110101:
                return new Instruction("CBNZ", 'C');
            case 0b10110100:
                return new Instruction("CBZ", 'C');
            case 0b01010100:
                return new Instruction("B.cond", 'C');
        }

        switch (i10) {
            case 0b1001000100:
                return new Instruction("ADDI", 'I');
            case 0b1011000100:
                return new Instruction("ADDIS", 'I');
            case 0b1001001000:
                return new Instruction("ANDI", 'I');
            case 0b1111001000:
                return new Instruction("ANDIS", 'I');
            case 0b1110101000:
                return new Instruction("ANDS", 'R');
            case 0b1101001000:
                return new Instruction("EORI", 'I');
            case 0b1011001000:
                return new Instruction("ORRI", 'I');
            case 0b1101000100:
                return new Instruction("SUBI", 'I');
            case 0b1111000100:
                return new Instruction("SUBIS", 'I');

        }
        switch (i11) {
            case 0b10001011000:
                return new Instruction("ADD", 'R');
            case 0b10101011000:
                return new Instruction("ADDS", 'R');
            case 0b10001010000:
                return new Instruction("AND", 'R');
            case 0b11010110000:
                return new Instruction("BR", 'R');
            case 0b11111111110:
                return new Instruction("DUMP", 'R');
            case 0b11001010000:
                return new Instruction("EOR", 'R');
            case 0b00011110011:
                return new Instruction("FADDD", 'R');
            case 0b00011110001:
                return new Instruction("FADDS", 'R');
            case 0b11111111111:
                return new Instruction("HALT", 'R');
            case 0b11111000010:
                return new Instruction("LDUR", 'D');
            case 0b11010011011:
                return new Instruction("LSL", 'R');
            case 0b11010011010:
                return new Instruction("LSR", 'R');
            case 0b10011011000:
                return new Instruction("MUL", 'R');
            case 0b10101010000:
                return new Instruction("ORR", 'R');
            case 0b11111111100:
                return new Instruction("PRNL", 'R');
            case 0b11111111101:
                return new Instruction("PRNT", 'R');
            case 0b11111000000:
                return new Instruction("STUR", 'D');
            case 0b00111000000:
                return new Instruction("STURB", 'D');
            case 0b01111000000:
                return new Instruction("STURH", 'D');
            case 0b11001011000:
                return new Instruction("SUB", 'R');
            case 0b11101011000:
                return new Instruction("SUBS", 'R');
            case 0b10011010110:
                return new Instruction("UDIV", 'R');
            case 0b10011011110:
                return new Instruction("UMULH", 'R');

        }
        return new Instruction("0", '0');
    }

    static class Instruction {
        String instruction;
        char type;

        Instruction(String instruction, char type) {
            this.instruction = instruction;
            this.type = type;
        }
    }

}