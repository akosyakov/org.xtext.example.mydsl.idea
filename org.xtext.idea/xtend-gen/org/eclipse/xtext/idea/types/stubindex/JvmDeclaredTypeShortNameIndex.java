package org.eclipse.xtext.idea.types.stubindex;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndexKey;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;

@SuppressWarnings("all")
public class JvmDeclaredTypeShortNameIndex extends StringStubIndexExtension<PsiJvmNamedEObject> {
  public final static String CLASS_SHORT_NAMES = "java.class.shortname";
  
  @Inject
  @Named(JvmDeclaredTypeShortNameIndex.CLASS_SHORT_NAMES)
  private StubIndexKey<String, PsiJvmNamedEObject> key;
  
  public JvmDeclaredTypeShortNameIndex(final IXtextLanguage language) {
    language.injectMembers(this);
  }
  
  public StubIndexKey<String, PsiJvmNamedEObject> getKey() {
    return this.key;
  }
}
