package org.eclipse.xtext.psi;

import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.StubBasedPsiElement;

public interface PsiNamedEObject extends PsiEObject, PsiNameIdentifierOwner, StubBasedPsiElement<PsiNamedEObjectStub> {

}
