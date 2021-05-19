package lab4;

import java.util.*;

public class Parser {

    private HashMap<Integer, String> map = new HashMap<>();
    private static TokenType[] tokenTypes = TokenType.values();

    public List<Token> parse(String pathToFile) throws Exception{
        List<Token> tokens = parseFileToTokenList(pathToFile);

//        checkExceptions(tokens);

        showTable();
        for (int i = 0; i < tokens.size(); i++) {
            System.out.println("I: " + i + "  " + tokens.get(i));
        }

        return tokens;
    }

    private void checkExceptions(List<Token> tokens) throws UnacceptableException {
        if (tokens.get(0).getTokenType() != TokenType.BEGIN)
            throw new UnacceptableException("No Begin at line: " + tokens.get(0).getPosition().getLine());
        if (tokens.get(1).getTokenType() != TokenType.SEMICOLON)
            throw new UnacceptableException("No Semicolon at line: " + tokens.get(0).getPosition().getLine());
        if (tokens.get(tokens.size() - 2).getTokenType() != TokenType.END)
            throw new UnacceptableException("No End at line: " + tokens.get(tokens.size() - 1).getPosition().getLine());
        if (tokens.get(tokens.size() - 1).getTokenType() != TokenType.SEMICOLON)
            throw new UnacceptableException("No Semicolon at line: " + tokens.get(tokens.size() - 1).getPosition().getLine());
        int startIndex = 2;
        for (int i = 2; i < tokens.size() - 2; i++) {
            if (tokens.get(i).getTokenType() == TokenType.SEMICOLON) {
                if (tokens.get(startIndex).getTokenType() == TokenType.VAR) {
                    for (int l = startIndex + 1; l <= i; l += 2) {
                        if (tokens.get(l).getTokenType() != TokenType.NAME)
                            throw new UnacceptableException("Unacceptable at line: " + tokens.get(l).getPosition().getLine() + ", Column: " + tokens.get(l).getPosition().getColumn());
                        if (!(tokens.get(l + 1).getTokenType() == TokenType.COMA || tokens.get(l + 1).getTokenType() == TokenType.SEMICOLON))
                            throw new UnacceptableException("Unacceptable at line: " + tokens.get(l + 1).getPosition().getLine() + ", Column: " + tokens.get(l + 1).getPosition().getColumn());
                    }
                }

                if (tokens.get(startIndex).getTokenType() == TokenType.CONST) {
                    if (tokens.get(startIndex + 1).getTokenType() != TokenType.NAME ||
                            tokens.get(startIndex + 2).getTokenType() != TokenType.ASSIGN ||
                            tokens.get(startIndex + 3).getTokenType() != TokenType.NAME ||
                            (startIndex + 4) != i)
                        throw new UnacceptableException("Unacceptable at line: " + tokens.get(startIndex).getPosition().getLine());
                }

                if (tokens.get(startIndex).getTokenType() == TokenType.NAME) {
                    if (tokens.get(startIndex + 1).getTokenType() != TokenType.ASSIGN) throw new UnacceptableException("Unacceptable at line: " + tokens.get(startIndex + 1).getPosition().getLine());
                    for (int l = startIndex + 2; l <= i; l += 2) {
                        if (tokens.get(l).getTokenType() != TokenType.NAME)
                            throw new UnacceptableException("Unacceptable at line: " + tokens.get(l).getPosition().getLine() + ", Column: " + tokens.get(l).getPosition().getColumn());
                        if (!(tokens.get(l + 1).getTokenType() == TokenType.PLUS ||
                                tokens.get(l + 1).getTokenType() == TokenType.MINUS ||
                                tokens.get(l + 1).getTokenType() == TokenType.MULTIPLICATION ||
                                tokens.get(l + 1).getTokenType() == TokenType.SUBTRACTION ||
                                tokens.get(l + 1).getTokenType() == TokenType.POW ||
                                tokens.get(l + 1).getTokenType() == TokenType.SEMICOLON))
                            throw new UnacceptableException("Unacceptable at line: " + tokens.get(l + 1).getPosition().getLine() + ", Column: " + tokens.get(l + 1).getPosition().getColumn());
                    }
                }

                startIndex = i + 1;
            }
        }
    }

    private List<Token> parseFileToTokenList(String pathToFile) {
        List<String> lines = new FileReader().readFile(pathToFile);

        List<Token> tokens = new ArrayList<>();

        TokenType delimiter = null;
        StringBuffer currentToken = new StringBuffer();

        for (int i = 0; i < lines.size(); i++) {
            String[] letters = lines.get(i).split("");
            int startTokenColumnIndex = 0;
            for (int l = 0; l < letters.length; l++) {

                delimiter = checkDelimiter(letters[l]);
                if (delimiter != null) {
                    if (!currentToken.toString().equals("")) {
                        tokens.add(isName(currentToken.toString(), i, startTokenColumnIndex));
                        currentToken.delete(0, currentToken.capacity() - 1);
                    }
                    if (delimiter != TokenType.SPACE) {
                        startTokenColumnIndex = l + 1;
                        tokens.add(new Token(delimiter, -1, new Position(i, l)));
                    }
                } else {
                    currentToken.append(letters[l]);
                }
            }
            if (!currentToken.toString().equals("")) {
                tokens.add(isName(currentToken.toString(), i, startTokenColumnIndex));
            }
            currentToken.delete(0, currentToken.capacity() - 1);
        }
        return tokens;
    }

    private Token isName(String word, int lineIndex, int columnIndex) {
        for (int i = 0; i < tokenTypes.length; i++) {
            if (word.equals(tokenTypes[i].getName())) {
                return new Token(tokenTypes[i], -1, new Position(lineIndex, columnIndex));
            }
        }
        int index;
        try {
            index = map.keySet().stream().max(Comparator.comparing(Integer::intValue)).get();
        } catch (Exception e) {
            map.put(0, word);
            return new Token(TokenType.NAME, 0, new Position(lineIndex, columnIndex));
        }
        for (int i = 0; i < index; i++) {
            if (map.get(i).equals(word)) {
                return new Token(TokenType.NAME, i, new Position(lineIndex, columnIndex));
            }
        }
        map.put(index + 1, word);
        return new Token(TokenType.NAME, index + 1, new Position(lineIndex, columnIndex));
    }

    private TokenType checkDelimiter(String character) {
        switch (character) {
            case " ":
                return TokenType.SPACE;
            case ",":
                return TokenType.COMA;
            case ";":
                return TokenType.SEMICOLON;
            case "+":
                return TokenType.PLUS;
            case "-":
                return TokenType.MINUS;
            case "*":
                return TokenType.MULTIPLICATION;
            case "/":
                return TokenType.SUBTRACTION;
            case "^":
                return TokenType.POW;
            default:
                return null;
        }
    }

    private void showTable() {
        Set<Integer> keys = map.keySet();
        System.out.println("SIZE of Map: " + keys.size());
        for (Integer i : keys) {
            System.out.println("Key: " + i + ", Name: " + map.get(i));
        }
    }

    public HashMap<Integer, String> getMap() {
        return map;
    }
}
