package org.eclipse.xtext.idea.types.access

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.impl.JavaPsiFacadeEx
import com.intellij.psi.impl.compiled.SignatureParsing
import java.text.StringCharacterIterator
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.xtext.common.types.access.impl.AbstractRuntimeJvmTypeProvider
import org.eclipse.xtext.common.types.access.impl.ITypeFactory
import org.eclipse.xtext.common.types.access.impl.IndexedJvmTypeAccess
import org.eclipse.xtext.common.types.access.impl.URIHelperConstants

import static extension org.eclipse.xtend.lib.annotations.AccessorType.*

class StubJvmTypeProvider extends AbstractRuntimeJvmTypeProvider {
	
	val static String PRIMITIVES = URIHelperConstants.PRIMITIVES_URI.segment(0)
	
	@Accessors(AccessorType.PUBLIC_GETTER)
	val Project project
	
	val ITypeFactory<PsiClass, JvmDeclaredType> psiClassFactory
	
	@Accessors(AccessorType.PUBLIC_GETTER)
	val extension StubURIHelper uriHelper
	
	protected new(Project project, ResourceSet resourceSet, IndexedJvmTypeAccess indexedJvmTypeAccess) {
		super(resourceSet, indexedJvmTypeAccess)
		this.project = project
		this.uriHelper = createStubURIHelper
		this.psiClassFactory = createPsiClassFactory
	}
	
	def createPsiClassFactory() {
		new PsiClassFactory(uriHelper)
	}
	
	protected def createStubURIHelper() {
		new StubURIHelper
	}
	
	override protected createMirrorForFQN(String name) {
		val psiClass = JavaPsiFacadeEx.getInstanceEx(project).findClass(name)
		if (psiClass == null) {
			return null
		}
		new PsiClassMirror(psiClass, psiClassFactory)
	}
	
	override findTypeByName(String name) {
		findTypeByName(name, true)
	} 
	
	override findTypeByName(String name, boolean binaryNestedTypeDelimiter) {
		val normalizedName = name.nozmalize
		val indexedJvmTypeAccess = indexedJvmTypeAccess
		val resourceURI = normalizedName.createResourceURI
		if (indexedJvmTypeAccess != null) {
			val proxyURI = resourceURI.appendFragment(normalizedName.fragment)
			val candidate = indexedJvmTypeAccess.getIndexedJvmType(proxyURI, resourceSet)
			if (candidate instanceof JvmType) {
				return candidate
			}
		}
		val result = resourceSet.getResource(resourceURI, true)
		normalizedName.findTypeByClass(result)
	}
	
	protected def nozmalize(String name) {
		if (name.startsWith('[')) {
			SignatureParsing.parseTypeString(new StringCharacterIterator(name))
		} else {
			name
		}
	}

//	protected def tryFindTypeInIndex(String name, boolean binaryNestedTypeDelimiter) {
//		val adapter = EcoreUtil.getAdapter(resourceSet.eAdapters, TypeInResourceSetAdapter) as TypeInResourceSetAdapter
//		if (adapter != null) {
//			adapter.tryFindTypeInIndex(name, this, binaryNestedTypeDelimiter)
//		} else {
//			doTryFindInIndex(name, binaryNestedTypeDelimiter)
//		}
//	}
	
	protected def findTypeByClass(String name, Resource resource) {
		val fragment = name.fragment
		val result = resource.getEObject(fragment) as JvmType
//		if (result == null) {
//			throw new IllegalStateException("Resource has not been loaded: " + fragment)
//		}
		result
	}
	
}