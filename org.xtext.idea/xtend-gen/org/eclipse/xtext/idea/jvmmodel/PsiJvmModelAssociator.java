package org.eclipse.xtext.idea.jvmmodel;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.lang.Language;
import com.intellij.psi.PsiArrayType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiEnumConstant;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.impl.light.LightField;
import com.intellij.psi.impl.light.LightMethod;
import com.intellij.psi.impl.light.LightParameter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmArrayType;
import org.eclipse.xtext.common.types.JvmComponentType;
import org.eclipse.xtext.common.types.JvmConstructor;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmEnumerationLiteral;
import org.eclipse.xtext.common.types.JvmExecutable;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmGenericArrayTypeReference;
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference;
import org.eclipse.xtext.common.types.JvmPrimitiveType;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.JvmUnknownTypeReference;
import org.eclipse.xtext.common.types.JvmVoid;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmDeclaredTypeImpl;
import org.eclipse.xtext.psi.IPsiModelAssociations;
import org.eclipse.xtext.psi.IPsiModelAssociator;
import org.eclipse.xtext.psi.PsiElementProvider;
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xtype.XFunctionTypeRef;

@SuppressWarnings("all")
public class PsiJvmModelAssociator extends JvmModelAssociator {
  @Inject
  @Extension
  private IPsiModelAssociator _iPsiModelAssociator;
  
  @Inject
  @Extension
  private IPsiModelAssociations _iPsiModelAssociations;
  
  public void associate(final EObject sourceElement, final EObject jvmElement) {
    super.associate(sourceElement, jvmElement);
    final PsiElementProvider psiElementProvider = this.createPsiElementProvider(sourceElement, jvmElement);
    boolean _notEquals = (!Objects.equal(psiElementProvider, null));
    if (_notEquals) {
      this._iPsiModelAssociator.associate(jvmElement, psiElementProvider);
    }
  }
  
  public void associatePrimary(final EObject sourceElement, final EObject jvmElement) {
    super.associatePrimary(sourceElement, jvmElement);
    final PsiElementProvider psiElementProvider = this.createPsiElementProvider(sourceElement, jvmElement);
    boolean _notEquals = (!Objects.equal(psiElementProvider, null));
    if (_notEquals) {
      this._iPsiModelAssociator.associatePrimary(jvmElement, psiElementProvider);
    }
  }
  
