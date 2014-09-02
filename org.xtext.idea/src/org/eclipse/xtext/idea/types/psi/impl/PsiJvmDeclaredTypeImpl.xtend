package org.eclipse.xtext.idea.types.psi.impl

import com.google.inject.Inject
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.lang.Language
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.PsiManager
import com.intellij.psi.impl.light.AbstractLightClass
import java.util.Collections
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.descriptions.IStubGenerator
import org.eclipse.xtext.generator.InMemoryFileSystemAccess
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

	protected new(JvmDeclaredType declaredType, PsiNamedEObject psiNamedEObject, PsiManager manager, Language language) {
		this(declaredType.qualifiedName, declaredType.eClass, psiNamedEObject, manager, language)
	}

	new(String qualifiedName, EClass type, PsiNamedEObject psiNamedEObject, PsiManager manager, Language language) {
		super(manager, language)
		if (language instanceof IXtextLanguage) {
			language.injectMembers(this)
		}
		this.type = type
		this.qualifiedName = qualifiedName
		this.psiNamedEObject = psiNamedEObject
	}

	override copy() {
		new PsiJvmDeclaredTypeImpl(qualifiedName, type, psiNamedEObject, manager, language)
	}

	override getDelegate() {
		if (delegate == null) {
			val fsa = new InMemoryFileSystemAccess
			stubGenerator.doGenerateStubs(fsa,
				new JvmDeclaredTypeResourceDescription(qualifiedNameConverter.toQualifiedName(qualifiedName), type))
			val text = fsa.textFiles.entrySet.head.value

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
	
	override equals(Object object) {
		if (this === object) { 
			return true
		}
		if (object instanceof PsiJvmDeclaredTypeImpl) {
			return qualifiedName == object.qualifiedName
		}
		false
	}
	
	override hashCode() {
		qualifiedName.hashCode
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
