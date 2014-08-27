package org.eclipse.xtext.idea.types.psi

import com.intellij.psi.PsiClass
import org.eclipse.emf.ecore.EClass

interface PsiJvmDeclaredType extends PsiClass {
	
	def EClass getType()

}
