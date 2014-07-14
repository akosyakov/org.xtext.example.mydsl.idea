package org.xtext.example.mydsl.lang.parser;

import static org.xtext.example.mydsl.parser.antlr.internal.InternalMyDslParser.tokenNames;

import java.util.HashMap;
import java.util.Map;

import org.xtext.example.mydsl.lang.MyDslLanguage;
import org.xtext.example.mydsl.parser.antlr.internal.InternalMyDslParser;

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

	public static final TokenSet COMMENTS = TokenSet.create(tokenTypes[InternalMyDslParser.RULE_SL_COMMENT],
			tokenTypes[InternalMyDslParser.RULE_ML_COMMENT]);
	
	public static final TokenSet LINE_COMMENTS = TokenSet.create(tokenTypes[InternalMyDslParser.RULE_SL_COMMENT]);
	
	public static final TokenSet BLOCK_COMMENTS = TokenSet.create(tokenTypes[InternalMyDslParser.RULE_ML_COMMENT]);

	public static final TokenSet WHITESPACES = TokenSet.create(tokenTypes[InternalMyDslParser.RULE_WS]);

	public static final TokenSet STRINGS = TokenSet.create(tokenTypes[InternalMyDslParser.RULE_STRING]);

}
