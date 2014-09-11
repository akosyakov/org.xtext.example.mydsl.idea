package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.stubindex

import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage

class RefactoringTestLanguageJvmDeclaredTypeFullClassNameIndex extends JvmDeclaredTypeFullClassNameIndex {

	new() {
		super(RefactoringTestLanguageLanguage.INSTANCE)
	}

}
