package visitor;

import ast.*;
import symbolTable.Attributes;
import symbolTable.SymTable;

public class TypeCheckingVisitor implements Visitor {

    private StringBuilder log = new StringBuilder();

    @Override
    public void visit(NodeProgram node) {
        for( NodeDecSt element : node.getDecsts())
            element.accept(this);
    }

    @Override
    public void visit(NodeId node) {
        Attributes attribute;
        attribute = SymTable.lookup(node.getName());
        if(attribute == null) {
            node.setResType(TypeDescriptor.ERROR);
            log.append("variabile [" + node.getName() + "] non dichiarata\n");
        }
        else {
            node.setDefinition(attribute);
            node.setResType(attribute.getType());
        }
    }

    @Override
    public void visit(NodeDecl node) {
        TypeDescriptor descriptor;
        LangType type = node.getType();
        if(type.equals(LangType.INT)){
            descriptor = TypeDescriptor.INT;
        }
        else{
            descriptor = TypeDescriptor.FLOAT;
        }
        Attributes control = SymTable.lookup(node.getId().getName());
        if(control != null) {
            node.getId().setResType(TypeDescriptor.ERROR);
            log.append("variabile [" + node.getId().getName() + "] già presente\n");
        }
        else {
            Attributes attribute = new Attributes(descriptor);
            SymTable.enter(node.getId().getName(), attribute);
            node.getId().setResType(descriptor);
            node.getId().setDefinition(attribute);
        }
    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        if((node.getLeft().getResType().equals(TypeDescriptor.ERROR)) || (node.getRight().getResType().equals(TypeDescriptor.ERROR))) {
            node.setResType(TypeDescriptor.ERROR);
            log.append("Operazione impossibile per [" + node.toString() + "]");
        }
        else {
            if (node.getLeft().getResType().equals(node.getRight().getResType())) {
                node.setResType(node.getLeft().getResType());
            } else {
                node.setLeft(convert(node.getLeft()));
                node.setRight(convert(node.getRight()));
                node.setResType(TypeDescriptor.FLOAT);
            }
        }
    }

    @Override
    public void visit(NodePrint node) {
        node.getId().accept(this);
        node.setResType(node.getId().getResType());
    }

    @Override
    public void visit(NodeAssign node) {
        node.getId().accept(this);
        node.getExpr().accept(this);
        if(!booleanCompatible(node.getId().getResType(), node.getExpr().getResType())) {
            node.setResType(TypeDescriptor.ERROR);
            log.append("Variabile [" + node.getId().getName() + "] e [" + node.getExpr().toString() + "] incompatibili");
        }
        else { //converto solo se necessario
            if(node.getId().getResType().equals(TypeDescriptor.FLOAT) && node.getExpr().getResType().equals(TypeDescriptor.INT)) {
                node.setExpr(convert(node.getExpr()));
            }
        }
    }

    @Override
    public void visit(NodeCost node) {
        LangType type = node.getType();
        if(type.equals(LangType.INT))
            node.setResType(TypeDescriptor.INT);
        else if(type.equals(LangType.FLOAT))
            node.setResType(TypeDescriptor.FLOAT);
        else
            node.setResType(TypeDescriptor.ERROR);
    }

    @Override
    public void visit(NodeDeref node) {
        node.getId().accept(this);
        node.setResType(node.getId().getResType()); //il tipo è uguale a quello dell'identificatore
    }

    @Override
    public void visit(NodeConv node) {
        //visita expr (l’espressione contenuta nel nodo)
        node.getExpr().accept(this);
        if(!node.getExpr().getResType().equals(TypeDescriptor.INT)){
            node.setResType(TypeDescriptor.ERROR);
            log.append("Impossibile convertire expr [" + node.getExpr().toString() +"]");
        }
        else{
            node.setResType(TypeDescriptor.FLOAT);
        }
    }

    private boolean booleanCompatible (TypeDescriptor t1, TypeDescriptor t2) {
        if(!t1.equals(TypeDescriptor.ERROR) && !t2.equals(TypeDescriptor.ERROR)) {
            if((t1.equals(TypeDescriptor.FLOAT) && t2.equals(TypeDescriptor.INT)) || t1.equals(t2)) {
                return true;
            }
            else return false;
        }
        else return false;
    }

    private NodeExpr convert (NodeExpr node) {
        if (node.getResType().equals(TypeDescriptor.FLOAT)) {
            return node;
        }
        else {
            NodeConv nodo = new NodeConv(node);
            nodo.setResType(TypeDescriptor.FLOAT);
            return nodo;
        }
    }

    public String getLog() { return log.toString(); }

}
