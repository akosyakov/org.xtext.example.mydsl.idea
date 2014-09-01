package org.eclipse.xtext.idea.types;

import com.google.common.base.Objects;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.idea.lang.parser.AbstractXtextParserDefinition;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmNamedEObjectImpl;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;

@SuppressWarnings("all")
public abstract class AbstractJvmTypesParserDefinition extends AbstractXtextParserDefinition {
  public PsiElement createElement(final ASTNode node) {
    IElementTypeProvider _elementTypeProvider = this.getElementTypeProvider();
    IStubElementType<? extends PsiNamedEObjectStub, ? extends PsiNamedEObject> _namedObjectType = _elementTypeProvider.getNamedObjectType();
    IElementType _elementType = node.getElementType();
    boolean _equals = Objects.equal(_namedObjectType, _elementType);
    if (_equals) {
      IElementTypeProvider _elementTypeProvider_1 = this.getElementTypeProvider();
      IElementType _nameType = _elementTypeProvider_1.getNameType();
      return new PsiJvmNamedEObjectImpl(node, _nameType);
    }
    return super.createElement(node);
  }
}
