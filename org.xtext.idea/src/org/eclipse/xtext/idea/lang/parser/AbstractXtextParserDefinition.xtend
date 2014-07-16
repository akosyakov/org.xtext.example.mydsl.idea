package org.eclipse.xtext.idea.lang.parser

import com.google.inject.Inject
import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import javax.inject.Provider
import org.eclipse.xtext.generator.idea.TokenTypeProvider
import org.eclipse.xtext.idea.lang.BaseXtextPsiParser
import org.eclipse.xtext.idea.lang.IElementTypeProvider
import org.eclipse.xtext.psi.impl.PsiEObjectImpl
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl
import org.eclipse.xtext.psi.impl.PsiReferenceEObjectImpl
import org.jetbrains.annotations.NotNull

abstract class AbstractXtextParserDefinition implements ParserDefinition {

	@Inject
	private IElementTypeProvider elementTypeProvider;
	
	@Inject
	private Provider<BaseXtextPsiParser> baseXtextPsiParserProvider;
	
	@Inject
	private Provider<Lexer> lexerProvider; 
	
	@Inject
	private TokenTypeProvider tokenTypeProvider;
	
	@NotNull
	override Lexer createLexer(Project project) {
		return lexerProvider.get();
	}

	override IFileElementType getFileNodeType() {
		return elementTypeProvider.getFileType();
	}

	@NotNull
	override TokenSet getWhitespaceTokens() {
		return tokenTypeProvider.getWhitespaceTokens();
	}

	@NotNull
	override TokenSet getCommentTokens() {
		return tokenTypeProvider.getCommentTokens();
	}

	@NotNull
	override TokenSet getStringLiteralElements() {
		return tokenTypeProvider.getStringLiteralTokens();
	}

	@NotNull
	override PsiParser createParser(Project project) {
		return baseXtextPsiParserProvider.get();
	}

	override SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
		return SpaceRequirements.MAY;
	}

	@NotNull
	override PsiElement createElement(ASTNode node) {
		if (elementTypeProvider.getNamedObjectType().equals(node.getElementType())) {
			return new PsiNamedEObjectImpl(node, elementTypeProvider.getNameType());
		}
		if (elementTypeProvider.getCrossReferenceType().equals(node.getElementType())) {
			return new PsiReferenceEObjectImpl(node);
		}
		return new PsiEObjectImpl(node);
	}	
}