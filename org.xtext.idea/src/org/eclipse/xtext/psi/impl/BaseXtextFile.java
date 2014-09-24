package org.eclipse.xtext.psi.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.resource.ResourceDescriptionAdapter;
import org.eclipse.xtext.idea.resource.impl.StubBasedResourceDescriptions;
import org.eclipse.xtext.psi.PsiEObject;
import org.eclipse.xtext.psi.stubs.ExportedObject;
import org.eclipse.xtext.psi.stubs.XtextFileStub;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.indexing.IndexingDataKeys;

public abstract class BaseXtextFile extends PsiFileBase {

    @Inject
    private Provider<ResourceSet> resourceSetProvider;
    
	protected BaseXtextFile(@NotNull FileViewProvider viewProvider, @NotNull Language language) {
        super(viewProvider, language);
        if (language instanceof IXtextLanguage) {
        	((IXtextLanguage) language).injectMembers(this);
        } else {
        	throw new IllegalArgumentException("Expected an Xtext language but got "+language.getDisplayName());
        }
    }

    public Resource createResource() {    	
    	VirtualFile virtualFile = getViewProvider().getVirtualFile();
        if (virtualFile == null) {
            return null;
        }
        ResourceSet resourceSet = resourceSetProvider.get();
        resourceSet.eAdapters().add(new StubBasedResourceDescriptions.ProjectAdapter(getProject()));
        
        URI uri = getUri();
        Resource resource = resourceSet.createResource(uri);
        try {
            resource.load(virtualFile.getInputStream(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resource;
    }

	public EObject getEObject(URI uri) {
    	Resource resource = getResource();
    	if (resource == null) {
    		return null;
    	}
    	return resource.getEObject(uri.fragment());
	}

	protected URI getUri() {
		PsiFile originalFile = getOriginalFile();
		if (originalFile != this && originalFile instanceof BaseXtextFile) {
			BaseXtextFile originalXtextFile = (BaseXtextFile) originalFile;
			return originalXtextFile.getUri();
		}
    	VirtualFile virtualFile = getUserData(IndexingDataKeys.VIRTUAL_FILE);
		if (virtualFile == null) {
    		virtualFile = getViewProvider().getVirtualFile();
    	}
		String url = virtualFile.getUrl();
        return URI.createURI(url);
	}
    
    public IResourceDescription getResourceDescription() {
    	Resource resource = getResource();
    	return resource != null ? ResourceDescriptionAdapter.get(resource) : null;
    }
    
    public Resource getResource() {
    	PsiEObject root = getRoot();
    	if (root != null) {
    		return root.getResource();
    	}
    	return null;
    }
    
    public PsiEObject getRoot() {
    	PsiElement firstChild = getFirstChild();
    	if (firstChild instanceof PsiEObject) {
    		return (PsiEObject) firstChild;
    	}
    	return null;
    }

	public Iterable<IEObjectDescription> getExportedObjects() {
		StubElement<?> stub = getStub();
		if (stub instanceof XtextFileStub<?>) {
			XtextFileStub<?> xtextFileStub = (XtextFileStub<?>) stub;
			List<IEObjectDescription> exportedObjects = new ArrayList<IEObjectDescription>();
			for (ExportedObject exportedObject : xtextFileStub.getExportedObjects()) {
				EFactory factory = exportedObject.getEClass().getEPackage().getEFactoryInstance();
				InternalEObject element = (InternalEObject) factory.create(exportedObject.getEClass());
				element.eSetProxyURI(exportedObject.getEObjectURI());
				exportedObjects.add(EObjectDescription.create(exportedObject.getQualifiedName(), element));
			}
			return exportedObjects;
		}
		IResourceDescription resourceDescription = getResourceDescription();
		if (resourceDescription != null) {
			return resourceDescription.getExportedObjects();
		}
		return Collections.emptyList();
	}

}