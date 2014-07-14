package org.xtext.example.mydsl.lang;

import com.intellij.lang.Language;

public final class MyDslLanguage extends Language {

	public static final MyDslLanguage INSTANCE = new MyDslLanguage();

	private MyDslLanguage() {
		super("org.xtext.example.mydsl.MyDsl");
	}

}
