package test;

import ast.NodeProgram;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import scanner.ScannerException;
import symbolTable.SymTable;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

import java.io.File;
import java.io.IOException;

public class CodeGeneratorVisitorTest {

    private TypeCheckingVisitor tcVisitor;
    private CodeGeneratorVisitor visitor;
    

    @BeforeEach
    public void setUp() {
        SymTable.init();
        tcVisitor = new TypeCheckingVisitor();
        visitor = new CodeGeneratorVisitor();
    }
    
    @Test
    public void visitNodeProgram() throws ScannerException, SyntacticException, IOException {
        String path = "src/test/data/inputCorretto.txt";
        Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        NodeProgram node = parser.parse();
        tcVisitor.visit(node);
        assertEquals("", tcVisitor.getLog());
        visitor.visit(node);
        assertEquals("5  sa 0k la5k 3.2 + sb 0k lb p P ", visitor.getCodice());
    }

    @Test
    public void visitEsempioSlide() throws ScannerException, SyntacticException, IOException {
        String path = "src/test/data/inputCodeGeneration.txt";
        Scanner scanner = new Scanner(path);
        Parser parser = new Parser(scanner);
        NodeProgram node = parser.parse();
        tcVisitor.visit(node);
        assertEquals("", tcVisitor.getLog());
        visitor.visit(node);
        assertEquals("1.0 6 5k / sb 0k lb p P 1 6 / sa 0k la p P ", visitor.getCodice());
    }
}