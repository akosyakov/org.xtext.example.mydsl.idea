package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types;

import com.intellij.openapi.project.Project;
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage;
import org.eclipse.xtext.idea.types.JvmTypesShortNamesCache;

@SuppressWarnings("all")
public class RefactoringTestLanguageJvmTypesShortNamesCache extends JvmTypesShortNamesCache {
  public RefactoringTestLanguageJvmTypesShortNamesCache(final Project project) {
    super(RefactoringTestLanguageLanguage.INSTANCE, project);
  }
}
