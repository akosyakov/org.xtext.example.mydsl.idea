package org.eclipse.xtext.idea.jvmmodel;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.impl.light.LightMethod;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmOperation;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmDeclaredTypeImpl;
import org.eclipse.xtext.psi.IPsiModelAssociations;
import org.eclipse.xtext.psi.IPsiModelAssociator;
import org.eclipse.xtext.psi.PsiElementProvider;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

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
    boolean _matched = false;
    if (!_matched) {
      if (jvmElement instanceof JvmDeclaredType) {
        _matched=true;
        final PsiElement psiElement = this._iPsiModelAssociations.getPsiElement(sourceElement);
        if ((psiElement instanceof PsiNamedEObject)) {
          final PsiElementProvider _function = new PsiElementProvider() {
            public PsiElement get() {
              return new PsiJvmDeclaredTypeImpl(((JvmDeclaredType)jvmElement), ((PsiNamedEObject)psiElement));
            }
          };
          this._iPsiModelAssociator.associate(jvmElement, _function);
        }
      }
    }
    if (!_matched) {
      if (jvmElement instanceof JvmOperation) {
        _matched=true;
        final PsiElement psiElement = this._iPsiModelAssociations.getPsiElement(sourceElement);
        final PsiElementProvider _function = new PsiElementProvider() {
          public PsiElement get() {
            LightMethod _xblockexpression = null;
            {
              JvmDeclaredType _declaringType = ((JvmOperation)jvmElement).getDeclaringType();
              PsiElement _psiElement = PsiJvmModelAssociator.this._iPsiModelAssociations.getPsiElement(_declaringType);
              final PsiClass containingClass = ((PsiClass) _psiElement);
              PsiMethod[] _methods = containingClass.getMethods();
              final Function1<PsiMethod, Boolean> _function = new Function1<PsiMethod, Boolean>() {
                public Boolean apply(final PsiMethod it) {
                  String _name = it.getName();
                  String _simpleName = ((JvmOperation)jvmElement).getSimpleName();
                  return Boolean.valueOf(Objects.equal(_name, _simpleName));
                }
              };
              final PsiMethod method = IterableExtensions.<PsiMethod>findFirst(((Iterable<PsiMethod>)Conversions.doWrapArray(_methods)), _function);
              PsiManager _manager = method.getManager();
              LightMethod _lightMethod = new LightMethod(_manager, method, containingClass);
              final Procedure1<LightMethod> _function_1 = new Procedure1<LightMethod>() {
                public void apply(final LightMethod it) {
                  it.setNavigationElement(psiElement);
                }
              };
              _xblockexpression = ObjectExtensions.<LightMethod>operator_doubleArrow(_lightMethod, _function_1);
            }
            return _xblockexpression;
          }
        };
        this._iPsiModelAssociator.associate(jvmElement, _function);
      }
    }
  }
  
  public void associatePrimary(final EObject sourceElement, final EObject jvmElement) {
    super.associatePrimary(sourceElement, jvmElement);
    if ((jvmElement instanceof JvmDeclaredType)) {
      final PsiElement psiElement = this._iPsiModelAssociations.getPsiElement(sourceElement);
      if ((psiElement instanceof PsiNamedEObject)) {
        final PsiElementProvider _function = new PsiElementProvider() {
          public PsiElement get() {
            return new PsiJvmDeclaredTypeImpl(((JvmDeclaredType)jvmElement), ((PsiNamedEObject)psiElement));
          }
        };
        this._iPsiModelAssociator.associatePrimary(jvmElement, _function);
      }
    }
  }
}
