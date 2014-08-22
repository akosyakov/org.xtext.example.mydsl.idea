package org.eclipse.xtext.idea.types.access;

import com.google.common.base.Objects;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.JavaPsiFacadeEx;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.access.IMirror;
import org.eclipse.xtext.common.types.access.impl.AbstractRuntimeJvmTypeProvider;
import org.eclipse.xtext.common.types.access.impl.IndexedJvmTypeAccess;
import org.eclipse.xtext.idea.types.access.PsiClassFactory;
import org.eclipse.xtext.idea.types.access.PsiClassMirror;
import org.eclipse.xtext.idea.types.access.StubURIHelper;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class StubJvmTypeProvider extends AbstractRuntimeJvmTypeProvider {
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final Project project;
  
  private final PsiClassFactory psiClassFactory;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  @Extension
  private final StubURIHelper uriHelper;
  
  protected StubJvmTypeProvider(final Project project, final ResourceSet resourceSet, final IndexedJvmTypeAccess indexedJvmTypeAccess) {
    super(resourceSet, indexedJvmTypeAccess);
    this.project = project;
    StubURIHelper _createStubURIHelper = this.createStubURIHelper();
    this.uriHelper = _createStubURIHelper;
    PsiClassFactory _createPsiClassFactory = this.createPsiClassFactory();
    this.psiClassFactory = _createPsiClassFactory;
  }
  
  public PsiClassFactory createPsiClassFactory() {
    return new PsiClassFactory(this.uriHelper);
  }
  
  protected StubURIHelper createStubURIHelper() {
    return new StubURIHelper();
  }
  
  protected IMirror createMirrorForFQN(final String name) {
    PsiClassMirror _xblockexpression = null;
    {
      final PsiClass psiClass = this.findClass(name);
      boolean _equals = Objects.equal(psiClass, null);
      if (_equals) {
        return null;
      }
      _xblockexpression = new PsiClassMirror(psiClass, this.psiClassFactory);
    }
    return _xblockexpression;
  }
  
  public JvmType findTypeByName(final String name) {
    return this.findTypeByName(name, true);
  }
  
  public JvmType findTypeByName(final String name, final boolean binaryNestedTypeDelimiter) {
    JvmType _xblockexpression = null;
    {
      final PsiClass psiClass = this.findClass(name);
      boolean _equals = Objects.equal(psiClass, null);
      if (_equals) {
        return this.tryFindTypeInIndex(name, binaryNestedTypeDelimiter);
      }
      _xblockexpression = this.findTypeByClass(psiClass);
    }
    return _xblockexpression;
  }
  
  protected JvmType tryFindTypeInIndex(final String name, final boolean binaryNestedTypeDelimiter) {
    JvmType _xblockexpression = null;
    {
      ResourceSet _resourceSet = this.getResourceSet();
      EList<Adapter> _eAdapters = _resourceSet.eAdapters();
      Adapter _adapter = EcoreUtil.getAdapter(_eAdapters, AbstractRuntimeJvmTypeProvider.TypeInResourceSetAdapter.class);
      final AbstractRuntimeJvmTypeProvider.TypeInResourceSetAdapter adapter = ((AbstractRuntimeJvmTypeProvider.TypeInResourceSetAdapter) _adapter);
      JvmType _xifexpression = null;
      boolean _notEquals = (!Objects.equal(adapter, null));
      if (_notEquals) {
        _xifexpression = adapter.tryFindTypeInIndex(name, this, binaryNestedTypeDelimiter);
      } else {
        _xifexpression = this.doTryFindInIndex(name, binaryNestedTypeDelimiter);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected PsiClass findClass(final String name) {
    JavaPsiFacadeEx _instanceEx = JavaPsiFacadeEx.getInstanceEx(this.project);
    return _instanceEx.findClass(name);
  }
  
  protected JvmType findTypeByClass(final PsiClass psiClass) {
    JvmType _xblockexpression = null;
    {
      final IndexedJvmTypeAccess indexedJvmTypeAccess = this.getIndexedJvmTypeAccess();
      final URI resourceURI = this.uriHelper.createResourceURI(psiClass);
      boolean _notEquals = (!Objects.equal(indexedJvmTypeAccess, null));
      if (_notEquals) {
        String _fragment = this.uriHelper.getFragment(psiClass);
        final URI proxyURI = resourceURI.appendFragment(_fragment);
        ResourceSet _resourceSet = this.getResourceSet();
        final EObject candidate = indexedJvmTypeAccess.getIndexedJvmType(proxyURI, _resourceSet);
        if ((candidate instanceof JvmType)) {
          return ((JvmType)candidate);
        }
      }
      ResourceSet _resourceSet_1 = this.getResourceSet();
      final Resource result = _resourceSet_1.getResource(resourceURI, true);
      _xblockexpression = this.findTypeByClass(psiClass, result);
    }
    return _xblockexpression;
  }
  
  protected JvmType findTypeByClass(final PsiClass psiClass, final Resource resource) {
    JvmType _xblockexpression = null;
    {
      final String fragment = this.uriHelper.getFragment(psiClass);
      EObject _eObject = resource.getEObject(fragment);
      final JvmType result = ((JvmType) _eObject);
      boolean _equals = Objects.equal(result, null);
      if (_equals) {
        throw new IllegalStateException("Resource has not been loaded");
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  @Pure
  public Project getProject() {
    return this.project;
  }
  
  @Pure
  public StubURIHelper getUriHelper() {
    return this.uriHelper;
  }
}
