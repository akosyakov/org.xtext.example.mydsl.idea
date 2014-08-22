package org.eclipse.xtext.idea.types.access;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.psi.PsiAnonymousClass;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiSyntheticClass;
import com.intellij.psi.PsiTypeParameter;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.JvmTypeConstraint;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.JvmUpperBound;
import org.eclipse.xtext.common.types.JvmVisibility;
import org.eclipse.xtext.common.types.JvmVoid;
import org.eclipse.xtext.common.types.TypesFactory;
import org.eclipse.xtext.common.types.access.impl.ITypeFactory;
import org.eclipse.xtext.common.types.impl.JvmTypeConstraintImplCustom;
import org.eclipse.xtext.idea.types.access.StubURIHelper;
import org.eclipse.xtext.psi.PsiModelAssociations;
import org.eclipse.xtext.util.internal.Stopwatches;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class PsiClassFactory implements ITypeFactory<PsiClass, JvmDeclaredType> {
  private final Stopwatches.StoppedTask createTypeTask = Stopwatches.forTask("PsiClassFactory.createType");
  
  /**
   * A cache mapping each type to its corresponding type proxy.
   * It's demand populated when {@link #createProxy(PsiClassType) creating} a type proxy.
   */
  private final Map<PsiClass, JvmType> typeProxies = new HashMap<PsiClass, JvmType>();
  
  @Extension
  private final TypesFactory _typesFactory = TypesFactory.eINSTANCE;
  
  @Extension
  private final StubURIHelper uriHelper;
  
  @Inject
  public PsiClassFactory(final StubURIHelper uriHelper) {
    this.uriHelper = uriHelper;
  }
  
  public JvmDeclaredType createType(final PsiClass psiClass) {
    JvmGenericType _xtrycatchfinallyexpression = null;
    try {
      JvmGenericType _xblockexpression = null;
      {
        this.createTypeTask.start();
        boolean _or = false;
        boolean _isAnonymous = this.isAnonymous(psiClass);
        if (_isAnonymous) {
          _or = true;
        } else {
          boolean _isSynthetic = this.isSynthetic(psiClass);
          _or = _isSynthetic;
        }
        if (_or) {
          throw new IllegalStateException("Cannot create type for anonymous or synthetic classes");
        }
        JvmGenericType _createJvmGenericType = this._typesFactory.createJvmGenericType();
        final Procedure1<JvmGenericType> _function = new Procedure1<JvmGenericType>() {
          public void apply(final JvmGenericType it) {
            boolean _isInterface = psiClass.isInterface();
            it.setInterface(_isInterface);
            PsiModifierList _modifierList = psiClass.getModifierList();
            boolean _hasModifierProperty = _modifierList.hasModifierProperty(PsiModifier.STRICTFP);
            it.setStrictFloatingPoint(_hasModifierProperty);
            PsiClassFactory.this.setTypeModifiers(it, psiClass);
            PsiClassFactory.this.setVisibility(it, psiClass);
            String _qualifiedName = psiClass.getQualifiedName();
            it.internalSetIdentifier(_qualifiedName);
            String _name = psiClass.getName();
            it.setSimpleName(_name);
            PsiClass _containingClass = psiClass.getContainingClass();
            boolean _equals = Objects.equal(_containingClass, null);
            if (_equals) {
              PsiFile _containingFile = psiClass.getContainingFile();
              final PsiJavaFile javaFile = ((PsiJavaFile) _containingFile);
              final String psiPackageName = javaFile.getPackageName();
              boolean _isEmpty = psiPackageName.isEmpty();
              boolean _not = (!_isEmpty);
              if (_not) {
                it.setPackageName(psiPackageName);
              }
            }
            EList<Adapter> _eAdapters = it.eAdapters();
            PsiModelAssociations.PsiAdapter _psiAdapter = new PsiModelAssociations.PsiAdapter(psiClass);
            _eAdapters.add(_psiAdapter);
          }
        };
        _xblockexpression = ObjectExtensions.<JvmGenericType>operator_doubleArrow(_createJvmGenericType, _function);
      }
      _xtrycatchfinallyexpression = _xblockexpression;
    } finally {
      this.createTypeTask.stop();
    }
    return _xtrycatchfinallyexpression;
  }
  
  protected JvmOperation createOperation(final PsiMethod psiMethod) {
    JvmOperation _createJvmOperation = this._typesFactory.createJvmOperation();
    final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
      public void apply(final JvmOperation it) {
        EList<JvmTypeParameter> _typeParameters = it.getTypeParameters();
        final InternalEList<JvmTypeParameter> jvmTypeParameters = ((InternalEList<JvmTypeParameter>) _typeParameters);
        PsiTypeParameter[] _typeParameters_1 = psiMethod.getTypeParameters();
        for (final PsiTypeParameter typeParameter : _typeParameters_1) {
          JvmTypeParameter _createTypeParameter = PsiClassFactory.this.createTypeParameter(typeParameter);
          jvmTypeParameters.addUnique(_createTypeParameter);
        }
      }
    };
    return ObjectExtensions.<JvmOperation>operator_doubleArrow(_createJvmOperation, _function);
  }
  
  protected JvmTypeParameter createTypeParameter(final PsiTypeParameter parameter) {
    JvmTypeParameter _createJvmTypeParameter = this._typesFactory.createJvmTypeParameter();
    final Procedure1<JvmTypeParameter> _function = new Procedure1<JvmTypeParameter>() {
      public void apply(final JvmTypeParameter it) {
        String _name = parameter.getName();
        it.setName(_name);
        EList<JvmTypeConstraint> _constraints = it.getConstraints();
        final InternalEList<JvmTypeConstraint> constraints = ((InternalEList<JvmTypeConstraint>) _constraints);
        PsiClassType[] _extendsListTypes = parameter.getExtendsListTypes();
        for (final PsiClassType upperBound : _extendsListTypes) {
          {
            JvmUpperBound _createJvmUpperBound = PsiClassFactory.this._typesFactory.createJvmUpperBound();
            final JvmTypeConstraintImplCustom jvmUpperBound = ((JvmTypeConstraintImplCustom) _createJvmUpperBound);
            JvmTypeReference _createTypeReference = PsiClassFactory.this.createTypeReference(upperBound);
            jvmUpperBound.internalSetTypeReference(_createTypeReference);
            constraints.addUnique(jvmUpperBound);
          }
        }
      }
    };
    return ObjectExtensions.<JvmTypeParameter>operator_doubleArrow(_createJvmTypeParameter, _function);
  }
  
  protected JvmTypeReference createTypeReference(final PsiClassType psiClassType) {
    JvmParameterizedTypeReference _createJvmParameterizedTypeReference = this._typesFactory.createJvmParameterizedTypeReference();
    final Procedure1<JvmParameterizedTypeReference> _function = new Procedure1<JvmParameterizedTypeReference>() {
      public void apply(final JvmParameterizedTypeReference it) {
        JvmType _createProxy = PsiClassFactory.this.createProxy(psiClassType);
        it.setType(_createProxy);
      }
    };
    return ObjectExtensions.<JvmParameterizedTypeReference>operator_doubleArrow(_createJvmParameterizedTypeReference, _function);
  }
  
  protected JvmType createProxy(final PsiClassType psiClassType) {
    JvmType _xblockexpression = null;
    {
      final PsiClass psiClass = psiClassType.resolve();
      JvmType _elvis = null;
      JvmType _get = this.typeProxies.get(psiClass);
      if (_get != null) {
        _elvis = _get;
      } else {
        JvmVoid _createJvmVoid = this._typesFactory.createJvmVoid();
        _elvis = _createJvmVoid;
      }
      final Procedure1<JvmType> _function = new Procedure1<JvmType>() {
        public void apply(final JvmType it) {
          final URI uri = PsiClassFactory.this.uriHelper.getFullURI(psiClass);
          if ((it instanceof InternalEObject)) {
            ((InternalEObject)it).eSetProxyURI(uri);
          }
          PsiClassFactory.this.typeProxies.put(psiClass, it);
        }
      };
      _xblockexpression = ObjectExtensions.<JvmType>operator_doubleArrow(_elvis, _function);
    }
    return _xblockexpression;
  }
  
  protected void createNestedTypes(final JvmGenericType it, final PsiClass psiClass) {
    EList<JvmMember> _members = it.getMembers();
    final InternalEList<JvmMember> members = ((InternalEList<JvmMember>) _members);
    PsiClass[] _innerClasses = psiClass.getInnerClasses();
    final Function1<PsiClass, Boolean> _function = new Function1<PsiClass, Boolean>() {
      public Boolean apply(final PsiClass it) {
        boolean _and = false;
        boolean _isAnonymous = PsiClassFactory.this.isAnonymous(it);
        boolean _not = (!_isAnonymous);
        if (!_not) {
          _and = false;
        } else {
          boolean _isSynthetic = PsiClassFactory.this.isSynthetic(it);
          boolean _not_1 = (!_isSynthetic);
          _and = _not_1;
        }
        return Boolean.valueOf(_and);
      }
    };
    Iterable<PsiClass> _filter = IterableExtensions.<PsiClass>filter(((Iterable<PsiClass>)Conversions.doWrapArray(_innerClasses)), _function);
    for (final PsiClass innerClass : _filter) {
      JvmDeclaredType _createType = this.createType(innerClass);
      members.addUnique(_createType);
    }
  }
  
  protected boolean isAnonymous(final PsiClass psiClass) {
    return (psiClass instanceof PsiAnonymousClass);
  }
  
  protected boolean isSynthetic(final PsiClass psiClass) {
    return (psiClass instanceof PsiSyntheticClass);
  }
  
  protected void setVisibility(final JvmGenericType it, final PsiClass psiClass) {
    JvmVisibility _switchResult = null;
    PsiModifierList _modifierList = psiClass.getModifierList();
    final PsiModifierList modifierList = _modifierList;
    boolean _matched = false;
    if (!_matched) {
      boolean _hasModifierProperty = modifierList.hasModifierProperty(PsiModifier.PRIVATE);
      if (_hasModifierProperty) {
        _matched=true;
        _switchResult = JvmVisibility.PRIVATE;
      }
    }
    if (!_matched) {
      boolean _hasModifierProperty_1 = modifierList.hasModifierProperty(PsiModifier.PROTECTED);
      if (_hasModifierProperty_1) {
        _matched=true;
        _switchResult = JvmVisibility.PROTECTED;
      }
    }
    if (!_matched) {
      boolean _hasModifierProperty_2 = modifierList.hasModifierProperty(PsiModifier.PUBLIC);
      if (_hasModifierProperty_2) {
        _matched=true;
        _switchResult = JvmVisibility.PUBLIC;
      }
    }
    it.setVisibility(_switchResult);
  }
  
  protected void setTypeModifiers(final JvmGenericType it, final PsiClass psiClass) {
    PsiModifierList _modifierList = psiClass.getModifierList();
    boolean _hasModifierProperty = _modifierList.hasModifierProperty(PsiModifier.ABSTRACT);
    it.setAbstract(_hasModifierProperty);
    PsiModifierList _modifierList_1 = psiClass.getModifierList();
    boolean _hasModifierProperty_1 = _modifierList_1.hasModifierProperty(PsiModifier.STATIC);
    it.setStatic(_hasModifierProperty_1);
    boolean _isEnum = psiClass.isEnum();
    boolean _not = (!_isEnum);
    if (_not) {
      PsiModifierList _modifierList_2 = psiClass.getModifierList();
      boolean _hasModifierProperty_2 = _modifierList_2.hasModifierProperty(PsiModifier.FINAL);
      it.setFinal(_hasModifierProperty_2);
    }
  }
}
