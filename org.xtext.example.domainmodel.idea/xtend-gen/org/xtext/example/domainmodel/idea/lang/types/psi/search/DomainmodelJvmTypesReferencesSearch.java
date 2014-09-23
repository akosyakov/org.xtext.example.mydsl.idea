package org.xtext.example.domainmodel.idea.lang.types.psi.search;

import org.eclipse.xtext.idea.types.psi.search.JvmTypesReferencesSearch;
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

@SuppressWarnings("all")
public class DomainmodelJvmTypesReferencesSearch extends JvmTypesReferencesSearch {
  public DomainmodelJvmTypesReferencesSearch() {
    super(DomainmodelLanguage.INSTANCE);
  }
}
