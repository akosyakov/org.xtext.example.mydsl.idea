package org.eclipse.xtext.idea.types.psi.search;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.lang.Language;
import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchRequestCollector;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import java.util.ArrayList;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredTypes;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.xbase.lib.Extension;

@SuppressWarnings("all")
public class JvmTypesReferencesSearch extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters> {
  @Inject
  @Extension
  private PsiJvmDeclaredTypes _psiJvmDeclaredTypes;
  
  private final IXtextLanguage language;
  
  public JvmTypesReferencesSearch(final IXtextLanguage language) {
    this.language = language;
    this.language.injectMembers(this);
  }
  
  public void processQuery(final ReferencesSearch.SearchParameters queryParameters, final Processor<PsiReference> consumer) {
    final PsiElement element = queryParameters.getElementToSearch();
    Language _language = element.getLanguage();
    boolean _notEquals = (!Objects.equal(_language, this.language));
    if (_notEquals) {
      return;
    }
    if ((element instanceof PsiNamedEObject<?>)) {
      ArrayList<PsiJvmDeclaredType> _psiJvmDeclaredTypes = this._psiJvmDeclaredTypes.getPsiJvmDeclaredTypes(((PsiNamedEObject<?>)element));
      for (final PsiJvmDeclaredType psiJvmDeclaredType : _psiJvmDeclaredTypes) {
        SearchRequestCollector _optimizer = queryParameters.getOptimizer();
        String _name = psiJvmDeclaredType.getName();
        SearchScope _effectiveSearchScope = queryParameters.getEffectiveSearchScope();
        _optimizer.searchWord(_name, _effectiveSearchScope, true, psiJvmDeclaredType);
      }
    }
  }
}
