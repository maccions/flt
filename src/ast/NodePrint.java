package ast;

import visitor.Visitor;

public class NodePrint extends NodeStm {

    private NodeId id;

    public NodePrint(NodeId id) {
        this.id = id;
    }

    public NodeId getId() {
        return id;
    }

    public void setId(NodeId id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "<PRINT "+id+">";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
