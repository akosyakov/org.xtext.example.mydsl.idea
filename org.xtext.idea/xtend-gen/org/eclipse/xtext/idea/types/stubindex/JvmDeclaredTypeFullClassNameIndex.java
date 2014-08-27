package org.eclipse.xtext.idea.types.stubindex;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;

@SuppressWarnings("all")
public class JvmDeclaredTypeFullClassNameIndex extends StringStubIndexExtension<PsiJvmNamedEObject> {
  public final static String CLASS_FQN = "java.class.fqn";
  
  @Inject
  @Named(JvmDeclaredTypeFullClassNameIndex.CLASS_FQN)
  private StubIndexKey<String, PsiJvmNamedEObject> key;
  
  public JvmDeclaredTypeFullClassNameIndex(final IXtextLanguage language) {
    language.injectMembers(this);
  }
  
  public StubIndexKey<String, PsiJvmNamedEObject> getKey() {
    return this.key;
  }
}
