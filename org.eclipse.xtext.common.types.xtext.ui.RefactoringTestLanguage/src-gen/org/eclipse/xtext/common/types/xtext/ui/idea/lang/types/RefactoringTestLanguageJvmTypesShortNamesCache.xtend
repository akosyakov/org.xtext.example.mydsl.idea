package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types

import com.intellij.openapi.project.Project
import org.eclipse.xtext.idea.types.JvmTypesShortNamesCache
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage

class RefactoringTestLanguageJvmTypesShortNamesCache extends JvmTypesShortNamesCache {

	new(Project project) {
		super(RefactoringTestLanguageLanguage.INSTANCE, project)
	}

}
