package lexer;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LexerTests {

    void assertToken(Token token, String assertValue, TokenType assertType) {
        assertEquals(assertType, token.get_type());
        assertEquals(assertValue, token.get_value());
    }

    @Test
    public void test_expression() throws LexicalException {
        Lexer lexer = new Lexer();
        String source = "(a+b)^100.12==+100-20";
        List<Token> tokenList = lexer.analyse(source.chars().mapToObj(x -> (char) x));
        assertEquals(11, tokenList.size());
        assertToken(tokenList.get(0), "(", TokenType.BRACKET);
        assertToken(tokenList.get(1), "a", TokenType.VARIABLE);
        assertToken(tokenList.get(2), "+", TokenType.OPERATOR);
        assertToken(tokenList.get(3), "b", TokenType.VARIABLE);
        assertToken(tokenList.get(4), ")", TokenType.BRACKET);
        assertToken(tokenList.get(5), "^", TokenType.OPERATOR);
        assertToken(tokenList.get(6), "100.12", TokenType.FLOAT);
        assertToken(tokenList.get(7), "==", TokenType.OPERATOR);
        assertToken(tokenList.get(8), "+100", TokenType.INTEGER);
        assertToken(tokenList.get(9), "-", TokenType.OPERATOR);
        assertToken(tokenList.get(10), "20", TokenType.INTEGER);
    }

    @Test
    public void test_function() throws LexicalException {
        String source = "func foo(a, b){\n" + "print(a + b)\n" + "}\n" + "foo(-100.0, 100)";
        Lexer lexer = new Lexer();
        List<Token> tokenList = lexer.analyse(source.chars().mapToObj(x -> (char) x));

        assertEquals(21, tokenList.size());
        assertToken(tokenList.get(0), "func", TokenType.KEYWORD);
        assertToken(tokenList.get(1), "foo", TokenType.VARIABLE);
        assertToken(tokenList.get(2), "(", TokenType.BRACKET);
        assertToken(tokenList.get(3), "a", TokenType.VARIABLE);
        assertToken(tokenList.get(4), ",", TokenType.OPERATOR);
        assertToken(tokenList.get(5), "b", TokenType.VARIABLE);
        assertToken(tokenList.get(6), ")", TokenType.BRACKET);
        assertToken(tokenList.get(7), "{", TokenType.BRACKET);
        assertToken(tokenList.get(8), "print", TokenType.VARIABLE);
        assertToken(tokenList.get(9), "(", TokenType.BRACKET);
        assertToken(tokenList.get(10), "a", TokenType.VARIABLE);
        assertToken(tokenList.get(11), "+", TokenType.OPERATOR);
        assertToken(tokenList.get(12), "b", TokenType.VARIABLE);
        assertToken(tokenList.get(13), ")", TokenType.BRACKET);
        assertToken(tokenList.get(14), "}", TokenType.BRACKET);
        assertToken(tokenList.get(15), "foo", TokenType.VARIABLE);
        assertToken(tokenList.get(16), "(", TokenType.BRACKET);
        assertToken(tokenList.get(17), "-100.0", TokenType.FLOAT);
        assertToken(tokenList.get(18), ",", TokenType.OPERATOR);
        assertToken(tokenList.get(19), "100", TokenType.INTEGER);
        assertToken(tokenList.get(20), ")", TokenType.BRACKET);
    }

    @Test
    public void test_deleteComments() throws LexicalException {
        String source = "/*12312312321\n12312313*/a=1";
        Lexer lexer = new Lexer();
        List<Token> tokenList = lexer.analyse(source.chars().mapToObj(x -> (char) x));
        assertEquals(3, tokenList.size());
        assertToken(tokenList.get(0), "a", TokenType.VARIABLE);
        assertToken(tokenList.get(1), "=", TokenType.OPERATOR);
        assertToken(tokenList.get(2), "1", TokenType.INTEGER);
    }
}
