package it.polimi.ingsw.model;

/**
 * Not useless anymore
 * @param <K>
 * @param <V>
 */
public class Pair <K,V>{
    private K val1;
    private V val2;
    /**
     * constructor
     */
    public Pair(){
        val1=null;
        val2=null;
    }

    /**
     * constructor
     */
    public Pair(K val1, V val2) {
        this.val1 = val1;
        this.val2 = val2;
    }
    /**
     * Return the first value of the pair
     */
    public K getVal1() {
        return val1;
    }

    /**
     * Return the second value of the pair
     */
    public void setVal1(K val1) {
        this.val1 = val1;
    }

    /**
     * sets the first value of the pair
     */
    public V getVal2() {
        return val2;
    }

    /**
     * sets the first value of the pair
     */
    public void setVal2(V val2) {
        this.val2 = val2;
    }
}