  public PsiElementProvider createPsiElementProvider(final EObject sourceElement, final EObject jvmElement) {
    boolean _matched = false;
    if (!_matched) {
      if (jvmElement instanceof JvmDeclaredType) {
        _matched=true;
        final PsiElementProvider _function = new PsiElementProvider() {
          public PsiElement get() {
            PsiElement _psiElement = PsiJvmModelAssociator.this._iPsiModelAssociations.getPsiElement(sourceElement);
            return new PsiJvmDeclaredTypeImpl(((JvmDeclaredType)jvmElement), _psiElement);
          }
        };
        return _function;
      }
    }
    if (!_matched) {
      if (jvmElement instanceof JvmField) {
        _matched=true;
        final PsiElementProvider _function = new PsiElementProvider() {
          public PsiElement get() {
            LightField _xblockexpression = null;
            {
              JvmDeclaredType _declaringType = ((JvmField)jvmElement).getDeclaringType();
              PsiElement _psiElement = PsiJvmModelAssociator.this._iPsiModelAssociations.getPsiElement(_declaringType);
              final PsiClass containingClass = ((PsiClass) _psiElement);
              final PsiField field = PsiJvmModelAssociator.this.findField(containingClass, ((JvmField)jvmElement));
              boolean _equals = Objects.equal(field, null);
              if (_equals) {
                return null;
              }
              LightField _switchResult = null;
              boolean _matched = false;
              if (!_matched) {
                if (field instanceof LightField) {
                  _matched=true;
                  _switchResult = ((LightField)field);
                }
              }
              if (!_matched) {
                PsiManager _manager = field.getManager();
                _switchResult = new LightField(_manager, field, containingClass);
              }
              final Procedure1<LightField> _function = new Procedure1<LightField>() {
                public void apply(final LightField it) {
                  PsiElement _psiElement = PsiJvmModelAssociator.this._iPsiModelAssociations.getPsiElement(sourceElement);
                  it.setNavigationElement(_psiElement);
                }
              };
              _xblockexpression = ObjectExtensions.<LightField>operator_doubleArrow(_switchResult, _function);
            }
            return _xblockexpression;
          }
        };
        return _function;
      }
    }
    if (!_matched) {
      if (jvmElement instanceof JvmExecutable) {
        _matched=true;
        final PsiElementProvider _function = new PsiElementProvider() {
          public PsiElement get() {
            LightMethod _xblockexpression = null;
            {
              JvmDeclaredType _declaringType = ((JvmExecutable)jvmElement).getDeclaringType();
              PsiElement _psiElement = PsiJvmModelAssociator.this._iPsiModelAssociations.getPsiElement(_declaringType);
              final PsiClass containingClass = ((PsiClass) _psiElement);
              final PsiMethod method = PsiJvmModelAssociator.this.findMethod(containingClass, ((JvmExecutable)jvmElement));
              boolean _equals = Objects.equal(method, null);
              if (_equals) {
                return null;
              }
              LightMethod _switchResult = null;
              boolean _matched = false;
              if (!_matched) {
                if (method instanceof LightMethod) {
                  _matched=true;
                  _switchResult = ((LightMethod)method);
                }
              }
              if (!_matched) {
                PsiManager _manager = method.getManager();
                _switchResult = new LightMethod(_manager, method, containingClass);
              }
              final Procedure1<LightMethod> _function = new Procedure1<LightMethod>() {
                public void apply(final LightMethod it) {
                  PsiElement _psiElement = PsiJvmModelAssociator.this._iPsiModelAssociations.getPsiElement(sourceElement);
                  it.setNavigationElement(_psiElement);
                }
              };
              _xblockexpression = ObjectExtensions.<LightMethod>operator_doubleArrow(_switchResult, _function);
            }
            return _xblockexpression;
          }
        };
        return _function;
      }
    }
    if (!_matched) {
      if (jvmElement instanceof JvmFormalParameter) {
        _matched=true;
        final PsiElementProvider _function = new PsiElementProvider() {
          public PsiElement get() {
            LightParameter _xblockexpression = null;
            {
              EObject _eContainer = ((JvmFormalParameter)jvmElement).eContainer();
              final JvmExecutable jvmExecutable = ((JvmExecutable) _eContainer);
              JvmDeclaredType _declaringType = jvmExecutable.getDeclaringType();
              PsiElement _psiElement = PsiJvmModelAssociator.this._iPsiModelAssociations.getPsiElement(_declaringType);
              final PsiClass containingClass = ((PsiClass) _psiElement);
              final PsiMethod method = PsiJvmModelAssociator.this.findMethod(containingClass, jvmExecutable);
              PsiParameter _findParameter = null;
              if (method!=null) {
                _findParameter=PsiJvmModelAssociator.this.findParameter(method, ((JvmFormalParameter)jvmElement));
              }
              final PsiParameter parameter = _findParameter;
              boolean _equals = Objects.equal(parameter, null);
              if (_equals) {
                return null;
              }
              LightParameter _switchResult = null;
              boolean _matched = false;
              if (!_matched) {
                if (parameter instanceof LightParameter) {
                  _matched=true;
                  _switchResult = ((LightParameter)parameter);
                }
              }
              if (!_matched) {
                String _name = parameter.getName();
                PsiType _type = parameter.getType();
                Language _language = method.getLanguage();
                boolean _isVarArgs = parameter.isVarArgs();
                _switchResult = new LightParameter(_name, _type, method, _language, _isVarArgs);
              }
              final Procedure1<LightParameter> _function = new Procedure1<LightParameter>() {
                public void apply(final LightParameter it) {
                  PsiElement _psiElement = PsiJvmModelAssociator.this._iPsiModelAssociations.getPsiElement(sourceElement);
                  it.setNavigationElement(_psiElement);
                }
              };
              _xblockexpression = ObjectExtensions.<LightParameter>operator_doubleArrow(_switchResult, _function);
            }
            return _xblockexpression;
          }
        };
        return _function;
      }
    }
    return null;
  }
  
