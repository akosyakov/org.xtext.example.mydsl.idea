package org.eclipse.xtext.idea.types.access;

import com.google.common.base.Objects;
import com.intellij.psi.PsiArrayType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiPrimitiveType;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.PsiTypeParameterListOwner;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.common.types.access.impl.Primitives;
import org.eclipse.xtext.common.types.access.impl.URIHelperConstants;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class StubURIHelper implements URIHelperConstants {
  public URI getFullURI(final String name) {
    StringBuilder _createURIBuilder = this.createURIBuilder();
    StringBuilder _appendClassResourceURI = this.appendClassResourceURI(_createURIBuilder, name);
    StringBuilder _append = _appendClassResourceURI.append("#");
    StringBuilder _appendTypeFragment = this.appendTypeFragment(_append, name);
    return this.createURI(_appendTypeFragment);
  }
  
  public URI createResourceURI(final String name) {
    StringBuilder _createURIBuilder = this.createURIBuilder();
    StringBuilder _appendClassResourceURI = this.appendClassResourceURI(_createURIBuilder, name);
    return this.createURI(_appendClassResourceURI);
  }
  
  public String getFragment(final String name) {
    StringBuilder _createFragmentBuilder = this.createFragmentBuilder();
    StringBuilder _appendTypeFragment = this.appendTypeFragment(_createFragmentBuilder, name);
    return _appendTypeFragment.toString();
  }
  
  protected StringBuilder appendClassResourceURI(final StringBuilder builder, final String name) {
    StringBuilder _xblockexpression = null;
    {
      final int endIndex = name.indexOf("[");
      String _xifexpression = null;
      if ((endIndex == (-1))) {
        _xifexpression = name;
      } else {
        _xifexpression = name.substring(0, endIndex);
      }
      final String typeName = _xifexpression;
      StringBuilder _xifexpression_1 = null;
      final Function1<Class<?>, Boolean> _function = new Function1<Class<?>, Boolean>() {
        public Boolean apply(final Class<?> type) {
          String _name = type.getName();
          return Boolean.valueOf(Objects.equal(_name, typeName));
        }
      };
      boolean _exists = IterableExtensions.<Class<?>>exists(Primitives.ALL_PRIMITIVE_TYPES, _function);
      if (_exists) {
        _xifexpression_1 = builder.append(URIHelperConstants.PRIMITIVES);
      } else {
        StringBuilder _append = builder.append(URIHelperConstants.OBJECTS);
        _xifexpression_1 = _append.append(typeName);
      }
      _xblockexpression = _xifexpression_1;
    }
    return _xblockexpression;
  }
  
  protected StringBuilder appendTypeFragment(final StringBuilder builder, final String name) {
    return builder.append(name);
  }
  
  public URI getFullURI(final PsiType type) {
    StringBuilder _createURIBuilder = this.createURIBuilder();
    StringBuilder _appendTypeResourceURI = this.appendTypeResourceURI(_createURIBuilder, type);
    StringBuilder _append = _appendTypeResourceURI.append("#");
    StringBuilder _appendTypeFragment = this.appendTypeFragment(_append, type);
    return this.createURI(_appendTypeFragment);
  }
  
  protected StringBuilder appendTypeResourceURI(final StringBuilder builder, final PsiType type) {
    StringBuilder _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (type instanceof PsiArrayType) {
        _matched=true;
        PsiType _componentType = ((PsiArrayType)type).getComponentType();
        _switchResult = this.appendTypeResourceURI(builder, _componentType);
      }
    }
    if (!_matched) {
      if (type instanceof PsiPrimitiveType) {
        _matched=true;
        _switchResult = builder.append(URIHelperConstants.PRIMITIVES);
      }
    }
    if (!_matched) {
      if (type instanceof PsiClassType) {
        _matched=true;
        StringBuilder _switchResult_1 = null;
        PsiClass _resolve = ((PsiClassType)type).resolve();
        final PsiClass resolvedType = _resolve;
        boolean _matched_1 = false;
        if (!_matched_1) {
          if (resolvedType instanceof PsiTypeParameter) {
            _matched_1=true;
            _switchResult_1 = this.appendTypeParameterResourceURI(builder, ((PsiTypeParameter)resolvedType));
          }
        }
        if (!_matched_1) {
          if (resolvedType instanceof PsiClass) {
            _matched_1=true;
            _switchResult_1 = this.appendClassResourceURI(builder, resolvedType);
          }
        }
        if (!_matched_1) {
          String _canonicalText = ((PsiClassType)type).getCanonicalText(false);
          _switchResult_1 = builder.append(_canonicalText);
        }
        _switchResult = _switchResult_1;
      }
    }
    if (!_matched) {
      throw new IllegalStateException(("unexpected type: " + type));
    }
    return _switchResult;
  }
  
  protected StringBuilder appendTypeParameterResourceURI(final StringBuilder builder, final PsiTypeParameter typeParameter) {
    StringBuilder _switchResult = null;
    PsiTypeParameterListOwner _owner = typeParameter.getOwner();
    final PsiTypeParameterListOwner owner = _owner;
    boolean _matched = false;
    if (!_matched) {
      if (owner instanceof PsiClass) {
        _matched=true;
        _switchResult = this.appendClassResourceURI(builder, ((PsiClass)owner));
      }
    }
    if (!_matched) {
      if (owner instanceof PsiMethod) {
        _matched=true;
        PsiClass _containingClass = ((PsiMethod)owner).getContainingClass();
        _switchResult = this.appendClassResourceURI(builder, _containingClass);
      }
    }
    return _switchResult;
  }
  
  protected StringBuilder appendClassResourceURI(final StringBuilder builder, final PsiClass psiClass) {
    StringBuilder _xblockexpression = null;
    {
      final PsiClass containingClass = psiClass.getContainingClass();
      StringBuilder _xifexpression = null;
      boolean _notEquals = (!Objects.equal(containingClass, null));
      if (_notEquals) {
        _xifexpression = this.appendClassResourceURI(builder, containingClass);
      } else {
        StringBuilder _append = builder.append(URIHelperConstants.OBJECTS);
        String _qualifiedName = psiClass.getQualifiedName();
        _xifexpression = _append.append(_qualifiedName);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected StringBuilder appendTypeFragment(final StringBuilder builder, final PsiType type) {
    StringBuilder _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (type instanceof PsiPrimitiveType) {
        _matched=true;
        String _canonicalText = ((PsiPrimitiveType)type).getCanonicalText(false);
        _switchResult = builder.append(_canonicalText);
      }
    }
    if (!_matched) {
      if (type instanceof PsiClassType) {
        _matched=true;
        StringBuilder _switchResult_1 = null;
        PsiClass _resolve = ((PsiClassType)type).resolve();
        final PsiClass resolvedType = _resolve;
        boolean _matched_1 = false;
        if (!_matched_1) {
          if (resolvedType instanceof PsiTypeParameter) {
            _matched_1=true;
            _switchResult_1 = this.appendTypeParameterFragment(builder, ((PsiTypeParameter)resolvedType));
          }
        }
        if (!_matched_1) {
          if (resolvedType instanceof PsiClass) {
            _matched_1=true;
            _switchResult_1 = this.appendClassFragment(builder, resolvedType);
          }
        }
        if (!_matched_1) {
          String _canonicalText = ((PsiClassType)type).getCanonicalText(false);
          _switchResult_1 = builder.append(_canonicalText);
        }
        _switchResult = _switchResult_1;
      }
    }
    if (!_matched) {
      if (type instanceof PsiArrayType) {
        _matched=true;
        PsiType _componentType = ((PsiArrayType)type).getComponentType();
        StringBuilder _appendTypeFragment = this.appendTypeFragment(builder, _componentType);
        _switchResult = _appendTypeFragment.append("[]");
      }
    }
    if (!_matched) {
      throw new IllegalStateException(("unknown type: " + type));
    }
    return _switchResult;
  }
  
  protected StringBuilder appendTypeParameterFragment(final StringBuilder builder, final PsiTypeParameter typeParameter) {
    StringBuilder _xblockexpression = null;
    {
      PsiTypeParameterListOwner _owner = typeParameter.getOwner();
      final PsiTypeParameterListOwner owner = _owner;
      boolean _matched = false;
      if (!_matched) {
        if (owner instanceof PsiClass) {
          _matched=true;
          this.appendClassFragment(builder, ((PsiClass)owner));
        }
      }
      if (!_matched) {
        if (owner instanceof PsiMethod) {
          _matched=true;
          this.appendMethodFragment(builder, ((PsiMethod)owner));
        }
      }
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("/");
      String _name = typeParameter.getName();
      _builder.append(_name, "");
      _xblockexpression = builder.append(_builder);
    }
    return _xblockexpression;
  }
  
  protected StringBuilder appendClassFragment(final StringBuilder builder, final PsiClass psiClass) {
    StringBuilder _xblockexpression = null;
    {
      final PsiClass containingClass = psiClass.getContainingClass();
      StringBuilder _xifexpression = null;
      boolean _equals = Objects.equal(containingClass, null);
      if (_equals) {
        String _qualifiedName = psiClass.getQualifiedName();
        _xifexpression = builder.append(_qualifiedName);
      } else {
        StringBuilder _appendClassFragment = this.appendClassFragment(builder, containingClass);
        StringBuilder _append = _appendClassFragment.append("$");
        String _name = psiClass.getName();
        _xifexpression = _append.append(_name);
      }
      _xblockexpression = _xifexpression;
    }
    return _xblockexpression;
  }
  
  protected StringBuilder appendMethodFragment(final StringBuilder builder, final PsiMethod method) {
    StringBuilder _xblockexpression = null;
    {
      PsiClass _containingClass = method.getContainingClass();
      this.appendClassFragment(builder, _containingClass);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append(".");
      String _name = method.getName();
      _builder.append(_name, "");
      _builder.append("(");
      builder.append(_builder);
      PsiParameterList _parameterList = method.getParameterList();
      final int parameterCount = _parameterList.getParametersCount();
      for (int i = 0; (i < parameterCount); i++) {
        {
          if ((i != 0)) {
            builder.append(",");
          }
          PsiParameterList _parameterList_1 = method.getParameterList();
          PsiParameter[] _parameters = _parameterList_1.getParameters();
          PsiParameter _get = _parameters[i];
          PsiType _type = _get.getType();
          this.appendTypeName(builder, _type);
        }
      }
      _xblockexpression = builder.append(")");
    }
    return _xblockexpression;
  }
  
  public StringBuilder appendTypeName(final StringBuilder builder, final PsiType type) {
    StringBuilder _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (type instanceof PsiPrimitiveType) {
        _matched=true;
        String _canonicalText = ((PsiPrimitiveType)type).getCanonicalText(false);
        _switchResult = builder.append(_canonicalText);
      }
    }
    if (!_matched) {
      if (type instanceof PsiClassType) {
        _matched=true;
        StringBuilder _switchResult_1 = null;
        PsiClass _resolve = ((PsiClassType)type).resolve();
        final PsiClass resolvedType = _resolve;
        boolean _matched_1 = false;
        if (!_matched_1) {
          if (resolvedType instanceof PsiTypeParameter) {
            _matched_1=true;
            String _name = ((PsiTypeParameter)resolvedType).getName();
            _switchResult_1 = builder.append(_name);
          }
        }
        if (!_matched_1) {
          String _qualifiedName = resolvedType.getQualifiedName();
          _switchResult_1 = builder.append(_qualifiedName);
        }
        _switchResult = _switchResult_1;
      }
    }
    if (!_matched) {
      if (type instanceof PsiArrayType) {
        _matched=true;
        PsiType _componentType = ((PsiArrayType)type).getComponentType();
        StringBuilder _appendTypeName = this.appendTypeName(builder, _componentType);
        _switchResult = _appendTypeName.append("[]");
      }
    }
    if (!_matched) {
      throw new IllegalStateException(("unknown type: " + type));
    }
    return _switchResult;
  }
  
  protected StringBuilder createURIBuilder() {
    StringBuilder _stringBuilder = new StringBuilder(48);
    StringBuilder _append = _stringBuilder.append(URIHelperConstants.PROTOCOL);
    return _append.append(":");
  }
  
  protected StringBuilder createFragmentBuilder() {
    return new StringBuilder(32);
  }
  
  protected URI createURI(final StringBuilder uriBuilder) {
    String _string = uriBuilder.toString();
    return URI.createURI(_string);
  }
  
  public boolean isPrimitive(final PsiType type) {
    return (type instanceof PsiPrimitiveType);
  }
  
  public boolean isClassType(final PsiType type, final Class<?> clazz) {
    boolean _xifexpression = false;
    if ((type instanceof PsiClassType)) {
      PsiClass _resolve = ((PsiClassType)type).resolve();
      String _qualifiedName = _resolve.getQualifiedName();
      String _name = clazz.getName();
      _xifexpression = Objects.equal(_qualifiedName, _name);
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  public boolean isAnnotation(final PsiType type) {
    boolean _xifexpression = false;
    if ((type instanceof PsiClassType)) {
      PsiClass _resolve = ((PsiClassType)type).resolve();
      _xifexpression = _resolve.isAnnotationType();
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  public boolean isEnum(final PsiType type) {
    boolean _xifexpression = false;
    if ((type instanceof PsiClassType)) {
      PsiClass _resolve = ((PsiClassType)type).resolve();
      _xifexpression = _resolve.isEnum();
    } else {
      _xifexpression = false;
    }
    return _xifexpression;
  }
  
  public boolean isArray(final PsiType type) {
    return (type instanceof PsiArrayType);
  }
}
