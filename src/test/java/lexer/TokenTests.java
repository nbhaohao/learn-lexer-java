package lexer;

import org.junit.jupiter.api.Test;
import utils.PeekIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTests {

    void assertToken(Token token, String assertValue, TokenType assertType) {
        assertEquals(assertType, token.get_type());
        assertEquals(assertValue, token.get_value());
    }

    @Test
    void test_makeVarOrKeyword() {
        PeekIterator<Character> it1 = new PeekIterator<>("if abc".chars().mapToObj(c -> (char) c));
        PeekIterator<Character> it2 = new PeekIterator<>("true abc".chars().mapToObj(c -> (char) c));
        PeekIterator<Character> it3 = new PeekIterator<>("abc abc".chars().mapToObj(c -> (char) c));
        Token token1 = Token.makeVarOrKeyword(it1);
        assertToken(token1, "if", TokenType.KEYWORD);
        it1.next();
        Token token1_1 = Token.makeVarOrKeyword(it1);
        assertToken(token1_1, "abc", TokenType.VARIABLE);
        Token token2 = Token.makeVarOrKeyword(it2);
        assertToken(token2, "true", TokenType.BOOLEAN);
        Token token3 = Token.makeVarOrKeyword(it3);
        assertToken(token3, "abc", TokenType.VARIABLE);
    }

    @Test
    void test_makeString() {
        PeekIterator<Character> it1 = new PeekIterator<>("\"abc abc\"".chars().mapToObj(c -> (char) c));
        try {
            Token token1 = Token.makeString(it1);
            assertToken(token1, "\"abc abc\"", TokenType.STRING);
        } catch (LexicalException e) {
            e.printStackTrace();
        }
        PeekIterator<Character> it2 = new PeekIterator<>("'abc abc'".chars().mapToObj(c -> (char) c));
        try {
            Token token2 = Token.makeString(it2);
            assertToken(token2, "'abc abc'", TokenType.STRING);
        } catch (LexicalException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test_makeOperator() throws LexicalException {
        PeekIterator<Character> it1 = new PeekIterator<>("++ +1".chars().mapToObj(c -> (char) c));
        Token token1 = Token.makeOperator(it1);
        assertToken(token1, "++", TokenType.OPERATOR);
        it1.next();
        Token token2 = Token.makeOperator(it1);
        assertToken(token2, "+", TokenType.OPERATOR);
        PeekIterator<Character> it2 = new PeekIterator<>("* *=".chars().mapToObj(c -> (char) c));
        Token token3 = Token.makeOperator(it2);
        assertToken(token3, "*", TokenType.OPERATOR);
        it2.next();
        Token token4 = Token.makeOperator(it2);
        assertToken(token4, "*=", TokenType.OPERATOR);
    }

    @Test
    void test_makeNumber() throws LexicalException {
        String[] tests = {"00001 xz", "0.2 322", "1 abc", "+0 aa", "-0 aa", ".3 ccc", "7788.96 123", "-100.22*3"};
        for (int i = 0; i < tests.length; i++) {
            PeekIterator<Character> it = new PeekIterator<>(tests[i].chars().mapToObj(c -> (char) c));
            Token token = Token.makeNumber(it);
            assertToken(token, tests[i].split("[* ]+")[0], tests[i].indexOf('.') != -1 ? TokenType.FLOAT : TokenType.INTEGER);
        }
    }
}
