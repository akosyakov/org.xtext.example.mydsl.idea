package org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.psi;

import com.intellij.openapi.project.Project;
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage;
import org.eclipse.xtext.idea.types.psi.JvmTypesElementFinder;

@SuppressWarnings("all")
public class RefactoringTestLanguageJvmTypesElementFinder extends JvmTypesElementFinder {
  public RefactoringTestLanguageJvmTypesElementFinder(final Project project) {
    super(RefactoringTestLanguageLanguage.INSTANCE, project);
  }
}
