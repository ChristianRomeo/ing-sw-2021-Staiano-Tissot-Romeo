package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * type of three same type resources
 * @param <T>
 */
public class SameTypeTriple<T> implements Serializable {
    private T val1;
    private T val2;
    private T val3;

    /**
     * constructor
     */
    public SameTypeTriple(){
        val1=null;
        val2=null;
        val3=null;
    }
    /**
     * constructor
     * @param val1 is the first value of the SameTypeTriple
     * @param val2 is the first value of the SameTypeTriple
     * @param val3 is the first value of the SameTypeTriple
     */
    public SameTypeTriple(T val1,T val2,T val3){
        this.val1 = val1;
        this.val2 = val2;
        this.val3 = val3;
    }

    /**
     * @return the first value of the SameTypeTriple
     */
    public T getVal1() {
        return val1;
    }

    /**
     * @param val1 is the variable to be set to the first value of the SameTypeTriple
     */
    public void setVal1(T val1) {
        this.val1 = val1;
    }

    /**
     * @return the second value of the SameTypeTriple
     */
    public T getVal2() {
        return val2;
    }

    /**
     * @param val2 is the variable to be set to the second value of the SameTypeTriple
     */
    public void setVal2(T val2) {
        this.val2 = val2;
    }

    /**
     * @return the third value of the SameTypeTriple
     */
    public T getVal3() {
        return val3;
    }

    /**
     * @param val3 is the variable to be set to the third value of the SameTypeTriple
     */
    public void setVal3(T val3) {
        this.val3 = val3;
    }

    /**
     * @param val is the variable to be set as a value in the SameTypeTriple
     * @param i is the variable which tells where to put val
     */
    public void set(T val, int i){
        if(i==1)
            val1=val;

        if(i==2)
            val2=val;

        if(i==3)
            val3=val;
    }

    /**
     * @return val1 if i == 1, val2 if i ==2, val3 if i == 3 otherwise null
     */
    public T get(int i){
        if(i==1)
            return val1;

        if(i==2)
            return val2;

        if(i==3)
            return val3;

        return null;
    }

    /**
     * @return true if val is part of the SameTypeTriple, otherwise false
     */
    public boolean contains(T val){
        return (val1 == val || val2 == val|| val3==val);
    }
}
