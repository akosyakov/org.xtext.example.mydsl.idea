package org.eclipse.xtext.idea.resource.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.intellij.psi.PsiNamedElement;

public class StubEObjectDescription implements IEObjectDescription {

	private final PsiNamedEObject psiNamedEObject;

	public StubEObjectDescription(PsiNamedEObject psiNamedEObject) {
		this.psiNamedEObject = psiNamedEObject;
	}

	public PsiNamedElement getPsiNamedEObject() {
		return psiNamedEObject;
	}

	public QualifiedName getName() {
		return getQualifiedName();
	}

	public QualifiedName getQualifiedName() {
		return psiNamedEObject.getQualifiedName();
	}

	public EObject getEObjectOrProxy() {
		return psiNamedEObject.getEObject();
	}

	public URI getEObjectURI() {
		EObject objectOrProxy = getEObjectOrProxy();
		return ((InternalEObject) objectOrProxy).eProxyURI();
	}

	public EClass getEClass() {
		return getEObjectOrProxy().eClass();
	}

	public String getUserData(String key) {
		return null;
	}

	public String[] getUserDataKeys() {
		return new String[0];
	}

}