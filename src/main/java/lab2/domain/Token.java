package lab2.domain;

import java.util.Objects;

public abstract class Token {

    private String name;

    public Token(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Token setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Token{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Token)) return false;
        Token token = (Token) o;
        return this.name.equals(token.name);
    }
}
