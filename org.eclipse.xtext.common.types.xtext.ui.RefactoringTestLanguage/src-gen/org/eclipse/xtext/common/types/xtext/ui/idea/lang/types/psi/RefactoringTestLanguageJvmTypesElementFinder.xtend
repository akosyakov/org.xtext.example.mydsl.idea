package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.psi

import com.intellij.openapi.project.Project
import org.eclipse.xtext.idea.types.psi.JvmTypesElementFinder
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage

class RefactoringTestLanguageJvmTypesElementFinder extends JvmTypesElementFinder {

	new(Project project) {
		super(RefactoringTestLanguageLanguage.INSTANCE, project)
	}

}
