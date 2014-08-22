package org.eclipse.xtext.idea.types.access;

import com.google.common.base.Objects;
import com.intellij.openapi.project.Project;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.common.types.access.AbstractTypeProviderFactory;
import org.eclipse.xtext.common.types.access.IJvmTypeProvider;
import org.eclipse.xtext.common.types.access.impl.IndexedJvmTypeAccess;
import org.eclipse.xtext.idea.resource.impl.StubBasedResourceDescriptions;
import org.eclipse.xtext.idea.types.access.StubJvmTypeProvider;

@SuppressWarnings("all")
public class StubTypeProviderFactory extends AbstractTypeProviderFactory {
  public IJvmTypeProvider createTypeProvider(final ResourceSet resourceSet) {
    StubJvmTypeProvider _xblockexpression = null;
    {
      boolean _equals = Objects.equal(resourceSet, null);
      if (_equals) {
        throw new IllegalArgumentException("resourceSet may not be null.");
      }
      final Project project = StubBasedResourceDescriptions.ProjectAdapter.getProject(resourceSet);
      boolean _equals_1 = Objects.equal(project, null);
      if (_equals_1) {
        throw new IllegalArgumentException("project may not be null.");
      }
      IndexedJvmTypeAccess _indexedJvmTypeAccess = this.getIndexedJvmTypeAccess();
      _xblockexpression = new StubJvmTypeProvider(project, resourceSet, _indexedJvmTypeAccess);
    }
    return _xblockexpression;
  }
}