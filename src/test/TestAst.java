package test;

import java.io.File;
import java.io.IOException;

import ast.NodeProgram;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;
import scanner.ScannerException;

public class TestAst {

	@Test
	public void testDcl() throws ScannerException, SyntacticException, IOException {
		String path ="src/test/data/dichiarazione.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram tree = parser.parse();
		assertEquals(
				"[NodeDecl{id=NodeId{name='a', definition=null}, type=INT}]\n" +
						"[NodeDecl{id=NodeId{name='b', definition=null}, type=FLOAT}]\n",
				tree.toString());
	}

	@Test
	public void testDclAsignPrint() throws ScannerException, SyntacticException, IOException {
		String path ="src/test/data/dicAssignPrint.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram tree = parser.parse();
		assertEquals(
				"[NodeDecl{id=NodeId{name='a', definition=null}, type=INT}]\n" +
						"[NodeAssign{id=NodeId{name='a', definition=null}, expr=NodeCost{value='5', type=INT}}]\n" +
						"[NodePrint{id=NodeId{name='a', definition=null}}]\n",
				tree.toString()
		);
	}

	@Test
	public void testPlus() throws ScannerException, SyntacticException, IOException {
		String path ="src/test/data/operazione.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram tree = parser.parse();
		assertEquals(
				"[NodeDecl{id=NodeId{name='a', definition=null}, type=INT}]\n" +
						"[NodeAssign{id=NodeId{name='a', definition=null}, expr=NodeBinOp{op=PLUS, left=NodeCost{value='2', type=INT}, right=NodeCost{value='3', type=INT}}}]\n",
				tree.toString()
		);
	}

	@Test
	public void testInputCorretto() throws ScannerException, SyntacticException, IOException {
		String path ="src/test/data/inputCorretto.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram tree = parser.parse();
		assertEquals(
				"[NodeDecl{id=NodeId{name='a', definition=null}, type=INT}]\n" +
						"[NodeAssign{id=NodeId{name='a', definition=null}, expr=NodeCost{value='5', type=INT}}]\n" +
						"[NodeDecl{id=NodeId{name='b', definition=null}, type=FLOAT}]\n" +
						"[NodeAssign{id=NodeId{name='b', definition=null}, expr=NodeBinOp{op=PLUS, left=NodeDeref{id=NodeId{name='a', definition=null}}, right=NodeCost{value='3.2', type=FLOAT}}}]\n" +
						"[NodePrint{id=NodeId{name='b', definition=null}}]\n",
				tree.toString());
	}
}
