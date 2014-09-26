package org.eclipse.xtext.idea.resource.impl;

import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;

@FinalFieldsConstructor
@SuppressWarnings("all")
public class PsiFileBasedResourceDescription extends AbstractResourceDescription implements IResourceDescription {
  private final BaseXtextFile xtextFile;
  
  protected List<IEObjectDescription> computeExportedObjects() {
    return this.xtextFile.getExportedObjects();
  }
  
  public Iterable<QualifiedName> getImportedNames() {
    return CollectionLiterals.<QualifiedName>emptyList();
  }
  
  public Iterable<IReferenceDescription> getReferenceDescriptions() {
    return CollectionLiterals.<IReferenceDescription>emptyList();
  }
  
  public URI getURI() {
    return this.xtextFile.getURI();
  }
  
  public PsiFileBasedResourceDescription(final BaseXtextFile xtextFile) {
    super();
    this.xtextFile = xtextFile;
  }
}