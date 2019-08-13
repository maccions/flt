package ast;

import visitor.Visitor;

public abstract class NodeAST {

    private TypeDescriptor resType;

    public void setResType(TypeDescriptor resType) {
        this.resType= resType;
    }

    public TypeDescriptor getResType() {
        return resType;
    }

    public abstract void accept(Visitor visitor);
}
