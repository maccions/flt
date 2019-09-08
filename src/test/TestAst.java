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
				"<ID a ,INT>\n" +
						"<ID b ,FLOAT>\n",
				tree.toString());
	}

	@Test
	public void testDclAsignPrint() throws ScannerException, SyntacticException, IOException {
		String path ="src/test/data/dicAssignPrint.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		NodeProgram tree = parser.parse();
		assertEquals(
				"<ID a ,INT>\n" +
						"<ASSIGN a ,5>\n" +
						"<PRINT a>\n",
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
				"<ID a ,INT>\n" +
						"<ASSIGN a ,(2 PLUS 3)>\n",
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
				"<ID a ,INT>\n" +
						"<ASSIGN a ,5>\n" +
						"<ID b ,FLOAT>\n" +
						"<ASSIGN b ,(a PLUS 3.2)>\n" +
						"<PRINT b>\n",
				tree.toString());
	}
}
