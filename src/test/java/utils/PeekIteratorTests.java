package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PeekIteratorTests {
    @Test
    void test_next() {
        String source = "abcfe";
        PeekIterator<Character> it1 = new PeekIterator<>(source.chars().mapToObj(c -> (char) c));
        assertEquals('a', it1.next());
        assertEquals('b', it1.next());
        it1.next();
        it1.next();
        assertEquals('e', it1.next());
        assertNull(it1.next());
    }

    @Test
    void test_peek() {
        String source = "abcfe";
        PeekIterator<Character> it1 = new PeekIterator<>(source.chars().mapToObj(c -> (char) c));
        assertEquals('a', it1.peek());
        assertEquals('a', it1.peek());
        assertEquals('a', it1.next());
        assertEquals('b', it1.next());
        assertEquals('c', it1.peek());
    }

    @Test
    void test_has_next() {
        String source = "ab";
        PeekIterator<Character> it1 = new PeekIterator<>(source.chars().mapToObj(c -> (char) c));
        assertTrue(it1.hasNext());
        it1.next();
        assertTrue(it1.hasNext());
        it1.next();
        assertFalse(it1.hasNext());
    }

    @Test
    void test_put_back() {
        String source = "abcfe";
        PeekIterator<Character> it1 = new PeekIterator<>(source.chars().mapToObj(c -> (char) c));
        assertEquals('a', it1.next());
        assertEquals('b', it1.next());
        it1.pubBack();
        assertEquals('b', it1.next());
    }

    @Test
    void test_end_token() {
        String source = "abc";
        PeekIterator<Character> it1 = new PeekIterator<>(source.chars().mapToObj(c -> (char) c), 'z');
        it1.next();
        it1.next();
        it1.next();
        assertTrue(it1.hasNext());
        assertEquals('z', it1.next());
        assertFalse(it1.hasNext());
    }

    @Test
    void test_cache() {
        String source = "abcdefghijklmn";
        PeekIterator<Character> it1 = new PeekIterator<>(source.chars().mapToObj(c -> (char) c));
        int i = 0;
        // 调用 11 次，由于我们的缓存是 10 次，所以只能放回 10 个结果，如果无限的话，下面的结果应该是 a
        while (i <= 10) {
            it1.next();
            i++;
        }
        while (i >= 0) {
            it1.pubBack();
            i--;
        }
        assertEquals('b', it1.next());
    }
}
