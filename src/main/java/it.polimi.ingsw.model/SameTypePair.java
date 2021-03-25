package it.polimi.ingsw;

public class SameTypePair<T>{
    private T val1;
    private T val2;

    public SameTypePair(){
        val1=null;
        val2=null;
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
        if(i==1){
            val1=val;
        }
        if(i==2){
            val2=val;
        }
    }
    public T get(int i){
        if(i==1){
            return val1;
        }
        if(i==2){
            return val2;
        }
        return null;
    }
    public boolean contains(T val){
        return (val1 == val || val2 == val);
    }
}
