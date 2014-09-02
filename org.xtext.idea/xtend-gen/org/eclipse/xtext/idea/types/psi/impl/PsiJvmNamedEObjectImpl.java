package org.eclipse.xtext.idea.types.psi.impl;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.PsiManagerEx;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmDeclaredTypeImpl;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmDeclaredTypeDTO;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub;
import org.eclipse.xtext.psi.IPsiModelAssociations;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.util.RuntimeIOException;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ListExtensions;

@SuppressWarnings("all")
public class PsiJvmNamedEObjectImpl extends PsiNamedEObjectImpl<PsiJvmNamedEObjectStub> implements PsiJvmNamedEObject {
  @Inject
  @Extension
  private IPsiModelAssociations _iPsiModelAssociations;
  
  @Inject
  @Extension
  private IJvmModelAssociations _iJvmModelAssociations;
  
  public PsiJvmNamedEObjectImpl(final PsiJvmNamedEObjectStub stub, final IStubElementType<? extends PsiNamedEObjectStub, ? extends PsiNamedEObject> nodeType, final IElementType nameType) {
    super(stub, nodeType, nameType);
  }
  
  public PsiJvmNamedEObjectImpl(final ASTNode node, final IElementType nameType) {
    super(node, nameType);
  }
  
  public List<PsiJvmDeclaredType> findClasses(final String qualifiedName) {
    List<PsiJvmDeclaredType> _classes = this.getClasses();
    final Function1<PsiJvmDeclaredType, Boolean> _function = new Function1<PsiJvmDeclaredType, Boolean>() {
      public Boolean apply(final PsiJvmDeclaredType class_) {
        String _qualifiedName = class_.getQualifiedName();
        return Boolean.valueOf(Objects.equal(_qualifiedName, qualifiedName));
      }
    };
    Iterable<PsiJvmDeclaredType> _filter = IterableExtensions.<PsiJvmDeclaredType>filter(_classes, _function);
    return IterableExtensions.<PsiJvmDeclaredType>toList(_filter);
  }
  
  public List<PsiJvmDeclaredType> findClassesByName(final String shortName) {
    List<PsiJvmDeclaredType> _classes = this.getClasses();
    final Function1<PsiJvmDeclaredType, Boolean> _function = new Function1<PsiJvmDeclaredType, Boolean>() {
      public Boolean apply(final PsiJvmDeclaredType class_) {
        String _name = class_.getName();
        return Boolean.valueOf(Objects.equal(_name, shortName));
      }
    };
    Iterable<PsiJvmDeclaredType> _filter = IterableExtensions.<PsiJvmDeclaredType>filter(_classes, _function);
    return IterableExtensions.<PsiJvmDeclaredType>toList(_filter);
  }
  
  public List<PsiJvmDeclaredType> getClasses() {
    List<PsiJvmDeclaredType> _xblockexpression = null;
    {
      final PsiJvmNamedEObjectStub stub = this.getStub();
      final PsiManagerEx manager = this.getManager();
      final Language language = this.getLanguage();
      List<PsiJvmDeclaredType> _xifexpression = null;
      boolean _notEquals = (!Objects.equal(stub, null));
      if (_notEquals) {
        List<PsiJvmDeclaredTypeDTO> _classes = stub.getClasses();
        final Function1<PsiJvmDeclaredTypeDTO, PsiJvmDeclaredType> _function = new Function1<PsiJvmDeclaredTypeDTO, PsiJvmDeclaredType>() {
          public PsiJvmDeclaredType apply(final PsiJvmDeclaredTypeDTO it) {
            String _qualifiedName = it.getQualifiedName();
            EClass _type = it.getType();
            return new PsiJvmDeclaredTypeImpl(_qualifiedName, _type, PsiJvmNamedEObjectImpl.this, manager, language);
          }
        };
        _xifexpression = ListExtensions.<PsiJvmDeclaredTypeDTO, PsiJvmDeclaredType>map(_classes, _function);
      } else {
        ArrayList<PsiJvmDeclaredType> _xblockexpression_1 = null;
        {
          final ArrayList<PsiJvmDeclaredType> result = CollectionLiterals.<PsiJvmDeclaredType>newArrayList();
          Collection<EObject> _jvmElements = this.getJvmElements();
          Iterable<JvmDeclaredType> _filter = Iterables.<JvmDeclaredType>filter(_jvmElements, JvmDeclaredType.class);
          for (final JvmDeclaredType jvmElement : _filter) {
            {
              final PsiElement psiJvmDeclaredType = this._iPsiModelAssociations.getPsiElement(jvmElement);
              if ((psiJvmDeclaredType instanceof PsiJvmDeclaredType)) {
                result.add(((PsiJvmDeclaredType)psiJvmDeclaredType));
              }
            }
          }
          _xblockexpression_1 = result;
        }
        _xifexpression = _xblockexpression_1;
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected Collection<EObject> getJvmElements() {
    Collection<EObject> _switchResult = null;
    Resource _resource = this.getResource();
    final Resource resource = _resource;
    boolean _matched = false;
    if (!_matched) {
      if (resource instanceof DerivedStateAwareResource) {
        _matched=true;
        Set<EObject> _xblockexpression = null;
        {
          boolean _isLoaded = ((DerivedStateAwareResource)resource).isLoaded();
          boolean _not = (!_isLoaded);
          if (_not) {
            try {
              ResourceSet _resourceSet = ((DerivedStateAwareResource)resource).getResourceSet();
              Map<Object, Object> _loadOptions = _resourceSet.getLoadOptions();
              ((DerivedStateAwareResource)resource).load(_loadOptions);
            } catch (final Throwable _t) {
              if (_t instanceof IOException) {
                final IOException e = (IOException)_t;
                throw new RuntimeIOException(e);
              } else {
                throw Exceptions.sneakyThrow(_t);
              }
            }
          }
          boolean _or = false;
          boolean _isFullyInitialized = ((DerivedStateAwareResource)resource).isFullyInitialized();
          if (_isFullyInitialized) {
            _or = true;
          } else {
            boolean _isInitializing = ((DerivedStateAwareResource)resource).isInitializing();
            _or = _isInitializing;
          }
          final boolean isInitialized = _or;
          Set<EObject> _xtrycatchfinallyexpression = null;
          try {
            Set<EObject> _xblockexpression_1 = null;
            {
              if ((!isInitialized)) {
                ((DerivedStateAwareResource)resource).eSetDeliver(false);
                ((DerivedStateAwareResource)resource).installDerivedState(false);
              }
              EObject _eObject = this.getEObject();
              _xblockexpression_1 = this._iJvmModelAssociations.getJvmElements(_eObject);
            }
            _xtrycatchfinallyexpression = _xblockexpression_1;
          } finally {
            if ((!isInitialized)) {
              ((DerivedStateAwareResource)resource).discardDerivedState();
              ((DerivedStateAwareResource)resource).eSetDeliver(true);
            }
          }
          _xblockexpression = _xtrycatchfinallyexpression;
        }
        _switchResult = _xblockexpression;
      }
    }
    if (!_matched) {
      _switchResult = CollectionLiterals.<EObject>emptyList();
    }
    return _switchResult;
  }
}
