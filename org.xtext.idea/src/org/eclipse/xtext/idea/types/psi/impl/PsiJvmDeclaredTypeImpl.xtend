package org.eclipse.xtext.idea.types.psi.impl

import com.google.inject.Inject
import com.intellij.ide.highlighter.JavaFileType
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.impl.light.AbstractLightClass
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.idea.lang.IXtextLanguage
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType
import org.eclipse.xtext.xbase.compiler.GeneratorConfig
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator
import org.eclipse.xtext.service.OperationCanceledError

class PsiJvmDeclaredTypeImpl extends AbstractLightClass implements PsiJvmDeclaredType {
	
	@Inject
	extension JvmModelGenerator

	PsiClass delegate

	@Accessors(PUBLIC_GETTER)
	val EClass type

	@Accessors(PUBLIC_GETTER)
	val String qualifiedName
	
	val PsiElement psiElement
	
	val JvmDeclaredType declaredType

	new(JvmDeclaredType declaredType, PsiElement psiElement) {
		super(psiElement.manager, psiElement.language)
		this.type = type
		this.declaredType = declaredType
		this.qualifiedName = declaredType.qualifiedName
		this.psiElement = psiElement
		val language = language
		if (language instanceof IXtextLanguage) {
			language.injectMembers(this)
		}
	}

	override copy() {
		new PsiJvmDeclaredTypeImpl(declaredType, psiElement)
	}

	override getDelegate() {
		if (delegate == null) {
			val text = try {
				 declaredType.generateType(new GeneratorConfig => [
					generateExpressions = false
				])
			} catch (OperationCanceledError cancel) {
				throw cancel.wrapped
			}

			switch psiFile : PsiFileFactory.getInstance(project).createFileFromText("_Dummy_.java", JavaFileType.INSTANCE, text) {
				PsiJavaFile: delegate = psiFile.classes.head
			}
		}
		delegate
	}
	
	override getNavigationElement() {
		psiElement
	}
	
	override isValid() {
		psiElement.valid
	}
	
	override isEquivalentTo(PsiElement another) {
		if (another instanceof PsiClass) {
			qualifiedName == another.qualifiedName
		}
		false
	}

}