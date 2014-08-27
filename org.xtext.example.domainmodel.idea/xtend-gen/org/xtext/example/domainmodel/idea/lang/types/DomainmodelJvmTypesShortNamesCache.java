package org.xtext.example.domainmodel.idea.lang.types;

import com.intellij.openapi.project.Project;
import org.eclipse.xtext.idea.types.JvmTypesShortNamesCache;
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

@SuppressWarnings("all")
public class DomainmodelJvmTypesShortNamesCache extends JvmTypesShortNamesCache {
  public DomainmodelJvmTypesShortNamesCache(final Project project) {
    super(DomainmodelLanguage.INSTANCE, project);
  }
}
