package org.eclipse.xtext.idea.jvmmodel

import com.google.inject.Inject
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmIdentifiableElement
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmDeclaredTypeImpl
import org.eclipse.xtext.psi.IPsiModelAssociations
import org.eclipse.xtext.psi.PsiModelAssociations.PsiAdapter
import org.eclipse.xtext.psi.PsiNamedEObject
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations
import org.eclipse.xtext.xbase.jvmmodel.JvmModelCompleter

class PsiJvmModelCompleter extends JvmModelCompleter {

	@Inject
	extension IJvmModelAssociations

	@Inject
	extension IPsiModelAssociations

	override complete(JvmIdentifiableElement element) {
		super.complete(element)
		if (element instanceof JvmDeclaredType) {
			val psiElement = element.primarySourceElement.psiElement
			if (psiElement instanceof PsiNamedEObject) {
				element.eAdapters += new PsiAdapter(
					new PsiJvmDeclaredTypeImpl(element, psiElement, psiElement.manager, psiElement.language)
				)
			}
		}
	}

}
