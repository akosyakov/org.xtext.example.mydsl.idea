package org.xtext.example.domainmodel.idea;

import org.eclipse.xtext.idea.types.StubBasedTypeScopeProvider;
import org.eclipse.xtext.idea.types.access.StubTypeProviderFactory;

public class DomainmodelIdeaModule extends AbstractDomainmodelIdeaModule {

	public Class<? extends org.eclipse.xtext.common.types.access.IJvmTypeProvider.Factory> bindIJvmTypeProvider$Factory() {
		return StubTypeProviderFactory.class;
	}

	public Class<? extends org.eclipse.xtext.common.types.xtext.AbstractTypeScopeProvider> bindAbstractTypeScopeProvider() {
		return StubBasedTypeScopeProvider.class;
	}

}
