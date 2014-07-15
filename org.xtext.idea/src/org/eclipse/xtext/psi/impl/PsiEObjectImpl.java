package org.eclipse.xtext.psi.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.psi.PsiEObject;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;

public class PsiEObjectImpl<T extends StubElement> extends StubBasedPsiElementBase<T> implements PsiEObject {

	protected PsiEObjectImpl(T stub, IStubElementType nodeType) {
		super(stub, nodeType);
	}

	public PsiEObjectImpl(ASTNode node) {
		super(node);
	}
	
	public EClass getType() {
		return getEReference().eClass();
	}

	public EReference getEReference() {
		return getNode().getUserData(XTEXT_EREFERENCE_KEY);
	}

	public INode getINode() {
		return getNode().getUserData(XTEXT_NODE_KEY);
	}
	
}
