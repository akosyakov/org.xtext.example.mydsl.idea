package org.xtext.example.mydsl.idea;

import org.xtext.example.mydsl.idea.lang.MyDslLanguage;

import com.intellij.openapi.extensions.ExtensionFactory;

public class MyDslExtensionFactory implements ExtensionFactory {

	public Object createInstance(final String factoryArgument, final String implementationClass) {
		Class<?> clazz;
		try {
			clazz = Class.forName(implementationClass);
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException("Couldn't load "+implementationClass, e);
		}
		return MyDslLanguage.INSTANCE.<Object> getInstance(clazz);
	}

}
