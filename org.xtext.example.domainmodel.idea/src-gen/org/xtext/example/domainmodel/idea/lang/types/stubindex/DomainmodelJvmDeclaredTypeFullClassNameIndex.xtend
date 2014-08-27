package org.xtext.example.domainmodel.idea.lang.types.stubindex

import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage

class DomainmodelJvmDeclaredTypeFullClassNameIndex extends JvmDeclaredTypeFullClassNameIndex {

	new() {
		super(DomainmodelLanguage.INSTANCE)
	}

}
