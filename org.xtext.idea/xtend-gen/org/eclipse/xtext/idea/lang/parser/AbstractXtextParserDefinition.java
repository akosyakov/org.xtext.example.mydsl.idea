package org.eclipse.xtext.idea.lang.parser;

import com.google.inject.Inject;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import javax.inject.Provider;
import org.eclipse.xtext.generator.idea.TokenTypeProvider;
import org.eclipse.xtext.idea.lang.BaseXtextPsiParser;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;
import org.eclipse.xtext.psi.impl.PsiEObjectImpl;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl;
import org.eclipse.xtext.psi.impl.PsiReferenceEObjectImpl;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public abstract class AbstractXtextParserDefinition implements ParserDefinition {
  @Inject
  private IElementTypeProvider elementTypeProvider;
  
  @Inject
  private Provider<BaseXtextPsiParser> baseXtextPsiParserProvider;
  
  @Inject
  private Provider<Lexer> lexerProvider;
  
  @Inject
  private TokenTypeProvider tokenTypeProvider;
  
  @NotNull
  public Lexer createLexer(final Project project) {
    return this.lexerProvider.get();
  }
  
  public IFileElementType getFileNodeType() {
    return this.elementTypeProvider.getFileType();
  }
  
  @NotNull
  public TokenSet getWhitespaceTokens() {
    return this.tokenTypeProvider.getWhitespaceTokens();
  }
  
  @NotNull
  public TokenSet getCommentTokens() {
    return this.tokenTypeProvider.getCommentTokens();
  }
  
  @NotNull
  public TokenSet getStringLiteralElements() {
    return this.tokenTypeProvider.getStringLiteralTokens();
  }
  
  @NotNull
  public PsiParser createParser(final Project project) {
    return this.baseXtextPsiParserProvider.get();
  }
  
  public ParserDefinition.SpaceRequirements spaceExistanceTypeBetweenTokens(final ASTNode left, final ASTNode right) {
    return ParserDefinition.SpaceRequirements.MAY;
  }
  
  @NotNull
  public PsiElement createElement(final ASTNode node) {
    IStubElementType<? extends PsiNamedEObjectStub, ? extends PsiNamedEObject> _namedObjectType = this.elementTypeProvider.getNamedObjectType();
    IElementType _elementType = node.getElementType();
    boolean _equals = _namedObjectType.equals(_elementType);
    if (_equals) {
      IElementType _nameType = this.elementTypeProvider.getNameType();
      return new PsiNamedEObjectImpl(node, _nameType);
    }
    IElementType _crossReferenceType = this.elementTypeProvider.getCrossReferenceType();
    IElementType _elementType_1 = node.getElementType();
    boolean _equals_1 = _crossReferenceType.equals(_elementType_1);
    if (_equals_1) {
      return new PsiReferenceEObjectImpl<StubElement>(node);
    }
    return new PsiEObjectImpl<StubElement>(node);
  }
}
