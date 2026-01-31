import java.util.HexFormat;

public class Memory {
    private int opcode; // current opcode being read
    private short[] stack = new short[16]; // Memory Stack
    private byte stackPointer = 0; // stack pointer
    private int[] V = new int[16]; // V registries
    private int indexRegister = 0;
    private short[] addressableMemory = new short[4096];

    public Memory() {

    }

    public boolean loadIntoRegistry(char V, int value) {
        this.V[HexFormat.fromHexDigit(V)] = value;
        return true;
    }

    public boolean loadIntoRegistry(char Vx, char Vy) {
        this.V[HexFormat.fromHexDigit(Vx)] = this.V[HexFormat.fromHexDigit(Vy)];
        return true;
    }

    public int getIndexRegister(){
        return this.indexRegister;
    }

    public void setIndexRegister(int value){
        this.indexRegister = value;
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

    public void pushStack(short value){
        this.stack[this.stackPointer] = value;
        this.stackPointer ++;
    }

    public short popStack(){
        short value = this.stack[this.stackPointer];
        this.stackPointer --;
        return value;
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

    public void setOpcode(int code) {
        this.opcode = code;
    }

    public int getOpcode() {
        return this.opcode;
    }
}
