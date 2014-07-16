package org.eclipse.xtext.psi.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;

import com.intellij.lang.ASTFactory;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;

public class PsiNamedEObjectImpl extends PsiEObjectImpl<PsiNamedEObjectStub> implements PsiNamedEObject {
	
	private IElementType nameType;
	
	public PsiNamedEObjectImpl(PsiNamedEObjectStub stub, IStubElementType<PsiNamedEObjectStub, PsiNamedEObject> nodeType, IElementType nameType) {
		super(stub, nodeType);
		this.nameType = nameType;
	}
	
	public PsiNamedEObjectImpl(ASTNode node, IElementType nameType) {
		super(node);
		this.nameType = nameType;
	}

	@Override
	public String getName() {
		PsiNamedEObjectStub stub = getStub();
		if (stub != null) {
			return stub.getName();
		}
		PsiElement nameIdentifier = getNameIdentifier();
		if (nameIdentifier == null) {
			return null;
		}
		return nameIdentifier.getText();
	}
	
	@Override
	public EClass getType() {
		PsiNamedEObjectStub stub = getStub();
		if (stub != null) {
			return stub.getType();
		}
		return super.getType();
	}

	public PsiElement setName(String name) throws IncorrectOperationException {
		PsiElement nameIdentifier = getNameIdentifier();
		if (nameIdentifier != null) {
			ASTNode nameNode = nameIdentifier.getNode();
			ASTNode oldNode = nameNode.getFirstChildNode();
			LeafElement newChild = ASTFactory.leaf(oldNode.getElementType(), name);
			CodeEditUtil.setNodeGenerated(newChild, true);
			nameNode.replaceChild(oldNode, newChild);
		}
		return this;
	}

	public PsiElement getNameIdentifier() {
		return findChildByType(nameType);
	}

	@Override
	public int getTextOffset() {
		PsiElement nameIdentifier = getNameIdentifier();
		if (nameIdentifier == null) {
			return super.getTextOffset();
		}
		return nameIdentifier.getTextOffset();
	}

}