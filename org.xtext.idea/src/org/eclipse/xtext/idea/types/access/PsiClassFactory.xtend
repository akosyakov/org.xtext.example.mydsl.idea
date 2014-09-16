package org.eclipse.xtext.idea.types.access

import com.google.inject.Inject
import com.intellij.psi.JavaPsiFacade
import com.intellij.psi.PsiAnnotationMethod
import com.intellij.psi.PsiAnonymousClass
import com.intellij.psi.PsiArrayType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiModifierListOwner
import com.intellij.psi.PsiParameter
import com.intellij.psi.PsiType
import com.intellij.psi.PsiTypeParameter
import com.intellij.psi.PsiWildcardType
import java.lang.reflect.Array
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.InternalEList
import org.eclipse.xtext.common.types.JvmAnnotationAnnotationValue
import org.eclipse.xtext.common.types.JvmAnnotationValue
import org.eclipse.xtext.common.types.JvmBooleanAnnotationValue
import org.eclipse.xtext.common.types.JvmByteAnnotationValue
import org.eclipse.xtext.common.types.JvmCharAnnotationValue
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmDoubleAnnotationValue
import org.eclipse.xtext.common.types.JvmEnumAnnotationValue
import org.eclipse.xtext.common.types.JvmExecutable
import org.eclipse.xtext.common.types.JvmFloatAnnotationValue
import org.eclipse.xtext.common.types.JvmIntAnnotationValue
import org.eclipse.xtext.common.types.JvmLongAnnotationValue
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.JvmShortAnnotationValue
import org.eclipse.xtext.common.types.JvmStringAnnotationValue
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.xtext.common.types.JvmTypeAnnotationValue
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.common.types.access.impl.ITypeFactory
import org.eclipse.xtext.common.types.impl.JvmTypeConstraintImplCustom
import org.eclipse.xtext.psi.PsiModelAssociations.PsiAdapter
import org.eclipse.xtext.util.internal.Stopwatches
import com.intellij.psi.PsiField
import com.intellij.psi.PsiEnumConstant
import com.intellij.openapi.progress.ProgressIndicatorProvider

class PsiClassFactory implements ITypeFactory<PsiClass, JvmDeclaredType> {

	val final createTypeTask = Stopwatches.forTask("PsiClassFactory.createType")

	/**
	 * A cache mapping each type to its corresponding type proxy.
	 * It's demand populated when {@link #createProxy(PsiType) creating} a type proxy.
	 */
	val Map<PsiType, JvmType> typeProxies = new HashMap<PsiType, JvmType>();

	val extension TypesFactory = TypesFactory.eINSTANCE

	val extension StubURIHelper uriHelper

	@Inject
	new(StubURIHelper uriHelper) {
		this.uriHelper = uriHelper
	}

	override createType(PsiClass psiClass) {
		try {
			createTypeTask.start
			val fqn = new StringBuilder(100)
			val packageName = psiClass.packageName
			if (packageName != null) {
				fqn.append(packageName).append('.')
			}
			val type = psiClass.createType(fqn)
			type.packageName = packageName
			type.eAdapters.add(new PsiAdapter(psiClass))
			type
		} finally {
			createTypeTask.stop
		}
	}
	
	protected def JvmDeclaredType createType(PsiClass psiClass, StringBuilder fqn) {
		ProgressIndicatorProvider.checkCanceled
		if (psiClass.anonymous || psiClass.synthetic) {
			throw new IllegalStateException("Cannot create type for anonymous or synthetic classes")
		}
		switch psiClass {
			case psiClass.annotationType: createJvmAnnotationType
			case psiClass.enum: createJvmEnumerationType
			default:
				createJvmGenericType => [
					interface = psiClass.interface
					strictFloatingPoint = psiClass.modifierList.hasModifierProperty(PsiModifier.STRICTFP)
				]
		} => [
			ProgressIndicatorProvider.checkCanceled
			setTypeModifiers(psiClass)
			setVisibility(psiClass)
			deprecated = psiClass.deprecated
			
			simpleName = psiClass.name
			fqn.append(psiClass.name)
			internalSetIdentifier(fqn.toString)
			
			fqn.append('$') [|
				createNestedTypes(psiClass, fqn)	
			]

			fqn.append('.')
			createMethods(psiClass, fqn)	
			if (!psiClass.annotationType) {
				createFields(psiClass, fqn)				
			}
			createSuperTypes(psiClass)
		]
	}
	
