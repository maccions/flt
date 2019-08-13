package ast;

public class NodeDecl extends NodeDecSt {

    private NodeId id;

    private LangType type;

    public NodeDecl(NodeId id, LangType type) {
        this.id = id;
        this.type = type;
    }

    public NodeId getId() {
        return id;
    }

    public void setId(NodeId id) {
        this.id = id;
    }

    public LangType getType() {
        return type;
    }

    public void setType(LangType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NodeDecl{" +
                "id=" + id +
                ", type=" + type +
                '}';
    }
}
