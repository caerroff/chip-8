import java.util.HexFormat;

public class Processor {
    private Display display;

    private Memory memory;

    public Processor(Display display) {
        this.display = display;
        this.memory = new Memory();
    }

    public void copyToMemory(byte[] bytes) {
        for (int i = 0; i < bytes.length; i++) {
            memory.setMemory(bytes[i]);
        }
    }

    public void printMemory() {
        System.out.println(this.memory.getMemory());
    }

    public void parse() {
        this.memory.setIndex(0);
        while (true) {
            int instruction = this.memory.loadCurrentAddress();
            int argument = this.memory.loadCurrentAddressPlusOne();
            try{
                this.execute(instruction, argument);
            }catch(Exception e){
                System.err.println(e);
                break;
            }
            if(!this.memory.nextIndex(4)){
                return;
            }
        }
    }

    private void execute(int opcode, int argument) throws Exception {
        // Total of 35 different OPcodes, 34 to be implemented.
        // Currently implemented: 9.
        this.memory.setOpcode(opcode);
        System.out.println("");
        switch(Integer.toHexString(opcode).charAt(0)){
            // Clear Display
            case '0':
                if(opcode == 0x00 && argument == 0xE0){
                    this.display.clearFrame();
                }
                if(argument == 0xEE){
                    //return from subroutine
                }
                break;
            // Calls subroutine at 2nnn
            case '2':
                String positionHex = Integer.toHexString(opcode).charAt(1) + Integer.toHexString(argument);
                System.out.println("Jumping to :" + positionHex);
                int position = HexFormat.fromHexDigits(positionHex);
                this.memory.callSubroutine(position);
                break;


            case '4':
                // opcode[1] is registry, argument is value
                // Check if V[registry] == argument
                // If yes, do nothing
                // If no, increment program counter by 2
                if(!this.memory.checkIfEquals(Integer.toHexString(opcode).charAt(1), argument)){
                    this.memory.nextIndex(4);
                }
                break;
            //load into registry
            case '6':
                this.memory.loadIntoRegistry(Integer.toHexString(opcode).charAt(1), argument);
                break;
            case '7':
                this.memory.addToRegistry(Integer.toHexString(opcode).charAt(1), argument);
                break;
            case '8':
                if(Integer.toHexString(argument).charAt(1) == '0'){
                    this.memory.loadIntoRegistry(Integer.toHexString(opcode).charAt(1), Integer.toHexString(argument).charAt(0));
                }
                if(Integer.toHexString(argument).charAt(1) == '4'){
                    this.memory.addToRegistry( Integer.toHexString(opcode).charAt(1), Integer.toHexString(argument).charAt(0));
                }
                if(Integer.toHexString(argument).charAt(1) == '5'){
                    this.memory.subRegistry( Integer.toHexString(opcode).charAt(1), Integer.toHexString(argument).charAt(0));
                }
                if(Integer.toHexString(argument).charAt(1) == '7'){
                    this.memory.subnRegistry( Integer.toHexString(opcode).charAt(1), Integer.toHexString(argument).charAt(0));
                }
                break;
            default:
                throw new Exception("Not Implemented " + Integer.toHexString(opcode));
        }
    }
}
