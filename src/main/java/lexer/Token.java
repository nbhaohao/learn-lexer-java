package lexer;

import utils.AlphabetHelper;
import utils.OperatorHelper.OperatorConfig;
import utils.PeekIterator;
import utils.OperatorHelper;

import java.util.Arrays;
import java.util.List;

/**
 * 按我们之前的说法，每个符号都是一个元祖，
 * _type 表示符号类型，例如 KeyWord
 * _value 表示字符串，例如 "var"
 */
public class Token {
    // TokenType 值类型的集合
    static final List<TokenType> scalarList = Arrays.asList(TokenType.INTEGER, TokenType.FLOAT, TokenType.BOOLEAN, TokenType.STRING);
    TokenType _type;
    String _value;

    /**
     * 提取变量或关键字
     *
     * @param it
     * @return
     */
    public static Token makeVarOrKeyword(PeekIterator<Character> it) {
        StringBuilder symbol = new StringBuilder();

        while (it.hasNext()) {
            Character lookahead = it.peek();
            if (AlphabetHelper.isLiteral(lookahead)) {
                symbol.append(lookahead);
            } else {
                break;
            }
            it.next();
        }

        // 判断是关键字还是变量
        String symbolString = symbol.toString();
        if (Keywords.isKeyword(symbolString)) {
            return new Token(TokenType.KEYWORD, symbolString);
        }
        if (symbolString.equals("true") || symbolString.equals("false")) {
            return new Token(TokenType.BOOLEAN, symbolString);
        }
        return new Token(TokenType.VARIABLE, symbolString);
    }

    public static Token makeString(PeekIterator<Character> it) throws LexicalException {
        StringBuilder symbol = new StringBuilder();
        int state = 0;
        while (it.hasNext()) {
            char c = it.next();
            switch (state) {
                case 0:
                    state = c == '\'' ? 2 : 1;
                    symbol.append(c);
                    break;
                case 1:
                    symbol.append(c);
                    if (c == '"') {
                        return new Token(TokenType.STRING, symbol.toString());
                    }
                    break;
                case 2:
                    symbol.append(c);
                    if (c == '\'') {
                        return new Token(TokenType.STRING, symbol.toString());
                    }
                    break;
            }
        }
        throw new LexicalException("Unexpected Error");
    }

    public static Token makeOperator(PeekIterator<Character> it) throws LexicalException {
        int state = 0;
        Character prevOperator = null;
        while (it.hasNext()) {
            Character lookahead = it.next();
            if (state == 0) {
                if (OperatorHelper.operatorConfigMap.containsKey(lookahead)) {
                    state = OperatorHelper.operatorConfigMap.get(lookahead).getCode();
                    prevOperator = lookahead;
                }
                if (lookahead == ',' || lookahead == ';') {
                    return new Token(TokenType.OPERATOR, String.valueOf(lookahead));
                }
            } else {
                OperatorConfig operatorConfig = OperatorHelper.operatorConfigMap.get(prevOperator);
                if (operatorConfig != null) {
                    if (Arrays.asList(operatorConfig.getCombineOperators()).contains(lookahead)) {
                        /*
                         * '+' -> '++'
                         * '&' -> '&&' || '&='
                         */
                        return new Token(TokenType.OPERATOR, prevOperator + "" + lookahead);
                    }
                    it.putBack();
                    return new Token(TokenType.OPERATOR, String.valueOf(prevOperator));
                }
            }
        }
        throw new LexicalException("Unexpected Error");
    }

    public static Token makeNumber(PeekIterator<Character> it) throws LexicalException {
        StringBuilder symbol = new StringBuilder();
        int state = 0;
        while (it.hasNext()) {
            char lookahead = it.peek();
            switch (state) {
                case 0:
                    if (lookahead == '0') {
                        state = 1;
                    } else if (AlphabetHelper.isNumber(lookahead)) {
                        state = 2;
                    } else if (lookahead == '+' || lookahead == '-') {
                        state = 3;
                    } else if (lookahead == '.') {
                        state = 5;
                    }
                    break;
                case 1:
                    if (lookahead == '0') {
                        state = 1;
                    } else if (AlphabetHelper.isNumber(lookahead)) {
                        state = 2;
                    } else if (lookahead == '.') {
                        state = 4;
                    } else {
                        return new Token(TokenType.INTEGER, symbol.toString());
                    }
                    break;
                case 2:
                    if (AlphabetHelper.isNumber(lookahead)) {
                        state = 2;
                    } else if (lookahead == '.') {
                        state = 4;
                    } else {
                        return new Token(TokenType.INTEGER, symbol.toString());
                    }
                    break;
                case 3:
                    if (AlphabetHelper.isNumber(lookahead)) {
                        state = 2;
                    } else if (lookahead == '.') {
                        state = 5;
                    } else {
                        throw new LexicalException(lookahead);
                    }
                    break;
                case 4:
                    if (lookahead == '.') {
                        throw new LexicalException(lookahead);
                    } else if (AlphabetHelper.isNumber(lookahead)) {
                        state = 4;
                    } else {
                        return new Token(TokenType.FLOAT, symbol.toString());
                    }
                    break;
                case 5:
                    if (AlphabetHelper.isNumber(lookahead)) {
                        state = 4;
                    } else {
                        throw new LexicalException(lookahead);
                    }
                    break;
            }
            it.next();
            symbol.append(lookahead);
        }
        throw new LexicalException("Unexpected Error");
    }

    public Token(TokenType _type, String _value) {
        this._type = _type;
        this._value = _value;
    }

    public TokenType get_type() {
        return _type;
    }

    public String get_value() {
        return _value;
    }

    @Override
    public String toString() {
        return "Token{" +
                "_type=" + _type +
                ", _value='" + _value + '\'' +
                '}';
    }

    // 判断这个符号是否是变量
    public Boolean isVariable() {
        return _type == TokenType.VARIABLE;
    }

    // 判断这个符号是否是值类型
    public Boolean isScalar() {
        return scalarList.contains(_type);
    }
}
