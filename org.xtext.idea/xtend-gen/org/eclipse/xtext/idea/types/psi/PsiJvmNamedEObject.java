package org.eclipse.xtext.idea.types.psi;

import java.util.List;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub;
import org.eclipse.xtext.psi.PsiNamedEObject;

@SuppressWarnings("all")
public interface PsiJvmNamedEObject extends PsiNamedEObject<PsiJvmNamedEObjectStub> {
  public abstract List<PsiJvmDeclaredType> findClasses(final String qualifiedName);
  
  public abstract List<PsiJvmDeclaredType> findClassesByName(final String shortName);
  
  public abstract List<PsiJvmDeclaredType> getClasses();
}
