package org.xtext.example.mydsl.lang;

import javax.swing.Icon;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;

public final class MyDslFileType extends LanguageFileType {

	public static final MyDslFileType INSTANCE = new MyDslFileType();
	
	@NonNls 
	public static final String DEFAULT_EXTENSION = "mydsl";

	private MyDslFileType() {
		super(MyDslLanguage.INSTANCE);
	}

	public String getDefaultExtension() {
		return DEFAULT_EXTENSION;
	}

	public String getDescription() {
		return "MyDsl files";
	}

	public Icon getIcon() {
		return null;
	}

	public String getName() {
		return "MyDsl";
	}

}
