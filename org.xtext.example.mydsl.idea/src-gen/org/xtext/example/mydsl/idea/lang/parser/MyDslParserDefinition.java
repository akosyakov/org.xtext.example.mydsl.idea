package org.xtext.example.mydsl.idea.lang.parser;

import org.eclipse.xtext.idea.lang.BaseXtextPsiParser;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.psi.impl.PsiEObjectImpl;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl;
import org.eclipse.xtext.psi.impl.PsiReferenceEObjectImpl;
import org.jetbrains.annotations.NotNull;
import org.xtext.example.mydsl.idea.lang.MyDslLanguage;
import org.xtext.example.mydsl.idea.lang.psi.impl.MyDslFileImpl;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;

public class MyDslParserDefinition implements ParserDefinition {
	
	@Inject
	private IElementTypeProvider elementTypeProvider;
	
	@Inject
	private Provider<BaseXtextPsiParser> baseXtextPsiParserProvider; 
	
	public MyDslParserDefinition() {
		MyDslLanguage.INSTANCE.injectMembers(this);
	}

	@NotNull
	public Lexer createLexer(Project project) {
		return new MyDslLexer();
	}

	public IFileElementType getFileNodeType() {
		return elementTypeProvider.getFileType();
	}

	@NotNull
	public TokenSet getWhitespaceTokens() {
		return MyDslTokenTypes.WHITESPACES;
	}

	@NotNull
	public TokenSet getCommentTokens() {
		return MyDslTokenTypes.COMMENTS;
	}

	@NotNull
	public TokenSet getStringLiteralElements() {
		return MyDslTokenTypes.STRINGS;
	}

	@NotNull
	public PsiParser createParser(Project project) {
		return baseXtextPsiParserProvider.get();
	}

	public PsiFile createFile(FileViewProvider viewProvider) {
		return new MyDslFileImpl(viewProvider);
	}

	public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
		return SpaceRequirements.MAY;
	}

	@NotNull
	public PsiElement createElement(ASTNode node) {
		if (elementTypeProvider.getNamedObjectType().equals(node.getElementType())) {
			return new PsiNamedEObjectImpl(node, elementTypeProvider.getNameType());
		}
		if (elementTypeProvider.getCrossReferenceType().equals(node.getElementType())) {
			return new PsiReferenceEObjectImpl(node);
		}
		return new PsiEObjectImpl(node);
	}

}
