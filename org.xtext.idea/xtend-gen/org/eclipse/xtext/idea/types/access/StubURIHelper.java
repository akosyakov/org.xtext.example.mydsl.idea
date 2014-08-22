package org.eclipse.xtext.idea.types.access;

import com.intellij.psi.PsiClass;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.common.types.access.impl.URIHelperConstants;

@SuppressWarnings("all")
public class StubURIHelper implements URIHelperConstants {
  public URI createResourceURI(final PsiClass psiClass) {
    StringBuilder _createURIBuilder = this.createURIBuilder();
    StringBuilder _appendResourceURI = this.appendResourceURI(_createURIBuilder, psiClass);
    return this.createURI(_appendResourceURI);
  }
  
  protected StringBuilder createURIBuilder() {
    StringBuilder _stringBuilder = new StringBuilder(48);
    StringBuilder _append = _stringBuilder.append(URIHelperConstants.PROTOCOL);
    return _append.append(":");
  }
  
  protected URI createURI(final StringBuilder uriBuilder) {
    String _string = uriBuilder.toString();
    return URI.createURI(_string);
  }
  
  protected StringBuilder appendResourceURI(final StringBuilder builder, final PsiClass psiClass) {
    StringBuilder _append = builder.append(URIHelperConstants.OBJECTS);
    String _qualifiedName = psiClass.getQualifiedName();
    return _append.append(_qualifiedName);
  }
  
  public String getFragment(final PsiClass psiClass) {
    StringBuilder _stringBuilder = new StringBuilder(32);
    StringBuilder _appendFragment = this.appendFragment(_stringBuilder, psiClass);
    return _appendFragment.toString();
  }
  
  protected StringBuilder appendFragment(final StringBuilder builder, final PsiClass psiClass) {
    String _qualifiedName = psiClass.getQualifiedName();
    return builder.append(_qualifiedName);
  }
  
  public URI getFullURI(final PsiClass psiClass) {
    StringBuilder _createURIBuilder = this.createURIBuilder();
    StringBuilder _appendResourceURI = this.appendResourceURI(_createURIBuilder, psiClass);
    StringBuilder _append = _appendResourceURI.append("#");
    StringBuilder _appendFragment = this.appendFragment(_append, psiClass);
    return this.createURI(_appendFragment);
  }
  
  public URI getFullURI(final String qualifiedName) {
    StringBuilder _createURIBuilder = this.createURIBuilder();
    StringBuilder _appendResourceURI = this.appendResourceURI(_createURIBuilder, qualifiedName);
    StringBuilder _append = _appendResourceURI.append("#");
    StringBuilder _appendFragment = this.appendFragment(_append, qualifiedName);
    return this.createURI(_appendFragment);
  }
  
  protected StringBuilder appendResourceURI(final StringBuilder builder, final String qualifiedName) {
    StringBuilder _append = builder.append(URIHelperConstants.OBJECTS);
    return _append.append(qualifiedName);
  }
  
  protected StringBuilder appendFragment(final StringBuilder builder, final String qualifiedName) {
    return builder.append(qualifiedName);
  }
}
