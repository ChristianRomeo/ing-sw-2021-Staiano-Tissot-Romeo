package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Type that contains two resources to be of the same type
 * @param <T>
 */
public class SameTypePair<T> implements Serializable {
    private T val1;
    private T val2;

    /**
     *constructor
     */
    public SameTypePair(){
        val1=null;
        val2=null;
    }

    /**
     *constructor
     */
    public SameTypePair(T val1,T val2){
        this.val1 = val1;
        this.val2 = val2;
    }

    /**
     * @return the first value of the SameTypePair
     */
    public T getVal1() {
        return val1;
    }

    /**
     * @param val1 is the variable to be set to the first value of the SameTypePair
     */
    public void setVal1(T val1) {
        this.val1 = val1;
    }

    /**
     * @return the second value of the SameTypePair
     */
    public T getVal2() {
        return val2;
    }

    /**
     * @param val2 is the variable to be set to the second value of the SameTypePair
     */
    public void setVal2(T val2) {
        this.val2 = val2;
    }

    /**
     * @param val is the variable to be set as a value in the SameTypePair
     * @param i is the variable which tells where to put val
     */
    public void set(T val, int i){
        if(i==1)
            val1=val;

        if(i==2)
            val2=val;
    }
    /**
     * @return val1 if i == 1, val2 if i ==2, otherwise null
     */
    public T get(int i){
        if(i==1)
            return val1;

        if(i==2)
            return val2;

        return null;
    }

    /**
     * @return true if val is part of the SameTypePair, otherwise false
     */
    public boolean contains(T val){
        return (val1 == val || val2 == val);
    }
}
