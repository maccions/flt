package token;

public enum TokenType {
    FLOAT (1, "^float$"),
    INT (2, "^int$"),
    PRINT(3, "^print$"),
    ID(4, "^[a-z]+$"),
    INUM(5, "^[0-9]+$"),
    FNUM(5, "^[0-9]*\\.[0-9]{1,5}$"),
    ASSIGN(6, "^=$"),
    PLUS(7, "^\\+$"),
    MINUS(8, "^\\-$"),
    TIMES(9, "^\\*$"),
    DIV(10, "^\\$"),
    EOF(11, "");

    private int code;
    private String regex;

    TokenType(int code, String regex) {
        this.code = code;
        this.regex = regex;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }
}
