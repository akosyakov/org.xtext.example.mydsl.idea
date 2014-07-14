package org.eclipse.xtext.psi;

import org.eclipse.emf.ecore.EObject;

import com.intellij.openapi.util.Key;

public interface PsiReferenceEObject extends PsiEObject {
	
	Key<EObject> XTEXT_EPROXY_KEY = new Key<EObject>("XTEXT_EPROXY_KEY");

	Key<EObject> XTEXT_ECONTEXT_KEY = new Key<EObject>("XTEXT_ECONTEXT_KEY");

	EObject getEProxy();

	EObject getEContext();

}
