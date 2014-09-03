package org.eclipse.xtext.idea.types.psi.search

import com.intellij.openapi.application.QueryExecutorBase
import com.intellij.psi.PsiReference
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.search.searches.ReferencesSearch.SearchParameters
import com.intellij.util.Processor
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject

class JvmTypesReferencesSearch extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters> {

	override processQuery(SearchParameters queryParameters, Processor<PsiReference> consumer) {
		val element = queryParameters.elementToSearch
 		if (element instanceof PsiJvmNamedEObject) {
			for (psiClass : element.classes) {
				queryParameters.optimizer.searchWord(psiClass.name, queryParameters.effectiveSearchScope, true, psiClass)	
			}
		}
	}

}
