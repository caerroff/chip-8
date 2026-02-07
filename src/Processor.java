import java.util.HexFormat;

public class Processor {

    private Display display;

    private Memory memory;

    private byte[] code;

    private short codeIndex;

    public Processor(Display display) {
        this.display = display;
        this.memory = new Memory();
    }

    public void setCode(byte[] code) {
        this.code = code;
        this.codeIndex = 0;
    }

    public boolean callSubroutine(short position) {
        this.codeIndex += 1;
        this.memory.pushStack(codeIndex);
        this.codeIndex = position;
        return true;
    }

    public void jump(String positionInHex) {
        int position = HexFormat.fromHexDigits(positionInHex);
        this.codeIndex = (short) position;
    }

    public void parse() {
        this.codeIndex = 0;
        int instruction;
        int argument;
        while (true) {
            instruction = this.code[codeIndex] & 0xFF;
            argument = this.code[codeIndex + 1] & 0xFF;
            // System.out.println(Integer.toHexString(instruction));
            try {
                this.execute(instruction, argument);
            } catch (Exception e) {
                System.err.println(e);
                break;
            }
            if (this.code.length <= this.codeIndex + 1) {
                System.out.println(
                    "Out of bounds... Trying to load " +
                        this.codeIndex +
                        " out of length " +
                        this.code.length
                );
                return;
            } else {
                this.codeIndex += 2;
            }
        }
    }

    private void execute(int opcode, int argument) throws Exception {
        // Total of 35 different OPcodes, 34 to be implemented.
        // Currently implemented: 9.
        Instruction inst = new Instruction(opcode, argument);
        this.memory.setOpcode(opcode);
        System.out.println(
            "OpCode: 0x" +
                inst.getOpCodeHexString() +
                " with argument " +
                inst.getArgumentHexString() +
                " at index 0x" +
                Integer.toHexString(codeIndex)
        );
        switch (inst.getOpCodeHexString().charAt(0)) {
            // Clear Display
            case '0':
                if (inst.getOpCode() == 0x00 && inst.getArgument() == 0xE0) {
                    this.display.clearFrame();
                }
                if (argument == 0xEE) {
                    //return from subroutine
                }
                break;
            case '1':
                this.jump(
                    inst.getOpCodeHexString().charAt(1) +
                        inst.getArgumentHexString()
                );
                break;
            // Calls subroutine at 2nnn
            case '2':
                String positionHex =
                    Integer.toHexString(opcode).charAt(1) +
                    Integer.toHexString(argument);
                System.out.println("Calling subroutine at " + positionHex);
                short position = (short) HexFormat.fromHexDigits(positionHex);
                this.callSubroutine(position);
                break;
            case '4':
                // opcode[1] is registry, argument is value
                // Check if V[registry] == argument
                // If yes, do nothing
                // If no, increment program counter by 2
                if (
                    !this.memory.checkIfEquals(
                        Integer.toHexString(opcode).charAt(1),
                        argument
                    )
                ) {
                    this.codeIndex++;
                }
                break;
            //load into registry
            case '6':
                this.memory.loadIntoRegistry(
                    Integer.toHexString(opcode).charAt(1),
                    argument
                );
                break;
            case '7':
                this.memory.addToRegistry(
                    Integer.toHexString(opcode).charAt(1),
                    argument
                );
                break;
            case '8':
                if (Integer.toHexString(argument).charAt(1) == '0') {
                    this.memory.loadIntoRegistry(
                        Integer.toHexString(opcode).charAt(1),
                        Integer.toHexString(argument).charAt(0)
                    );
                }
                if (Integer.toHexString(argument).charAt(1) == '4') {
                    this.memory.addToRegistry(
                        Integer.toHexString(opcode).charAt(1),
                        Integer.toHexString(argument).charAt(0)
                    );
                }
                if (Integer.toHexString(argument).charAt(1) == '5') {
                    this.memory.subRegistry(
                        Integer.toHexString(opcode).charAt(1),
                        Integer.toHexString(argument).charAt(0)
                    );
                }
                if (Integer.toHexString(argument).charAt(1) == '7') {
                    this.memory.subnRegistry(
                        Integer.toHexString(opcode).charAt(1),
                        Integer.toHexString(argument).charAt(0)
                    );
                }
                break;
            case 'a':
                String valueHex =
                    Integer.toHexString(opcode).charAt(1) +
                    Integer.toHexString(argument);
                short value = (short) HexFormat.fromHexDigits(valueHex);
                this.memory.setIndexRegister(value);
                break;
            case 'd':
                this.display.displaySprite(
                    Integer.toHexString(opcode).charAt(1),
                    Integer.toHexString(argument).charAt(0),
                    Integer.toHexString(argument).charAt(1)
                );
                break;
            default:
                throw new Exception(
                    "Not Implemented " + Integer.toHexString(opcode)
                );
        }
    }
}
