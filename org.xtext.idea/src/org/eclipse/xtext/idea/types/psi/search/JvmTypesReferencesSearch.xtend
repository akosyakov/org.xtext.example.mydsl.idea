package org.eclipse.xtext.idea.types.psi.search

import com.google.inject.Inject
import com.intellij.openapi.application.QueryExecutorBase
import com.intellij.psi.PsiReference
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.search.searches.ReferencesSearch.SearchParameters
import com.intellij.util.Processor
import org.eclipse.xtext.idea.lang.IXtextLanguage
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredTypes
import org.eclipse.xtext.psi.PsiNamedEObject

class JvmTypesReferencesSearch extends QueryExecutorBase<PsiReference, ReferencesSearch.SearchParameters> {
	
	@Inject
	extension PsiJvmDeclaredTypes
	
	val IXtextLanguage language
	
	new(IXtextLanguage language) {
		this.language = language
		this.language.injectMembers(this)
	}
	
	override processQuery(SearchParameters queryParameters, Processor<PsiReference> consumer) {
		val element = queryParameters.elementToSearch
		if (element.language != language) {
			return
		}
 		if (element instanceof PsiNamedEObject<?>) {
 			for (psiJvmDeclaredType : element.psiJvmDeclaredTypes) {
				queryParameters.optimizer.searchWord(psiJvmDeclaredType.name, queryParameters.effectiveSearchScope, true, psiJvmDeclaredType)
			}
		}
	}

}
