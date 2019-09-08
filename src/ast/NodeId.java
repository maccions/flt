package ast;

import symbolTable.Attributes;
import visitor.Visitor;

public class NodeId extends NodeAST {

    private String name;
    private Attributes definition;

    public NodeId(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attributes getDefinition() {
        return definition;
    }

    public void setDefinition(Attributes definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
