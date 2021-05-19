package lab1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grammar {

    public static boolean check(String statement){
        if (statement.length() % 3 == 0 || statement.length() == 1) {
            int a = statement.length() >= 6 ? statement.length() / 3 - 1: 0;
            int b = statement.length() >= 3 ? statement.length() / 3: 0;
            int v = statement.length() / 3 + 1;
            return Pattern.compile("^[А]{" + a + "}[Б]{" + b + "}[В]{" + v + "}$").matcher(statement).matches();
        } else return false;
    }
}
