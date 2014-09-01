package org.eclipse.xtext.idea.types.psi

import com.google.inject.Inject
import org.eclipse.xtext.psi.PsiModelAssociations
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmDeclaredType

class PsiJvmModelAssociations extends PsiModelAssociations {
	
	@Inject
	extension IJvmModelAssociations
	
	override getPsiElement(EObject object) {
		val result = super.getPsiElement(object)
		if (result != null) {
			return result
		}
		if (object instanceof JvmDeclaredType) {
			return super.getPsiElement(object.primarySourceElement)
		}
		null
	}
	
}