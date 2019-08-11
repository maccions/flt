package parser;

import scanner.Scanner;
import scanner.ScannerException;
import token.Token;
import token.TokenType;

import java.io.IOException;

public class Parser {

    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public void parseProg() throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
                parseDSs();
                match(TokenType.EOF);
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    private void parseDSs () throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case FLOAT:
            case INT:
                parseDcl();
                parseDSs();
                return;
            case ID:
            case PRINT:
                parseStm();
                parseDSs();
                return;
            case EOF:
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    private void parseDcl () throws SyntacticException, ScannerException, IOException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case FLOAT:
                match(TokenType.FLOAT);
                match(TokenType.ID);
                return;
            case INT:
                match(TokenType.INT);
                match(TokenType.ID);
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    private void parseStm () throws SyntacticException, ScannerException, IOException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case ID:
                match(TokenType.ID);
                match(TokenType.ASSIGN);
                parseExp();
                return;
            case PRINT:
                match(TokenType.PRINT);
                match(TokenType.ID);
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    private void parseExp() throws ScannerException, SyntacticException, IOException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case INUM:
            case FNUM:
            case ID:
                parseTr();
                parseExpP();
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    private void parseExpP() throws SyntacticException, ScannerException, IOException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case PLUS:
                match(TokenType.PLUS);
                parseTr();
                parseExpP();
                return;
            case MINUS:
                match(TokenType.MINUS);
                parseTr();
                parseExpP();
                return;
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    private void parseTr() throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case INUM:
            case FNUM:
            case ID:
                parseVal();
                parseTrP();
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    private void parseTrP() throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case TIMES:
                match(TokenType.TIMES);
                parseVal();
                parseTrP();
                return;
            case DIV:
                match(TokenType.DIV);
                parseVal();
                parseTrP();
                return;
            case PLUS:
            case MINUS:
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    private void parseVal () throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case INUM:
                match(TokenType.INUM);
                return;
            case FNUM:
                match(TokenType.FNUM);
                return;
            case ID:
                match(TokenType.ID);
                return;
            default:
                throw new SyntacticException(scanner.peekToken());
        }
    }

    public void match(TokenType type) throws IOException, ScannerException, SyntacticException {
        if(type.equals(scanner.peekToken().getTipo())) {
            scanner.nextToken();
        }
        else {
            throw new SyntacticException(scanner.peekToken());
        }
    }
}