package ast;

import visitor.Visitor;

public class NodeCost extends NodeExpr {

    private String value;

    private LangType type;

    public NodeCost(String value, LangType type) {
        this.value = value;
        this.type = type;
    }

    @Override
    public String toString() {
        return "NodeCost{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LangType getType() {
        return type;
    }

    public void setType(LangType type) {
        this.type = type;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
