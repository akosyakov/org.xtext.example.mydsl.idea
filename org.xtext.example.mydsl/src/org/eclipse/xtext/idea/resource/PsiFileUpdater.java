package org.eclipse.xtext.idea.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.xtext.example.mydsl.lang.MyDslFileType;

import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.AbstractProjectComponent;
import com.intellij.openapi.components.ProjectComponent;
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
		List<VirtualFile> updatedFiles = collectUpdatedFiles(events);
		if (updatedFiles.isEmpty()) {
			return;
		}
		GlobalSearchScope projectScope = GlobalSearchScope.projectScope(myProject);
		Collection<VirtualFile> files = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, MyDslFileType.INSTANCE, projectScope);

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

	protected List<VirtualFile> collectUpdatedFiles(List<? extends VFileEvent> events) {
		List<VirtualFile> updatedFiles = new ArrayList<VirtualFile>();
		for (VFileEvent event : events) {
			VirtualFile file = event.getFile();
			if (file.getFileType() == MyDslFileType.INSTANCE) {
				updatedFiles.add(file);
			}
		}
		return updatedFiles;
	}

	public void before(List<? extends VFileEvent> events) { }

}
