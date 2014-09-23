package org.eclipse.xtext.idea.types.psi;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import java.util.ArrayList;
import java.util.Set;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.TypesPackage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.IPsiModelAssociations;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.resource.DerivedStateAwareResource;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;

@Singleton
@SuppressWarnings("all")
public class PsiJvmDeclaredTypes {
  @Inject
  @Extension
  private IPsiModelAssociations _iPsiModelAssociations;
  
  @Inject
  @Extension
  private IJvmModelAssociations _iJvmModelAssociations;
  
  public ArrayList<PsiJvmDeclaredType> getPsiJvmDeclaredTypes(final PsiNamedEObject<?> psiNamedEObject) {
    ArrayList<PsiJvmDeclaredType> _xblockexpression = null;
    {
      PsiFile _containingFile = psiNamedEObject.getContainingFile();
      final PsiFile xtextFile = _containingFile;
      boolean _matched = false;
      if (!_matched) {
        if (xtextFile instanceof BaseXtextFile) {
          _matched=true;
          Resource _resource = ((BaseXtextFile)xtextFile).getResource();
          this.installDerivedState(_resource);
        }
      }
      final ArrayList<PsiJvmDeclaredType> result = CollectionLiterals.<PsiJvmDeclaredType>newArrayList();
      EObject _eObject = psiNamedEObject.getEObject();
      Set<EObject> _jvmElements = this._iJvmModelAssociations.getJvmElements(_eObject);
      Iterable<JvmDeclaredType> _filter = Iterables.<JvmDeclaredType>filter(_jvmElements, JvmDeclaredType.class);
      for (final JvmDeclaredType jvmDecaredType : _filter) {
        PsiElement _psiElement = this._iPsiModelAssociations.getPsiElement(jvmDecaredType);
        result.add(((PsiJvmDeclaredType) _psiElement));
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public ArrayList<PsiJvmDeclaredType> getPsiJvmDeclaredTypesByName(final BaseXtextFile it, final String name) {
    ArrayList<PsiJvmDeclaredType> _xblockexpression = null;
    {
      final Resource resource = it.getResource();
      final ArrayList<PsiJvmDeclaredType> result = CollectionLiterals.<PsiJvmDeclaredType>newArrayList();
      IResourceDescription _resourceDescription = it.getResourceDescription();
      Iterable<IEObjectDescription> _exportedObjectsByType = _resourceDescription.getExportedObjectsByType(TypesPackage.Literals.JVM_DECLARED_TYPE);
      for (final IEObjectDescription description : _exportedObjectsByType) {
        ResourceSet _resourceSet = resource.getResourceSet();
        URI _eObjectURI = description.getEObjectURI();
        EObject _eObject = _resourceSet.getEObject(_eObjectURI, true);
        final EObject jvmDeclaredType = _eObject;
        boolean _matched = false;
        if (!_matched) {
          if (jvmDeclaredType instanceof JvmDeclaredType) {
            String _simpleName = ((JvmDeclaredType)jvmDeclaredType).getSimpleName();
            boolean _equals = Objects.equal(_simpleName, name);
            if (_equals) {
              _matched=true;
              PsiElement _psiElement = this._iPsiModelAssociations.getPsiElement(jvmDeclaredType);
              result.add(((PsiJvmDeclaredType) _psiElement));
            }
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  public ArrayList<PsiJvmDeclaredType> getPsiJvmDeclaredTypes(final BaseXtextFile it, final QualifiedName qualifiedName) {
    ArrayList<PsiJvmDeclaredType> _xblockexpression = null;
    {
      final Resource resource = it.getResource();
      final ArrayList<PsiJvmDeclaredType> result = CollectionLiterals.<PsiJvmDeclaredType>newArrayList();
      IResourceDescription _resourceDescription = it.getResourceDescription();
      Iterable<IEObjectDescription> _exportedObjects = _resourceDescription.getExportedObjects(TypesPackage.Literals.JVM_DECLARED_TYPE, qualifiedName, false);
      for (final IEObjectDescription description : _exportedObjects) {
        ResourceSet _resourceSet = resource.getResourceSet();
        URI _eObjectURI = description.getEObjectURI();
        EObject _eObject = _resourceSet.getEObject(_eObjectURI, true);
        final EObject jvmDeclaredType = _eObject;
        boolean _matched = false;
        if (!_matched) {
          if (jvmDeclaredType instanceof JvmDeclaredType) {
            _matched=true;
            PsiElement _psiElement = this._iPsiModelAssociations.getPsiElement(jvmDeclaredType);
            result.add(((PsiJvmDeclaredType) _psiElement));
          }
        }
      }
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
  
  protected void installDerivedState(final Resource resource) {
    if ((resource instanceof DerivedStateAwareResource)) {
      final boolean deliver = ((DerivedStateAwareResource)resource).eDeliver();
      try {
        ((DerivedStateAwareResource)resource).eSetDeliver(false);
        ((DerivedStateAwareResource)resource).installDerivedState(false);
      } finally {
        ((DerivedStateAwareResource)resource).eSetDeliver(deliver);
      }
    }
  }
}
