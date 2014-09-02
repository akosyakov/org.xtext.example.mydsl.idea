package org.eclipse.xtext.idea.jvmmodel;

import com.google.inject.Inject;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiManager;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.JvmIdentifiableElement;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmDeclaredTypeImpl;
import org.eclipse.xtext.psi.IPsiModelAssociations;
import org.eclipse.xtext.psi.PsiModelAssociations;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations;
import org.eclipse.xtext.xbase.jvmmodel.JvmModelCompleter;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class PsiJvmModelCompleter extends JvmModelCompleter {
  @Inject
  @Extension
  private IJvmModelAssociations _iJvmModelAssociations;
  
  @Inject
  @Extension
  private IPsiModelAssociations _iPsiModelAssociations;
  
  public void complete(final JvmIdentifiableElement element) {
    super.complete(element);
    if ((element instanceof JvmDeclaredType)) {
      EObject _primarySourceElement = this._iJvmModelAssociations.getPrimarySourceElement(element);
      final PsiElement psiElement = this._iPsiModelAssociations.getPsiElement(_primarySourceElement);
      if ((psiElement instanceof PsiNamedEObject)) {
        EList<Adapter> _eAdapters = ((JvmDeclaredType)element).eAdapters();
        PsiManager _manager = ((PsiNamedEObject)psiElement).getManager();
        Language _language = ((PsiNamedEObject)psiElement).getLanguage();
        PsiJvmDeclaredTypeImpl _psiJvmDeclaredTypeImpl = new PsiJvmDeclaredTypeImpl(((JvmDeclaredType)element), ((PsiNamedEObject)psiElement), _manager, _language);
        PsiModelAssociations.PsiAdapter _psiAdapter = new PsiModelAssociations.PsiAdapter(_psiJvmDeclaredTypeImpl);
        _eAdapters.add(_psiAdapter);
      }
    }
  }
}
