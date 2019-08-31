package parser;

import ast.*;
import scanner.Scanner;
import scanner.ScannerException;
import token.Token;
import token.TokenType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private Scanner scanner;

    public Parser(Scanner scanner) {
        this.scanner = scanner;
    }

    public NodeProgram parse() throws SyntacticException, ScannerException, IOException {
        return parseProg();
    }

    public NodeProgram parseProg() throws IOException, ScannerException, SyntacticException {
        List<NodeDecSt> nodeDecStList = new ArrayList<>();
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
                nodeDecStList = parseDSs(nodeDecStList);
                match(TokenType.EOF);
                return new NodeProgram(nodeDecStList);
            default:
                throw new SyntacticException(token);
        }
    }

    private List<NodeDecSt> parseDSs (List<NodeDecSt> nodeDecStList) throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case FLOAT:
            case INT:
                NodeDecl nodeDecl = parseDcl();
                nodeDecStList.add(nodeDecl);
                nodeDecStList = parseDSs(nodeDecStList);
                return nodeDecStList;
            case ID:
            case PRINT:
                NodeStm nodeStm = parseStm();
                nodeDecStList.add(nodeStm);
                nodeDecStList = parseDSs(nodeDecStList);
                return nodeDecStList;
            case EOF:
                return nodeDecStList;
            default:
                throw new SyntacticException(token);
        }
    }

    private NodeDecl parseDcl () throws SyntacticException, ScannerException, IOException {
        Token token=scanner.peekToken();
        String valore;
        switch (token.getTipo()) {
            case FLOAT:
                match(TokenType.FLOAT);
                valore = scanner.peekToken().getValore();
                match(TokenType.ID);
                return new NodeDecl(new NodeId(valore), LangType.FLOAT);
            case INT:
                match(TokenType.INT);
                valore = scanner.peekToken().getValore();
                match(TokenType.ID);
                return new NodeDecl(new NodeId(valore), LangType.INT);
            default:
                throw new SyntacticException(token);
        }
    }

    private NodeStm parseStm () throws SyntacticException, ScannerException, IOException {
        Token token=scanner.peekToken();
        String valore;
        switch (token.getTipo()) {
            case ID:
                valore = token.getValore();
                match(TokenType.ID);
                match(TokenType.ASSIGN);
                NodeExpr n = parseExp();
                return new NodeAssign(new NodeId(valore), n);
            case PRINT:
                match(TokenType.PRINT);
                valore = scanner.peekToken().getValore();
                match(TokenType.ID);
                return new NodePrint(new NodeId(valore));
            default:
                throw new SyntacticException(token);
        }
    }

    private NodeExpr parseExp() throws ScannerException, SyntacticException, IOException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case INUM:
            case FNUM:
            case ID:
                //parseTr();
                //parseExpP();
                return parseExpP(parseTr());
            default:
                throw new SyntacticException(token);
        }
    }

    private NodeExpr parseExpP(NodeExpr sinistro) throws SyntacticException, ScannerException, IOException {
        Token token=scanner.peekToken();
        NodeExpr destro;
        switch (token.getTipo()) {
            case PLUS:
                match(TokenType.PLUS);
                destro = parseTr();
                //parseTr();
                //parseExpP();
                return parseExpP(new NodeBinOp(LangOper.PLUS, sinistro, destro));
            case MINUS:
                match(TokenType.MINUS);
                destro = parseTr();
                //parseTr();
                //parseExpP();
                return parseExpP(new NodeBinOp(LangOper.MINUS, sinistro, destro));
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                return sinistro;
            default:
                throw new SyntacticException(token);
        }
    }

    private NodeExpr parseTr() throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        switch (token.getTipo()) {
            case INUM:
            case FNUM:
            case ID:
                //parseVal();
                //parseTrP();
                return parseTrP(parseVal());
            default:
                throw new SyntacticException(token);
        }
    }

    private NodeExpr parseTrP(NodeExpr sinistro) throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        NodeExpr destro;
        switch (token.getTipo()) {
            case TIMES:
                match(TokenType.TIMES);
                destro = parseVal();
                //parseVal();
                //parseTrP();
                return parseTrP(new NodeBinOp(LangOper.TIMES, sinistro, destro));
            case DIV:
                match(TokenType.DIV);
                destro = parseVal();
                return parseTrP(new NodeBinOp(LangOper.DIV, sinistro, destro));
            case PLUS:
            case MINUS:
            case FLOAT:
            case INT:
            case ID:
            case PRINT:
            case EOF:
                return sinistro;
            default:
                throw new SyntacticException(token);
        }
    }

    private NodeExpr parseVal () throws IOException, ScannerException, SyntacticException {
        Token token=scanner.peekToken();
        String val = token.getValore();
        switch (token.getTipo()) {
            case INUM:
                match(TokenType.INUM);
                return new NodeCost(val, LangType.INT);
            case FNUM:
                match(TokenType.FNUM);
                return new NodeCost(val, LangType.FLOAT);
            case ID:
                match(TokenType.ID);
                return new NodeDeref(new NodeId(val));
            default:
                throw new SyntacticException(token);
        }
    }

    public void match(TokenType type) throws IOException, ScannerException, SyntacticException {
        if(type.equals(scanner.peekToken().getTipo())) {
            scanner.nextToken();
        }
        else {
            throw new SyntacticException(null);
        }
    }
}
