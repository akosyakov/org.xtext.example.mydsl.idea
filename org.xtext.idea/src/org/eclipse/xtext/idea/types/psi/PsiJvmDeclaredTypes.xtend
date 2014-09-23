package org.eclipse.xtext.idea.types.psi

import com.google.inject.Inject
import com.google.inject.Singleton
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.psi.IPsiModelAssociations
import org.eclipse.xtext.psi.PsiNamedEObject
import org.eclipse.xtext.psi.impl.BaseXtextFile
import org.eclipse.xtext.resource.DerivedStateAwareResource
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations

import static org.eclipse.xtext.common.types.TypesPackage.Literals.JVM_DECLARED_TYPE

@Singleton
class PsiJvmDeclaredTypes {

	@Inject
	extension IPsiModelAssociations

	@Inject
	extension IJvmModelAssociations

	def getPsiJvmDeclaredTypes(PsiNamedEObject<?> psiNamedEObject) {
		switch xtextFile : psiNamedEObject.containingFile {
			BaseXtextFile: xtextFile.resource.installDerivedState
		}

		val result = newArrayList
		for (jvmDecaredType : psiNamedEObject.EObject.jvmElements.filter(JvmDeclaredType)) {
			result += jvmDecaredType.psiElement as PsiJvmDeclaredType
		}
		result
	}

	def getPsiJvmDeclaredTypesByName(BaseXtextFile it, String name) {
		val resource = resource

		val result = newArrayList
		for (description : resourceDescription.getExportedObjectsByType(JVM_DECLARED_TYPE)) {
			switch jvmDeclaredType : resource.resourceSet.getEObject(description.EObjectURI, true) {
				JvmDeclaredType case jvmDeclaredType.simpleName == name:
					result += jvmDeclaredType.psiElement as PsiJvmDeclaredType
			}
		}
		result
	}

	def getPsiJvmDeclaredTypes(BaseXtextFile it, QualifiedName qualifiedName) {
		val resource = resource

		val result = newArrayList
		for (description : resourceDescription.getExportedObjects(JVM_DECLARED_TYPE, qualifiedName, false)) {
			switch jvmDeclaredType : resource.resourceSet.getEObject(description.EObjectURI, true) {
				JvmDeclaredType:
					result += jvmDeclaredType.psiElement as PsiJvmDeclaredType
			}
		}
		result
	}

	protected def installDerivedState(Resource resource) {
		if (resource instanceof DerivedStateAwareResource) {
			val deliver = resource.eDeliver
			try {
				resource.eSetDeliver(false)
				resource.installDerivedState(false)
			} finally {
				resource.eSetDeliver(deliver)
			}
		}
	}

}
