package lab4;

import lab2.PostfixConverter;
import lab2.domain.Operand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CreateTree {

    public static void creatTree(List<Token> tokens, HashMap<Integer, String> map) throws Exception {
        Tree tree = new Tree();
        List<Integer> constReferences = new ArrayList<>();
        List<Integer> vars = new ArrayList<>();
        List<Integer> initialized = new ArrayList<>();

        Node currentNode = tree.getRoot();
        for (int i = 2; i < tokens.size(); i++) {
            if (tokens.get(i).getTokenType() == TokenType.VAR) {
                while (true) {
                    i++;
                    if (tokens.get(i).getTokenType() == TokenType.NAME) {
                        vars.add(tokens.get(i).getReference());
                    }
                    if (tokens.get(i).getTokenType() == TokenType.SEMICOLON) {
                        break;
                    }
                }
            }
            if (tokens.get(i).getTokenType() == TokenType.CONST) {
                currentNode.setLeft(new Node(tokens.get(i + 2)));
                currentNode.getLeft().setLeft(new Node(tokens.get(i + 1)));
                currentNode.getLeft().setRight(new Node(tokens.get(i + 3)));
                currentNode.setRight(new Node(true));

                constReferences.add(tokens.get(i + 1).getReference());
                currentNode = currentNode.getRight();
                i = i + 4;
            }
            if (tokens.get(i).getTokenType() == TokenType.NAME) {
                initialized.add(tokens.get(i).getReference());
                findError2(tokens.get(i).getReference(), constReferences, tokens.get(i).getPosition().getLine());
                findError3(tokens.get(i).getReference(), vars, tokens.get(i).getPosition().getLine());
                if (tokens.get(i + 1).getTokenType() == TokenType.ASSIGN) {
                    currentNode.setLeft(new Node(tokens.get(i + 1)));
                    currentNode.getLeft().setLeft(new Node(tokens.get(i)));
                    i++;
                    int startIndex = i + 1;
                    while (true) {
                        i++;
                        if (tokens.get(i).getTokenType() == TokenType.SEMICOLON) {
                            List<Node> statement = setPriorities(tokens, startIndex, i, vars, map, initialized);
                            if (statement.size() == 1){
                                currentNode.getLeft().setRight(statement.get(0));
                                currentNode.setRight(new Node(true));
                                currentNode = currentNode.getRight();
                            } else {
                                while (statement.size() != 1) {
                                    for (int l = 0; l < statement.size(); l++) {
                                        if (statement.get(l).getToken().getTokenType() != TokenType.NAME && !statement.get(l).isFlag()) {
                                            statement.get(l).setLeft(statement.get(l - 2));
                                            statement.get(l).setRight(statement.get(l - 1));
                                            statement.get(l).setFlag(true);
                                            statement.remove(l - 1);
                                            statement.remove(l - 2);
                                            break;
                                        }
                                    }
                                }
                                currentNode.getLeft().setRight(statement.get(0));
                                currentNode.setRight(new Node(true));
                                currentNode = currentNode.getRight();
                            }
                            break;
                        }
                    }
                } else throw new UnacceptableException("a");
            }
        }
        tree.print(map);
    }

    private static List<Node> setPriorities(List<Token> tokens, int start, int finish, List<Integer> vars, HashMap<Integer, String> map, List<Integer> initialized) throws Exception{
        List<Node> prioritizedTokens = new ArrayList<>();
        TokenType[] tokenTypes = TokenType.values();

        StringBuilder builder = new StringBuilder();
        for (int i = start; i < finish; i++) {
            if (tokens.get(i).getTokenType() == TokenType.NAME) {
                try {
                    Integer.parseInt(map.get(tokens.get(i).getReference()));
                } catch (Exception e){
                    findError3(tokens.get(i).getReference(), vars, tokens.get(i).getPosition().getLine());
                    findError4(tokens.get(i).getReference(), initialized, tokens.get(i).getPosition().getLine());
                }
            }
            builder.append(tokens.get(i).getTokenType() == TokenType.NAME ? tokens.get(i).getReference() : tokens.get(i).getTokenType().getName());
        }
        findError1(builder.toString(), tokens.get(start).getPosition().getLine());
        List<lab2.domain.Token> list = new PostfixConverter().convert(builder.toString());
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) instanceof Operand) {
                prioritizedTokens.add(new Node(new Token(TokenType.NAME, Integer.parseInt(list.get(i).getName()), new Position(tokens.get(start).getPosition().getLine(), 0))));
            } else {
                for (int l = 0; l < tokenTypes.length; l++) {
                    if (tokenTypes[l].getName().equals(list.get(i).getName()))
                    prioritizedTokens.add(new Node(new Token(tokenTypes[l], -1, new Position(tokens.get(start).getPosition().getLine(), 0))));
                }
            }
        }
        return prioritizedTokens;
    }

    private static void findError1(String statement, int line) throws Exception{
        int open = 0;
        int close = 0;
        char[] tokens = statement.toCharArray();
        for (int i = 0; i < tokens.length; i++){
            if (tokens[i] == '(') {
                open++;
            }
            if (tokens[i] == ')'){
                close++;
                if (close > open) throw new UnacceptableException("Problem with BRACES in line: " + line);
            }
        }
        if (close != open) throw new UnacceptableException("Problem with BRACES in line: " + line);
    }

    private static void findError2(int reference, List<Integer> constReferences, int line) throws Exception{
        for (int i = 0; i < constReferences.size(); i++){
            if (constReferences.get(i) == reference)
                throw new UnacceptableException("Problem with CONST in line: " + line);
        }
    }

    private static void findError3(int reference, List<Integer> varReferences, int line) throws Exception{
        boolean flag = false;
        for (int i = 0; i < varReferences.size(); i++){
            if (varReferences.get(i) == reference) flag = true;

        }
        if (!flag) throw new UnacceptableException("Problem with VAR in line: " + line);
    }

    private static void findError4(int reference, List<Integer> initialized, int line) throws Exception{
        boolean flag = false;
        for (int i = 0; i < initialized.size(); i++){
            if (initialized.get(i) == reference) flag = true;

        }
        if (!flag) throw new UnacceptableException("Problem with VAR(var wasn't initialized) in line: " + line);
    }
}
