package es.uclm.sri.sis.dataswap;

/**
 * 
 * @author Sergio Navarro
 * */
public abstract class AbstractDataSwap implements IDataSwap {
    
    private IDataSwap dataSwap;

    public void generarDataSwap() {
        
    }
    
    public IDataSwap getDataSwap() {
        return this.dataSwap;
    }
    
    public void setDataSwap(IDataSwap dataSwap) {
        this.dataSwap = dataSwap;
    }

}