package parser;

public class Block extends Stmt {
    public Block(ASTNode parent) {
        super(parent, ASTNodeTypes.BLOCK, "block");
    }
}
