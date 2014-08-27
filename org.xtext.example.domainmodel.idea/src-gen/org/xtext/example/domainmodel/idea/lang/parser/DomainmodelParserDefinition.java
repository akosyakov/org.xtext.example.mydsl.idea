package org.xtext.example.domainmodel.idea.lang.parser;

import org.eclipse.xtext.idea.lang.parser.AbstractXtextParserDefinition;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmNamedEObjectImpl;
import org.xtext.example.domainmodel.idea.lang.psi.impl.DomainmodelFileImpl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;

public class DomainmodelParserDefinition extends AbstractXtextParserDefinition {
	
	public PsiFile createFile(FileViewProvider viewProvider) {
		return new DomainmodelFileImpl(viewProvider);
	}
	
	@Override
	public PsiElement createElement(ASTNode node) {
		if (getElementTypeProvider().getNamedObjectType().equals(node.getElementType())) {
			return new PsiJvmNamedEObjectImpl(node, getElementTypeProvider().getNameType());
		}
		return super.createElement(node);
	}

}
