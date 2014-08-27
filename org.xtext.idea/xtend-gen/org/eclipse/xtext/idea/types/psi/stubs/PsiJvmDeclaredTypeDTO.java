package org.eclipse.xtext.idea.types.psi.stubs;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.xbase.lib.Pure;

@Accessors
@SuppressWarnings("all")
public class PsiJvmDeclaredTypeDTO {
  private final String name;
  
  private final String qualifiedName;
  
  private final EClass type;
  
  public PsiJvmDeclaredTypeDTO(final String name, final String qualifiedName, final EClass type) {
    super();
    this.name = name;
    this.qualifiedName = qualifiedName;
    this.type = type;
  }
  
  @Pure
  public String getName() {
    return this.name;
  }
  
  @Pure
  public String getQualifiedName() {
    return this.qualifiedName;
  }
  
  @Pure
  public EClass getType() {
    return this.type;
  }
}
