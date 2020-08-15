package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AlphabetHelperTests {
    @Test
    void test_is_match_letter() {
        assertFalse(AlphabetHelper.isLetter('+'));
        assertFalse(AlphabetHelper.isLetter('1'));
        assertFalse(AlphabetHelper.isLetter('%'));
        assertTrue(AlphabetHelper.isLetter('a'));
    }

    @Test
    void test_is_match_number() {
        assertFalse(AlphabetHelper.isNumber('-'));
        assertFalse(AlphabetHelper.isNumber('a'));
        assertFalse(AlphabetHelper.isNumber('%'));
        assertTrue(AlphabetHelper.isNumber('1'));
    }

    @Test
    void test_is_match_literal() {
        assertFalse(AlphabetHelper.isLiteral(' '));
        assertFalse(AlphabetHelper.isLiteral('+'));
        assertFalse(AlphabetHelper.isLiteral('%'));
        assertTrue(AlphabetHelper.isLiteral('1'));
    }

    @Test
    void test_is_match_operator() {
        assertFalse(AlphabetHelper.isOperator(' '));
        assertFalse(AlphabetHelper.isOperator('1'));
        assertFalse(AlphabetHelper.isOperator('a'));
        assertTrue(AlphabetHelper.isOperator('*'));
    }
}
