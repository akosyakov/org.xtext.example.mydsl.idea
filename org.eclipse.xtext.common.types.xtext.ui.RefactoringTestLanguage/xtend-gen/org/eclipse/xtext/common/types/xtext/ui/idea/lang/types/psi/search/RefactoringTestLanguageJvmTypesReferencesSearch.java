package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.psi.search;

import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage;
import org.eclipse.xtext.idea.types.psi.search.JvmTypesReferencesSearch;

@SuppressWarnings("all")
public class RefactoringTestLanguageJvmTypesReferencesSearch extends JvmTypesReferencesSearch {
  public RefactoringTestLanguageJvmTypesReferencesSearch() {
    super(RefactoringTestLanguageLanguage.INSTANCE);
  }
}
