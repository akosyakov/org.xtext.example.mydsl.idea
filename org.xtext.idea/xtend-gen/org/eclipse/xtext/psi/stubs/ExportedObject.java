package org.eclipse.xtext.psi.stubs;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.xbase.lib.Pure;

@FinalFieldsConstructor
@SuppressWarnings("all")
public class ExportedObject {
  @Accessors
  private final String name;
  
  @Accessors
  private final String qualifiedName;
  
  @Accessors
  private final EClass type;
  
  public ExportedObject(final String name, final String qualifiedName, final EClass type) {
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
