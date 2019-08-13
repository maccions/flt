package ast;

import visitor.Visitor;

import java.util.List;

public class NodeProgram extends NodeAST {

    private List<NodeDecSt> decsts;

    public NodeProgram(List<NodeDecSt> decsts) {
        this.decsts = decsts;
    }

    @Override
    public String toString() {
        StringBuilder lista = new StringBuilder();

        for (NodeDecSt x : decsts)
            lista.append("[").append(x.toString()).append("]\n");

        return lista.toString();
    }

    public List<NodeDecSt> getDecsts() {
        return decsts;
    }

    public void setDecsts(List<NodeDecSt> decsts) {
        this.decsts = decsts;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
