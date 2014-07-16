package org.xtext.example.mydsl.idea.lang.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Lexer;
import org.eclipse.xtext.idea.lang.parser.AbstractAntlrDelegatingIdeaLexer;
import org.xtext.example.mydsl.parser.antlr.internal.InternalMyDslLexer;

public class MyDslLexer extends AbstractAntlrDelegatingIdeaLexer {

	@Override
	public Lexer createAntlrLexer(String text) {
		return new InternalMyDslLexer(new ANTLRStringStream(text));
	}

}
