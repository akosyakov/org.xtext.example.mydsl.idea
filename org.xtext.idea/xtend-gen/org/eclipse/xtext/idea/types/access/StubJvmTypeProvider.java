package org.eclipse.xtext.idea.types.access;

import com.google.common.base.Objects;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.JavaPsiFacadeEx;
import com.intellij.psi.impl.compiled.SignatureParsing;
import java.text.StringCharacterIterator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.access.IMirror;
import org.eclipse.xtext.common.types.access.impl.AbstractJvmTypeProvider;
import org.eclipse.xtext.common.types.access.impl.AbstractRuntimeJvmTypeProvider;
import org.eclipse.xtext.common.types.access.impl.ITypeFactory;
import org.eclipse.xtext.common.types.access.impl.IndexedJvmTypeAccess;
import org.eclipse.xtext.common.types.access.impl.URIHelperConstants;
import org.eclipse.xtext.idea.types.access.PsiClassFactory;
import org.eclipse.xtext.idea.types.access.PsiClassMirror;
import org.eclipse.xtext.idea.types.access.StubURIHelper;
import org.eclipse.xtext.util.Strings;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
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
  
  public JvmType findTypeByName(final String name) {
    return this.doFindTypeByName(name, false);
  }
  
  public JvmType findTypeByName(final String name, final boolean binaryNestedTypeDelimiter) {
    JvmType _xblockexpression = null;
    {
      JvmType result = this.doFindTypeByName(name, false);
      boolean _or = false;
      boolean _notEquals = (!Objects.equal(result, null));
      if (_notEquals) {
        _or = true;
      } else {
        boolean _isBinaryNestedTypeDelimiter = this.isBinaryNestedTypeDelimiter(name, binaryNestedTypeDelimiter);
        _or = _isBinaryNestedTypeDelimiter;
      }
      if (_or) {
        return result;
      }
      final AbstractJvmTypeProvider.ClassNameVariants nameVariants = new AbstractJvmTypeProvider.ClassNameVariants(name);
      while ((Objects.equal(result, null) && nameVariants.hasNext())) {
        {
          final String nextVariant = nameVariants.next();
          JvmType _doFindTypeByName = this.doFindTypeByName(nextVariant, true);
          result = _doFindTypeByName;
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public JvmType doFindTypeByName(final String name, final boolean traverseNestedTypes) {
    JvmType _xblockexpression = null;
    {
      ProgressIndicatorProvider.checkCanceled();
      final String normalizedName = this.normalize(name);
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
      ProgressIndicatorProvider.checkCanceled();
      ResourceSet _resourceSet_1 = this.getResourceSet();
      final Resource result = _resourceSet_1.getResource(resourceURI, true);
      _xblockexpression = this.findTypeByClass(normalizedName, result, traverseNestedTypes);
    }
    return _xblockexpression;
  }
  
  protected String normalize(final String name) {
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
  
  protected JvmType findTypeByClass(final String name, final Resource resource, final boolean traverseNestedTypes) {
    final String fragment = this.uriHelper.getFragment(name);
    EObject _eObject = resource.getEObject(fragment);
    final JvmType result = ((JvmType) _eObject);
    boolean _or = false;
    boolean _notEquals = (!Objects.equal(result, null));
    if (_notEquals) {
      _or = true;
    } else {
      _or = (!traverseNestedTypes);
    }
    if (_or) {
      return result;
    }
    EList<EObject> _contents = resource.getContents();
    final EObject rootType = IterableExtensions.<EObject>head(_contents);
    if ((rootType instanceof JvmDeclaredType)) {
      URI _uRI = resource.getURI();
      final String rootTypeName = _uRI.segment(1);
      int _length = rootTypeName.length();
      int _plus = (_length + 1);
      final String nestedTypeName = fragment.substring(_plus);
      final List<String> segments = Strings.split(nestedTypeName, "$");
      return this.findNestedType(((JvmDeclaredType)rootType), segments, 0);
    }
    return null;
  }
  
  protected IMirror createMirrorForFQN(final String name) {
    PsiClassMirror _xblockexpression = null;
    {
      JavaPsiFacadeEx _instanceEx = JavaPsiFacadeEx.getInstanceEx(this.project);
      final PsiClass psiClass = _instanceEx.findClass(name);
      boolean _or = false;
      boolean _equals = Objects.equal(psiClass, null);
      if (_equals) {
        _or = true;
      } else {
        PsiClass _containingClass = psiClass.getContainingClass();
        boolean _notEquals = (!Objects.equal(_containingClass, null));
        _or = _notEquals;
      }
      if (_or) {
        return null;
      }
      _xblockexpression = new PsiClassMirror(psiClass, this.psiClassFactory);
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
