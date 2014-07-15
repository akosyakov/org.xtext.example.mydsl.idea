package org.eclipse.xtext.idea.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.xtext.idea.lang.AbstractXtextLanguage;
import org.eclipse.xtext.psi.impl.BaseXtextFile;

import com.google.common.collect.Lists;
import com.intellij.lang.Language;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.intellij.openapi.vfs.newvfs.BulkFileListener;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.PsiFileEx;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.messages.MessageBusConnection;

public class PsiFileUpdater extends AbstractProjectComponent implements ProjectComponent, BulkFileListener {

	public PsiFileUpdater(Project project) {
		super(project);
		Application application = ApplicationManager.getApplication();
		MessageBusConnection conn = application.getMessageBus().connect();
		conn.subscribe(VirtualFileManager.VFS_CHANGES, this);
	}

	public void after(List<? extends VFileEvent> events) {
		List<LanguageFileType> fileTypes = getFileTypes();
		if (fileTypes.isEmpty()) {
			return;
		}
		List<VirtualFile> updatedFiles = collectUpdatedFiles(events, fileTypes);
		if (updatedFiles.isEmpty()) {
			return;
		}
		GlobalSearchScope projectScope = GlobalSearchScope.projectScope(myProject);
		for (LanguageFileType fileType : fileTypes) {
			Collection<VirtualFile> files = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, fileType, projectScope);
	
			PsiManager psiManager = PsiManager.getInstance(myProject);
			for (VirtualFile virtualFile : files) {
				if (updatedFiles.contains(virtualFile)) {
					continue;
				}
				PsiFile psiFile = psiManager.findFile(virtualFile);
				if (psiFile instanceof BaseXtextFile) {
					PsiFileEx psiFileEx = (PsiFileEx) psiFile;
					if (psiFileEx.isContentsLoaded()) {
						psiManager.reloadFromDisk(psiFileEx);
					}
				}
			}
		}
	}

	protected List<LanguageFileType> getFileTypes() {
		List<LanguageFileType> fileTypes = Lists.newArrayList();
		for (Language language : Language.getRegisteredLanguages()) {
			if (language instanceof AbstractXtextLanguage) {
				fileTypes.add(language.getAssociatedFileType());
			}
		}
		return fileTypes;
	}

	protected List<VirtualFile> collectUpdatedFiles(List<? extends VFileEvent> events, List<LanguageFileType> fileTypes) {
		List<VirtualFile> updatedFiles = new ArrayList<VirtualFile>();
		for (VFileEvent event : events) {
			VirtualFile file = event.getFile();
			if (fileTypes.contains(file.getFileType())) {
				updatedFiles.add(file);
			}
		}
		return updatedFiles;
	}

	public void before(List<? extends VFileEvent> events) { }

}
