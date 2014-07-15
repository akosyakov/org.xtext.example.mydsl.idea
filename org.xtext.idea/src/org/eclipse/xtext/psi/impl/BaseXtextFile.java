package org.eclipse.xtext.psi.impl;

import java.io.IOException;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.resource.impl.StubBasedResourceDescriptions;
import org.jetbrains.annotations.NotNull;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;

public abstract class BaseXtextFile extends PsiFileBase {

    @Inject
    private Provider<ResourceSet> resourceSetProvider;
    
	private Resource resource;

    protected BaseXtextFile(@NotNull FileViewProvider viewProvider, @NotNull Language language) {
        super(viewProvider, language);
        if (language instanceof IXtextLanguage) {
        	((IXtextLanguage) language).injectMembers(this);
        } else {
        	throw new IllegalArgumentException("Expected an Xtext language but got "+language.getDisplayName());
        }
    }
    
    @Override
    public void onContentReload() {
    	super.onContentReload();
    	resource = null;
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
        resource = resourceSet.createResource(uri);
        try {
            resource.load(virtualFile.getInputStream(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }

	public Resource getResource() {
		return resource;
	}

}