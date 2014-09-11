package org.eclipse.xtext.common.types.xtext.ui.idea.lang.psi.stubindex;

import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;

@SuppressWarnings("all")
public class RefactoringTestLanguagePsiNamedEObjectIndex extends PsiNamedEObjectIndex {
  public RefactoringTestLanguagePsiNamedEObjectIndex() {
    super(RefactoringTestLanguageLanguage.INSTANCE);
  }
}
