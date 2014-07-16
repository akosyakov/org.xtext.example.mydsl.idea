package org.eclipse.xtext.idea.resource.impl;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.idea.ProcessCanceledExceptionHandling;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.IPsiModelAssociations;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.impl.AbstractCompoundSelectable;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;

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

class StubResourceDescription extends AbstractResourceDescription implements IResourceDescription {
	
	public static final URI STUB_URI = URI.createURI("stub");

	@Inject
	private PsiNamedEObjectIndex psiNamedEObjectIndex;

	@Inject
	private IPsiModelAssociations psiModelAssociations;
	
	private Project project;
	
	public void setProject(Project project) {
		this.project = project;
	}

	public Iterable<QualifiedName> getImportedNames() {
		return emptyList();
	}

	public Iterable<IReferenceDescription> getReferenceDescriptions() {
		return emptyList();
	}

	public URI getURI() {
		return STUB_URI;
	}

	@Override
	protected List<IEObjectDescription> computeExportedObjects() {
		try {
			final List<IEObjectDescription> allDescriptions = new ArrayList<IEObjectDescription>();
			final GlobalSearchScope projectScope = GlobalSearchScope.projectScope(project);
			psiNamedEObjectIndex.processAllKeys(project, new Processor<String>() {
				
				public boolean process(String key) {
					Collection<PsiNamedEObject> psiNamedObjects = psiNamedEObjectIndex.get(key, project, projectScope);
					for (PsiNamedEObject psiNamedObject : psiNamedObjects) {
						allDescriptions.add(new StubEObjectDescription(psiNamedObject, psiModelAssociations));
					}
					return true;
				}
	
			});
			return allDescriptions;
		} catch (ProcessCanceledException e) {
			throw ProcessCanceledExceptionHandling.wrappedReThrow(e);
		}
	}
	
}

class StubEObjectDescription implements IEObjectDescription {

	private final EClass type;

	private final QualifiedName name;

	private final PsiNamedEObject psiNamedEObject;

	private final IPsiModelAssociations psiModelAssociations;

	public StubEObjectDescription(PsiNamedEObject psiNamedEObject, IPsiModelAssociations psiModelAssociations) {
		this.psiNamedEObject = psiNamedEObject;
		this.name = QualifiedName.create(psiNamedEObject.getName());
		this.type = psiNamedEObject.getType();
		this.psiModelAssociations = psiModelAssociations;
	}

	public QualifiedName getName() {
		return name;
	}

	public QualifiedName getQualifiedName() {
		return name;
	}

	public EObject getEObjectOrProxy() {
		return psiModelAssociations.getEObject(psiNamedEObject);
	}

	public URI getEObjectURI() {
		EObject objectOrProxy = getEObjectOrProxy();
		return ((InternalEObject) objectOrProxy).eProxyURI();
	}

	public EClass getEClass() {
		return type;
	}

	public String getUserData(String key) {
		return null;
	}

	public String[] getUserDataKeys() {
		return new String[0];
	}

}
