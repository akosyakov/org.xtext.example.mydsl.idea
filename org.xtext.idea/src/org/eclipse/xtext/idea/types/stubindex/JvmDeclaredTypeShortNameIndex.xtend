package org.eclipse.xtext.idea.types.stubindex

import com.google.inject.Inject
import com.google.inject.name.Named
import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import org.eclipse.xtext.idea.lang.IXtextLanguage
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject

class JvmDeclaredTypeShortNameIndex extends StringStubIndexExtension<PsiJvmNamedEObject> {
	
	public static val CLASS_SHORT_NAMES = "java.class.shortname"
	
	@Inject
	@Named(CLASS_SHORT_NAMES)
	StubIndexKey<String, PsiJvmNamedEObject> key
	
	new(IXtextLanguage language) {
		language.injectMembers(this)
	}
	
	override getKey() {
		key
	}

}