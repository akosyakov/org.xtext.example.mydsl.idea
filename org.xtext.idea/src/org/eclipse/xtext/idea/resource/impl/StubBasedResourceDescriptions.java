package org.eclipse.xtext.idea.resource.impl;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.impl.AbstractCompoundSelectable;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intellij.openapi.project.Project;

public class StubBasedResourceDescriptions extends AbstractCompoundSelectable implements IResourceDescriptions.IContextAware {

	public static class ProjectAdapter extends AdapterImpl {
	
		private final Project project;
	
		public ProjectAdapter(Project project) {
			this.project = project;
		}
	
		public static Project getProject(Notifier ctx) {
			if (ctx == null) {
				return null;
			}
			for (Adapter adapter : ctx.eAdapters()) {
				if (adapter instanceof ProjectAdapter) {
					return ((ProjectAdapter) adapter).project;
				}
			}
			return null;
		}
	
	}

	private Project project;
	
	@Inject
	private Provider<StubResourceDescription> stubResourceDescriptionProvider;

	public Iterable<IResourceDescription> getAllResourceDescriptions() {
		if (project == null) {
			return emptyList();
		}
		StubResourceDescription stubResourceDescription = stubResourceDescriptionProvider.get();
		stubResourceDescription.setProject(project);
		return singletonList((IResourceDescription) stubResourceDescription);
	}

	public IResourceDescription getResourceDescription(URI uri) {
		return null;
	}

	@Override
	protected Iterable<? extends ISelectable> getSelectables() {
		return getAllResourceDescriptions();
	}

	public void setContext(Notifier ctx) {
		this.project = ProjectAdapter.getProject(ctx);
	}

}
