package org.eclipse.xtext.idea.types

import com.google.inject.Inject
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiField
import com.intellij.psi.PsiMethod
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.util.ArrayUtil
import com.intellij.util.Processor
import com.intellij.util.containers.ContainerUtil
import com.intellij.util.containers.HashSet
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex
import org.eclipse.xtext.idea.lang.IXtextLanguage

class JvmTypesShortNamesCache extends PsiShortNamesCache {
	
	static final val PsiMethod[] NO_METHODS = #[]
    static final val PsiField[] NO_FIELDS = #[]
    
    @Inject
    JvmDeclaredTypeShortNameIndex jvmDeclaredTypeShortNameIndex
	
	val Project project
	
	new(IXtextLanguage language, Project project) {
		language.injectMembers(this)
		this.project = project
	}
	
	override getAllClassNames() {
		jvmDeclaredTypeShortNameIndex.getAllKeys(project)
	}
	
	override getAllClassNames(HashSet<String> dest) {
		dest += allClassNames
	}
	
	override getAllFieldNames() {
		ArrayUtil.EMPTY_STRING_ARRAY
	}
	
	override getAllFieldNames(HashSet<String> set) {
	}
	
	override getAllMethodNames() {
		ArrayUtil.EMPTY_STRING_ARRAY
	}
	
	override getAllMethodNames(HashSet<String> set) {
		
	}
	
	override getClassesByName(String name, GlobalSearchScope scope) {
		val result = newArrayList
		val namedEObjects = jvmDeclaredTypeShortNameIndex.get(name, project, scope)
		for (namedEObject : namedEObjects) {
			result += namedEObject.findClassesByName(name)
		}
		result
	}
	
	override getFieldsByName(String name, GlobalSearchScope scope) {
		NO_FIELDS
	}
	
	override getFieldsByNameIfNotMoreThan(String name, GlobalSearchScope scope, int maxCount) {
		NO_FIELDS
	}
	
	override getMethodsByName(String name, GlobalSearchScope scope) {
		NO_METHODS
	}
	
	override getMethodsByNameIfNotMoreThan(String name, GlobalSearchScope scope, int maxCount) {
		NO_METHODS
	}
	
	override processMethodsWithName(String name, GlobalSearchScope scope, Processor<PsiMethod> processor) {
		ContainerUtil.process(getMethodsByName(name, scope), processor)
	}
	
}