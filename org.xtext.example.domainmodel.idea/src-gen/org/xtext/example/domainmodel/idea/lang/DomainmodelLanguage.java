package org.xtext.example.domainmodel.idea.lang;

import org.eclipse.xtext.idea.lang.AbstractXtextLanguage;

import com.google.inject.Injector;

public final class DomainmodelLanguage extends AbstractXtextLanguage {

	public static final DomainmodelLanguage INSTANCE = new DomainmodelLanguage();

	private Injector injector;

	private DomainmodelLanguage() {
		super("org.xtext.example.domainmodel.Domainmodel");
		this.injector = new org.xtext.example.domainmodel.idea.DomainmodelStandaloneSetupIdea().createInjectorAndDoEMFRegistration();
		
	}

	@Override
	protected Injector getInjector() {
		return injector;
	}
}
