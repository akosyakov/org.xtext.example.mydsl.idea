package org.eclipse.xtext.idea.resource.impl;

import static java.util.Collections.emptyList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.resource.ProjectAdapter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.psi.stubindex.ExportedObjectQualifiedNameIndex;
import org.eclipse.xtext.resource.CompilerPhases;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.ISelectable;
import org.eclipse.xtext.resource.impl.AbstractCompoundSelectable;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;

public class PsiFilesBasedResourceDescriptions extends AbstractCompoundSelectable implements IResourceDescriptions.IContextAware {

	private Project project;

	private Notifier context;
	
	@Inject
	private CompilerPhases compilerPhases;
	
	@Inject
	private ExportedObjectQualifiedNameIndex exportedObjectQualifiedNameIndex;

	@Override
	protected Iterable<? extends ISelectable> getSelectables() {
		return getAllResourceDescriptions();
	}
	
	@Override
	public Iterable<IEObjectDescription> getExportedObjects(EClass type, QualifiedName qualifiedName, boolean ignoreCase) {
		if (project == null || isIndexing()) {
			return emptyList();
		}
		GlobalSearchScope projectScope = GlobalSearchScope.projectScope(project);
		List<IEObjectDescription> allDescriptions = new ArrayList<IEObjectDescription>();
		Collection<BaseXtextFile> xtextFiles = exportedObjectQualifiedNameIndex.get(qualifiedName.toString(), project, projectScope);
		for (BaseXtextFile xtextFile : xtextFiles) {
			PsiFileBasedResourceDescription resourceDescription = new PsiFileBasedResourceDescription(xtextFile);
			allDescriptions.addAll(IterableExtensions.toList(resourceDescription.getExportedObjects(type, qualifiedName, ignoreCase)));
		}
		return allDescriptions;
	}

	public IResourceDescription getResourceDescription(URI uri) {
		if (project == null || isIndexing()) {
			return null;
		}
		String fileName = uri.lastSegment();
		for (PsiFile psiFile : FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.projectScope(project))) {
			if (psiFile instanceof BaseXtextFile) {
				BaseXtextFile baseXtextFile = (BaseXtextFile) psiFile;
				if (uri.equals(baseXtextFile.getURI())) {
					return new PsiFileBasedResourceDescription(baseXtextFile);
				}
			}
		}
		return null;
	}

	public Iterable<IResourceDescription> getAllResourceDescriptions() {
		if (project == null || isIndexing()) {
			return null;
		}
		PsiManager psiManager = PsiManager.getInstance(project);
		GlobalSearchScope scope = GlobalSearchScope.projectScope(project);
		
		List<IResourceDescription> descriptions = new ArrayList<IResourceDescription>();
		for (FileType fileType : getXtextLanguageFileTypes()) {
			for (VirtualFile file : FileTypeIndex.getFiles(fileType, scope)) {
				PsiFile psiFile = psiManager.findFile(file);
				if (psiFile instanceof BaseXtextFile) {
					descriptions.add(new PsiFileBasedResourceDescription((BaseXtextFile) psiFile));
				}
			}
		}
		return descriptions;
	}

	protected Iterable<FileType> getXtextLanguageFileTypes() {
		Collection<FileType> fileTypes = FileBasedIndex.getInstance().getAllKeys(FileTypeIndex.NAME, project);
		return Iterables.filter(fileTypes, new Predicate<FileType>() {

			@Override
			public boolean apply(FileType fileType) {
				return isXtextLanguage(fileType);
			}
			
		});
	}

	protected boolean isXtextLanguage(FileType fileType) {
		return fileType instanceof LanguageFileType && ((LanguageFileType) fileType).getLanguage() instanceof IXtextLanguage;
	}

	public void setContext(Notifier ctx) {
		this.context = ctx;
		this.project = ProjectAdapter.getProject(ctx);
	}

	protected boolean isIndexing() {
		if (compilerPhases.isIndexing(context)) {
			return true;
		}
		return DumbService.isDumb(project);
	}

}
