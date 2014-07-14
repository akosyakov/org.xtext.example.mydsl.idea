package org.xtext.example.mydsl.lang.psi.impl;

import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.xtext.example.mydsl.lang.MyDslFileType;
import org.xtext.example.mydsl.lang.MyDslIdeaSetup;
import org.xtext.example.mydsl.lang.MyDslLanguage;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

public final class MyDslFileImpl extends BaseXtextFile {

	public MyDslFileImpl(FileViewProvider viewProvider) {
		super(viewProvider, MyDslLanguage.INSTANCE);
		MyDslIdeaSetup.getInjector().injectMembers(this);
	}

	public FileType getFileType() {
		return MyDslFileType.INSTANCE;
	}

}
