package org.eclipse.xtext.idea.resource

import com.google.inject.Inject
import com.google.inject.Provider
import com.intellij.openapi.project.Project
import org.eclipse.emf.ecore.resource.ResourceSet
import com.google.inject.Singleton

@Singleton
class ProjectBasedResourceSetProvider implements IResourceSetProvider {

	@Inject
	Provider<ResourceSet> resourceSetProvider

	override get(Object context) {
		val resourceSet = resourceSetProvider.get
		if (context instanceof Project) {
			resourceSet.eAdapters.add(new ProjectAdapter(context))
		}
		resourceSet
	}

}
