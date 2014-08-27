package org.eclipse.xtext.idea.types.psi.impl;

import java.util.Collections;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.idea.types.psi.impl.JvmDeclaredTypeEObjectDescription;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@SuppressWarnings("all")
public class JvmDeclaredTypeResourceDescription extends AbstractResourceDescription {
  private final List<IEObjectDescription> exportedObjects;
  
  public JvmDeclaredTypeResourceDescription(final QualifiedName qualifiedName, final EClass type) {
    JvmDeclaredTypeEObjectDescription _jvmDeclaredTypeEObjectDescription = new JvmDeclaredTypeEObjectDescription(qualifiedName, type);
    List<IEObjectDescription> _singletonList = Collections.<IEObjectDescription>singletonList(_jvmDeclaredTypeEObjectDescription);
    this.exportedObjects = _singletonList;
  }
  
  protected List<IEObjectDescription> computeExportedObjects() {
    return this.exportedObjects;
  }
  
  public Iterable<QualifiedName> getImportedNames() {
    return CollectionLiterals.<QualifiedName>emptyList();
  }
  
  public Iterable<IReferenceDescription> getReferenceDescriptions() {
    return CollectionLiterals.<IReferenceDescription>emptyList();
  }
  
  public URI getURI() {
    return null;
  }
}
