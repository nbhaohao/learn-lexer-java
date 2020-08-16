package lexer;

import utils.AlphabetHelper;
import utils.PeekIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Lexer {

    /**
     * 接受一个字符串流，返回分析好的 Token 元组
     * 具体实现先留空
     *
     * @param source 字符串流
     * @return 返回 Token 元组
     */
    public List<Token> analyse(Stream<Character> source) throws LexicalException {
        List<Token> tokens = new ArrayList<>();
        PeekIterator<Character> it = new PeekIterator<>(source, (char) 0);
        while (it.hasNext()) {
            char c = it.next();
            // 我们定义了一个 endToken，表示结束
            if (c == 0) {
                break;
            }
            char lookahead = it.peek();
            if (c == ' ' || c == '\n') {
                continue;
            }
            if (c == '/') {
                if (lookahead == '/') {
                    while (it.hasNext() && (c = it.next()) != '\n') ;
                } else if (lookahead == '*') {
                    boolean valid = false;
                    while (it.hasNext()) {
                        char p = it.next();
                        if (p == '*' && it.peek() == '/') {
                            it.next();
                            valid = true;
                            break;
                        }
                    }
                    if (!valid) {
                        throw new LexicalException("comments not match");
                    }
                    continue;
                }
            }
            if (c == '{' || c == '}' || c == '(' || c == ')') {
                tokens.add(new Token(TokenType.BRACKET, String.valueOf(c)));
                continue;
            }
            if (c == '\'' || c == '"') {
                it.putBack();
                tokens.add(Token.makeString(it));
                continue;
            }
            if (AlphabetHelper.isLetter(c)) {
                it.putBack();
                tokens.add(Token.makeVarOrKeyword(it));
                continue;
            }
            if (AlphabetHelper.isNumber(c)) {
                it.putBack();
                tokens.add(Token.makeNumber(it));
                continue;
            }
            // +-: 3 + 5, +5, 3 * -5, 3 * .5 这种情况的 + 和 - 我们要理解为数字是一起的
            if ((c == '+' || c == '-' || c == '.') && AlphabetHelper.isNumber(lookahead)) {
                // 最近的 Token 就是上一个 token
                Token lastToken = tokens.isEmpty() ? null : tokens.get(tokens.size() - 1);
                if (lastToken == null || !lastToken.isNumber() || lastToken.isOperator()) {
                    it.putBack();
                    tokens.add(Token.makeNumber(it));
                    continue;
                }
            }
            if (AlphabetHelper.isOperator(c)) {
                it.putBack();
                tokens.add(Token.makeOperator(it));
                continue;
            }
            throw new LexicalException(c);
        }
        return tokens;
    }
}
