package lexer;

import utils.AlphabetHelper;
import utils.PeekIterator;

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