  protected PsiParameter findParameter(final PsiMethod it, final JvmFormalParameter jvmFormalParameter) {
    PsiParameterList _parameterList = it.getParameterList();
    PsiParameter[] _parameters = _parameterList.getParameters();
    final Function1<PsiParameter, Boolean> _function = new Function1<PsiParameter, Boolean>() {
      public Boolean apply(final PsiParameter it) {
        String _name = it.getName();
        String _simpleName = jvmFormalParameter.getSimpleName();
        return Boolean.valueOf(Objects.equal(_name, _simpleName));
      }
    };
    return IterableExtensions.<PsiParameter>findFirst(((Iterable<PsiParameter>)Conversions.doWrapArray(_parameters)), _function);
  }
  
  protected PsiField findField(final PsiClass it, final JvmField field) {
    PsiField[] _fields = it.getFields();
    final Function1<PsiField, Boolean> _function = new Function1<PsiField, Boolean>() {
      public Boolean apply(final PsiField it) {
        String _name = it.getName();
        String _simpleName = field.getSimpleName();
        boolean _notEquals = (!Objects.equal(_name, _simpleName));
        if (_notEquals) {
          return Boolean.valueOf(false);
        }
        if (((it instanceof PsiEnumConstant) != (field instanceof JvmEnumerationLiteral))) {
          return Boolean.valueOf(false);
        }
        JvmTypeReference _type = field.getType();
        PsiType _type_1 = it.getType();
        return Boolean.valueOf(PsiJvmModelAssociator.this.equals(_type, _type_1));
      }
    };
    return IterableExtensions.<PsiField>findFirst(((Iterable<PsiField>)Conversions.doWrapArray(_fields)), _function);
  }
  
  protected PsiMethod findMethod(final PsiClass it, final JvmExecutable jvmExecutable) {
    PsiMethod[] _methods = it.getMethods();
    final Function1<PsiMethod, Boolean> _function = new Function1<PsiMethod, Boolean>() {
      public Boolean apply(final PsiMethod it) {
        boolean _xblockexpression = false;
        {
          String _name = it.getName();
          String _simpleName = jvmExecutable.getSimpleName();
          boolean _notEquals = (!Objects.equal(_name, _simpleName));
          if (_notEquals) {
            return Boolean.valueOf(false);
          }
          boolean _and = false;
          if (!(jvmExecutable instanceof JvmConstructor)) {
            _and = false;
          } else {
            boolean _isConstructor = it.isConstructor();
            boolean _not = (!_isConstructor);
            _and = _not;
          }
          if (_and) {
            return Boolean.valueOf(false);
          }
          PsiParameterList _parameterList = it.getParameterList();
          final int parametersCount = _parameterList.getParametersCount();
          EList<JvmFormalParameter> _parameters = jvmExecutable.getParameters();
          int _size = _parameters.size();
          boolean _notEquals_1 = (parametersCount != _size);
          if (_notEquals_1) {
            return Boolean.valueOf(false);
          }
          for (int i = 0; (i < parametersCount); i++) {
            {
              PsiParameterList _parameterList_1 = it.getParameterList();
              PsiParameter[] _parameters_1 = _parameterList_1.getParameters();
              final PsiParameter psiParameter = _parameters_1[i];
              EList<JvmFormalParameter> _parameters_2 = jvmExecutable.getParameters();
              final JvmFormalParameter jvmFormalParameter = _parameters_2.get(i);
              String _name_1 = psiParameter.getName();
              String _simpleName_1 = jvmFormalParameter.getSimpleName();
              boolean _notEquals_2 = (!Objects.equal(_name_1, _simpleName_1));
              if (_notEquals_2) {
                return Boolean.valueOf(false);
              }
              JvmTypeReference _parameterType = jvmFormalParameter.getParameterType();
              PsiType _type = psiParameter.getType();
              boolean _equals = PsiJvmModelAssociator.this.equals(_parameterType, _type);
              boolean _not_1 = (!_equals);
              if (_not_1) {
                return Boolean.valueOf(false);
              }
            }
          }
          _xblockexpression = true;
        }
        return Boolean.valueOf(_xblockexpression);
      }
    };
    return IterableExtensions.<PsiMethod>findFirst(((Iterable<PsiMethod>)Conversions.doWrapArray(_methods)), _function);
  }
  
