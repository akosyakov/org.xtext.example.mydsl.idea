package org.eclipse.xtext.idea.types.access;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiAnnotationMemberValue;
import com.intellij.psi.PsiAnnotationMethod;
import com.intellij.psi.PsiAnonymousClass;
import com.intellij.psi.PsiArrayType;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiConstantEvaluationHelper;
import com.intellij.psi.PsiEnumConstant;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifier;
import com.intellij.psi.PsiModifierList;
import com.intellij.psi.PsiModifierListOwner;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.PsiParameterList;
import com.intellij.psi.PsiType;
import com.intellij.psi.PsiTypeParameter;
import com.intellij.psi.PsiWildcardType;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.xtext.common.types.JvmAnnotationAnnotationValue;
import org.eclipse.xtext.common.types.JvmAnnotationValue;
import org.eclipse.xtext.common.types.JvmBooleanAnnotationValue;
import org.eclipse.xtext.common.types.JvmByteAnnotationValue;
import org.eclipse.xtext.common.types.JvmCharAnnotationValue;
import org.eclipse.xtext.common.types.JvmConstructor;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmDoubleAnnotationValue;
import org.eclipse.xtext.common.types.JvmEnumAnnotationValue;
import org.eclipse.xtext.common.types.JvmExecutable;
import org.eclipse.xtext.common.types.JvmField;
import org.eclipse.xtext.common.types.JvmFloatAnnotationValue;
import org.eclipse.xtext.common.types.JvmFormalParameter;
import org.eclipse.xtext.common.types.JvmGenericArrayTypeReference;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmIntAnnotationValue;
import org.eclipse.xtext.common.types.JvmLongAnnotationValue;
import org.eclipse.xtext.common.types.JvmLowerBound;
import org.eclipse.xtext.common.types.JvmMember;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference;
import org.eclipse.xtext.common.types.JvmShortAnnotationValue;
import org.eclipse.xtext.common.types.JvmStringAnnotationValue;
import org.eclipse.xtext.common.types.JvmType;
import org.eclipse.xtext.common.types.JvmTypeAnnotationValue;
import org.eclipse.xtext.common.types.JvmTypeConstraint;
import org.eclipse.xtext.common.types.JvmTypeParameter;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.common.types.JvmUpperBound;
import org.eclipse.xtext.common.types.JvmVisibility;
import org.eclipse.xtext.common.types.JvmVoid;
import org.eclipse.xtext.common.types.JvmWildcardTypeReference;
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
import org.eclipse.xtext.xbase.lib.Procedures.Procedure0;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class PsiClassFactory implements ITypeFactory<PsiClass, JvmDeclaredType> {
  private final Stopwatches.StoppedTask createTypeTask = Stopwatches.forTask("PsiClassFactory.createType");
  
  /**
   * A cache mapping each type to its corresponding type proxy.
   * It's demand populated when {@link #createProxy(PsiType) creating} a type proxy.
   */
  private final Map<PsiType, JvmType> typeProxies = new HashMap<PsiType, JvmType>();
  
  @Extension
  private final TypesFactory _typesFactory = TypesFactory.eINSTANCE;
  
  @Extension
  private final StubURIHelper uriHelper;
  
  @Inject
  public PsiClassFactory(final StubURIHelper uriHelper) {
    this.uriHelper = uriHelper;
  }
  
  public JvmDeclaredType createType(final PsiClass psiClass) {
    JvmDeclaredType _xtrycatchfinallyexpression = null;
    try {
      JvmDeclaredType _xblockexpression = null;
      {
        this.createTypeTask.start();
        final StringBuilder fqn = new StringBuilder(100);
        final String packageName = this.getPackageName(psiClass);
        boolean _notEquals = (!Objects.equal(packageName, null));
        if (_notEquals) {
          StringBuilder _append = fqn.append(packageName);
          _append.append(".");
        }
        final JvmDeclaredType type = this.createType(psiClass, fqn);
        type.setPackageName(packageName);
        EList<Adapter> _eAdapters = type.eAdapters();
        PsiModelAssociations.PsiAdapter _psiAdapter = new PsiModelAssociations.PsiAdapter(psiClass);
        _eAdapters.add(_psiAdapter);
        _xblockexpression = type;
      }
      _xtrycatchfinallyexpression = _xblockexpression;
    } finally {
      this.createTypeTask.stop();
    }
    return _xtrycatchfinallyexpression;
  }
  
  protected JvmDeclaredType createType(final PsiClass psiClass, final StringBuilder fqn) {
    JvmDeclaredType _xblockexpression = null;
    {
      ProgressIndicatorProvider.checkCanceled();
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
      JvmDeclaredType _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        boolean _isAnnotationType = psiClass.isAnnotationType();
        if (_isAnnotationType) {
          _matched=true;
          _switchResult = this._typesFactory.createJvmAnnotationType();
        }
      }
      if (!_matched) {
        boolean _isEnum = psiClass.isEnum();
        if (_isEnum) {
          _matched=true;
          _switchResult = this._typesFactory.createJvmEnumerationType();
        }
      }
      if (!_matched) {
        JvmGenericType _createJvmGenericType = this._typesFactory.createJvmGenericType();
        final Procedure1<JvmGenericType> _function = new Procedure1<JvmGenericType>() {
          public void apply(final JvmGenericType it) {
            boolean _isInterface = psiClass.isInterface();
            it.setInterface(_isInterface);
            PsiModifierList _modifierList = psiClass.getModifierList();
            boolean _hasModifierProperty = _modifierList.hasModifierProperty(PsiModifier.STRICTFP);
            it.setStrictFloatingPoint(_hasModifierProperty);
          }
        };
        _switchResult = ObjectExtensions.<JvmGenericType>operator_doubleArrow(_createJvmGenericType, _function);
      }
      final Procedure1<JvmDeclaredType> _function_1 = new Procedure1<JvmDeclaredType>() {
        public void apply(final JvmDeclaredType it) {
          ProgressIndicatorProvider.checkCanceled();
          PsiClassFactory.this.setTypeModifiers(it, psiClass);
          PsiClassFactory.this.setVisibility(it, psiClass);
          boolean _isDeprecated = psiClass.isDeprecated();
          it.setDeprecated(_isDeprecated);
          String _name = psiClass.getName();
          it.setSimpleName(_name);
          String _name_1 = psiClass.getName();
          fqn.append(_name_1);
          String _string = fqn.toString();
          it.internalSetIdentifier(_string);
          final Procedure0 _function = new Procedure0() {
            public void apply() {
              PsiClassFactory.this.createNestedTypes(it, psiClass, fqn);
            }
          };
          PsiClassFactory.this.append(fqn, "$", _function);
          fqn.append(".");
          PsiClassFactory.this.createMethods(it, psiClass, fqn);
          boolean _isAnnotationType = psiClass.isAnnotationType();
          boolean _not = (!_isAnnotationType);
          if (_not) {
            PsiClassFactory.this.createFields(it, psiClass, fqn);
          }
          PsiClassFactory.this.createSuperTypes(it, psiClass);
        }
      };
      _xblockexpression = ObjectExtensions.<JvmDeclaredType>operator_doubleArrow(_switchResult, _function_1);
    }
    return _xblockexpression;
  }
  
  protected void createFields(final JvmDeclaredType it, final PsiClass psiClass, final StringBuilder fqn) {
    PsiField[] _fields = psiClass.getFields();
    for (final PsiField field : _fields) {
      final Procedure0 _function = new Procedure0() {
        public void apply() {
          EList<JvmMember> _members = it.getMembers();
          Object _createField = PsiClassFactory.this.createField(field, fqn);
          PsiClassFactory.this.addUnique(_members, _createField);
        }
      };
      this.preserve(fqn, _function);
    }
  }
  
  protected Object createField(final PsiField field, final StringBuilder fqn) {
    JvmField _xblockexpression = null;
    {
      ProgressIndicatorProvider.checkCanceled();
      JvmField _switchResult = null;
      boolean _matched = false;
      if (!_matched) {
        if (field instanceof PsiEnumConstant) {
          _matched=true;
          _switchResult = this._typesFactory.createJvmEnumerationLiteral();
        }
      }
      if (!_matched) {
        JvmField _createJvmField = this._typesFactory.createJvmField();
        final Procedure1<JvmField> _function = new Procedure1<JvmField>() {
          public void apply(final JvmField it) {
            final Object value = field.computeConstantValue();
            boolean _notEquals = (!Objects.equal(value, null));
            if (_notEquals) {
              it.setConstant(true);
              it.setConstantValue(value);
            } else {
              it.setConstant(false);
            }
          }
        };
        _switchResult = ObjectExtensions.<JvmField>operator_doubleArrow(_createJvmField, _function);
      }
      final Procedure1<JvmField> _function_1 = new Procedure1<JvmField>() {
        public void apply(final JvmField it) {
          String _name = field.getName();
          StringBuilder _append = fqn.append(_name);
          String _string = _append.toString();
          it.internalSetIdentifier(_string);
          String _name_1 = field.getName();
          it.setSimpleName(_name_1);
          PsiModifierList _modifierList = field.getModifierList();
          boolean _hasModifierProperty = _modifierList.hasModifierProperty(PsiModifier.FINAL);
          it.setFinal(_hasModifierProperty);
          PsiModifierList _modifierList_1 = field.getModifierList();
          boolean _hasModifierProperty_1 = _modifierList_1.hasModifierProperty(PsiModifier.STATIC);
          it.setStatic(_hasModifierProperty_1);
          PsiModifierList _modifierList_2 = field.getModifierList();
          boolean _hasModifierProperty_2 = _modifierList_2.hasModifierProperty(PsiModifier.TRANSIENT);
          it.setTransient(_hasModifierProperty_2);
          PsiModifierList _modifierList_3 = field.getModifierList();
          boolean _hasModifierProperty_3 = _modifierList_3.hasModifierProperty(PsiModifier.VOLATILE);
          it.setVolatile(_hasModifierProperty_3);
          boolean _isDeprecated = field.isDeprecated();
          it.setDeprecated(_isDeprecated);
          PsiClassFactory.this.setVisibility(it, field);
          PsiType _type = field.getType();
          JvmTypeReference _createTypeReference = PsiClassFactory.this.createTypeReference(_type);
          it.setType(_createTypeReference);
        }
      };
      _xblockexpression = ObjectExtensions.<JvmField>operator_doubleArrow(_switchResult, _function_1);
    }
    return _xblockexpression;
  }
  
  protected void createSuperTypes(final JvmDeclaredType it, final PsiClass psiClass) {
    PsiClassType[] _superTypes = psiClass.getSuperTypes();
    for (final PsiClassType superType : _superTypes) {
      EList<JvmTypeReference> _superTypes_1 = it.getSuperTypes();
      JvmTypeReference _createTypeReference = this.createTypeReference(superType);
      this.addUnique(_superTypes_1, _createTypeReference);
    }
  }
  
  protected void createMethods(final JvmDeclaredType it, final PsiClass psiClass, final StringBuilder fqn) {
    PsiMethod[] _methods = psiClass.getMethods();
    for (final PsiMethod method : _methods) {
      final Procedure0 _function = new Procedure0() {
        public void apply() {
          JvmExecutable _xifexpression = null;
          boolean _isConstructor = method.isConstructor();
          if (_isConstructor) {
            _xifexpression = PsiClassFactory.this.createConstructor(method, fqn);
          } else {
            JvmOperation _createOperation = PsiClassFactory.this.createOperation(method, fqn);
            final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
              public void apply(final JvmOperation it) {
                PsiClassFactory.this.setDefaultValue(it, method);
              }
            };
            _xifexpression = ObjectExtensions.<JvmOperation>operator_doubleArrow(_createOperation, _function);
          }
          final JvmExecutable operation = _xifexpression;
          EList<JvmMember> _members = it.getMembers();
          PsiClassFactory.this.addUnique(_members, operation);
        }
      };
      this.preserve(fqn, _function);
    }
  }
  
  protected void setDefaultValue(final JvmOperation operation, final PsiMethod method) {
    if ((method instanceof PsiAnnotationMethod)) {
      final Object defaultValue = this.computeDefaultValue(((PsiAnnotationMethod)method));
      boolean _notEquals = (!Objects.equal(defaultValue, null));
      if (_notEquals) {
        final PsiType returnType = ((PsiAnnotationMethod)method).getReturnType();
        JvmAnnotationValue _xifexpression = null;
        boolean _isArray = this.uriHelper.isArray(returnType);
        if (_isArray) {
          _xifexpression = this.createArrayAnnotationValue(defaultValue, returnType);
        } else {
          _xifexpression = this.createAnnotationValue(defaultValue, returnType);
        }
        final JvmAnnotationValue annotationValue = _xifexpression;
        operation.setDefaultValue(annotationValue);
        annotationValue.setOperation(operation);
      }
    }
  }
  
  protected JvmAnnotationValue createAnnotationValue(final Object value, final PsiType type) {
    JvmAnnotationValue _createAnnotationValue = this.createAnnotationValue(type);
    final Procedure1<JvmAnnotationValue> _function = new Procedure1<JvmAnnotationValue>() {
      public void apply(final JvmAnnotationValue it) {
        PsiClassFactory.this.addValue(it, value);
      }
    };
    return ObjectExtensions.<JvmAnnotationValue>operator_doubleArrow(_createAnnotationValue, _function);
  }
  
  protected JvmAnnotationValue createArrayAnnotationValue(final Object value, final PsiType type) {
    if ((type instanceof PsiArrayType)) {
      final PsiType componentType = ((PsiArrayType)type).getComponentType();
      JvmAnnotationValue _createAnnotationValue = this.createAnnotationValue(componentType);
      final Procedure1<JvmAnnotationValue> _function = new Procedure1<JvmAnnotationValue>() {
        public void apply(final JvmAnnotationValue it) {
          boolean _or = false;
          boolean _isPrimitive = PsiClassFactory.this.uriHelper.isPrimitive(type);
          if (_isPrimitive) {
            _or = true;
          } else {
            boolean _isClassType = PsiClassFactory.this.uriHelper.isClassType(type, String.class);
            _or = _isClassType;
          }
          if (_or) {
            Class<?> _class = value.getClass();
            boolean _isArray = _class.isArray();
            if (_isArray) {
              final int length = Array.getLength(value);
              for (int i = 0; (i < length); i++) {
                Object _get = Array.get(value, i);
                PsiClassFactory.this.addValue(it, _get);
              }
            } else {
              PsiClassFactory.this.addValue(it, value);
            }
          } else {
            boolean _isClassType_1 = PsiClassFactory.this.uriHelper.isClassType(type, Class.class);
            if (_isClassType_1) {
            } else {
              boolean _isAnnotation = PsiClassFactory.this.uriHelper.isAnnotation(type);
              if (_isAnnotation) {
              } else {
                boolean _isEnum = PsiClassFactory.this.uriHelper.isEnum(type);
                if (_isEnum) {
                }
              }
            }
          }
        }
      };
      return ObjectExtensions.<JvmAnnotationValue>operator_doubleArrow(_createAnnotationValue, _function);
    }
    String _canonicalText = type.getCanonicalText();
    String _plus = ("type is not an array type: " + _canonicalText);
    throw new IllegalArgumentException(_plus);
  }
  
  protected Object addValue(final JvmAnnotationValue it, final Object value) {
    Object _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (it instanceof JvmBooleanAnnotationValue) {
        _matched=true;
        EList<Boolean> _values = ((JvmBooleanAnnotationValue)it).getValues();
        this.addUnique(_values, ((Boolean) value));
      }
    }
    if (!_matched) {
      if (it instanceof JvmIntAnnotationValue) {
        _matched=true;
        EList<Integer> _values = ((JvmIntAnnotationValue)it).getValues();
        Integer _asInteger = this.asInteger(value);
        this.addUnique(_values, _asInteger);
      }
    }
    if (!_matched) {
      if (it instanceof JvmLongAnnotationValue) {
        _matched=true;
        EList<Long> _values = ((JvmLongAnnotationValue)it).getValues();
        Long _asLong = this.asLong(value);
        this.addUnique(_values, _asLong);
      }
    }
    if (!_matched) {
      if (it instanceof JvmShortAnnotationValue) {
        _matched=true;
        EList<Short> _values = ((JvmShortAnnotationValue)it).getValues();
        Short _asShort = this.asShort(value);
        this.addUnique(_values, _asShort);
      }
    }
    if (!_matched) {
      if (it instanceof JvmFloatAnnotationValue) {
        _matched=true;
        EList<Float> _values = ((JvmFloatAnnotationValue)it).getValues();
        Float _asFloat = this.asFloat(value);
        this.addUnique(_values, _asFloat);
      }
    }
    if (!_matched) {
      if (it instanceof JvmDoubleAnnotationValue) {
        _matched=true;
        EList<Double> _values = ((JvmDoubleAnnotationValue)it).getValues();
        Double _asDouble = this.asDouble(value);
        this.addUnique(_values, _asDouble);
      }
    }
    if (!_matched) {
      if (it instanceof JvmCharAnnotationValue) {
        _matched=true;
        EList<Character> _values = ((JvmCharAnnotationValue)it).getValues();
        Character _asCharacter = this.asCharacter(value);
        this.addUnique(_values, _asCharacter);
      }
    }
    if (!_matched) {
      if (it instanceof JvmByteAnnotationValue) {
        _matched=true;
        EList<Byte> _values = ((JvmByteAnnotationValue)it).getValues();
        Byte _asByte = this.asByte(value);
        this.addUnique(_values, _asByte);
      }
    }
    if (!_matched) {
      if (it instanceof JvmStringAnnotationValue) {
        _matched=true;
        EList<String> _values = ((JvmStringAnnotationValue)it).getValues();
        this.addUnique(_values, ((String) value));
      }
    }
    if (!_matched) {
      if (it instanceof JvmTypeAnnotationValue) {
        _matched=true;
      }
      if (!_matched) {
        if (it instanceof JvmAnnotationAnnotationValue) {
          _matched=true;
        }
      }
      if (!_matched) {
        if (it instanceof JvmEnumAnnotationValue) {
          _matched=true;
        }
      }
      if (_matched) {
        _switchResult = null;
      }
    }
    return _switchResult;
  }
  
  protected Integer asInteger(final Object value) {
    Integer _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (value instanceof Integer) {
        _matched=true;
        _switchResult = ((Integer)value);
      }
    }
    if (!_matched) {
      if (value instanceof Number) {
        _matched=true;
        _switchResult = Integer.valueOf(((Number)value).intValue());
      }
    }
    if (!_matched) {
      _switchResult = ((Integer) value);
    }
    return _switchResult;
  }
  
  protected Long asLong(final Object value) {
    Long _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (value instanceof Long) {
        _matched=true;
        _switchResult = ((Long)value);
      }
    }
    if (!_matched) {
      if (value instanceof Number) {
        _matched=true;
        _switchResult = Long.valueOf(((Number)value).longValue());
      }
    }
    if (!_matched) {
      _switchResult = ((Long) value);
    }
    return _switchResult;
  }
  
  protected Short asShort(final Object value) {
    Short _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (value instanceof Short) {
        _matched=true;
        _switchResult = ((Short)value);
      }
    }
    if (!_matched) {
      if (value instanceof Number) {
        _matched=true;
        _switchResult = Short.valueOf(((Number)value).shortValue());
      }
    }
    if (!_matched) {
      _switchResult = ((Short) value);
    }
    return _switchResult;
  }
  
  protected Float asFloat(final Object value) {
    Float _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (value instanceof Float) {
        _matched=true;
        _switchResult = ((Float)value);
      }
    }
    if (!_matched) {
      if (value instanceof Number) {
        _matched=true;
        _switchResult = Float.valueOf(((Number)value).floatValue());
      }
    }
    if (!_matched) {
      _switchResult = ((Float) value);
    }
    return _switchResult;
  }
  
  protected Double asDouble(final Object value) {
    Double _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (value instanceof Double) {
        _matched=true;
        _switchResult = ((Double)value);
      }
    }
    if (!_matched) {
      if (value instanceof Number) {
        _matched=true;
        _switchResult = Double.valueOf(((Number)value).doubleValue());
      }
    }
    if (!_matched) {
      _switchResult = ((Double) value);
    }
    return _switchResult;
  }
  
  protected Character asCharacter(final Object value) {
    Character _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (value instanceof Character) {
        _matched=true;
        _switchResult = ((Character)value);
      }
    }
    if (!_matched) {
      if (value instanceof Number) {
        _matched=true;
        byte _byteValue = ((Number)value).byteValue();
        _switchResult = Character.valueOf(((char) _byteValue));
      }
    }
    if (!_matched) {
      _switchResult = ((Character) value);
    }
    return _switchResult;
  }
  
  protected Byte asByte(final Object value) {
    Byte _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (value instanceof Byte) {
        _matched=true;
        _switchResult = ((Byte)value);
      }
    }
    if (!_matched) {
      if (value instanceof Number) {
        _matched=true;
        _switchResult = Byte.valueOf(((Number)value).byteValue());
      }
    }
    if (!_matched) {
      _switchResult = ((Byte) value);
    }
    return _switchResult;
  }
  
  protected JvmAnnotationValue createAnnotationValue(final PsiType type) {
    JvmAnnotationValue _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(type, PsiType.BOOLEAN)) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmBooleanAnnotationValue();
      }
    }
    if (!_matched) {
      if (Objects.equal(type, PsiType.INT)) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmIntAnnotationValue();
      }
    }
    if (!_matched) {
      if (Objects.equal(type, PsiType.LONG)) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmLongAnnotationValue();
      }
    }
    if (!_matched) {
      if (Objects.equal(type, PsiType.SHORT)) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmShortAnnotationValue();
      }
    }
    if (!_matched) {
      if (Objects.equal(type, PsiType.FLOAT)) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmFloatAnnotationValue();
      }
    }
    if (!_matched) {
      if (Objects.equal(type, PsiType.DOUBLE)) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmDoubleAnnotationValue();
      }
    }
    if (!_matched) {
      if (Objects.equal(type, PsiType.CHAR)) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmCharAnnotationValue();
      }
    }
    if (!_matched) {
      if (Objects.equal(type, PsiType.BYTE)) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmByteAnnotationValue();
      }
    }
    if (!_matched) {
      boolean _isClassType = this.uriHelper.isClassType(type, String.class);
      if (_isClassType) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmStringAnnotationValue();
      }
    }
    if (!_matched) {
      boolean _isClassType_1 = this.uriHelper.isClassType(type, Class.class);
      if (_isClassType_1) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmTypeAnnotationValue();
      }
    }
    if (!_matched) {
      boolean _isAnnotation = this.uriHelper.isAnnotation(type);
      if (_isAnnotation) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmAnnotationAnnotationValue();
      }
    }
    if (!_matched) {
      boolean _isEnum = this.uriHelper.isEnum(type);
      if (_isEnum) {
        _matched=true;
        _switchResult = this._typesFactory.createJvmEnumAnnotationValue();
      }
    }
    if (!_matched) {
      String _canonicalText = type.getCanonicalText();
      String _plus = ("Unexpected type: " + _canonicalText);
      throw new IllegalArgumentException(_plus);
    }
    return _switchResult;
  }
  
  protected Object computeDefaultValue(final PsiAnnotationMethod method) {
    Project _project = method.getProject();
    JavaPsiFacade _instance = JavaPsiFacade.getInstance(_project);
    PsiConstantEvaluationHelper _constantEvaluationHelper = _instance.getConstantEvaluationHelper();
    PsiAnnotationMemberValue _defaultValue = method.getDefaultValue();
    return _constantEvaluationHelper.computeConstantExpression(_defaultValue);
  }
  
  protected String getPackageName(final PsiClass psiClass) {
    PsiFile _containingFile = psiClass.getContainingFile();
    final PsiJavaFile javaFile = ((PsiJavaFile) _containingFile);
    final String psiPackageName = javaFile.getPackageName();
    boolean _isEmpty = psiPackageName.isEmpty();
    boolean _not = (!_isEmpty);
    if (_not) {
      return psiPackageName;
    }
    return null;
  }
  
  protected JvmConstructor createConstructor(final PsiMethod psiMethod, final StringBuilder fqn) {
    JvmConstructor _createJvmConstructor = this._typesFactory.createJvmConstructor();
    final Procedure1<JvmConstructor> _function = new Procedure1<JvmConstructor>() {
      public void apply(final JvmConstructor it) {
        PsiClassFactory.this.enhanceExecutable(it, psiMethod, fqn);
      }
    };
    return ObjectExtensions.<JvmConstructor>operator_doubleArrow(_createJvmConstructor, _function);
  }
  
  protected JvmOperation createOperation(final PsiMethod method, final StringBuilder fqn) {
    JvmOperation _xblockexpression = null;
    {
      ProgressIndicatorProvider.checkCanceled();
      JvmOperation _createJvmOperation = this._typesFactory.createJvmOperation();
      final Procedure1<JvmOperation> _function = new Procedure1<JvmOperation>() {
        public void apply(final JvmOperation it) {
          PsiClassFactory.this.enhanceExecutable(it, method, fqn);
          PsiModifierList _modifierList = method.getModifierList();
          boolean _hasModifierProperty = _modifierList.hasModifierProperty(PsiModifier.ABSTRACT);
          it.setAbstract(_hasModifierProperty);
          PsiModifierList _modifierList_1 = method.getModifierList();
          boolean _hasModifierProperty_1 = _modifierList_1.hasModifierProperty(PsiModifier.FINAL);
          it.setFinal(_hasModifierProperty_1);
          PsiModifierList _modifierList_2 = method.getModifierList();
          boolean _hasModifierProperty_2 = _modifierList_2.hasModifierProperty(PsiModifier.STATIC);
          it.setStatic(_hasModifierProperty_2);
          PsiModifierList _modifierList_3 = method.getModifierList();
          boolean _hasModifierProperty_3 = _modifierList_3.hasModifierProperty(PsiModifier.STRICTFP);
          it.setStrictFloatingPoint(_hasModifierProperty_3);
          PsiModifierList _modifierList_4 = method.getModifierList();
          boolean _hasModifierProperty_4 = _modifierList_4.hasModifierProperty(PsiModifier.SYNCHRONIZED);
          it.setSynchronized(_hasModifierProperty_4);
          PsiModifierList _modifierList_5 = method.getModifierList();
          boolean _hasModifierProperty_5 = _modifierList_5.hasModifierProperty(PsiModifier.NATIVE);
          it.setNative(_hasModifierProperty_5);
          PsiType _returnType = method.getReturnType();
          JvmTypeReference _createTypeReference = PsiClassFactory.this.createTypeReference(_returnType);
          it.setReturnType(_createTypeReference);
        }
      };
      _xblockexpression = ObjectExtensions.<JvmOperation>operator_doubleArrow(_createJvmOperation, _function);
    }
    return _xblockexpression;
  }
  
  protected void enhanceExecutable(final JvmExecutable it, final PsiMethod psiMethod, final StringBuilder fqn) {
    PsiTypeParameter[] _typeParameters = psiMethod.getTypeParameters();
    for (final PsiTypeParameter typeParameter : _typeParameters) {
      EList<JvmTypeParameter> _typeParameters_1 = it.getTypeParameters();
      JvmTypeParameter _createTypeParameter = this.createTypeParameter(typeParameter);
      this.addUnique(_typeParameters_1, _createTypeParameter);
    }
    String _name = psiMethod.getName();
    StringBuilder _append = fqn.append(_name);
    _append.append("(");
    final PsiParameterList parameterList = psiMethod.getParameterList();
    for (int i = 0; (i < parameterList.getParametersCount()); i++) {
      {
        PsiParameter[] _parameters = parameterList.getParameters();
        final PsiParameter parameter = _parameters[i];
        if ((i != 0)) {
          fqn.append(",");
        }
        PsiType _type = parameter.getType();
        this.uriHelper.appendTypeName(fqn, _type);
        EList<JvmFormalParameter> _parameters_1 = it.getParameters();
        JvmFormalParameter _createFormalParameter = this.createFormalParameter(parameter);
        this.addUnique(_parameters_1, _createFormalParameter);
      }
    }
    StringBuilder _append_1 = fqn.append(")");
    final String identifier = _append_1.toString();
    it.internalSetIdentifier(identifier);
    String _name_1 = psiMethod.getName();
    it.setSimpleName(_name_1);
    this.setVisibility(it, psiMethod);
    boolean _isDeprecated = psiMethod.isDeprecated();
    it.setDeprecated(_isDeprecated);
  }
  
  protected JvmFormalParameter createFormalParameter(final PsiParameter parameter) {
    JvmFormalParameter _createJvmFormalParameter = this._typesFactory.createJvmFormalParameter();
    final Procedure1<JvmFormalParameter> _function = new Procedure1<JvmFormalParameter>() {
      public void apply(final JvmFormalParameter it) {
        String _name = parameter.getName();
        it.setName(_name);
        PsiType _type = parameter.getType();
        JvmTypeReference _createTypeReference = PsiClassFactory.this.createTypeReference(_type);
        it.setParameterType(_createTypeReference);
      }
    };
    return ObjectExtensions.<JvmFormalParameter>operator_doubleArrow(_createJvmFormalParameter, _function);
  }
  
  protected JvmTypeParameter createTypeParameter(final PsiTypeParameter parameter) {
    JvmTypeParameter _createJvmTypeParameter = this._typesFactory.createJvmTypeParameter();
    final Procedure1<JvmTypeParameter> _function = new Procedure1<JvmTypeParameter>() {
      public void apply(final JvmTypeParameter it) {
        String _name = parameter.getName();
        it.setName(_name);
        PsiClassType[] _extendsListTypes = parameter.getExtendsListTypes();
        for (final PsiClassType upperBound : _extendsListTypes) {
          {
            JvmUpperBound _createJvmUpperBound = PsiClassFactory.this._typesFactory.createJvmUpperBound();
            final JvmTypeConstraintImplCustom jvmUpperBound = ((JvmTypeConstraintImplCustom) _createJvmUpperBound);
            JvmTypeReference _createTypeReference = PsiClassFactory.this.createTypeReference(upperBound);
            jvmUpperBound.internalSetTypeReference(_createTypeReference);
            EList<JvmTypeConstraint> _constraints = it.getConstraints();
            PsiClassFactory.this.addUnique(_constraints, jvmUpperBound);
          }
        }
      }
    };
    return ObjectExtensions.<JvmTypeParameter>operator_doubleArrow(_createJvmTypeParameter, _function);
  }
  
  protected JvmTypeReference createTypeReference(final PsiType psiType) {
    JvmTypeReference _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (psiType instanceof PsiArrayType) {
        _matched=true;
        PsiType _componentType = ((PsiArrayType)psiType).getComponentType();
        _switchResult = this.createArrayTypeReference(_componentType);
      }
    }
    if (!_matched) {
      if (psiType instanceof PsiClassType) {
        int _parameterCount = ((PsiClassType)psiType).getParameterCount();
        boolean _notEquals = (_parameterCount != 0);
        if (_notEquals) {
          _matched=true;
          JvmParameterizedTypeReference _createJvmParameterizedTypeReference = this._typesFactory.createJvmParameterizedTypeReference();
          final Procedure1<JvmParameterizedTypeReference> _function = new Procedure1<JvmParameterizedTypeReference>() {
            public void apply(final JvmParameterizedTypeReference it) {
              PsiClassType _rawType = ((PsiClassType)psiType).rawType();
              JvmType _createProxy = PsiClassFactory.this.createProxy(_rawType);
              it.setType(_createProxy);
              PsiType[] _parameters = ((PsiClassType)psiType).getParameters();
              for (final PsiType parameter : _parameters) {
                EList<JvmTypeReference> _arguments = it.getArguments();
                JvmTypeReference _createTypeArgument = PsiClassFactory.this.createTypeArgument(parameter);
                PsiClassFactory.this.addUnique(_arguments, _createTypeArgument);
              }
            }
          };
          _switchResult = ObjectExtensions.<JvmParameterizedTypeReference>operator_doubleArrow(_createJvmParameterizedTypeReference, _function);
        }
      }
    }
    if (!_matched) {
      JvmParameterizedTypeReference _createJvmParameterizedTypeReference = this._typesFactory.createJvmParameterizedTypeReference();
      final Procedure1<JvmParameterizedTypeReference> _function = new Procedure1<JvmParameterizedTypeReference>() {
        public void apply(final JvmParameterizedTypeReference it) {
          JvmType _createProxy = PsiClassFactory.this.createProxy(psiType);
          it.setType(_createProxy);
        }
      };
      _switchResult = ObjectExtensions.<JvmParameterizedTypeReference>operator_doubleArrow(_createJvmParameterizedTypeReference, _function);
    }
    return _switchResult;
  }
  
  protected JvmTypeReference createTypeArgument(final PsiType type) {
    JvmTypeReference _switchResult = null;
    boolean _matched = false;
    if (!_matched) {
      if (type instanceof PsiWildcardType) {
        _matched=true;
        JvmWildcardTypeReference _createJvmWildcardTypeReference = this._typesFactory.createJvmWildcardTypeReference();
        final Procedure1<JvmWildcardTypeReference> _function = new Procedure1<JvmWildcardTypeReference>() {
          public void apply(final JvmWildcardTypeReference it) {
            final PsiType superBound = ((PsiWildcardType)type).getSuperBound();
            boolean _notEquals = (!Objects.equal(superBound, PsiType.NULL));
            if (_notEquals) {
              JvmUpperBound _createJvmUpperBound = PsiClassFactory.this._typesFactory.createJvmUpperBound();
              final JvmTypeConstraintImplCustom upperBound = ((JvmTypeConstraintImplCustom) _createJvmUpperBound);
              JvmTypeReference _createTypeReference = PsiClassFactory.this.createTypeReference(superBound);
              upperBound.internalSetTypeReference(_createTypeReference);
              EList<JvmTypeConstraint> _constraints = it.getConstraints();
              PsiClassFactory.this.addUnique(_constraints, upperBound);
            }
            final PsiType extendsBound = ((PsiWildcardType)type).getExtendsBound();
            boolean _notEquals_1 = (!Objects.equal(extendsBound, PsiType.NULL));
            if (_notEquals_1) {
              JvmLowerBound _createJvmLowerBound = PsiClassFactory.this._typesFactory.createJvmLowerBound();
              final JvmTypeConstraintImplCustom lowerBound = ((JvmTypeConstraintImplCustom) _createJvmLowerBound);
              JvmTypeReference _createTypeReference_1 = PsiClassFactory.this.createTypeReference(extendsBound);
              lowerBound.internalSetTypeReference(_createTypeReference_1);
              EList<JvmTypeConstraint> _constraints_1 = it.getConstraints();
              PsiClassFactory.this.addUnique(_constraints_1, lowerBound);
            }
          }
        };
        _switchResult = ObjectExtensions.<JvmWildcardTypeReference>operator_doubleArrow(_createJvmWildcardTypeReference, _function);
      }
    }
    if (!_matched) {
      _switchResult = this.createTypeReference(type);
    }
    return _switchResult;
  }
  
  protected JvmGenericArrayTypeReference createArrayTypeReference(final PsiType psiComponentType) {
    JvmGenericArrayTypeReference _createJvmGenericArrayTypeReference = this._typesFactory.createJvmGenericArrayTypeReference();
    final Procedure1<JvmGenericArrayTypeReference> _function = new Procedure1<JvmGenericArrayTypeReference>() {
      public void apply(final JvmGenericArrayTypeReference it) {
        JvmTypeReference _createTypeReference = PsiClassFactory.this.createTypeReference(psiComponentType);
        it.setComponentType(_createTypeReference);
      }
    };
    return ObjectExtensions.<JvmGenericArrayTypeReference>operator_doubleArrow(_createJvmGenericArrayTypeReference, _function);
  }
  
  protected JvmType createProxy(final PsiType psiType) {
    JvmType _elvis = null;
    JvmType _get = this.typeProxies.get(psiType);
    if (_get != null) {
      _elvis = _get;
    } else {
      JvmVoid _createJvmVoid = this._typesFactory.createJvmVoid();
      _elvis = _createJvmVoid;
    }
    final Procedure1<JvmType> _function = new Procedure1<JvmType>() {
      public void apply(final JvmType it) {
        final URI uri = PsiClassFactory.this.uriHelper.getFullURI(psiType);
        if ((it instanceof InternalEObject)) {
          ((InternalEObject)it).eSetProxyURI(uri);
        }
        PsiClassFactory.this.typeProxies.put(psiType, it);
      }
    };
    return ObjectExtensions.<JvmType>operator_doubleArrow(_elvis, _function);
  }
  
  protected void createNestedTypes(final JvmDeclaredType it, final PsiClass psiClass, final StringBuilder fqn) {
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
      final Procedure0 _function_1 = new Procedure0() {
        public void apply() {
          EList<JvmMember> _members = it.getMembers();
          JvmDeclaredType _createType = PsiClassFactory.this.createType(innerClass, fqn);
          PsiClassFactory.this.addUnique(_members, _createType);
        }
      };
      this.preserve(fqn, _function_1);
    }
  }
  
  protected boolean isAnonymous(final PsiClass psiClass) {
    return (psiClass instanceof PsiAnonymousClass);
  }
  
  protected boolean isSynthetic(final PsiClass psiClass) {
    return false;
  }
  
  protected void setVisibility(final JvmMember it, final PsiModifierListOwner modifierListOwner) {
    JvmVisibility _switchResult = null;
    PsiModifierList _modifierList = modifierListOwner.getModifierList();
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
  
  protected void setTypeModifiers(final JvmDeclaredType it, final PsiClass psiClass) {
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
  
  protected void addUnique(final EList<?> list, final Object object) {
    ((InternalEList<Object>) list).addUnique(object);
  }
  
  protected StringBuilder append(final StringBuilder builder, final String value, final Procedure0 procedure) {
    StringBuilder _xblockexpression = null;
    {
      final int length = builder.length();
      builder.append(value);
      procedure.apply();
      builder.setLength(length);
      _xblockexpression = builder;
    }
    return _xblockexpression;
  }
  
  protected StringBuilder preserve(final StringBuilder builder, final Procedure0 procedure) {
    StringBuilder _xblockexpression = null;
    {
      final int length = builder.length();
      procedure.apply();
      builder.setLength(length);
      _xblockexpression = builder;
    }
    return _xblockexpression;
  }
}
