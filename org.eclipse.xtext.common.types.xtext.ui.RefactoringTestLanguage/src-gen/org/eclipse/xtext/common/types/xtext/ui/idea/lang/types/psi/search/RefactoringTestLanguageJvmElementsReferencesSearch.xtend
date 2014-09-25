package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.psi.search

import org.eclipse.xtext.idea.types.psi.search.JvmElementsReferencesSearch
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage

class RefactoringTestLanguageJvmElementsReferencesSearch extends JvmElementsReferencesSearch {

	new() {
		super(RefactoringTestLanguageLanguage.INSTANCE)
	}

}
