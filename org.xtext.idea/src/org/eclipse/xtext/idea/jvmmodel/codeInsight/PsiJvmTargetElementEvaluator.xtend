package org.eclipse.xtext.idea.jvmmodel.codeInsight

import com.intellij.codeInsight.TargetElementEvaluator
import com.intellij.psi.PsiReference
import com.intellij.psi.PsiElement
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType

class PsiJvmTargetElementEvaluator implements TargetElementEvaluator {
	
	override getElementByReference(PsiReference ref, int flags) {
		switch element : ref.resolve {
			PsiJvmDeclaredType: element.navigationElement
			default: element
		}
	}
	
	override includeSelfInGotoImplementation(PsiElement element) {
		true
	}
	
}