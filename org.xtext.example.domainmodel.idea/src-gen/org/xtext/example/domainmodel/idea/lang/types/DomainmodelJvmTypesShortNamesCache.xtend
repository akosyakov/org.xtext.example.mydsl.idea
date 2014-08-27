package org.xtext.example.domainmodel.idea.lang.types

import com.intellij.openapi.project.Project
import org.eclipse.xtext.idea.types.JvmTypesShortNamesCache
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage

class DomainmodelJvmTypesShortNamesCache extends JvmTypesShortNamesCache {
	
	new(Project project) {
		super(DomainmodelLanguage.INSTANCE, project)
	}
	
}