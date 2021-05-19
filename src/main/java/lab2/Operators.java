package lab2;

public enum Operators {
    ADD("+", 1), MINUS("-", 1),
    MULTIPLICATION("*", 2), DIVIDING("/", 2),
    POW("^", 3), UNARY_MINUS("-", 4);

    private String name;
    private int priority;

    Operators(String name, int priority){
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public int getPriority() {
        return priority;
    }
}
