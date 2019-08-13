package token;

public class Token {

    private int riga;
    private TokenType tipo;
    private String valore;

    public Token(int riga, TokenType tipo, String valore) {
        this.riga = riga;
        this.tipo = tipo;
        this.valore = valore;
    }

    public int getRiga() {
        return riga;
    }

    public void setRiga(int riga) {
        this.riga = riga;
    }

    public TokenType getTipo() {
        return tipo;
    }

    public void setTipo(TokenType tipo) {
        this.tipo = tipo;
    }

    public String getValore() {
        return valore;
    }

    public void setValore(String valore) {
        this.valore = valore;
    }

    @Override
    public String toString() {
        return "<" +
                tipo +
                " , r: " +
                riga +
                " v: " +
                (valore == null? "" : valore) +
                ">";
    }
}
