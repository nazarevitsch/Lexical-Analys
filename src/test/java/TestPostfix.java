import lab2.PostfixConverter;
import lab2.domain.Operand;
import lab2.domain.Operator;
import lab2.domain.Token;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestPostfix {

    private PostfixConverter postfixConverter = new PostfixConverter();

    private String firstStatement = "A * B + C";
    private List<Token> firstPostfix = createFirstPostfixList();

    private String secondStatement = "- 4 - (6 + 9)";
    private List<Token> secondPostfix = createSecondPostfixList();

    private String thirdStatement = "A * B ^ C + D";
    private List<Token> thirdPostfix = createThirdPostfixList();

    private String forthStatement = "A * (B + C * D) + E";
    private List<Token> forthPostfix = createForthPostfixList();

    @Test
    public void test1() {
        List<Token> postfixResult = postfixConverter.convert(firstStatement);
        compareTwoLists(postfixResult, firstPostfix);
    }

    @Test
    public void test2() {
        List<Token> postfixResult = postfixConverter.convert(secondStatement);
        compareTwoLists(postfixResult, secondPostfix);
    }

    @Test
    public void test3() {
        List<Token> postfixResult = postfixConverter.convert(thirdStatement);
        compareTwoLists(postfixResult, thirdPostfix);
    }

    @Test
    public void test4() {
        List<Token> postfixResult = postfixConverter.convert(forthStatement);
        compareTwoLists(postfixResult, forthPostfix);
    }

    private void compareTwoLists(List<Token> result, List<Token> expectation){
        Assert.assertEquals(result.size(), expectation.size());
        for (int i = 0; i < result.size(); i++) {
            Assert.assertEquals(result.get(i), expectation.get(i));
        }
    }

    private static List<Token> createFirstPostfixList(){
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new Operand("A"));
        tokens.add(new Operand("B"));
        tokens.add(new Operator("*"));
        tokens.add(new Operand("C"));
        tokens.add(new Operator("+"));
        return tokens;
    }

    private static List<Token> createSecondPostfixList(){
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new Operand("4"));
        tokens.add(new Operator("-").setUnaryMinus());
        tokens.add(new Operand("6"));
        tokens.add(new Operand("9"));
        tokens.add(new Operator("+"));
        tokens.add(new Operator("-"));
        return tokens;
    }

    private static List<Token> createThirdPostfixList(){
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new Operand("A"));
        tokens.add(new Operand("B"));
        tokens.add(new Operand("C"));
        tokens.add(new Operator("^"));
        tokens.add(new Operator("*"));
        tokens.add(new Operand("D"));
        tokens.add(new Operator("+"));
        return tokens;
    }

    private static List<Token> createForthPostfixList(){
        List<Token> tokens = new ArrayList<Token>();
        tokens.add(new Operand("A"));
        tokens.add(new Operand("B"));
        tokens.add(new Operand("C"));
        tokens.add(new Operand("D"));
        tokens.add(new Operator("*"));
        tokens.add(new Operator("+"));
        tokens.add(new Operator("*"));
        tokens.add(new Operand("E"));
        tokens.add(new Operator("+"));
        return tokens;
    }
}
