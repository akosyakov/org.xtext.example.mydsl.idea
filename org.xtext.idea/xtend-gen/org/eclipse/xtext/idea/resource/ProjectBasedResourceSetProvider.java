package org.eclipse.xtext.idea.resource;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.intellij.openapi.project.Project;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.idea.resource.IResourceSetProvider;
import org.eclipse.xtext.idea.resource.ProjectAdapter;

@Singleton
@SuppressWarnings("all")
public class ProjectBasedResourceSetProvider implements IResourceSetProvider {
  @Inject
  private Provider<ResourceSet> resourceSetProvider;
  
  public ResourceSet get(final Object context) {
    ResourceSet _xblockexpression = null;
    {
      final ResourceSet resourceSet = this.resourceSetProvider.get();
      if ((context instanceof Project)) {
        EList<Adapter> _eAdapters = resourceSet.eAdapters();
        ProjectAdapter _projectAdapter = new ProjectAdapter(((Project)context));
        _eAdapters.add(_projectAdapter);
      }
      _xblockexpression = resourceSet;
    }
    return _xblockexpression;
  }
}
