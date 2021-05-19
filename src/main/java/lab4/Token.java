package lab4;

public class Token {

    private TokenType tokenType;
    private int reference;
    private Position position;

    public Token() {}

    public Token(TokenType tokenType, int reference, Position position) {
        this.tokenType = tokenType;
        this.reference = reference;
        this.position = position;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getReference() {
        return reference;
    }

    public void setReference(int reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                (reference == -1 ? ", value=(" + tokenType.getName() : ", reference=(" + reference) +
                "), position=" + position +
                '}';
    }
}
