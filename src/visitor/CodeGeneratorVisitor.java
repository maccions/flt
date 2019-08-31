package visitor;

import ast.*;

public class CodeGeneratorVisitor implements Visitor {

    private StringBuffer codice = new StringBuffer();

    static int index = 0;

    private static char newRegister() {
        char[] registers = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'l', 'm', 'n', 'o', 'p', 'q'};
        if(index>registers.length)
            index = 0; // o eccezione
        return registers[index++];
    }


    @Override
    public void visit(NodeProgram node) {
        for( NodeDecSt element : node.getDecsts())
            element.accept(this);
        index = 0; //indice azzerato per nuovo utilizzo a partire dal primo registro
    }

    @Override
    public void visit(NodeId node) {

    }

    @Override
    public void visit(NodeDecl node) {
        node.getId().getDefinition().setRegister(newRegister());
    }

    @Override
    public void visit(NodeBinOp node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        codice.append(opChar(node.getOp()));
    }

    private char opChar(LangOper op) {
        char ret = '+';
        switch(op) {
            case DIV:
                ret = '/';
                break;
            case TIMES:
                ret = '*';
                break;
            case PLUS:
                ret = '+';
                break;
            case MINUS:
                ret = '-';
                break;
        }
        return ret;
    }

    @Override
    public void visit(NodePrint node) {
        char reg = node.getId().getDefinition().getRegister();
        codice.append(" l" + reg + " p P "); //load e poi pop
    }

    @Override
    public void visit(NodeAssign node) {
        NodeId id = node.getId();
        char r = id.getDefinition().getRegister();
        node.getExpr().accept(this);
        codice.append(" s" + r + " 0k");
    }

    @Override
    public void visit(NodeCost node) {
        codice.append(node.getValue()+' ');
    }

    @Override
    public void visit(NodeDeref node) {
        NodeId id = node.getId();
        char r = id.getDefinition().getRegister();
        codice.append(" l" + r); //load
    }

    @Override
    public void visit(NodeConv node) {
        node.getExpr().accept(this);
        codice.append("5k ");
    }

    public String getCodice() { return codice.toString(); }

}
