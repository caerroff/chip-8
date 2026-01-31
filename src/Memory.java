import java.util.HexFormat;

public class Memory {
    private int opcode; // current opcode being read
    private int[] memory = new int[4096]; // Memory Stack
    private int stackPointer = 0; // stack pointer
    private int[] V = new int[16]; // V registries
    private int pc = 0; // Program counter
    private int indexRegister = 0;

    public Memory() {

    }

    public void setMemory(int data) {
        this.memory[stackPointer] = data;
        stackPointer += 4;
    }

    public int getMemory() {
        int value = this.memory[stackPointer];
        stackPointer -= 4;
        return value;
    }

    public int loadCurrentAddress() {
        return this.memory[this.stackPointer];
    }

    public boolean loadIntoRegistry(char V, int value) {
        this.V[HexFormat.fromHexDigit(V)] = value;
        return true;
    }

    public boolean loadIntoRegistry(char Vx, char Vy) {
        this.V[HexFormat.fromHexDigit(Vx)] = this.V[HexFormat.fromHexDigit(Vy)];
        return true;
    }

    /**
     * Add value to V registry
     * 
     * @param value value to add to registry
     * @param V     registry to add the value to
     * @return true
     */
    public boolean addToRegistry(char V, int value) {
        this.V[HexFormat.fromHexDigit(V)] += value;
        return true;
    }

    /**
     * Add V registry to another V registry
     * 
     * @param value value to add to registry
     * @param V     registry to add the value to
     * @return true
     */
    public boolean addToRegistry(char Vx, char Vy) {
        this.V[HexFormat.fromHexDigit(Vx)] += this.V[HexFormat.fromHexDigit(Vy)];
        // if result more than 8 bits:
        if (this.V[HexFormat.fromHexDigit(Vx)] > 0xFF) {
            // Keep only 8 lowest bits:
            String hex = Integer.toHexString(V[HexFormat.fromHexDigit(Vx)]);
            this.V[HexFormat.fromHexDigit(Vx)] = HexFormat.fromHexDigits(hex.substring(hex.length() - 2));
            // Carry:
            this.V[0xF] = 1;
        } else {
            // Wipe Vy:
            this.V[0xF] = 0;
        }
        return true;
    }

    public boolean subRegistry(char Vx, char Vy) {
        if (this.V[HexFormat.fromHexDigit(Vx)] > this.V[HexFormat.fromHexDigit(Vy)]) {
            this.V[0xF] = 1;
        } else {
            this.V[0xF] = 0;
        }
        this.V[HexFormat.fromHexDigit(Vx)] -= this.V[HexFormat.fromHexDigit(Vy)];

        return true;
    }

    public boolean subnRegistry(char Vx, char Vy) {
        if (this.V[HexFormat.fromHexDigit(Vy)] > this.V[HexFormat.fromHexDigit(Vx)]) {
            this.V[0xF] = 1;
        } else {
            this.V[0xF] = 0;
        }
        this.V[HexFormat.fromHexDigit(Vx)] = this.V[HexFormat.fromHexDigit(Vy)] - this.V[HexFormat.fromHexDigit(Vx)];

        return true;
    }

    public boolean checkIfEquals(char Vx, int value) {
        System.out.println("Here");
        return (this.V[HexFormat.fromHexDigit(Vx)] == value);
    }

    public boolean callSubroutine(int position) {
        this.stackPointer += 1;
        this.setMemory(this.pc);
        this.stackPointer = position;
        return true;
    }

    public int loadCurrentAddressPlusOne() {
        return this.memory[this.stackPointer + 2];
    }

    public void setIndex(int position) {
        this.stackPointer = position;
    }

    public int getIndex() {
        return this.stackPointer;
    }

    public boolean nextIndex() {
        if (stackPointer + 2 >= memory.length) {
            return false;
        }
        this.stackPointer += 2;
        return true;
    }

    public boolean nextIndex(int amount) {
        if (stackPointer + amount >= memory.length) {
            return false;
        }
        this.stackPointer += amount;
        return true;
    }

    public void setOpcode(int code) {
        this.opcode = code;
    }

    public int getOpcode() {
        return this.opcode;
    }
}
