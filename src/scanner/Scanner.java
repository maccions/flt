package scanner;

import token.Token;
import token.TokenType;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Scanner {

    private PushbackReader buffer;
    private Token nextToken;
    private int riga;
    private List<Character> skipChar = Arrays.asList(' ', '\n', '\t', '\r', '\u001a', '\uffff');
    private List<TokenType> keywords = Arrays.asList(
            TokenType.PRINT,
            TokenType.FLOAT,
            TokenType.INT
    );

    public Scanner(String path) throws IOException {
        this.buffer = new PushbackReader(new FileReader(path));
        riga = 1;
    }


    public Token nextToken() throws ScannerException, IOException {
        if(nextToken == null) {
            nextToken = readToken();
        }
        Token t = nextToken;
        nextToken = null;
        return t;
    }

    public Token peekToken() throws IOException, ScannerException {
        if(nextToken == null) {
            nextToken = readToken();
        }
        return nextToken;
    }

    private Token readToken() throws ScannerException, IOException {
        if (buffer == null)
            throw new ScannerException();
        do {
            char c = readChar();
            if (c == '\n') {
                riga++;
            }
            if (c == '\u001a' || c == '\uffff') {
                consumeChar();
                buffer = null;
                return new Token(riga, TokenType.EOF, "EOF");
            }
            if (c >= '0' && c <= '9' || c == '.') {
                return scanNumber();
            }
            if (c == '+' || c == '-' || c == '\\' || c == '*' || c == '=') {
                return scanOp();
            }
            if (c >= 'a' && c <= 'z') {
                return scanId();
            }
            consumeChar();
        } while (true);
    }

    private char consumeChar() throws IOException {
        return (char) buffer.read();
    }

    private char readChar() throws IOException {
        char c = (char) buffer.read();
        buffer.unread(c);
        return c;
    }


    private Token scanOp() throws ScannerException, IOException {
        char c = consumeChar();
        if (!skipChar.contains(readChar())) {
            throw new ScannerException();
        }
        Token token = null;
        switch (c) {
            case '+':
                token = new Token(riga, TokenType.PLUS, "" + c);
                ;
                break;
            case '-':
                token = new Token(riga, TokenType.MINUS, "" + c);
                break;
            case '\\':
                token = new Token(riga, TokenType.DIV, "" + c);
                break;
            case '*':
                token = new Token(riga, TokenType.TIMES, "" + c);
                break;
            case '=':
                token = new Token(riga, TokenType.ASSIGN, "" + c);
                break;
            default:
                throw new ScannerException();
        }
        return token;
    }

    private Token scanNumber() throws ScannerException, IOException {
        StringBuilder sb = new StringBuilder();
        while (!skipChar.contains(readChar())) {
            sb.append(consumeChar());
        }
        Token token = null;
        if (sb.toString().matches(TokenType.INUM.getRegex())) {
            token = new Token(riga, TokenType.INUM, sb.toString());
        } else {
            if (sb.toString().matches(TokenType.FNUM.getRegex())) {
                if(sb.charAt(0) == '.') {
                    sb.insert(0, '0');
                }
                token = new Token(riga, TokenType.FNUM, sb.toString());
            } else {
                throw new ScannerException();
            }
        }
        return token;
    }

    private Token scanId() throws ScannerException, IOException {
        StringBuilder sb = new StringBuilder();
        while (!skipChar.contains(readChar())) {
            sb.append(consumeChar());
        }
        Token token = null;
        for (TokenType tmpTokenType : keywords) {
            if (sb.toString().matches(tmpTokenType.getRegex())) {
                token = new Token(riga, tmpTokenType, sb.toString());
                break;
            }
        }
        if (token == null) {
            if (sb.toString().matches(TokenType.ID.getRegex())) {
                token = new Token(riga, TokenType.ID, sb.toString());
            } else {
                throw new ScannerException();
            }
        }
        return token;
    }
}
