package org.eclipse.xtext.idea.types.psi

import com.google.inject.Inject
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElementFinder
import com.intellij.psi.search.GlobalSearchScope
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex
import org.eclipse.xtext.idea.lang.IXtextLanguage

class JvmTypesElementFinder extends PsiElementFinder {
	
	@Inject
	JvmDeclaredTypeFullClassNameIndex jvmDeclaredTypeFullClassNameIndex
	
	val Project project
	
	new(IXtextLanguage language, Project project) {
		language.injectMembers(this)
		this.project = project
	}
	
	override findClass(String qualifiedName, GlobalSearchScope scope) {
		findClasses(qualifiedName, scope).head
	}
	
	override findClasses(String qualifiedName, GlobalSearchScope scope) {
		val result = newArrayList
		for (namedEObject : jvmDeclaredTypeFullClassNameIndex.get(qualifiedName, project, scope)) {
			result += namedEObject.findClasses(qualifiedName) 	
		}
		result
	}
	
}