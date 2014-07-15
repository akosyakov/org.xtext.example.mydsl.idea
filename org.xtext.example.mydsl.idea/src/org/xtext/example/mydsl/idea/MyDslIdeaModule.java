package org.xtext.example.mydsl.idea;

import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.idea.resource.impl.StubBasedResourceDescriptions;
import org.eclipse.xtext.idea.resource.impl.StubContainerManager;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.impl.SimpleResourceDescriptionsBasedContainerManager;
import org.eclipse.xtext.service.AbstractGenericModule;
import org.xtext.example.mydsl.lang.MyDslElementTypeProvider;

import com.google.inject.Singleton;

public class MyDslIdeaModule extends AbstractGenericModule {
	
	@Singleton
	public Class<? extends IElementTypeProvider> bindElementTypeProvider() {
		return MyDslElementTypeProvider.class;
	}
	
	@Singleton
	public Class<? extends PsiNamedEObjectIndex> bindPsiNamedEObjectIndex() {
		return PsiNamedEObjectIndex.class;
	}

	public void configureIResourceDescriptions(com.google.inject.Binder binder) {
		binder.bind(org.eclipse.xtext.resource.IResourceDescriptions.class).to(StubBasedResourceDescriptions.class);
	}
	
	public Class<? extends IContainer.Manager> bindIContainer$Manager() {
		return StubContainerManager.class;
	}

}
