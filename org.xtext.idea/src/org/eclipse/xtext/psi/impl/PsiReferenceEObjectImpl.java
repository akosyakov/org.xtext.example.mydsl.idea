package org.eclipse.xtext.psi.impl;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.psi.PsiEObjectReference;
import org.eclipse.xtext.psi.PsiReferenceEObject;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiReference;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;

public class PsiReferenceEObjectImpl<T extends StubElement> extends PsiEObjectImpl<T> implements PsiReferenceEObject {

	protected PsiReferenceEObjectImpl(T stub, IStubElementType nodeType) {
		super(stub, nodeType);
	}

	public PsiReferenceEObjectImpl(ASTNode node) {
		super(node);
	}

    @Override
    public PsiReference getReference() {
    	return new PsiEObjectReference(this, new TextRange(0, getTextLength()));
    }

	public Integer getIndex() {
		return getNode().getUserData(XTEXT_INDEX_KEY);
	}

	public EObject getEContext() {
		return getNode().getUserData(XTEXT_ECONTEXT_KEY);
	}

}
