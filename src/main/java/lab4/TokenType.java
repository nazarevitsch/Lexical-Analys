package lab4;

public enum TokenType {
    NAME("Name"),
    BEGIN("Begin"),
    END("End"),
    VAR("Var"),
    OPEN_P("("),
    CLOSE_P(")"),
    OPEN_CURLY("{"),
    CLOSE_CURLY("}"),
    ASSIGN("="),
    CONST("Const"),

    PLUS("+"),
    MINUS("-"),
    MULTIPLICATION("*"),
    SUBTRACTION("/"),
    POW("^"),

    COMA(","),
    SPACE(" "),
    SEMICOLON(";"),

    NONE("");

    private final String name;

    private TokenType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
