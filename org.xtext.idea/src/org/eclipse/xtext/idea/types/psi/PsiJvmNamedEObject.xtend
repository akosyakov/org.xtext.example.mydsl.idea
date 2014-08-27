package org.eclipse.xtext.idea.types.psi

import java.util.List
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub
import org.eclipse.xtext.psi.PsiNamedEObject

interface PsiJvmNamedEObject extends PsiNamedEObject<PsiJvmNamedEObjectStub> {
	
	def List<PsiJvmDeclaredType> findClasses(String qualifiedName)
	
	def List<PsiJvmDeclaredType> findClassesByName(String shortName)
	
	def List<PsiJvmDeclaredType> getClasses() 
	
}