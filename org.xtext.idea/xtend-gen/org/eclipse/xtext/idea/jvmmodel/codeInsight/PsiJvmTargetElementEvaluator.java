package org.eclipse.xtext.idea.jvmmodel.codeInsight;

import com.intellij.codeInsight.TargetElementEvaluator;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;

@SuppressWarnings("all")
public class PsiJvmTargetElementEvaluator implements TargetElementEvaluator {
  public PsiElement getElementByReference(final PsiReference ref, final int flags) {
    PsiElement _switchResult = null;
    PsiElement _resolve = ref.resolve();
    final PsiElement element = _resolve;
    boolean _matched = false;
    if (!_matched) {
      if (element instanceof PsiJvmDeclaredType) {
        _matched=true;
        _switchResult = ((PsiJvmDeclaredType)element).getNavigationElement();
      }
    }
    if (!_matched) {
      _switchResult = element;
    }
    return _switchResult;
  }
  
  public boolean includeSelfInGotoImplementation(final PsiElement element) {
    return true;
  }
}
