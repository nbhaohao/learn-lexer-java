package parser.util;

import lexer.Token;
import lexer.TokenType;
import utils.PeekIterator;

import java.util.stream.Stream;

public class PeekTokenIterator extends PeekIterator<Token> {
    public PeekTokenIterator(Stream<Token> stream) {
        super(stream);
    }

    public Token nextMatch(String value) throws ParseException {
        Token nextToken = this.next();
        if (!nextToken.get_value().equals(value)) {
            throw new ParseException(nextToken);
        }
        return nextToken;
    }

    public Token nextMatch(TokenType tokenType) throws ParseException {
        Token nextToken = this.next();
        if (nextToken.get_type() != tokenType) {
            throw new ParseException(nextToken);
        }
        return nextToken;
    }
}
