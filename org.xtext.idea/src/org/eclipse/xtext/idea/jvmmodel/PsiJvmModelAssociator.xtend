package org.eclipse.xtext.idea.jvmmodel

import com.google.inject.Inject
import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiPrimitiveType
import com.intellij.psi.PsiType
import com.intellij.psi.impl.light.LightField
import com.intellij.psi.impl.light.LightMethod
import com.intellij.psi.impl.light.LightParameter
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.common.types.JvmArrayType
import org.eclipse.xtext.common.types.JvmConstructor
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmExecutable
import org.eclipse.xtext.common.types.JvmField
import org.eclipse.xtext.common.types.JvmFormalParameter
import org.eclipse.xtext.common.types.JvmGenericArrayTypeReference
import org.eclipse.xtext.common.types.JvmParameterizedTypeReference
import org.eclipse.xtext.common.types.JvmPrimitiveType
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.JvmUnknownTypeReference
import org.eclipse.xtext.common.types.JvmVoid
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmDeclaredTypeImpl
import org.eclipse.xtext.psi.IPsiModelAssociations
import org.eclipse.xtext.psi.IPsiModelAssociator
import org.eclipse.xtext.psi.PsiElementProvider
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator
import org.eclipse.xtext.xtype.XFunctionTypeRef
import com.intellij.psi.PsiEnumConstant
import org.eclipse.xtext.common.types.JvmEnumerationLiteral

class PsiJvmModelAssociator extends JvmModelAssociator {

	@Inject
	extension IPsiModelAssociator

	@Inject
	extension IPsiModelAssociations

	override associate(EObject sourceElement, EObject jvmElement) {
		super.associate(sourceElement, jvmElement)
		val psiElementProvider = createPsiElementProvider(sourceElement, jvmElement)
		if (psiElementProvider != null) {
			jvmElement.associate(psiElementProvider)
		}
	}

	override associatePrimary(EObject sourceElement, EObject jvmElement) {
		super.associatePrimary(sourceElement, jvmElement)
		val psiElementProvider = createPsiElementProvider(sourceElement, jvmElement)
		if (psiElementProvider != null) {
			jvmElement.associatePrimary(psiElementProvider)
		}
	}

	def PsiElementProvider createPsiElementProvider(EObject sourceElement, EObject jvmElement) {
		switch jvmElement {
			JvmDeclaredType: {
				return [
					new PsiJvmDeclaredTypeImpl(jvmElement, sourceElement.psiElement)
				]
			}
			JvmField: {
				return [
					val containingClass = jvmElement.declaringType.psiElement as PsiClass
					val field = containingClass.findField(jvmElement)
					if (field == null)
						return null
					switch field {
						LightField: field
						default: new LightField(field.manager, field, containingClass)
					} => [
						navigationElement = sourceElement.psiElement
					]
				]
			}
			JvmExecutable: {
				return [
					val containingClass = jvmElement.declaringType.psiElement as PsiClass
					val method = containingClass.findMethod(jvmElement)
					if (method == null)
						return null
					switch method {
						LightMethod: method
						default: new LightMethod(method.manager, method, containingClass)
					} => [
						navigationElement = sourceElement.psiElement
					]
				]
			}
			JvmFormalParameter: {
				return [
					val jvmExecutable = jvmElement.eContainer as JvmExecutable
					val containingClass = jvmExecutable.declaringType.psiElement as PsiClass
					val method = containingClass.findMethod(jvmExecutable)
					val parameter = method?.findParameter(jvmElement)
					if (parameter == null)
						return null
					switch parameter {
						LightParameter:
							parameter
						default:
							new LightParameter(parameter.name, parameter.type, method, method.language,
								parameter.varArgs)
					} => [
						navigationElement = sourceElement.psiElement
					]
				]
			}
		}
	}

	protected def findParameter(PsiMethod it, JvmFormalParameter jvmFormalParameter) {
		parameterList.parameters.findFirst[
			name == jvmFormalParameter.simpleName
		]
	}
	
	protected def findField(PsiClass it, JvmField field) {
		fields.findFirst [
			if (name != field.simpleName) {
				return false
			}
			if ((it instanceof PsiEnumConstant) != field instanceof JvmEnumerationLiteral) {
				return false
			}
			return equals(field.type, type)
		]
	}

	protected def findMethod(PsiClass it, JvmExecutable jvmExecutable) {
		methods.findFirst [
			if (name != jvmExecutable.simpleName) {
				return false
			}
			if (jvmExecutable instanceof JvmConstructor && !constructor) {
				return false
			}
			val parametersCount = parameterList.parametersCount
			if (parametersCount != jvmExecutable.parameters.size) {
				return false
			}
			for (var i = 0; i < parametersCount; i++) {
				val psiParameter = parameterList.parameters.get(i)
				val jvmFormalParameter = jvmExecutable.parameters.get(i)
				if (psiParameter.name != jvmFormalParameter.simpleName) {
					return false
				}
				if (!equals(jvmFormalParameter.parameterType, psiParameter.type)) {
					return false
				}
			}
			true
		]
	}

	protected def boolean equals(JvmTypeReference jvmTypeReference, PsiType psiType) {
		switch jvmTypeReference {
			JvmParameterizedTypeReference:
				// TODO: arguments, inner type reference
				equals(jvmTypeReference.type, psiType)
			JvmGenericArrayTypeReference: {
				if (psiType instanceof PsiArrayType)
					equals(jvmTypeReference.componentType, psiType.componentType)
				else
					false
			}
			XFunctionTypeRef: {

				// TODO
				false
			}
			JvmUnknownTypeReference: {
				if (psiType instanceof PsiClassType)
					psiType.resolve == null
				else
					false
			}
			default:
				if (psiType.equalsToText(Object.name))
					true
				else
					throw new IllegalStateException("Unexpected jvmTypeReference: " + jvmTypeReference)
		}
	}

	protected def boolean equals(JvmType jvmType, PsiType psiType) {
		switch jvmType {
			case null: false
			JvmArrayType: {
				if (psiType instanceof PsiArrayType)
					equals(jvmType.componentType, psiType.componentType)
				else
					false
			}
			JvmDeclaredType:
				if (psiType instanceof PsiClassType)
					psiType.equalsToText(jvmType.qualifiedName)
				else
					false
			JvmVoid,
			JvmPrimitiveType:
				if (psiType instanceof PsiPrimitiveType)
					psiType.equalsToText(jvmType.simpleName)
				else
					false
			default:
				throw new IllegalStateException("Unexpected jvmType: " + jvmType)
		}
	}

}
