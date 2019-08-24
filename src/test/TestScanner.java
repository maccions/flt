package test;

import org.junit.jupiter.api.Test;
import scanner.Scanner;
import scanner.ScannerException;
import token.TokenType;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestScanner {

    @Test
    public void test() {
        assertThrows(Exception.class, () -> new Scanner(""));
        try {
            Scanner scanner;
            scanner = new Scanner("src/test/data/1.txt");
            while (scanner.nextToken().getTipo() != TokenType.EOF) {
            }
        } catch (ScannerException | IOException e) {
            fail();
        }
        assertTrue(true);
        assertThrows(ScannerException.class, () -> {
            Scanner s = new Scanner("src/test/data/2.txt");
            while (s.nextToken().getTipo() != TokenType.EOF) {
            }
        });
    }
}
