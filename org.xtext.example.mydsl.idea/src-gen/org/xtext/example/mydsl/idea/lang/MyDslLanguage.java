package org.xtext.example.mydsl.idea.lang;

import org.eclipse.xtext.idea.lang.AbstractXtextLanguage;

import com.google.inject.Injector;

public final class MyDslLanguage extends AbstractXtextLanguage {

	public static final MyDslLanguage INSTANCE = new MyDslLanguage();

	private Injector injector;

	private MyDslLanguage() {
		super("org.xtext.example.mydsl.MyDsl");
		this.injector = new org.xtext.example.mydsl.idea.MyDslStandaloneSetupIdea().createInjectorAndDoEMFRegistration();
		
	}

	@Override
	protected Injector getInjector() {
		return injector;
	}
}
