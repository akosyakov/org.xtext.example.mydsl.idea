package org.xtext.example.domainmodel.idea.lang.types.stubindex;

import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex;
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

@SuppressWarnings("all")
public class DomainmodelJvmDeclaredTypeFullClassNameIndex extends JvmDeclaredTypeFullClassNameIndex {
  public DomainmodelJvmDeclaredTypeFullClassNameIndex() {
    super(DomainmodelLanguage.INSTANCE);
  }
}
