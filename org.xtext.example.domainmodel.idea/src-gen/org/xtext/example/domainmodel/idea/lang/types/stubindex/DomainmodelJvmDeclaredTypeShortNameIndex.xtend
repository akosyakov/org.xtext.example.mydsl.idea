package org.xtext.example.domainmodel.idea.lang.types.stubindex

import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage

class DomainmodelJvmDeclaredTypeShortNameIndex extends JvmDeclaredTypeShortNameIndex {
	
	new() {
		super(DomainmodelLanguage.INSTANCE)
	}
	
}