package org.eclipse.xtext.idea.types.psi.search;

import com.intellij.openapi.application.QueryExecutorBase;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.SearchRequestCollector;
import com.intellij.psi.search.SearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import java.util.List;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;

@SuppressWarnings("all")
public class JvmTypesReferencesSearch extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters> {
  public void processQuery(final ReferencesSearch.SearchParameters queryParameters, final Processor<PsiReference> consumer) {
    final PsiElement element = queryParameters.getElementToSearch();
    if ((element instanceof PsiJvmNamedEObject)) {
      List<PsiJvmDeclaredType> _classes = ((PsiJvmNamedEObject)element).getClasses();
      for (final PsiJvmDeclaredType psiClass : _classes) {
        SearchRequestCollector _optimizer = queryParameters.getOptimizer();
        String _name = psiClass.getName();
        SearchScope _effectiveSearchScope = queryParameters.getEffectiveSearchScope();
        _optimizer.searchWord(_name, _effectiveSearchScope, true, psiClass);
      }
    }
  }
}
