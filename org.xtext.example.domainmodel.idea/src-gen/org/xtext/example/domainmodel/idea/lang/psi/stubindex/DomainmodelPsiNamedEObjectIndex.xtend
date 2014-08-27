package org.xtext.example.domainmodel.idea.lang.psi.stubindex

import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage

class DomainmodelPsiNamedEObjectIndex extends PsiNamedEObjectIndex {
	
	new() {
		super(DomainmodelLanguage.INSTANCE)
	}
	
}