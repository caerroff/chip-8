package Memory;

/**
 * Registry of X bit 
 */
public class Registry {
    private int size;

    private boolean[] value;
    
    public Registry(int size) {
        this.size = size;
        this.value = new boolean[size];
    }

    public int getSize(){
        return this.size;
    }

    public boolean[] getValue(){
        return this.value;
    }

    public void setValue(boolean[] value) throws Exception{
        if(value.length != this.value.length){
            throw new Exception("Trying to write a value of " + value.length + " to a registry of " + this.value.length + " bits");
        }

        this.value = value;
    }
}
