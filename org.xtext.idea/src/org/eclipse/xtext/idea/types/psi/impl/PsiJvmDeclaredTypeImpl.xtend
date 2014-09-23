package org.eclipse.xtext.idea.types.psi.impl

import com.google.inject.Inject
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.impl.light.AbstractLightClass
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmOperation
import org.eclipse.xtext.common.types.descriptions.IStubGenerator
import org.eclipse.xtext.idea.lang.IXtextLanguage
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.psi.PsiNamedEObject
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.AbstractResourceDescription

class PsiJvmDeclaredTypeImpl extends AbstractLightClass implements PsiJvmDeclaredType {

	@Inject
	IStubGenerator stubGenerator

	@Inject
	IQualifiedNameConverter qualifiedNameConverter

	PsiClass delegate

	@Accessors(PUBLIC_GETTER)
	val EClass type

	@Accessors(PUBLIC_GETTER)
	val String qualifiedName
	
	val PsiNamedEObject psiNamedEObject
	
	val JvmDeclaredType declaredType

	new(JvmDeclaredType declaredType, PsiNamedEObject psiNamedEObject) {
		super(psiNamedEObject.manager, psiNamedEObject.language)
		this.type = type
		this.declaredType = declaredType
		this.qualifiedName = qualifiedName
		this.psiNamedEObject = psiNamedEObject
		val language = language
		if (language instanceof IXtextLanguage) {
			language.injectMembers(this)
		}
	}

	override copy() {
		new PsiJvmDeclaredTypeImpl(declaredType, psiNamedEObject)
	}

	override getDelegate() {
		if (delegate == null) {
			val text = '''
				package «declaredType.packageName»;
				
				class «declaredType.simpleName» {
				
					«FOR method:declaredType.members.filter(JvmOperation)»
					public void «method.simpleName»(r) {
					}
					«ENDFOR»
				
				}
			''' 

			switch psiFile : PsiFileFactory.getInstance(project).createFileFromText("_Dummy_.java", JavaFileType.INSTANCE,text) {
				PsiJavaFile: delegate = psiFile.classes.head
			}
		}
		delegate
	}
	
	override getNavigationElement() {
		psiNamedEObject
	}
	
	override isValid() {
		psiNamedEObject.valid
	}
	
	override isEquivalentTo(PsiElement another) {
		if (another instanceof PsiJvmDeclaredType) {
			return isEquivalent(another)
		}
		false
	}
	
	protected def isEquivalent(PsiJvmDeclaredType one, PsiJvmDeclaredType another) {
		one.qualifiedName == another.qualifiedName
	}

}

class JvmDeclaredTypeResourceDescription extends AbstractResourceDescription {

	val List<IEObjectDescription> exportedObjects

	new(QualifiedName qualifiedName, EClass type) {
		exportedObjects = Collections.singletonList(new JvmDeclaredTypeEObjectDescription(qualifiedName, type))
	}

	override protected computeExportedObjects() {
		exportedObjects
	}

	override getImportedNames() {
		emptyList
	}

	override getReferenceDescriptions() {
		emptyList
	}

	override getURI() {
		null
	}

}

class JvmDeclaredTypeEObjectDescription implements IEObjectDescription {

	val EClass type

	val QualifiedName name

	new(QualifiedName name, EClass type) {
		this.name = name
		this.type = type
	}

	override getName() {
		name
	}

	override getQualifiedName() {
		name
	}

	override getEClass() {
		type
	}

	override getEObjectOrProxy() {
		throw new UnsupportedOperationException("getEObjectOrProxy")
	}

	override getEObjectURI() {
		throw new UnsupportedOperationException("getEObjectURI")
	}

	override getUserData(String key) {
		null
	}

	override getUserDataKeys() {
		emptyList
	}

}
