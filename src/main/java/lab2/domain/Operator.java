package lab2.domain;

import lab2.Operators;

public class Operator extends Token {

    private static Operators[] operators = Operators.values();

    private Operators operator;

    public Operator(String name){
        super(name);
        this.operator = whichOperator(name);
    }

    public int getPriority(){
        return this.operator.getPriority();
    }

    public Operator setUnaryMinus(){
        operator = Operators.UNARY_MINUS;
        return this;
    }

    private static Operators whichOperator(String s) {
        for (int i = 0 ; i < operators.length; i++) {
            if (operators[i].getName().equals(s)) return operators[i];
        }
        return null;
    }
}
