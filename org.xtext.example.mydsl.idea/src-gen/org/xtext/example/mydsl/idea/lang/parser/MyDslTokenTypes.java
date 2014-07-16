package org.xtext.example.mydsl.idea.lang.parser;

import static org.xtext.example.mydsl.parser.antlr.internal.InternalMyDslParser.*;

import java.util.HashMap;
import java.util.Map;

import org.xtext.example.mydsl.idea.lang.MyDslLanguage;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

public abstract class MyDslTokenTypes {

	public static final IElementType[] tokenTypes = new IElementType[tokenNames.length];
	
	public static final Map<String, IElementType> nameToTypeMap = new HashMap<String, IElementType>();

	static {
		for (int i = 0; i < tokenNames.length; i++) {
			tokenTypes[i] = new IElementType(tokenNames[i], MyDslLanguage.INSTANCE);
			nameToTypeMap.put(tokenNames[i], tokenTypes[i]);
		}
	}

	public static final TokenSet COMMENTS = TokenSet.create(tokenTypes[RULE_SL_COMMENT],
			tokenTypes[RULE_ML_COMMENT]);
	
	public static final TokenSet LINE_COMMENTS = TokenSet.create(tokenTypes[RULE_SL_COMMENT]);
	
	public static final TokenSet BLOCK_COMMENTS = TokenSet.create(tokenTypes[RULE_ML_COMMENT]);

	public static final TokenSet WHITESPACES = TokenSet.create(tokenTypes[RULE_WS]);

	public static final TokenSet STRINGS = TokenSet.create(tokenTypes[RULE_STRING]);

}
