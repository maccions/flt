package test;

import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.SyntacticException;
import scanner.Scanner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestParser {

    @Test
    public void test() {
        Scanner scanner = null;
        Scanner scanner2 = null;
        try {
            scanner = new Scanner("src/test/data/3.txt");
            scanner2 = new Scanner("src/test/data/4.txt");
        } catch (IOException e) {
            fail();
        }
        Parser parser = new Parser(scanner);
        Parser parser2 = new Parser(scanner2);
        assertDoesNotThrow(() -> parser.parseProg());
        assertThrows(SyntacticException.class, () -> parser2.parseProg());
    }
}
