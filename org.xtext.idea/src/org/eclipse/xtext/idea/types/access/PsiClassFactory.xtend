package org.eclipse.xtext.idea.types.access

import com.google.inject.Inject
import com.intellij.psi.PsiAnonymousClass
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiClassType
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiMethod
import com.intellij.psi.PsiModifier
import com.intellij.psi.PsiSyntheticClass
import com.intellij.psi.PsiTypeParameter
import java.util.HashMap
import java.util.Map
import org.eclipse.emf.ecore.InternalEObject
import org.eclipse.emf.ecore.util.InternalEList
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmGenericType
import org.eclipse.xtext.common.types.JvmMember
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.xtext.common.types.JvmTypeConstraint
import org.eclipse.xtext.common.types.JvmTypeParameter
import org.eclipse.xtext.common.types.JvmTypeReference
import org.eclipse.xtext.common.types.JvmVisibility
import org.eclipse.xtext.common.types.TypesFactory
import org.eclipse.xtext.common.types.access.impl.ITypeFactory
import org.eclipse.xtext.common.types.impl.JvmTypeConstraintImplCustom
import org.eclipse.xtext.psi.PsiModelAssociations.PsiAdapter
import org.eclipse.xtext.util.internal.Stopwatches

class PsiClassFactory implements ITypeFactory<PsiClass, JvmDeclaredType> {

	val final createTypeTask = Stopwatches.forTask("PsiClassFactory.createType")

	/**
	 * A cache mapping each type to its corresponding type proxy.
	 * It's demand populated when {@link #createProxy(PsiClassType) creating} a type proxy.
	 */
	val Map<PsiClass, JvmType> typeProxies = new HashMap<PsiClass, JvmType>();

	val extension TypesFactory = TypesFactory.eINSTANCE

	val extension StubURIHelper uriHelper

	@Inject
	new(StubURIHelper uriHelper) {
		this.uriHelper = uriHelper
	}

	override createType(PsiClass psiClass) {
		try {
			createTypeTask.start
			if (psiClass.anonymous || psiClass.synthetic) {
				throw new IllegalStateException("Cannot create type for anonymous or synthetic classes")
			}
			createJvmGenericType => [
				interface = psiClass.interface
				strictFloatingPoint = psiClass.modifierList.hasModifierProperty(PsiModifier.STRICTFP)
				setTypeModifiers(psiClass)
				setVisibility(psiClass)
				internalSetIdentifier(psiClass.qualifiedName)
				simpleName = psiClass.name
				if (psiClass.containingClass == null) {
					val javaFile = psiClass.containingFile as PsiJavaFile
					val psiPackageName = javaFile.getPackageName
					if (!psiPackageName.empty) {
						packageName = psiPackageName 	
					}
				}
//				createNestedTypes(psiClass)
//				val members = members as InternalEList<JvmMember>
//				for (method : psiClass.methods) {
//					// TODO: filter out synthetic methods
//					val operation = method.createOperation
//					members.addUnique(operation) 
//				}
				eAdapters.add(new PsiAdapter(psiClass))
			]
		} finally {
			createTypeTask.stop
		}
	}
	
	protected def createOperation(PsiMethod psiMethod) {
		createJvmOperation => [
			val jvmTypeParameters = typeParameters as InternalEList<JvmTypeParameter>
			for(typeParameter : psiMethod.typeParameters) {
				jvmTypeParameters.addUnique(typeParameter.createTypeParameter)
			}
		]
	}
	
	protected def createTypeParameter(PsiTypeParameter parameter) {
		createJvmTypeParameter => [
			name = parameter.name
			val constraints = constraints as InternalEList<JvmTypeConstraint>
			for (upperBound : parameter.extendsListTypes) {
				val jvmUpperBound = createJvmUpperBound as JvmTypeConstraintImplCustom
				jvmUpperBound.internalSetTypeReference(upperBound.createTypeReference)
				constraints.addUnique(jvmUpperBound)
			}
		]
	}
	
	protected def JvmTypeReference createTypeReference(PsiClassType psiClassType) {
		createJvmParameterizedTypeReference => [
			type = psiClassType.createProxy
		]
	}
	
	protected def JvmType createProxy(PsiClassType psiClassType) {
		val psiClass = psiClassType.resolve
		typeProxies.get(psiClass) ?: createJvmVoid => [
			val uri = psiClass.fullURI
			if (it instanceof InternalEObject) {
				eSetProxyURI(uri)
			}
			typeProxies.put(psiClass, it)	
		]	
	}
	
	protected def createNestedTypes(JvmGenericType it, PsiClass psiClass) {
		val members = members as InternalEList<JvmMember>
		for (innerClass : psiClass.innerClasses.filter [
			!anonymous && !synthetic
		]) {
			members.addUnique(innerClass.createType)
		}
	}
	
	protected def isAnonymous(PsiClass psiClass) {
		psiClass instanceof PsiAnonymousClass
	}
	
	protected def isSynthetic(PsiClass psiClass) {
		psiClass instanceof PsiSyntheticClass
	}
	
	protected def setVisibility(JvmGenericType it, PsiClass psiClass) {
		visibility = switch modifierList : psiClass.modifierList {
			case modifierList.hasModifierProperty(PsiModifier.PRIVATE): JvmVisibility.PRIVATE
			case modifierList.hasModifierProperty(PsiModifier.PROTECTED): JvmVisibility.PROTECTED
			case modifierList.hasModifierProperty(PsiModifier.PUBLIC): JvmVisibility.PUBLIC
		}
	}
	
	protected def setTypeModifiers(JvmGenericType it, PsiClass psiClass) {
		abstract = psiClass.modifierList.hasModifierProperty(PsiModifier.ABSTRACT)
		static = psiClass.modifierList.hasModifierProperty(PsiModifier.STATIC)
		if (!psiClass.enum) {
			final = psiClass.modifierList.hasModifierProperty(PsiModifier.FINAL)
		}
	}

}
