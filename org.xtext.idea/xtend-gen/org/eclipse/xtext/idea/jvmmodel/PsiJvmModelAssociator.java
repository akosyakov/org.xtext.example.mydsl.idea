package org.eclipse.xtext.idea.jvmmodel;

import com.google.inject.Inject;
import com.intellij.psi.PsiElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmDeclaredTypeImpl;
import org.eclipse.xtext.psi.IPsiModelAssociations;
import org.eclipse.xtext.psi.IPsiModelAssociator;
import org.eclipse.xtext.psi.PsiElementProvider;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator;
import org.eclipse.xtext.xbase.lib.Extension;

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
    if ((jvmElement instanceof JvmDeclaredType)) {
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
