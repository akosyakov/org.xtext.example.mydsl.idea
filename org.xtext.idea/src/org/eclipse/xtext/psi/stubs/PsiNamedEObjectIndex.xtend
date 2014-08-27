package org.eclipse.xtext.psi.stubs

import com.google.inject.Inject
import com.google.inject.name.Named
import com.intellij.openapi.project.Project
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.stubs.StubIndexKey
import org.eclipse.xtext.idea.lang.IXtextLanguage
import org.eclipse.xtext.psi.PsiNamedEObject
import org.jetbrains.annotations.NotNull

class PsiNamedEObjectIndex extends StringStubIndexExtension<PsiNamedEObject> {
	
	public static val EOBJECT_NAME = "eobject.name"

	@Inject
	@Named(EOBJECT_NAME)
	StubIndexKey<String, PsiNamedEObject> key
	
	new(IXtextLanguage language) {
		language.injectMembers(this)
	}

	override getKey() {
		key
	}

	override get(@NotNull String key, @NotNull Project project, @NotNull GlobalSearchScope scope) {
		StubIndex.getElements(getKey(), key, project, scope, PsiNamedEObject)
	}
	
}