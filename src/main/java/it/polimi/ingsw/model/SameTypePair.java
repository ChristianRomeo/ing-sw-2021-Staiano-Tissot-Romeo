package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Type that contains two resources to be of the same type
 * @param <T>
 */
public class SameTypePair<T> implements Serializable {
    private T val1;
    private T val2;

    public SameTypePair(){
        val1=null;
        val2=null;
    }

    public SameTypePair(T val1,T val2){
        this.val1 = val1;
        this.val2 = val2;
    }

    public T getVal1() {
        return val1;
    }

    public void setVal1(T val1) {
        this.val1 = val1;
    }

    public T getVal2() {
        return val2;
    }

    public void setVal2(T val2) {
        this.val2 = val2;
    }

    public void set(T val, int i){
        if(i==1)
            val1=val;

        if(i==2)
            val2=val;
    }

    public T get(int i){
        if(i==1)
            return val1;

        if(i==2)
            return val2;

        return null;
    }

    public boolean contains(T val){
        return (val1 == val || val2 == val);
    }
}
