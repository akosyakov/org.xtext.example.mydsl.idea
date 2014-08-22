package org.xtext.example.domainmodel.idea.lang;

import javax.swing.Icon;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;

public final class DomainmodelFileType extends LanguageFileType {

	public static final DomainmodelFileType INSTANCE = new DomainmodelFileType();
	
	@NonNls 
	public static final String DEFAULT_EXTENSION = "domainmodel";

	private DomainmodelFileType() {
		super(DomainmodelLanguage.INSTANCE);
	}

	public String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}

	public String getDescription() {
		return "Domainmodel files";
	}

	public Icon getIcon() {
		return null;
	}

	public String getName() {
		return "Domainmodel";
	}

}
