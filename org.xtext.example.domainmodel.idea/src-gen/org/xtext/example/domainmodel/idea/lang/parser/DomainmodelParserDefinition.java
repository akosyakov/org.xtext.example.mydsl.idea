package org.xtext.example.domainmodel.idea.lang.parser;

import org.eclipse.xtext.idea.lang.parser.AbstractXtextParserDefinition;
import org.xtext.example.domainmodel.idea.lang.psi.impl.DomainmodelFileImpl;

import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;

public class DomainmodelParserDefinition extends AbstractXtextParserDefinition {
	
	public PsiFile createFile(FileViewProvider viewProvider) {
		return new DomainmodelFileImpl(viewProvider);
	}

}