	protected def createFields(JvmDeclaredType it, PsiClass psiClass, StringBuilder fqn) {
		for (field : psiClass.fields) {
			fqn.preserve [|
				members.addUnique(field.createField(fqn))
			]
		}
	}
	
	protected def Object createField(PsiField field, StringBuilder fqn) {
		ProgressIndicatorProvider.checkCanceled
		switch field {
			PsiEnumConstant:
				createJvmEnumerationLiteral
			default:
				createJvmField => [
					val value = field.computeConstantValue
					if (value != null) {
						constant = true
						constantValue = value
					} else {
						constant = false
					}
				]
		} => [
			internalSetIdentifier(fqn.append(field.name).toString)
			simpleName = field.name
			final = field.modifierList.hasModifierProperty(PsiModifier.FINAL)
			static = field.modifierList.hasModifierProperty(PsiModifier.STATIC)
			transient = field.modifierList.hasModifierProperty(PsiModifier.TRANSIENT)
			volatile = field.modifierList.hasModifierProperty(PsiModifier.VOLATILE)
			deprecated = field.deprecated
			setVisibility(field)
			type = field.type.createTypeReference
		]
	}
	
	protected def createSuperTypes(JvmDeclaredType it, PsiClass psiClass) {
		for (superType : psiClass.superTypes) {
			superTypes.addUnique(superType.createTypeReference)
		}
	}
	
	protected def createMethods(JvmDeclaredType it, PsiClass psiClass, StringBuilder fqn) {
		for (method : psiClass.methods) {
			fqn.preserve [|
				val operation = if (method.constructor) {
						method.createConstructor(fqn)
					} else {
						method.createOperation(fqn) => [
							setDefaultValue(method)
						]
					}
				members.addUnique(operation)
			]
		}
	}
	
	protected def setDefaultValue(JvmOperation operation, PsiMethod method) {
		if (method instanceof PsiAnnotationMethod) {
			val defaultValue = method.computeDefaultValue
			if (defaultValue != null) {
				val returnType = method.returnType
				val annotationValue = if (returnType.array) {
						createArrayAnnotationValue(defaultValue, returnType)
					} else {
						createAnnotationValue(defaultValue, returnType)
					}
				operation.defaultValue = annotationValue
				annotationValue.operation = operation
			}
		}
	}
	
	protected def createAnnotationValue(Object value, PsiType type) {
		type.createAnnotationValue => [
			addValue(value)
		]
	}
	
	protected def createArrayAnnotationValue(Object value, PsiType type) {
		if (type instanceof PsiArrayType) {
			val componentType = type.componentType
			return componentType.createAnnotationValue => [
				if (type.primitive || type.isClassType(String)) {
					if (value.class.array) {
						val length = Array.getLength(value)
						for (var i = 0; i < length; i++) {
							addValue(Array.get(value, i))
						}
					} else {
						addValue(value)
					}
				} else if (type.isClassType(Class)) {
					// TODO
				} else if (type.annotation) {
					// TODO
				} else if (type.enum) {
					// TODO
				}
 			]
		}
		throw new IllegalArgumentException("type is not an array type: " + type.canonicalText)
	}
	
	protected def addValue(JvmAnnotationValue it, Object value) {
		switch it {
			JvmBooleanAnnotationValue: values.addUnique(value as Boolean)
			JvmIntAnnotationValue: values.addUnique(value.asInteger)
			JvmLongAnnotationValue: values.addUnique(value.asLong)
			JvmShortAnnotationValue: values.addUnique(value.asShort)
			JvmFloatAnnotationValue: values.addUnique(value.asFloat)
			JvmDoubleAnnotationValue: values.addUnique(value.asDouble)
			JvmCharAnnotationValue: values.addUnique(value.asCharacter)
			JvmByteAnnotationValue: values.addUnique(value.asByte)
			JvmStringAnnotationValue: values.addUnique(value as String)
			JvmTypeAnnotationValue,
			JvmAnnotationAnnotationValue,
			JvmEnumAnnotationValue: {
				// TODO
			}
		}
	}
	
	protected def asInteger(Object value) {
		switch value {
			Integer: value
			Number: value.intValue
			default: value as Integer 
		}
	}
	
