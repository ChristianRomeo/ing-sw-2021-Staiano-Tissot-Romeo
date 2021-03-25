package it.polimi.ingsw;

public class Triple<L,M,R> {
    private L val1;
    private M val2;
    private R val3;

    public Triple(){
        val1=null;
        val2=null;
        val3=null;
    }

    public L getVal1() {
        return val1;
    }

    public void setVal1(L val1) {
        this.val1 = val1;
    }

    public M getVal2() {
        return val2;
    }

    public void setVal2(M val2) {
        this.val2 = val2;
    }

    public R getVal3() {
        return val3;
    }

    public void setVal3(R val3) {
        this.val3 = val3;
    }
}
