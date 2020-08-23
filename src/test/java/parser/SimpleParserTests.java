package parser;

import lexer.Lexer;
import lexer.LexicalException;
import org.junit.jupiter.api.Test;
import parser.ast.ASTNode;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

public class SimpleParserTests {
    @Test
    public void test_parse() throws LexicalException, ParseException {
        /* 最后结果生成的树
         *    +
         *   / \
         *  1   +
         *     / \
         *    2   +
         *       / \
         *      3   4
         */
        Stream<Character> charStream = "1+2+3+4".chars().mapToObj(x -> (char) x);
        Lexer lexer = new Lexer();
        // 1, +, 2, +, 3, +, 4
        PeekTokenIterator iterator = new PeekTokenIterator(lexer.analyse(charStream).stream());
        ASTNode e1 = SimpleParser.parse(iterator);
        assertEquals(2, e1.getChildren().size());
        assertEquals("+", e1.getLexeme().get_value());
        ASTNode e1_v1 = e1.getChild(0);
        assertEquals("1", e1_v1.getLexeme().get_value());
        ASTNode e2 = e1.getChild(1);
        assertEquals("+", e2.getLexeme().get_value());
        assertEquals(2, e2.getChildren().size());
        ASTNode e2_v1 = e2.getChild(0);
        assertEquals("2", e2_v1.getLexeme().get_value());
        ASTNode e3 = e2.getChild(1);
        assertEquals("+", e3.getLexeme().get_value());
        assertEquals(2, e3.getChildren().size());
        ASTNode e3_v1 = e3.getChild(0);
        assertEquals("3", e3_v1.getLexeme().get_value());
        ASTNode e3_v2 = e3.getChild(1);
        assertEquals("4", e3_v2.getLexeme().get_value());

        e1.print(2);
    }
}
