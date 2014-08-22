package org.xtext.example.domainmodel.idea.lang.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Lexer;
import org.eclipse.xtext.idea.lang.parser.AbstractAntlrDelegatingIdeaLexer;
import org.xtext.example.domainmodel.parser.antlr.internal.InternalDomainmodelLexer;

public class DomainmodelLexer extends AbstractAntlrDelegatingIdeaLexer {

	@Override
	public Lexer createAntlrLexer(String text) {
		return new InternalDomainmodelLexer(new ANTLRStringStream(text));
	}

}
