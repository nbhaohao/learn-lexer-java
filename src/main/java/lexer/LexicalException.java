package lexer;

public class LexicalException extends Exception {
    private String msg;

    public LexicalException(String msg) {
        super();
        this.msg = msg;
    }

    public LexicalException(char c) {
        super();
        this.msg = String.format("Unexpected character %c", c);
    }

    @Override
    public String getMessage() {
        return msg;
    }
}
