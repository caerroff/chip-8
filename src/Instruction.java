public class Instruction {

    //First 2 bytes of the instruction
    private int opcode;

    //Last 2 bytes of the instruction
    private int argument;

    Instruction(int opcode, int argument) {
        this.opcode = opcode;
        this.argument = argument;
    }

    public int getOpCode() {
        return this.opcode;
    }

    public String getOpCodeHexString() {
        return Integer.toHexString(this.opcode);
    }

    public int getArgument() {
        return this.argument;
    }

    public String getArgumentHexString() {
        return Integer.toHexString(this.argument);
    }
}
