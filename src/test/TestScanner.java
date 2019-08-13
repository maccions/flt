package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Test;
import scanner.Scanner;
import scanner.ScannerException;

public class TestScanner {
	
	@Test
	public void inputNonCorretto() throws IOException {
		String path ="src/test/data/inputNonCorretto.txt";
		Scanner scanner = new Scanner(path);
		assertDoesNotThrow(() -> scanner.nextToken());
	}
	
	@Test
	public void inputNonCorretto2() throws IOException {
		String path ="src/test/data/provaFloatingPoint.txt";
		Scanner scanner = new Scanner(path);
		try {
			scanner.nextToken();
			assertEquals("<FNUM , r: 1 v: 0.23>", scanner.nextToken().toString());
			scanner.nextToken();
			assertEquals("<FNUM , r: 2 v: 0.123456>", scanner.nextToken().toString());
			scanner.nextToken();
			assertThrows(ScannerException.class, () -> scanner.nextToken());
		} catch (Exception e) {
			fail();
		}
	}
	
	@Test
	public void inputNonCorretto3() throws IOException {
		String path ="src/test/data/provaDoppioPunto.txt";
		Scanner scanner = new Scanner(path);
		assertThrows(ScannerException.class, () -> scanner.nextToken());
	}
	
	@Test
	public void inputNonCorretto4() throws IOException {
		String path ="src/test/data/provaMaiuscInterna.txt";
		Scanner scanner = new Scanner(path);
		assertThrows(ScannerException.class, () -> scanner.nextToken());
	}
	
	@Test
	public void inputNonValido() throws FileNotFoundException {
		assertThrows(FileNotFoundException.class, () -> new Scanner("src/main/java/test/fileNonEsistente.txt"));
	}

}
