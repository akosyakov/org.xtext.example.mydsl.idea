package org.eclipse.xtext.idea.types.psi.impl;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;
import java.util.List;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmDeclaredTypeDTO;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectStubImpl;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class PsiJvmNamedEObjectStubImpl extends PsiNamedEObjectStubImpl<PsiJvmNamedEObject> implements PsiJvmNamedEObjectStub {
  @Accessors
  private List<PsiJvmDeclaredTypeDTO> classes;
  
  public PsiJvmNamedEObjectStubImpl(final StubElement parent, final StringRef name, final QualifiedName qualifiedName, final EClass type, final IStubElementType<? extends PsiNamedEObjectStub, ? extends PsiNamedEObject> elementType) {
    super(parent, name, qualifiedName, type, elementType);
  }
  
  @Pure
  public List<PsiJvmDeclaredTypeDTO> getClasses() {
    return this.classes;
  }
  
  public void setClasses(final List<PsiJvmDeclaredTypeDTO> classes) {
    this.classes = classes;
  }
}
