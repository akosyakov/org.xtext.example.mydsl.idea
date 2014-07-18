package org.eclipse.xtext.idea.resource.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.resource.IEObjectDescription;

public class StubEObjectDescription implements IEObjectDescription {

	private final EClass type;

	private final QualifiedName qualifiedName;

	private final PsiNamedEObject psiNamedEObject;

	public StubEObjectDescription(PsiNamedEObject psiNamedEObject) {
		this.psiNamedEObject = psiNamedEObject;
		this.qualifiedName = psiNamedEObject.getQualifiedName();
		this.type = psiNamedEObject.getEClass();
	}
	
	public PsiNamedEObject getPsiNamedEObject() {
		return psiNamedEObject;
	}

	public QualifiedName getName() {
		return qualifiedName;
	}

	public QualifiedName getQualifiedName() {
		return qualifiedName;
	}

	public EObject getEObjectOrProxy() {
		return psiNamedEObject.getEObject();
	}

	public URI getEObjectURI() {
		EObject objectOrProxy = getEObjectOrProxy();
		return ((InternalEObject) objectOrProxy).eProxyURI();
	}

	public EClass getEClass() {
		return type;
	}

	public String getUserData(String key) {
		return null;
	}

	public String[] getUserDataKeys() {
		return new String[0];
	}

}