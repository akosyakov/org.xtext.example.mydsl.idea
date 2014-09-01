package org.eclipse.xtext.idea.types.psi;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.psi.PsiElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.psi.PsiModelAssociations;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class PsiJvmModelAssociations extends PsiModelAssociations {
  @Inject
  @Extension
  private IJvmModelAssociations _iJvmModelAssociations;
  
  public PsiElement getPsiElement(final EObject object) {
    Object _xblockexpression = null;
    {
      final PsiElement result = super.getPsiElement(object);
      boolean _notEquals = (!Objects.equal(result, null));
      if (_notEquals) {
        return result;
      }
      if ((object instanceof JvmDeclaredType)) {
        EObject _primarySourceElement = this._iJvmModelAssociations.getPrimarySourceElement(object);
        return super.getPsiElement(_primarySourceElement);
      }
      _xblockexpression = null;
    }
    return ((PsiElement)_xblockexpression);
  }
}
