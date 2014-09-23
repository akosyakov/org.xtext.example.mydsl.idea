package org.eclipse.xtext.psi.impl;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.resource.ResourceDescriptionAdapter;
import org.eclipse.xtext.idea.resource.impl.StubBasedResourceDescriptions;
import org.eclipse.xtext.psi.PsiEObject;
import org.eclipse.xtext.resource.IResourceDescription;
import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;

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
        String url = virtualFile.getUrl();
        URI uri = URI.createURI(url);
        ResourceSet resourceSet = resourceSetProvider.get();
        resourceSet.eAdapters().add(new StubBasedResourceDescriptions.ProjectAdapter(getProject()));
        Resource resource = resourceSet.createResource(uri);
        try {
            resource.load(virtualFile.getInputStream(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resource;
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

}