package symbolTable;

import ast.TypeDescriptor;

public class Attributes {

    private TypeDescriptor type;

    private char registro;

    public TypeDescriptor getType() { return type; }

    public Attributes(TypeDescriptor descriptor) {
        type = descriptor;
    }

    @Override
    public String toString(){
        return type.toString();
    }

    public void setRegister(char newRegister) { registro = newRegister; }

    public char getRegister() { return registro; }
}


