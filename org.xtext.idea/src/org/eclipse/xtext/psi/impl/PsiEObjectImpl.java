package org.eclipse.xtext.psi.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.psi.PsiEObject;

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;

public class PsiEObjectImpl<T extends StubElement> extends StubBasedPsiElementBase<T> implements PsiEObject {
	
	private final IElementType elementType;
	
	protected PsiEObjectImpl(T stub, IStubElementType nodeType) {
		super(stub, nodeType);
		this.elementType = nodeType;
	}

	public PsiEObjectImpl(ASTNode node) {
		super(node);
		this.elementType = node.getElementType();
	}
	
	public EClass getType() {
		EReference eReference = getEReference();
		if (eReference == null) {
			return null;
		}
		return eReference.getEReferenceType();
	}

	public EReference getEReference() {
		return getNode().getUserData(XTEXT_EREFERENCE_KEY);
	}

	public INode getINode() {
		return getNode().getUserData(XTEXT_NODE_KEY);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("PsiEObject");
		builder.append("(").append(elementType).append(")");
		EReference reference = getEReference();
		if (reference != null) {
			builder.append("(").append(reference.getName()).append(")");
		}
		EClass type = getType();
		if (type != null) {
			builder.append("(").append(type.getName()).append(")");
		}
		return builder.toString();
	}
	
}
