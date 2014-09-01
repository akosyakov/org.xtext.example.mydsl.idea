package org.eclipse.xtext.idea

import com.google.inject.Binder
import org.eclipse.xtext.idea.resource.impl.StubBasedResourceDescriptions
import org.eclipse.xtext.idea.resource.impl.StubContainerManager
import org.eclipse.xtext.psi.IPsiModelAssociations
import org.eclipse.xtext.psi.PsiModelAssociations
import org.eclipse.xtext.resource.IContainer
import org.eclipse.xtext.resource.IResourceDescriptions
import org.eclipse.xtext.service.AbstractGenericModule
import org.eclipse.xtext.service.SingletonBinding

class DefaultIdeaModule extends AbstractGenericModule {

	def void configureIResourceDescriptions(Binder binder) {
		binder.bind(IResourceDescriptions).to(StubBasedResourceDescriptions)
	}

	def Class<? extends IContainer.Manager> bindIContainer$Manager() {
		StubContainerManager
	}
	
	@SingletonBinding
	def Class<? extends IPsiModelAssociations> bindIPsiModelAssociations() {
		PsiModelAssociations
	}

}
