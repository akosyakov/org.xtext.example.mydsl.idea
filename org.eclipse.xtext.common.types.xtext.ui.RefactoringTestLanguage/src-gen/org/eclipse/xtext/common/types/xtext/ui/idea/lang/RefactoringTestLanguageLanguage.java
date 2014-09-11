package org.eclipse.xtext.common.types.xtext.ui.idea.lang;

import org.eclipse.xtext.idea.lang.AbstractXtextLanguage;

import com.google.inject.Injector;

public final class RefactoringTestLanguageLanguage extends AbstractXtextLanguage {

	public static final RefactoringTestLanguageLanguage INSTANCE = new RefactoringTestLanguageLanguage();

	private Injector injector;

	private RefactoringTestLanguageLanguage() {
		super("org.eclipse.xtext.common.types.xtext.ui.RefactoringTestLanguage");
		this.injector = new org.eclipse.xtext.common.types.xtext.ui.idea.RefactoringTestLanguageStandaloneSetupIdea().createInjectorAndDoEMFRegistration();
		
	}

	@Override
	protected Injector getInjector() {
		return injector;
	}
}
