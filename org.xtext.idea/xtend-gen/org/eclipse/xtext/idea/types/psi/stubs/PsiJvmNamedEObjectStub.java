package org.eclipse.xtext.idea.types.psi.stubs;

import java.util.List;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmDeclaredTypeDTO;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;

@SuppressWarnings("all")
public interface PsiJvmNamedEObjectStub extends PsiNamedEObjectStub<PsiJvmNamedEObject> {
  public abstract List<PsiJvmDeclaredTypeDTO> getClasses();
}
