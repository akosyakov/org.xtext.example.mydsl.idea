package org.xtext.example.domainmodel.idea.lang.types.stubindex;

import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex;
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

@SuppressWarnings("all")
public class DomainmodelJvmDeclaredTypeShortNameIndex extends JvmDeclaredTypeShortNameIndex {
  public DomainmodelJvmDeclaredTypeShortNameIndex() {
    super(DomainmodelLanguage.INSTANCE);
  }
}
