package org.eclipse.xtext.psi.stubs;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import java.util.Collection;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("all")
public class PsiNamedEObjectIndex extends StringStubIndexExtension<PsiNamedEObject> {
  public final static String EOBJECT_NAME = "eobject.name";
  
  @Inject
  @Named(PsiNamedEObjectIndex.EOBJECT_NAME)
  private StubIndexKey<String, PsiNamedEObject> key;
  
  public PsiNamedEObjectIndex(final IXtextLanguage language) {
    language.injectMembers(this);
  }
  
  public StubIndexKey<String, PsiNamedEObject> getKey() {
    return this.key;
  }
  
  public Collection<PsiNamedEObject> get(@NotNull final String key, @NotNull final Project project, @NotNull final GlobalSearchScope scope) {
    StubIndexKey<String, PsiNamedEObject> _key = this.getKey();
    return StubIndex.<String, PsiNamedEObject>getElements(_key, key, project, scope, PsiNamedEObject.class);
  }
}
