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
}
