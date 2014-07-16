package org.xtext.example.mydsl.idea.lang.parser;

import org.eclipse.xtext.idea.lang.parser.AbstractXtextParserDefinition;
import org.xtext.example.mydsl.idea.lang.MyDslLanguage;
import org.xtext.example.mydsl.idea.lang.psi.impl.MyDslFileImpl;

import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiFile;

public class MyDslParserDefinition extends AbstractXtextParserDefinition {
	
	public MyDslParserDefinition() {
		MyDslLanguage.INSTANCE.injectMembers(this);
	}

	public PsiFile createFile(FileViewProvider viewProvider) {
		return new MyDslFileImpl(viewProvider);
	}

}
