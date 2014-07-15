package org.eclipse.xtext.psi.stubs;

import java.util.Collection;

import org.eclipse.xtext.psi.PsiNamedEObject;
import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.project.Project;
import com.intellij.psi.impl.search.JavaSourceFilterScope;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;

public class PsiNamedEObjectIndex extends StringStubIndexExtension<PsiNamedEObject> {

	public static final StubIndexKey<String, PsiNamedEObject> NAME_INDEX_KEY = StubIndexKey.createIndexKey("PsiNamedEObject.Name");

	public StubIndexKey<String, PsiNamedEObject> getKey() {
		return NAME_INDEX_KEY;
	}

	@Override
	public Collection<PsiNamedEObject> get(@NotNull final String key, @NotNull final Project project, @NotNull final GlobalSearchScope scope) {
		return StubIndex.getElements(getKey(), key, project, new JavaSourceFilterScope(scope), PsiNamedEObject.class);
	}

}
