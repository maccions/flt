package ast;

import visitor.Visitor;

public class NodeAssign extends NodeStm {

    private NodeId id;

    private NodeExpr expr;

    public NodeAssign(NodeId id, NodeExpr expr) {
        this.id = id;
        this.expr = expr;
    }

    public NodeId getId() {
        return id;
    }

    public void setId(NodeId id) {
        this.id = id;
    }

    public NodeExpr getExpr() {
        return expr;
    }

    public void setExpr(NodeExpr expr) {
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "NodeAssign{" +
                "id=" + id +
                ", expr=" + expr +
                '}';
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
