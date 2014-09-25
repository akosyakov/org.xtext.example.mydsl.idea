package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.psi.search;

import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage;
import org.eclipse.xtext.idea.types.psi.search.JvmElementsReferencesSearch;

@SuppressWarnings("all")
public class RefactoringTestLanguageJvmElementsReferencesSearch extends JvmElementsReferencesSearch {
  public RefactoringTestLanguageJvmElementsReferencesSearch() {
    super(RefactoringTestLanguageLanguage.INSTANCE);
  }
}
