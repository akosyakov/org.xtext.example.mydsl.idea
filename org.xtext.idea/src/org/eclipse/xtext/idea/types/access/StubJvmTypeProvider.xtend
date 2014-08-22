package org.eclipse.xtext.idea.types.access

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiClass
import com.intellij.psi.impl.JavaPsiFacadeEx
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtend.lib.annotations.AccessorType
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.common.types.JvmType
import org.eclipse.xtext.common.types.access.impl.AbstractRuntimeJvmTypeProvider
import org.eclipse.xtext.common.types.access.impl.IndexedJvmTypeAccess

class StubJvmTypeProvider extends AbstractRuntimeJvmTypeProvider {
	
	@Accessors(AccessorType.PUBLIC_GETTER)
	val Project project
	
	val PsiClassFactory psiClassFactory
	
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
		val psiClass = name.findClass
		if (psiClass == null) {
			return null
		}
		new PsiClassMirror(psiClass, psiClassFactory)
	}
	
	override findTypeByName(String name) {
		findTypeByName(name, true)
	}
	
	override findTypeByName(String name, boolean binaryNestedTypeDelimiter) {
		val psiClass = findClass(name)
		if (psiClass == null) {
			return tryFindTypeInIndex(name, binaryNestedTypeDelimiter)
		}
		psiClass.findTypeByClass
	}

	protected def tryFindTypeInIndex(String name, boolean binaryNestedTypeDelimiter) {
		val adapter = EcoreUtil.getAdapter(resourceSet.eAdapters, TypeInResourceSetAdapter) as TypeInResourceSetAdapter
		if (adapter != null) {
			adapter.tryFindTypeInIndex(name, this, binaryNestedTypeDelimiter)
		} else {
			doTryFindInIndex(name, binaryNestedTypeDelimiter)
		}
	}
	
	protected def findClass(String name) {
		JavaPsiFacadeEx.getInstanceEx(project).findClass(name)
	}
	
	protected def findTypeByClass(PsiClass psiClass) {
		val indexedJvmTypeAccess = indexedJvmTypeAccess
		val resourceURI = psiClass.createResourceURI
		if (indexedJvmTypeAccess != null) {
			val proxyURI = resourceURI.appendFragment(psiClass.fragment)
			val candidate = indexedJvmTypeAccess.getIndexedJvmType(proxyURI, resourceSet)
			if (candidate instanceof JvmType) {
				return candidate
			}
		}
		val result = resourceSet.getResource(resourceURI, true)
		psiClass.findTypeByClass(result)
	}
	
	protected def findTypeByClass(PsiClass psiClass, Resource resource) {
		val fragment = psiClass.fragment
		val result = resource.getEObject(fragment) as JvmType
		if (result == null) {
			throw new IllegalStateException("Resource has not been loaded")
		}
		result
	}
	
}