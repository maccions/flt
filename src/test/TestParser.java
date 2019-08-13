package test;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import scanner.Scanner;

import parser.Parser;
import parser.SyntacticException;
import scanner.ScannerException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestParser {

	private Parser parser;
	private Scanner scanner;

	@BeforeEach
	public void setUp() throws IOException {
		String path ="src/test/data/sintassiScorretta.txt";
		scanner = new Scanner(path);
		parser = new Parser(scanner);
	}

	@Test
	public void throwsEccezione() throws IOException, ScannerException, SyntacticException {
		assertThrows(SyntacticException.class, () -> parser.parse());
	}

	@Test
	public void testIntegrazione() throws IOException, ScannerException, SyntacticException {
		String path ="src/test/data/inputCorretto.txt";
		Scanner scanner = new Scanner(path);
		Parser parser = new Parser(scanner);
		parser.parse();
	}
	
}
