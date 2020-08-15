package utils;

import java.util.regex.Pattern;

public class AlphabetHelper {
    static Pattern pthLetter = Pattern.compile("^[a-zA-Z]$");
    static Pattern ptnNumber = Pattern.compile("^[0-9]$");
    static Pattern ptnLiteral = Pattern.compile("^[_a-zA-Z0-9]$");
    static Pattern ptnOperator = Pattern.compile("^[-+*<>=!&|^%/]$");

    public static boolean isMatch(Pattern pattern, char c) {
        return pattern.matcher(String.valueOf(c)).matches();
    }

    public static boolean isLetter(char c) {
        return isMatch(pthLetter, c);
    }

    public static boolean isNumber(char c) {
        return isMatch(ptnNumber, c);
    }

    public static boolean isLiteral(char c) {
        return isMatch(ptnLiteral, c);
    }

    public static boolean isOperator(char c) {
        return isMatch(ptnOperator, c);
    }
}
