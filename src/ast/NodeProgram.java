package ast;

import java.util.List;

public class NodeProgram extends NodeAST {

    private List<NodeDecSt> decsts;

    public NodeProgram(List<NodeDecSt> decsts) {
        this.decsts = decsts;
    }

    @Override
    public String toString() {
        return "NodeProgram{" +
                "decsts=" + decsts +
                '}';
    }

    public List<NodeDecSt> getDecsts() {
        return decsts;
    }

    public void setDecsts(List<NodeDecSt> decsts) {
        this.decsts = decsts;
    }
}
