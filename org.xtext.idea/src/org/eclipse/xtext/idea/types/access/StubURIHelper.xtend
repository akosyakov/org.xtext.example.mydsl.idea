package org.eclipse.xtext.idea.types.access

import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiPrimitiveType
import com.intellij.psi.PsiType
import com.intellij.psi.PsiTypeParameter
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.common.types.access.impl.Primitives
import org.eclipse.xtext.common.types.access.impl.URIHelperConstants

class StubURIHelper implements URIHelperConstants {
	
	def getFullURI(String name) {
		createURIBuilder.appendClassResourceURI(name).append('#').appendTypeFragment(name).createURI
	}
	
	def createResourceURI(String name) {
		createURIBuilder.appendClassResourceURI(name).createURI
	}
	
	def getFragment(String name) {
		createFragmentBuilder.appendTypeFragment(name).toString
	}
	
	protected def appendClassResourceURI(StringBuilder builder, String name) {
		val endIndex = name.indexOf('[')
		val typeName = if (endIndex == -1) name else name.substring(0, endIndex)
		if (Primitives.ALL_PRIMITIVE_TYPES.exists[type | type.name == typeName]) {
			builder.append(PRIMITIVES)
		} else {
			builder.append(OBJECTS).append(typeName)
		}
	}
	
	protected def appendTypeFragment(StringBuilder builder, String name) {
		builder.append(name)
	}
	
	def getFullURI(PsiType type) {
		createURIBuilder.appendTypeResourceURI(type).append('#').appendTypeFragment(type).createURI
	}
	
	protected def StringBuilder appendTypeResourceURI(StringBuilder builder, PsiType type) {
		switch type {
			PsiArrayType: builder.appendTypeResourceURI(type.componentType)
			PsiPrimitiveType: builder.append(PRIMITIVES)
			PsiClassType:
				switch resolvedType : type.resolve {
					PsiTypeParameter: builder.appendTypeParameterResourceURI(resolvedType)
					PsiClass: builder.appendClassResourceURI(resolvedType)
					default: builder.append(type.getCanonicalText(false))
				}
			default:
				throw new IllegalStateException("unexpected type: " + type)
		}
	}
	
	protected def appendTypeParameterResourceURI(StringBuilder builder, PsiTypeParameter typeParameter) {
		switch owner : typeParameter.owner {
			PsiClass: builder.appendClassResourceURI(owner)
			PsiMethod: builder.appendClassResourceURI(owner.containingClass)
		}
	}
	
	protected def StringBuilder appendClassResourceURI(StringBuilder builder, PsiClass psiClass) {
		val containingClass = psiClass.containingClass
		if (containingClass != null) {
			builder.appendClassResourceURI(containingClass)
		} else {
			builder.append(OBJECTS).append(psiClass.qualifiedName)
		}
	}
	
	protected def StringBuilder appendTypeFragment(StringBuilder builder, PsiType type) {
		switch type {
			PsiPrimitiveType: builder.append(type.getCanonicalText(false))
			PsiClassType: {
				switch resolvedType : type.resolve {
					PsiTypeParameter: builder.appendTypeParameterFragment(resolvedType)
					PsiClass: builder.appendClassFragment(resolvedType) 
					default: builder.append(type.getCanonicalText(false))
				}
			}
			PsiArrayType: builder.appendTypeFragment(type.componentType).append('[]')
			default: throw new IllegalStateException("unknown type: " + type)
		}
	}
	
	protected def appendTypeParameterFragment(StringBuilder builder, PsiTypeParameter typeParameter) {
		switch owner : typeParameter.owner {
			PsiClass: builder.appendClassFragment(owner) 
			PsiMethod: builder.appendMethodFragment(owner)
		}
		builder.append('''/«typeParameter.name»''')
	}
	
	protected def StringBuilder appendClassFragment(StringBuilder builder, PsiClass psiClass) {
		val containingClass = psiClass.containingClass
		if (containingClass == null) {
			builder.append(psiClass.qualifiedName)	
		} else {
			builder.appendClassFragment(containingClass).append('$').append(psiClass.name)	
		}
	}
	
	protected def appendMethodFragment(StringBuilder builder, PsiMethod method) {
		builder.appendClassFragment(method.containingClass)
		builder.append('''.«method.name»(''')
		val parameterCount = method.parameterList.parametersCount
		for (var i = 0; i < parameterCount; i++) {
			if (i != 0) {
				builder.append(',')
			}
			builder.appendTypeName(method.parameterList.parameters.get(i).type)
		}
		builder.append(')')
	}
	
	def StringBuilder appendTypeName(StringBuilder builder, PsiType type) {
		switch type {
			PsiPrimitiveType: builder.append(type.getCanonicalText(false))
			PsiClassType: {
				switch resolvedType : type.resolve {
					PsiTypeParameter: builder.append(resolvedType.name)
					default: builder.append(resolvedType.qualifiedName) 
				}
			}
			PsiArrayType: builder.appendTypeName(type.componentType).append('[]')
			default: throw new IllegalStateException("unknown type: " + type)
		}
	}

	protected def createURIBuilder() {
		new StringBuilder(48).append(URIHelperConstants.PROTOCOL).append(':')
	}
	
	protected def createFragmentBuilder() {
		new StringBuilder(32)
	}

	protected def createURI(StringBuilder uriBuilder) {
		URI.createURI(uriBuilder.toString())
	}
	
	def isPrimitive(PsiType type) {
		type instanceof PsiPrimitiveType
	}
	
	def isClassType(PsiType type, Class<?> clazz) {
		if (type instanceof PsiClassType) {
			type.resolve.qualifiedName == clazz.name
		} else {
			false
		}
	}
	
	def isAnnotation(PsiType type) {
		if (type instanceof PsiClassType) {
			type.resolve.annotationType
		} else {
			false
		}
	}
	
	def isEnum(PsiType type) {
		if (type instanceof PsiClassType) {
			type.resolve.enum
		} else {
			false
		}
	}
	
	def isArray(PsiType type) {
		type instanceof PsiArrayType
	}
	
}