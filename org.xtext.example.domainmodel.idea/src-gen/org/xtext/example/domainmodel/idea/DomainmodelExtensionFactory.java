package org.xtext.example.domainmodel.idea;

import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage;

import com.intellij.openapi.extensions.ExtensionFactory;

public class DomainmodelExtensionFactory implements ExtensionFactory {

	public Object createInstance(final String factoryArgument, final String implementationClass) {
		Class<?> clazz;
		try {
			clazz = Class.forName(implementationClass);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Couldn't load "+implementationClass, e);
		}
		return DomainmodelLanguage.INSTANCE.<Object> getInstance(clazz);
	}

}
