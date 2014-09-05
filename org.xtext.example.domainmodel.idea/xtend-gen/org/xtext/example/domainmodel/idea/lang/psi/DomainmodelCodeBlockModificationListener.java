package org.xtext.example.domainmodel.idea.lang.psi;

import com.intellij.psi.impl.PsiTreeChangeEventImpl;
import com.intellij.psi.util.PsiModificationTracker;
import org.eclipse.xtext.psi.BaseXtextCodeBlockModificationListener;
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

@SuppressWarnings("all")
public class DomainmodelCodeBlockModificationListener extends BaseXtextCodeBlockModificationListener {
  public DomainmodelCodeBlockModificationListener(final PsiModificationTracker psiModificationTracker) {
    super(DomainmodelLanguage.INSTANCE, psiModificationTracker);
  }
  
  protected boolean hasJavaStructuralChanges(final PsiTreeChangeEventImpl event) {
    return true;
  }
}
