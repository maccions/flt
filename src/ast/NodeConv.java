package ast;

import visitor.Visitor;

public class NodeConv extends NodeExpr {

    private NodeExpr nodo;

    public NodeConv(NodeExpr nodo) {
        this.nodo = nodo;
    }

    public NodeExpr getExpr() { return nodo; }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return nodo.toString();
    }
}
