package org.eclipse.xtext.idea.containers

import com.google.inject.Inject
import com.google.inject.Provider
import org.eclipse.emf.common.util.URI
import org.eclipse.xtext.idea.resource.impl.ProjectScopeBasedResourceDescriptions
import org.eclipse.xtext.idea.resource.impl.PsiFileBasedResourceDescription
import org.eclipse.xtext.resource.IContainer.Manager
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.resource.IResourceDescriptions

class ResolveScopeBasedContainerManger implements Manager {

	@Inject
	Provider<ResolveScopeBasedContainer> resolveScopeBasedContainerProvider

	override getVisibleContainers(IResourceDescription desc, IResourceDescriptions resourceDescriptions) {
		val container = desc.getContainer(resourceDescriptions)
		if (container == null) {
			return emptyList
		}
		#[container]
	}

	override getContainer(IResourceDescription desc, IResourceDescriptions resourceDescriptions) {
		if (resourceDescriptions.indexing) {
			return null
		}
		desc.container ?: resourceDescriptions.getContainer(desc.URI)
	}

	protected def getContainer(IResourceDescriptions resourceDescriptions, URI uri) {
		if (resourceDescriptions instanceof ProjectScopeBasedResourceDescriptions) {
			resourceDescriptions.getResourceDescription(uri).container
		}
	}

	protected def getContainer(IResourceDescription desc) {
		if (desc instanceof PsiFileBasedResourceDescription) {
			return resolveScopeBasedContainerProvider.get => [
				scope = desc.xtextFile.resolveScope
			]
		}
	}

	protected def isIndexing(IResourceDescriptions resourceDescriptions) {
		if (resourceDescriptions instanceof ProjectScopeBasedResourceDescriptions)
			return resourceDescriptions.indexing
		else
			false
	}

}
