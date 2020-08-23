package parser;

import parser.ast.ASTNode;
import parser.ast.ASTNodeTypes;
import parser.ast.Expr;
import parser.ast.Scalar;
import parser.util.ParseException;
import parser.util.PeekTokenIterator;

public class SimpleParser {
    public static ASTNode parse(PeekTokenIterator iterator) throws ParseException {
        Expr expr = new Expr(null);
        Scalar scalar = new Scalar(expr, iterator);
        // 递归的出口
        if (!iterator.hasNext()) {
            return scalar;
        }
        expr.setLexeme(iterator.peek());
        iterator.nextMatch("+");
        expr.setLabel("+");
        expr.setType(ASTNodeTypes.BINARY_EXPR);
        expr.addChild(scalar);
        ASTNode rightNode = parse(iterator);
        expr.addChild(rightNode);
        return expr;
    }
}
