package org.eclipse.xtext.idea.types.access

import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.common.types.access.AbstractTypeProviderFactory
import org.eclipse.xtext.idea.resource.impl.StubBasedResourceDescriptions.ProjectAdapter

class StubTypeProviderFactory extends AbstractTypeProviderFactory {
	
	override createTypeProvider(ResourceSet resourceSet) {
		if (resourceSet == null)
			throw new IllegalArgumentException("resourceSet may not be null.")
		val project = ProjectAdapter.getProject(resourceSet)
		if (project == null)
			throw new IllegalArgumentException("project may not be null.")
		new StubJvmTypeProvider(project, resourceSet, indexedJvmTypeAccess)
	}
	
}