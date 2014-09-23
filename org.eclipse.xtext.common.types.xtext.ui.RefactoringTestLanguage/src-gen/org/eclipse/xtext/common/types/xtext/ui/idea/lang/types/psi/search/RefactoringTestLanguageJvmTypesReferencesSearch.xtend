package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.psi.search

import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage
import org.eclipse.xtext.idea.types.psi.search.JvmTypesReferencesSearch

class RefactoringTestLanguageJvmTypesReferencesSearch extends JvmTypesReferencesSearch {
	
	new() {
		super(RefactoringTestLanguageLanguage.INSTANCE)
	}
	
}