	protected def asLong(Object value) {
		switch value {
			Long: value
			Number: value.longValue
			default: value as Long
		}
	}
	
	protected def asShort(Object value) {
		switch value {
			Short: value
			Number: value.shortValue
			default: value as Short
		}
	}
	
	protected def asFloat(Object value) {
		switch value {
			Float: value
			Number: value.floatValue
			default: value as Float 
		}
	}
	
	protected def asDouble(Object value) {
		switch value {
			Double: value
			Number: value.doubleValue
			default: value as Double
		}
	}
	
	protected def asCharacter(Object value) {
		switch value {
			Character: value
			Number: value.byteValue as char
			default: value as Character
		}
	}
	
	protected def asByte(Object value) {
		switch value {
			Byte: value
			Number: value.byteValue
			default: value as Byte
		}
	}
	
	protected def createAnnotationValue(PsiType type) {
		switch type {
			case PsiType.BOOLEAN: createJvmBooleanAnnotationValue
			case PsiType.INT: createJvmIntAnnotationValue
			case PsiType.LONG: createJvmLongAnnotationValue
			case PsiType.SHORT: createJvmShortAnnotationValue
			case PsiType.FLOAT: createJvmFloatAnnotationValue
			case PsiType.DOUBLE: createJvmDoubleAnnotationValue
			case PsiType.CHAR: createJvmCharAnnotationValue
			case PsiType.BYTE: createJvmByteAnnotationValue
			case type.isClassType(String): createJvmStringAnnotationValue
			case type.isClassType(Class): createJvmTypeAnnotationValue
			case type.annotation: createJvmAnnotationAnnotationValue
			case type.enum: createJvmEnumAnnotationValue
			default: throw new IllegalArgumentException("Unexpected type: " + type.canonicalText)
		}
	}
	
	protected def computeDefaultValue(PsiAnnotationMethod method) {
		JavaPsiFacade.getInstance(method.project).constantEvaluationHelper.computeConstantExpression(method.defaultValue)
	}
	
	protected def getPackageName(PsiClass psiClass) {
		val javaFile = psiClass.containingFile as PsiJavaFile
		val psiPackageName = javaFile.getPackageName
		if (!psiPackageName.empty) {
			return psiPackageName 	
		}
		return null
	}
	
	protected def createConstructor(PsiMethod psiMethod, StringBuilder fqn) {
		createJvmConstructor => [
			enhanceExecutable(psiMethod, fqn)
		]
	}
	
	protected def createOperation(PsiMethod method, StringBuilder fqn) {
		ProgressIndicatorProvider.checkCanceled
		createJvmOperation => [
			enhanceExecutable(method, fqn)
			abstract = method.modifierList.hasModifierProperty(PsiModifier.ABSTRACT)
			final = method.modifierList.hasModifierProperty(PsiModifier.FINAL)
			static = method.modifierList.hasModifierProperty(PsiModifier.STATIC)
			strictFloatingPoint = method.modifierList.hasModifierProperty(PsiModifier.STRICTFP)
			synchronized = method.modifierList.hasModifierProperty(PsiModifier.SYNCHRONIZED)
			native = method.modifierList.hasModifierProperty(PsiModifier.NATIVE)
			returnType = method.returnType.createTypeReference
		]
	}
	
	protected def enhanceExecutable(JvmExecutable it, PsiMethod psiMethod, StringBuilder fqn) {
		for(typeParameter : psiMethod.typeParameters) {
			typeParameters.addUnique(typeParameter.createTypeParameter)
		}
		fqn.append(psiMethod.name).append('(')
		val parameterList = psiMethod.parameterList
		for (var i = 0; i < parameterList.parametersCount; i++) {
			val parameter = parameterList.parameters.get(i)
			if (i != 0) {
				fqn.append(',')
			}
			fqn.appendTypeName(parameter.type)
			parameters.addUnique(parameter.createFormalParameter)	
		}
		val identifier = fqn.append(')').toString
		internalSetIdentifier(identifier)
		simpleName = psiMethod.name
		setVisibility(psiMethod)
		deprecated = psiMethod.deprecated
	}
	
	protected def createFormalParameter(PsiParameter parameter) {
		createJvmFormalParameter => [
			name = parameter.name
			parameterType = parameter.type.createTypeReference
		]
	}
	
