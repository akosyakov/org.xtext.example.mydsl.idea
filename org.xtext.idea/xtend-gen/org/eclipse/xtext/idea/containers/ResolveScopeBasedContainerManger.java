package org.eclipse.xtext.idea.containers;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.idea.containers.ResolveScopeBasedContainer;
import org.eclipse.xtext.idea.resource.impl.ProjectScopeBasedResourceDescriptions;
import org.eclipse.xtext.idea.resource.impl.PsiFileBasedResourceDescription;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class ResolveScopeBasedContainerManger implements IContainer.Manager {
  @Inject
  private Provider<ResolveScopeBasedContainer> resolveScopeBasedContainerProvider;
  
  public List<IContainer> getVisibleContainers(final IResourceDescription desc, final IResourceDescriptions resourceDescriptions) {
    List<IContainer> _xblockexpression = null;
    {
      final IContainer container = this.getContainer(desc, resourceDescriptions);
      boolean _equals = Objects.equal(container, null);
      if (_equals) {
        return CollectionLiterals.<IContainer>emptyList();
      }
      _xblockexpression = Collections.<IContainer>unmodifiableList(CollectionLiterals.<IContainer>newArrayList(container));
    }
    return _xblockexpression;
  }
  
  public IContainer getContainer(final IResourceDescription desc, final IResourceDescriptions resourceDescriptions) {
    ResolveScopeBasedContainer _xblockexpression = null;
    {
      boolean _isIndexing = this.isIndexing(resourceDescriptions);
      if (_isIndexing) {
        return null;
      }
      ResolveScopeBasedContainer _elvis = null;
      ResolveScopeBasedContainer _container = this.getContainer(desc);
      if (_container != null) {
        _elvis = _container;
      } else {
        URI _uRI = desc.getURI();
        ResolveScopeBasedContainer _container_1 = this.getContainer(resourceDescriptions, _uRI);
        _elvis = _container_1;
      }
      _xblockexpression = _elvis;
    }
    return _xblockexpression;
  }
  
  protected ResolveScopeBasedContainer getContainer(final IResourceDescriptions resourceDescriptions, final URI uri) {
    ResolveScopeBasedContainer _xifexpression = null;
    if ((resourceDescriptions instanceof ProjectScopeBasedResourceDescriptions)) {
      IResourceDescription _resourceDescription = ((ProjectScopeBasedResourceDescriptions)resourceDescriptions).getResourceDescription(uri);
      _xifexpression = this.getContainer(_resourceDescription);
    }
    return _xifexpression;
  }
  
  protected ResolveScopeBasedContainer getContainer(final IResourceDescription desc) {
    if ((desc instanceof PsiFileBasedResourceDescription)) {
      ResolveScopeBasedContainer _get = this.resolveScopeBasedContainerProvider.get();
      final Procedure1<ResolveScopeBasedContainer> _function = new Procedure1<ResolveScopeBasedContainer>() {
        public void apply(final ResolveScopeBasedContainer it) {
          BaseXtextFile _xtextFile = ((PsiFileBasedResourceDescription)desc).getXtextFile();
          GlobalSearchScope _resolveScope = _xtextFile.getResolveScope();
          it.setScope(_resolveScope);
        }
      };
      return ObjectExtensions.<ResolveScopeBasedContainer>operator_doubleArrow(_get, _function);
    }
    return null;
  }
  
  protected boolean isIndexing(final IResourceDescriptions resourceDescriptions) {
    boolean _xifexpression = false;
    if ((resourceDescriptions instanceof ProjectScopeBasedResourceDescriptions)) {
      return ((ProjectScopeBasedResourceDescriptions)resourceDescriptions).isIndexing();
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
}
