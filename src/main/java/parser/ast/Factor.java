package parser.ast;

import lexer.Token;
import lexer.TokenType;
import parser.util.PeekTokenIterator;

public abstract class Factor extends ASTNode {
    public Factor(ASTNode parent, PeekTokenIterator it) {
        super(parent);
        Token token = it.next();
        if (token.get_type() == TokenType.VARIABLE) {
            this.type = ASTNodeTypes.VARIABLE;
        } else {
            this.type = ASTNodeTypes.SCALAR;
        }
        this.label = token.get_value();
        this.lexeme = token;
    }
}
