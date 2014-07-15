package org.eclipse.xtext.psi.stubs;

import org.eclipse.xtext.psi.PsiNamedEObject;

import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;

public class PsiNamedEObjectIndex extends StringStubIndexExtension<PsiNamedEObject> {
	
	public static final StubIndexKey<String, PsiNamedEObject> NAME_INDEX_KEY = StubIndexKey.createIndexKey("PsiNamedEObject.Name");

	public StubIndexKey<String, PsiNamedEObject> getKey() {
		return NAME_INDEX_KEY;
	}

}
