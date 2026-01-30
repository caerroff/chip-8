import Memory.Registry;

public class Memory {

    /**
     * Contains all the registries from V0 to VF. 8-bit registries.
     */
    private Registry[] memoryRegistry;

    /**
     * 16-bit index registry.
     */
    private Registry indexRegister = new Registry(16);

    /**
     * Initialise the memory and registries
     */
    public Memory(){
        this.memoryRegistry = new Registry[16];
        for(int i = 0; i < 16; i++){
            this.memoryRegistry[i] = new Registry(8);
        }
    }

    public Registry[] getMemoryRegistry(){
        return this.memoryRegistry;
    }

    public Registry getMemoryRegistry(int position) throws Exception{
        if(position > memoryRegistry.length){
            throw new Exception("Trying to access memory outside of available registries");
        }
        
        return this.memoryRegistry[position];
    }

    public void writeToRegistry(int position, boolean[] value) throws Exception{
        if(position > memoryRegistry.length){
            throw new Exception("Trying to access memory outside of available registries");
        }

        this.memoryRegistry[position].setValue(value);
    }
}
