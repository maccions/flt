package test;

import ast.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;
import parser.Parser;
import scanner.Scanner;
import symbolTable.SymTable;
import visitor.TypeCheckingVisitor;


import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TypeCheckingVisitorTest {

    private Scanner scanner;
    private Parser parser;
    private TypeCheckingVisitor visitor;

    @BeforeEach
    public void setUp() throws IOException {
        String path ="src/test/data/inputCorretto.txt";
        scanner = new Scanner(path);
        parser = new Parser(scanner);
        SymTable.init();
        visitor = new TypeCheckingVisitor();
    }

    @Test
    public void visitNodeId() {
        NodeId id = new NodeId("nodo");
        visitor.visit(id);
        assertEquals("variabile [nodo] non dichiarata\n", visitor.getLog());
        assertEquals(0, SymTable.size());
    }

    @Test
    public void visitNodeProgram() {
        NodeProgram node = null;
        try {
            node = parser.parse();
        } catch (Exception e) {
            fail();
        }
        visitor.visit(node);
        assertEquals( "", visitor.getLog());
        assertEquals(2, SymTable.size());
        assertEquals("symbol table\n=============\na   \tINT\nb   \tFLOAT\n", SymTable.toStr());
    }

    @Test
    public void visitNodeDeref() {
        NodeId id = new NodeId("nodo");
        NodeDeref deref = new NodeDeref(id);
        visitor.visit(deref);
        assertEquals("variabile [nodo] non dichiarata\n", visitor.getLog());
        assertEquals(0, SymTable.size());
    }

    @Test
    public void testIncompatibilita() {
        String path ="src/test/data/inputIncompatibile.txt";
        NodeProgram node = null;
        try {
            Scanner scanner = new Scanner(path);
            Parser parser = new Parser(scanner);
            node = parser.parse();
        } catch (Exception e) {
            fail();
        }
        visitor.visit(node);
        assertTrue(visitor.getLog().contains("incompatibili"));
        assertEquals(2, SymTable.size());
        assertEquals("symbol table\n=============\na   \tINT\nb   \tINT\n", SymTable.toStr());
    }

    @Test
    public void testConversioneCorretta() {
        String path ="src/test/data/inputConv.txt";
        NodeProgram node = null;
        try {
            Scanner scanner = new Scanner(path);
            Parser parser = new Parser(scanner);
            node = parser.parse();
        } catch (Exception e) {
            fail();
        }
        visitor.visit(node);
        assertEquals( "", visitor.getLog()); //stringa vuota attesa
        assertEquals( 1, SymTable.size());
        assertEquals("symbol table\n=============\na   \tFLOAT\n", SymTable.toStr());
    }
    
}