package org.eclipse.xtext.common.types.xtext.ui.idea.lang.psi.stubindex

import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage

class RefactoringTestLanguagePsiNamedEObjectIndex extends PsiNamedEObjectIndex {

	new() {
		super(RefactoringTestLanguageLanguage.INSTANCE)
	}

}
