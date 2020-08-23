package parser.ast;

import parser.util.PeekTokenIterator;

public class Variable extends Factor {
    public Variable(ASTNode parent, PeekTokenIterator iterator) {
        super(parent, iterator);
    }
}
