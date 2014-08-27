package org.eclipse.xtext.idea.types.psi.impl;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class JvmDeclaredTypeEObjectDescription implements IEObjectDescription {
  private final EClass type;
  
  private final QualifiedName name;
  
  public JvmDeclaredTypeEObjectDescription(final QualifiedName name, final EClass type) {
    this.name = name;
    this.type = type;
  }
  
  public QualifiedName getName() {
    return this.name;
  }
  
  public QualifiedName getQualifiedName() {
    return this.name;
  }
  
  public EClass getEClass() {
    return this.type;
  }
  
  public EObject getEObjectOrProxy() {
    throw new UnsupportedOperationException("getEObjectOrProxy");
  }
  
  public URI getEObjectURI() {
    throw new UnsupportedOperationException("getEObjectURI");
  }
  
  public String getUserData(final String key) {
    return null;
  }
  
  public String[] getUserDataKeys() {
    return ((String[])Conversions.unwrapArray(CollectionLiterals.<String>emptyList(), String.class));
  }
}
