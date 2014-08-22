package org.xtext.example.domainmodel.idea.lang.psi.impl;

import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.xtext.example.domainmodel.idea.lang.DomainmodelFileType;
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

public final class DomainmodelFileImpl extends BaseXtextFile {

	public DomainmodelFileImpl(FileViewProvider viewProvider) {
		super(viewProvider, DomainmodelLanguage.INSTANCE);
	}

	public FileType getFileType() {
		return DomainmodelFileType.INSTANCE;
	}

}
