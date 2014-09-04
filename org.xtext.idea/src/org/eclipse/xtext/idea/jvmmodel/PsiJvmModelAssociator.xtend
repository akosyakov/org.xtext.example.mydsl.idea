package org.eclipse.xtext.idea.jvmmodel

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmDeclaredTypeImpl
import org.eclipse.xtext.psi.IPsiModelAssociations
import org.eclipse.xtext.psi.IPsiModelAssociator
import org.eclipse.xtext.psi.PsiNamedEObject
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator

class PsiJvmModelAssociator extends JvmModelAssociator {

	@Inject
	extension IPsiModelAssociator

	@Inject
	extension IPsiModelAssociations

	override associate(EObject sourceElement, EObject jvmElement) {
		super.associate(sourceElement, jvmElement)
		if (jvmElement instanceof JvmDeclaredType) {
			val psiElement = sourceElement.psiElement
			if (psiElement instanceof PsiNamedEObject) {
				jvmElement.associate [
					new PsiJvmDeclaredTypeImpl(jvmElement, psiElement)
				]
			}
		}
	}

	override associatePrimary(EObject sourceElement, EObject jvmElement) {
		super.associatePrimary(sourceElement, jvmElement)
		if (jvmElement instanceof JvmDeclaredType) {
			val psiElement = sourceElement.psiElement
			if (psiElement instanceof PsiNamedEObject) {
				jvmElement.associatePrimary [
					new PsiJvmDeclaredTypeImpl(jvmElement, psiElement)
				]
			}
		}
	}

}
