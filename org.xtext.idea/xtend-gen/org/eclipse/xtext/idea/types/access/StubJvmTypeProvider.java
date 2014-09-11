package org.eclipse.xtext.idea.types.access;

import com.google.common.base.Objects;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.JavaPsiFacadeEx;
import com.intellij.psi.impl.compiled.SignatureParsing;
import java.text.StringCharacterIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.access.IMirror;
import org.eclipse.xtext.common.types.access.impl.AbstractRuntimeJvmTypeProvider;
import org.eclipse.xtext.common.types.access.impl.ITypeFactory;
import org.eclipse.xtext.common.types.access.impl.IndexedJvmTypeAccess;
import org.eclipse.xtext.common.types.access.impl.URIHelperConstants;
import org.eclipse.xtext.idea.types.access.PsiClassFactory;
import org.eclipse.xtext.idea.types.access.PsiClassMirror;
import org.eclipse.xtext.idea.types.access.StubURIHelper;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class StubJvmTypeProvider extends AbstractRuntimeJvmTypeProvider {
  private final static String PRIMITIVES = URIHelperConstants.PRIMITIVES_URI.segment(0);
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final Project project;
  
  private final ITypeFactory<PsiClass, JvmDeclaredType> psiClassFactory;
  
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
      JavaPsiFacadeEx _instanceEx = JavaPsiFacadeEx.getInstanceEx(this.project);
      final PsiClass psiClass = _instanceEx.findClass(name);
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
      final String normalizedName = this.nozmalize(name);
      final IndexedJvmTypeAccess indexedJvmTypeAccess = this.getIndexedJvmTypeAccess();
      final URI resourceURI = this.uriHelper.createResourceURI(normalizedName);
      boolean _notEquals = (!Objects.equal(indexedJvmTypeAccess, null));
      if (_notEquals) {
        String _fragment = this.uriHelper.getFragment(normalizedName);
        final URI proxyURI = resourceURI.appendFragment(_fragment);
        ResourceSet _resourceSet = this.getResourceSet();
        final EObject candidate = indexedJvmTypeAccess.getIndexedJvmType(proxyURI, _resourceSet);
        if ((candidate instanceof JvmType)) {
          return ((JvmType)candidate);
        }
      }
      ResourceSet _resourceSet_1 = this.getResourceSet();
      final Resource result = _resourceSet_1.getResource(resourceURI, true);
      _xblockexpression = this.findTypeByClass(normalizedName, result);
    }
    return _xblockexpression;
  }
  
  protected String nozmalize(final String name) {
    try {
      String _xifexpression = null;
      boolean _startsWith = name.startsWith("[");
      if (_startsWith) {
        StringCharacterIterator _stringCharacterIterator = new StringCharacterIterator(name);
        _xifexpression = SignatureParsing.parseTypeString(_stringCharacterIterator);
      } else {
        _xifexpression = name;
      }
      return _xifexpression;
    } catch (Throwable _e) {
      throw Exceptions.sneakyThrow(_e);
    }
  }
  
  protected JvmType findTypeByClass(final String name, final Resource resource) {
    JvmType _xblockexpression = null;
    {
      final String fragment = this.uriHelper.getFragment(name);
      EObject _eObject = resource.getEObject(fragment);
      final JvmType result = ((JvmType) _eObject);
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
