package org.eclipse.xtext.common.types.xtext.ui.idea.lang.psi;

import com.intellij.psi.impl.PsiTreeChangeEventImpl;
import com.intellij.psi.util.PsiModificationTracker;
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage;
import org.eclipse.xtext.psi.BaseXtextCodeBlockModificationListener;

@SuppressWarnings("all")
public class RefactoringTestLanguageCodeBlockModificationListener extends BaseXtextCodeBlockModificationListener {
  public RefactoringTestLanguageCodeBlockModificationListener(final PsiModificationTracker psiModificationTracker) {
    super(RefactoringTestLanguageLanguage.INSTANCE, psiModificationTracker);
  }
  
  protected boolean hasJavaStructuralChanges(final PsiTreeChangeEventImpl event) {
    return true;
  }
}
