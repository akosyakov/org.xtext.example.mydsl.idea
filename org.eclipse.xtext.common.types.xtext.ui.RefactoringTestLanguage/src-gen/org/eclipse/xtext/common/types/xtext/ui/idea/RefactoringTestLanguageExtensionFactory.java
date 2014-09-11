package org.eclipse.xtext.common.types.xtext.ui.idea;

import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage;

import com.intellij.openapi.extensions.ExtensionFactory;

public class RefactoringTestLanguageExtensionFactory implements ExtensionFactory {

	public Object createInstance(final String factoryArgument, final String implementationClass) {
		Class<?> clazz;
		try {
			clazz = Class.forName(implementationClass);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Couldn't load "+implementationClass, e);
		}
		return RefactoringTestLanguageLanguage.INSTANCE.<Object> getInstance(clazz);
	}

}
