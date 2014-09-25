package org.xtext.example.domainmodel.idea.lang.types.psi.search

import org.eclipse.xtext.idea.types.psi.search.JvmElementsReferencesSearch
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage

class DomainmodelJvmElementsReferencesSearch extends JvmElementsReferencesSearch {

	new() {
		super(DomainmodelLanguage.INSTANCE)
	}

}
