package org.xtext.example.domainmodel.idea.lang.psi.stubindex;

import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

@SuppressWarnings("all")
public class DomainmodelPsiNamedEObjectIndex extends PsiNamedEObjectIndex {
  public DomainmodelPsiNamedEObjectIndex() {
    super(DomainmodelLanguage.INSTANCE);
  }
}
