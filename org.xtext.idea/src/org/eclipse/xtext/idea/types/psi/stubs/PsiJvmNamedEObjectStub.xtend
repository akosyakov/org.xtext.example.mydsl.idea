package org.eclipse.xtext.idea.types.psi.stubs

import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject
import org.eclipse.xtext.psi.PsiNamedEObjectStub

interface PsiJvmNamedEObjectStub extends PsiNamedEObjectStub<PsiJvmNamedEObject> {
	
	def List<PsiJvmDeclaredTypeDTO> getClasses()
	
}

@Accessors
class PsiJvmDeclaredTypeDTO {
	val String name
	val String qualifiedName
	val EClass type
}