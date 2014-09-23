package org.eclipse.xtext.common.types.xtext.ui.idea.lang.parser;

import org.eclipse.xtext.common.types.xtext.ui.idea.lang.psi.impl.RefactoringTestLanguageFileImpl;
import org.eclipse.xtext.idea.lang.parser.AbstractXtextParserDefinition;

import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;

public class RefactoringTestLanguageParserDefinition extends AbstractXtextParserDefinition {

	public PsiFile createFile(FileViewProvider viewProvider) {
		return new RefactoringTestLanguageFileImpl(viewProvider);
	}

}
