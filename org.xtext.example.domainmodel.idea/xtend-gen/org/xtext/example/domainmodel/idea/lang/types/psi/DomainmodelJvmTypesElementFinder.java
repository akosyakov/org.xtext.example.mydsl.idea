package org.xtext.example.domainmodel.idea.lang.types.psi;

import com.intellij.openapi.project.Project;
import org.eclipse.xtext.idea.types.psi.JvmTypesElementFinder;
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

@SuppressWarnings("all")
public class DomainmodelJvmTypesElementFinder extends JvmTypesElementFinder {
  public DomainmodelJvmTypesElementFinder(final Project project) {
    super(DomainmodelLanguage.INSTANCE, project);
  }
}
