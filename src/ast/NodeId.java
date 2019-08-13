package ast;

import javax.management.Attribute;

public class NodeId extends NodeAST {

    private String name;
    private Attribute definition;

    public NodeId(String name, Attribute definition) {
        this.name = name;
        this.definition = definition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attribute getDefinition() {
        return definition;
    }

    public void setDefinition(Attribute definition) {
        this.definition = definition;
    }

    @Override
    public String toString() {
        return "NodeId{" +
                "name='" + name + '\'' +
                ", definition=" + definition +
                '}';
    }
}
