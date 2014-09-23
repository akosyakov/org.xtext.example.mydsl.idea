package org.eclipse.xtext.idea.resource.impl;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.idea.ProcessCanceledExceptionHandling;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.psi.stubindex.ExportedObjectQualifiedNameIndex;
import org.eclipse.xtext.resource.CompilerPhases;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.impl.AbstractResourceDescription;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.inject.Inject;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;

public class StubResourceDescription extends AbstractResourceDescription implements IResourceDescription {
	
	public static final URI STUB_URI = URI.createURI("stub");
	
	@Inject
	private CompilerPhases compilerPhases;
	
	@Inject
	private ExportedObjectQualifiedNameIndex exportedObjectQualifiedNameIndex;

	private Project project;
	
	private Notifier context;
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	public void setContext(Notifier context) {
		this.context = context;
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
			if (!isIndexing()) {
				final GlobalSearchScope projectScope = GlobalSearchScope.projectScope(project);
				final Set<Resource> resources = new HashSet<Resource>();
				exportedObjectQualifiedNameIndex.processAllKeys(project, new Processor<String>() {
					
					public boolean process(String key) {
						Collection<BaseXtextFile> xtextFiles = exportedObjectQualifiedNameIndex.get(key, project, projectScope);
						for (BaseXtextFile xtextFile : xtextFiles) {
							Resource resource = xtextFile.getResource();
							if (resource != null) {
								resources.add(resource);
							}
						}
						return true;
					}
		
				});
				for (Resource resource : resources) {
					if (resource instanceof XtextResource) {
						XtextResource xtextResource = (XtextResource) resource;
						Manager resourceDescriptionManager = xtextResource.getResourceServiceProvider().getResourceDescriptionManager();
						allDescriptions.addAll(IterableExtensions.toList(resourceDescriptionManager.getResourceDescription(resource).getExportedObjects()));
					}
				}
			}
			return allDescriptions;
		} catch (ProcessCanceledException e) {
			throw ProcessCanceledExceptionHandling.wrappedReThrow(e);
		}
	}

	protected boolean isIndexing() {
		if (compilerPhases.isIndexing(context)) {
			return true;
		}
		return DumbService.isDumb(project);
	}
	
}