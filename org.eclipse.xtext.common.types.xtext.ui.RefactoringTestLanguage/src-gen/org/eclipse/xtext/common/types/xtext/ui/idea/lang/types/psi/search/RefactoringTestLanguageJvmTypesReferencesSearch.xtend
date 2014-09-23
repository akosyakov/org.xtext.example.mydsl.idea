package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.psi.search

import org.eclipse.xtext.idea.types.psi.search.JvmTypesReferencesSearch
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage

class RefactoringTestLanguageJvmTypesReferencesSearch extends JvmTypesReferencesSearch {

	new() {
		super(RefactoringTestLanguageLanguage.INSTANCE)
	}

}