  protected boolean equals(final JvmTypeReference jvmTypeReference, final PsiType psiType) {
    boolean _switchResult = false;
    boolean _matched = false;
    if (!_matched) {
      if (jvmTypeReference instanceof JvmParameterizedTypeReference) {
        _matched=true;
        JvmType _type = ((JvmParameterizedTypeReference)jvmTypeReference).getType();
        _switchResult = this.equals(_type, psiType);
      }
    }
    if (!_matched) {
      if (jvmTypeReference instanceof JvmGenericArrayTypeReference) {
        _matched=true;
        boolean _xifexpression = false;
        if ((psiType instanceof PsiArrayType)) {
          JvmTypeReference _componentType = ((JvmGenericArrayTypeReference)jvmTypeReference).getComponentType();
          PsiType _componentType_1 = ((PsiArrayType)psiType).getComponentType();
          _xifexpression = this.equals(_componentType, _componentType_1);
        } else {
          _xifexpression = false;
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      if (jvmTypeReference instanceof XFunctionTypeRef) {
        _matched=true;
        _switchResult = false;
      }
    }
    if (!_matched) {
      if (jvmTypeReference instanceof JvmUnknownTypeReference) {
        _matched=true;
        boolean _xifexpression = false;
        if ((psiType instanceof PsiClassType)) {
          PsiClass _resolve = ((PsiClassType)psiType).resolve();
          _xifexpression = Objects.equal(_resolve, null);
        } else {
          _xifexpression = false;
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      boolean _xifexpression = false;
      String _name = Object.class.getName();
      boolean _equalsToText = psiType.equalsToText(_name);
      if (_equalsToText) {
        _xifexpression = true;
      } else {
        throw new IllegalStateException(("Unexpected jvmTypeReference: " + jvmTypeReference));
      }
      _switchResult = _xifexpression;
    }
    return _switchResult;
  }
  
  protected boolean equals(final JvmType jvmType, final PsiType psiType) {
    boolean _switchResult = false;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(jvmType, null)) {
        _matched=true;
        _switchResult = false;
      }
    }
    if (!_matched) {
      if (jvmType instanceof JvmArrayType) {
        _matched=true;
        boolean _xifexpression = false;
        if ((psiType instanceof PsiArrayType)) {
          JvmComponentType _componentType = ((JvmArrayType)jvmType).getComponentType();
          PsiType _componentType_1 = ((PsiArrayType)psiType).getComponentType();
          _xifexpression = this.equals(_componentType, _componentType_1);
        } else {
          _xifexpression = false;
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      if (jvmType instanceof JvmDeclaredType) {
        _matched=true;
        boolean _xifexpression = false;
        if ((psiType instanceof PsiClassType)) {
          String _qualifiedName = ((JvmDeclaredType)jvmType).getQualifiedName();
          _xifexpression = ((PsiClassType)psiType).equalsToText(_qualifiedName);
        } else {
          _xifexpression = false;
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      if (jvmType instanceof JvmVoid) {
        _matched=true;
      }
      if (!_matched) {
        if (jvmType instanceof JvmPrimitiveType) {
          _matched=true;
        }
      }
      if (_matched) {
        boolean _xifexpression = false;
        if ((psiType instanceof PsiPrimitiveType)) {
          String _simpleName = jvmType.getSimpleName();
          _xifexpression = ((PsiPrimitiveType)psiType).equalsToText(_simpleName);
        } else {
          _xifexpression = false;
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      throw new IllegalStateException(("Unexpected jvmType: " + jvmType));
    }
    return _switchResult;
  }
}
