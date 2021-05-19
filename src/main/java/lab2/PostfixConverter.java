package lab2;


import lab2.domain.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class PostfixConverter {
    private final static Operators[] operators = Operators.values();

    public List<Token> convert(String statement) {
        List<Token> tokens = split(statement);

        LinkedList<Token> stack = new LinkedList<Token>();
        List<Token> postfix = new LinkedList<Token>();
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i) instanceof Operand) postfix.add(tokens.get(i));
            else {
                if (tokens.get(i) instanceof Operator) {
                    for (int l = stack.size() - 1; l >= 0; l--){
                        if (stack.get(l) instanceof Operator){
                            if (((Operator)stack.get(l)).getPriority() > ((Operator)tokens.get(i)).getPriority()){
                                postfix.add(stack.removeLast());
                            } else break;
                        } else {
                            break;
                        }
                    }
                    stack.add(tokens.get(i));
                } else {
                    if (tokens.get(i) instanceof OpenBracket) stack.add(tokens.get(i));
                    else {
                        for (int l = stack.size() - 1; l >= 0; l--) {
                           if (stack.getLast() instanceof OpenBracket){
                               stack.removeLast();
                               break;
                           } else {
                               postfix.add(stack.removeLast());
                           }
                        }
                    }
                }
            }
        }
        for (int l = stack.size() - 1; l >= 0; l--){
            postfix.add(stack.removeLast());
        }
//        System.out.println("POSTFIX");
//        for (Token t : postfix) System.out.println(t);
        return postfix;
    }

    private List<Token> split(String statement){
        List<Token> list = new ArrayList<Token>();
        StringBuilder currentToken = new StringBuilder();

        String[] letters = statement.split("");
        for (int i = 0; i < letters.length; i++) {
            if (isOperator(letters[i])) {
                if (letters[i].equals("-") && currentToken.length() == 0 &&
                        (list.size() == 0 || list.get(list.size() - 1) instanceof Operator)) {
                    list.add(new Operator(letters[i]).setUnaryMinus());
                } else {
                    if (currentToken.length() > 0) {
                        list.add(new Operand(currentToken.toString()));
                        currentToken.delete(0, currentToken.length());
                    }
                    list.add(new Operator(letters[i]));
                }
            } else {
                if (isBracket(letters[i])) {
                    if (currentToken.length() > 0) {
                        list.add(new Operand(currentToken.toString()));
                        currentToken.delete(0, currentToken.length());
                    }
                    list.add(whichBracket(letters[i]));
                } else {
                    if (letters[i].equals(" ")) {
                        if (currentToken.length() > 0) {
                            list.add(new Operand(currentToken.toString()));
                            currentToken.delete(0, currentToken.length());
                        }
                    } else {
                        currentToken.append(letters[i]);
                    }
                }
            }
        }
        if (currentToken.length() > 0) list.add(isOperator(currentToken.toString()) ? new Operator(currentToken.toString()) :
                isBracket(currentToken.toString()) ? whichBracket(currentToken.toString()) : new Operand(currentToken.toString()));
        return list;
    }

    private boolean isOperator(String s) {
        for (int i = 0 ; i < operators.length; i++) {
            if (operators[i].getName().equals(s)) return true;
        }
        return false;
    }

    private boolean isBracket(String s) {
        return  s.equals("(") || s.equals(")");
    }

    private Bracket whichBracket(String s){
        return s.equals("(") ? new OpenBracket("(") : new CloseBracket(")");
    }

}
