package parser.ast;

import lexer.Token;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class ASTNode {
    /* 树 */
    protected ArrayList<ASTNode> children = new ArrayList<>(); // 子节点
    protected ASTNode parent; // 父节点
    protected ASTNodeTypes type; // 类型

    /* 关键信息 */
    protected Token lexeme; // 词法单元
    protected String label; // 备注(标签)

    public ASTNode(ASTNode parent) {
        this.parent = parent;
    }

    public ASTNode(ASTNode parent, ASTNodeTypes type, String label) {
        this.parent = parent;
        this.type = type;
        this.label = label;
    }

    public ASTNode getChild(int index) {
        return children.get(index);
    }

    public void addChild(ASTNode node) {
        children.add(node);
    }

    public Token getLexeme() {
        return lexeme;
    }

    public void setLexeme(Token lexeme) {
        this.lexeme = lexeme;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ASTNodeTypes getType() {
        return type;
    }

    public void setType(ASTNodeTypes type) {
        this.type = type;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public void print(int indent) {
        System.out.println(StringUtils.leftPad(" ", indent * 2) + label);
        for (ASTNode astNode : children) {
            astNode.print(indent + 1);
        }
    }
}

