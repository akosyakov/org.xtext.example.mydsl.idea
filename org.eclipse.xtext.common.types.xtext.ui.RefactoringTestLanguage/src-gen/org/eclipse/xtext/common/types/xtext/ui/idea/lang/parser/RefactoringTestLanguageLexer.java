package org.eclipse.xtext.common.types.xtext.ui.idea.lang.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.Lexer;
import org.eclipse.xtext.idea.lang.parser.AbstractAntlrDelegatingIdeaLexer;
import org.eclipse.xtext.common.types.xtext.ui.parser.antlr.internal.InternalRefactoringTestLanguageLexer;

public class RefactoringTestLanguageLexer extends AbstractAntlrDelegatingIdeaLexer {

	@Override
	public Lexer createAntlrLexer(String text) {
		return new InternalRefactoringTestLanguageLexer(new ANTLRStringStream(text));
	}

}
