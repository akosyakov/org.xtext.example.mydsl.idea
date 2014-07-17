package org.eclipse.xtext.idea.resource.impl;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.idea.ProcessCanceledExceptionHandling;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;

import com.google.inject.Inject;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;

public class StubResourceDescription extends AbstractResourceDescription implements IResourceDescription {
	
	public static final URI STUB_URI = URI.createURI("stub");

	@Inject
	private PsiNamedEObjectIndex psiNamedEObjectIndex;
	
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
						allDescriptions.add(new StubEObjectDescription(psiNamedObject));
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