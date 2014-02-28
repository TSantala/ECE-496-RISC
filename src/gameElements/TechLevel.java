package gameElements;

public class TechLevel {
    private int techlvl;
    public TechLevel(){
        techlvl = 0;
    }
    
    /*
     * returns techlvl
     */
    public int increment(){
        techlvl++;
        return techlvl;
    }
    
    public int getLevel(){
        return techlvl;
    }
}
