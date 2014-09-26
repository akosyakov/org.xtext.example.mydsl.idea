/*
 * generated by Xtext
 */
package org.eclipse.xtext.common.types.xtext.ui.ui;

import org.eclipse.xtext.ui.guice.AbstractGuiceAwareExecutableExtensionFactory;
import org.osgi.framework.Bundle;

import com.google.inject.Injector;

import org.eclipse.xtext.common.types.xtext.ui.ui.internal.RefactoringTestLanguageActivator;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class RefactoringTestLanguageExecutableExtensionFactory extends AbstractGuiceAwareExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return RefactoringTestLanguageActivator.getInstance().getBundle();
	}
	
	@Override
	protected Injector getInjector() {
		return RefactoringTestLanguageActivator.getInstance().getInjector(RefactoringTestLanguageActivator.ORG_ECLIPSE_XTEXT_COMMON_TYPES_XTEXT_UI_REFACTORINGTESTLANGUAGE);
	}
	
}