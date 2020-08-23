package parser.util;

import lexer.Token;

public class ParseException extends Exception {

    private String msg;

    public ParseException(String message) {
        msg = message;
    }

    public ParseException(Token token) {
        msg = String.format("Syntax Error, unexpected token %s", token.get_value());
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
