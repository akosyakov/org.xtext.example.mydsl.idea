package org.xtext.example.mydsl.idea.lang.parser;

import static org.xtext.example.mydsl.parser.antlr.internal.InternalMyDslParser.*;

import org.eclipse.xtext.generator.idea.TokenTypeProvider;
import org.xtext.example.mydsl.idea.lang.MyDslLanguage;

import com.google.inject.Singleton;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

@Singleton public class MyDslTokenTypeProvider implements TokenTypeProvider {

	private static final IElementType[] tokenTypes = new IElementType[tokenNames.length];
	
	static {
		for (int i = 0; i < tokenNames.length; i++) {
			tokenTypes[i] = new IndexedElementType(tokenNames[i], i, MyDslLanguage.INSTANCE);
		}
	}
	
	private static final TokenSet WHITESPACE_TOKENS = TokenSet.create(tokenTypes[RULE_WS]);
	private static final TokenSet COMMENT_TOKENS = TokenSet.create(tokenTypes[RULE_SL_COMMENT], tokenTypes[RULE_ML_COMMENT]);
	private static final TokenSet STRING_TOKENS = TokenSet.create(tokenTypes[RULE_STRING]);

    public int getAntlrType(IElementType iElementType) {
        return ((IndexedElementType)iElementType).getLocalIndex();
    }
    
    public IElementType getIElementType(int antlrType) {
    	return tokenTypes[antlrType];
    }

	@Override
	public TokenSet getWhitespaceTokens() {
		return WHITESPACE_TOKENS;
	}

	@Override
	public TokenSet getCommentTokens() {
		return COMMENT_TOKENS;
	}

	@Override
	public TokenSet getStringLiteralTokens() {
		return STRING_TOKENS;
	}

}
