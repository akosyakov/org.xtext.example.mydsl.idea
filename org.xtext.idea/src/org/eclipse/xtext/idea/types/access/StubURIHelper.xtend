package org.eclipse.xtext.idea.types.access

import com.intellij.psi.PsiClass
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.common.types.access.impl.URIHelperConstants

class StubURIHelper implements URIHelperConstants {
	
	def createResourceURI(PsiClass psiClass) {
		createURIBuilder.appendResourceURI(psiClass).createURI
	}

	protected def createURIBuilder() {
		new StringBuilder(48).append(URIHelperConstants.PROTOCOL).append(':')
	}

	protected def createURI(StringBuilder uriBuilder) {
		URI.createURI(uriBuilder.toString())
	}
	
	protected def appendResourceURI(StringBuilder builder, PsiClass psiClass) {
		builder.append(OBJECTS).append(psiClass.qualifiedName)
	}
	
	def getFragment(PsiClass psiClass) {
		new StringBuilder(32).appendFragment(psiClass).toString
	}
	
	protected def appendFragment(StringBuilder builder, PsiClass psiClass) {
		builder.append(psiClass.qualifiedName)
	}
	
	def getFullURI(PsiClass psiClass) {
		createURIBuilder.appendResourceURI(psiClass).append('#').appendFragment(psiClass).createURI
	}
	
	def getFullURI(String qualifiedName) {
		createURIBuilder.appendResourceURI(qualifiedName).append('#').appendFragment(qualifiedName).createURI
	}
	
	protected def appendResourceURI(StringBuilder builder, String qualifiedName) {
		builder.append(OBJECTS).append(qualifiedName)
	}
	
	protected def appendFragment(StringBuilder builder, String qualifiedName) {
		builder.append(qualifiedName)
	}
	
}