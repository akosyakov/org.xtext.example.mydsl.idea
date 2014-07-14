package org.eclipse.xtext.idea.resource.impl;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IContainer.Manager;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsBasedContainer;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

public class StubContainerManager implements Manager {

	public IContainer getContainer(IResourceDescription desc, IResourceDescriptions resourceDescriptions) {
		StubResourceDescriptionsBasedContainer result = new StubResourceDescriptionsBasedContainer(resourceDescriptions);
		result.setUriToDescriptionCacheEnabled(false);
		return result;
	}

	public List<IContainer> getVisibleContainers(IResourceDescription desc, IResourceDescriptions resourceDescriptions) {
		return Collections.singletonList(getContainer(desc, resourceDescriptions));
	}

}

class StubResourceDescriptionsBasedContainer extends ResourceDescriptionsBasedContainer {

	public StubResourceDescriptionsBasedContainer(IResourceDescriptions descriptions) {
		super(descriptions);
	}
	
	@Override
	public boolean hasResourceDescription(URI uri) {
		return StubResourceDescription.STUB_URI == uri;
	}
	
}
