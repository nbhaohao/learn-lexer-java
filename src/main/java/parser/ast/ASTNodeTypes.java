package parser.ast;

public enum ASTNodeTypes {
    BLOCK, // 代码块
    BINARY_EXPR, // 1 + 1
    UNARY_EXPR, // ++i
    VARIABLE, // 变量
    SCALAR, // 值类型， 1.0 true
    IF_STMT, // if 语句
    WHILE_STMT, // while 语句
    FOR_STMT, // for 语句
    ASSIGN_STMT, // 赋值语句
    FUNCTION_DECLARE_STMT, // 定义函数的语句
    DECLARE_STMT, // 定义的语句
}
