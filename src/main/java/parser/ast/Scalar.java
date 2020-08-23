package parser.ast;

import parser.util.PeekTokenIterator;

public class Scalar extends Factor {
    public Scalar(ASTNode parent, PeekTokenIterator iterator) {
        super(parent, iterator);
    }
}
