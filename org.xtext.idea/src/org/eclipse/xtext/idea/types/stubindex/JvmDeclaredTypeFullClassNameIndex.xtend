package org.eclipse.xtext.idea.types.stubindex

import com.google.inject.Inject
import com.google.inject.name.Named
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import org.eclipse.xtext.idea.lang.IXtextLanguage
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject

class JvmDeclaredTypeFullClassNameIndex extends StringStubIndexExtension<PsiJvmNamedEObject> {
	
	public static val CLASS_FQN = "java.class.fqn"
	
	@Inject
	@Named(CLASS_FQN)
	StubIndexKey<String, PsiJvmNamedEObject> key
	
	new(IXtextLanguage language) {
		language.injectMembers(this)
	}
	
	override getKey() {
		key
	}
	
}