package test;

import org.junit.jupiter.api.Test;
import token.Token;
import token.TokenType;

import static org.junit.jupiter.api.Assertions.*;

public class TestToken {

	@Test
	public void testCreazioneToken() {
		Token token = new Token(0, TokenType.INT, "4");
		assertEquals("<INT , r: 0 v: 4>", token.toString());
	}
	
	@Test
	public void testCreazioneToken2() {
		Token token = new Token(0, TokenType.EOF, null);
		assertEquals("<EOF , r: 0 v: >", token.toString());
	}

}
