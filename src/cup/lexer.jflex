/*
zona codice utente
viene copiato prima della classe lexer, serve per import e package da mettere prima della classe che produce (Lexer)
*/


package cup.example;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;
import java.lang.*;
import java.io.InputStreamReader;

%%

/*
zona opzioni
*/

%class Lexer    //come chiamare le classi
%implements sym
%public
%unicode
%line   //init contatore righe
%column //come line ma per colenne
%cup //il codice generato è compatibile con cup
%char


/*
codice messo all'inizio della nuova classe, serve per dichiarare metodi e comportamenti per le regole della grammatica
*/
%{
	
    public Lexer(ComplexSymbolFactory sf, java.io.InputStream is){
		this(is);
        symbolFactory = sf;
    }
	public Lexer(ComplexSymbolFactory sf, java.io.Reader reader){
		this(reader);
        symbolFactory = sf;
    }
    
    private StringBuffer sb;
    private ComplexSymbolFactory symbolFactory;
    private int csline,cscolumn;

    public Symbol symbol(String name, int code){
		return symbolFactory.newSymbol(name, code,
						new Location(yyline+1,yycolumn+1, yychar), // -yylength()
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength())
				);
    }
    public Symbol symbol(String name, int code, String lexem){
	return symbolFactory.newSymbol(name, code, 
						new Location(yyline+1, yycolumn +1, yychar), 
						new Location(yyline+1,yycolumn+yylength(), yychar+yylength()), lexem);
    }
    
    protected void emit_warning(String message){
    	System.out.println("scanner warning: " + message + " at : 2 "+ 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
    
    protected void emit_error(String message){
    	System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline+1) + " " + (yycolumn+1) + " " + yychar);
    }
%}


//pattern che identificano i token

Newline    = \r | \n | \r\n
Whitespace = [ \t\f] | {Newline}
INUM       = [0-9]+
FNUM	   = {INUM}+\.[0-9]+
CONV       = \.[0-9]+       //nel caso di float che iniziano con .
ID 	 	   = [a-z]+

%eofval{
    return symbolFactory.newSymbol("EOF",sym.EOF);
%eofval}

%state CODESEG

%%

/*
regole lessicali
serve per indicare caratteri
posso mettere ad esempio int senza dichiararlo sopra perche funziona tramite regex, solo che dichiararli lo rende più "leggibile"
*/

<YYINITIAL> {
 {Whitespace}  { }  
  "int"	   	   { return symbolFactory.newSymbol("INT", INT); }
  "float"	   { return symbolFactory.newSymbol("FLOAT", FLOAT); }
  "print"	   { return symbolFactory.newSymbol("PRINT", PRINT); }
  "+"          { return symbolFactory.newSymbol("PLUS", PLUS); }
  "-"          { return symbolFactory.newSymbol("MINUS", MINUS); }
  "*"          { return symbolFactory.newSymbol("TIMES", TIMES); }
  "/"          { return symbolFactory.newSymbol("DIVIDE", DIVIDE); }
  "="          { return symbolFactory.newSymbol("ASSIGN", ASSIGN); }
  {INUM}       { return symbolFactory.newSymbol("INUM", INUM, yytext()); }
  {CONV}       { return symbolFactory.newSymbol("CONV", CONV, yytext()); }
  {FNUM}       { return symbolFactory.newSymbol("FNUM", FNUM, yytext()); }
  {ID}         { return symbolFactory.newSymbol("ID", ID, yytext()); }
}

// error fallback
.|\n          { emit_warning("Unrecognized character '" +yytext()+"' -- ignored"); }