	protected def createTypeParameter(PsiTypeParameter parameter) {
		createJvmTypeParameter => [
			name = parameter.name
			for (upperBound : parameter.extendsListTypes) {
				val jvmUpperBound = createJvmUpperBound as JvmTypeConstraintImplCustom
				jvmUpperBound.internalSetTypeReference(upperBound.createTypeReference)
				constraints.addUnique(jvmUpperBound)
			}
		]
	}
	
	protected def JvmTypeReference createTypeReference(PsiType psiType) {
		switch psiType {
			PsiArrayType: {
				psiType.componentType.createArrayTypeReference	
			}
			PsiClassType case psiType.parameterCount != 0: {
				createJvmParameterizedTypeReference => [
					type = psiType.rawType.createProxy
					for (parameter : psiType.parameters) {
						arguments.addUnique(parameter.createTypeArgument)
					}
				]
			}
			default:
				createJvmParameterizedTypeReference => [
					type = psiType.createProxy
				] 
		}
	}
	
	protected def createTypeArgument(PsiType type) {
		switch type {
			PsiWildcardType:
				createJvmWildcardTypeReference => [
					val superBound = type.superBound
					if (superBound != PsiType.NULL) {
						val upperBound = createJvmUpperBound as JvmTypeConstraintImplCustom
						upperBound.internalSetTypeReference(superBound.createTypeReference)
						constraints.addUnique(upperBound)
					}
					
					val extendsBound = type.extendsBound
					if (extendsBound != PsiType.NULL) {
						val lowerBound = createJvmLowerBound as JvmTypeConstraintImplCustom
						lowerBound.internalSetTypeReference(extendsBound.createTypeReference)
						constraints.addUnique(lowerBound)
					}
				]
			default:
				type.createTypeReference
		}
	}
	
	protected def createArrayTypeReference(PsiType psiComponentType) {
		createJvmGenericArrayTypeReference => [
			componentType = psiComponentType.createTypeReference 
		]
	}
	
	protected def JvmType createProxy(PsiType psiType) {
		typeProxies.get(psiType) ?: createJvmVoid => [
			val uri = psiType.fullURI
			if (it instanceof InternalEObject) {
				eSetProxyURI(uri)
			}
			typeProxies.put(psiType, it)	
		]	
	}
	
	protected def createNestedTypes(JvmDeclaredType it, PsiClass psiClass, StringBuilder fqn) {
		for (innerClass : psiClass.innerClasses.filter [
			!anonymous && !synthetic
		]) {
			fqn.preserve [|
				members.addUnique(innerClass.createType(fqn))	
			]
		}
	}
	
	protected def isAnonymous(PsiClass psiClass) {
		psiClass instanceof PsiAnonymousClass
	}
	
	protected def isSynthetic(PsiClass psiClass) {
//		psiClass instanceof PsiSyntheticClass
		false
	}
	
	protected def setVisibility(JvmMember it, PsiModifierListOwner modifierListOwner) {
		visibility = switch modifierList : modifierListOwner.modifierList {
			case modifierList.hasModifierProperty(PsiModifier.PRIVATE): JvmVisibility.PRIVATE
			case modifierList.hasModifierProperty(PsiModifier.PROTECTED): JvmVisibility.PROTECTED
			case modifierList.hasModifierProperty(PsiModifier.PUBLIC): JvmVisibility.PUBLIC
		}
	}
	
	protected def setTypeModifiers(JvmDeclaredType it, PsiClass psiClass) {
		abstract = psiClass.modifierList.hasModifierProperty(PsiModifier.ABSTRACT)
		static = psiClass.modifierList.hasModifierProperty(PsiModifier.STATIC)
		if (!psiClass.enum) {
			final = psiClass.modifierList.hasModifierProperty(PsiModifier.FINAL)
		}
	}
	
	protected def void addUnique(EList<?> list, Object object) {
		(list as InternalEList<Object>).addUnique(object)
	}
	
	protected def append(StringBuilder builder, String value, ()=>void procedure) {
		val length = builder.length
		builder.append(value)
		procedure.apply
		builder.setLength(length)
		builder
	}
	
	protected def preserve(StringBuilder builder, ()=>void procedure) {
		val length = builder.length
		procedure.apply
		builder.setLength(length)
		builder
	}

}
