package ast;

public class NodeBinOp {

    private LangOper op;

    private NodeExpr left;

    private NodeExpr right;

    public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
        this.op = op;
        this.left = left;
        this.right = right;
    }

    public LangOper getOp() {
        return op;
    }

    public void setOp(LangOper op) {
        this.op = op;
    }

    public NodeExpr getLeft() {
        return left;
    }

    public void setLeft(NodeExpr left) {
        this.left = left;
    }

    public NodeExpr getRight() {
        return right;
    }

    public void setRight(NodeExpr right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "NodeBinOp{" +
                "op=" + op +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}
