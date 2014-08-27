package org.xtext.example.mydsl.idea.lang.psi.stubindex;

import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;
import org.xtext.example.mydsl.idea.lang.MyDslLanguage;

@SuppressWarnings("all")
public class MyDslPsiNamedEObjectIndex extends PsiNamedEObjectIndex {
  public MyDslPsiNamedEObjectIndex() {
    super(MyDslLanguage.INSTANCE);
  }
}
