package org.xtext.example.mydsl.idea.lang;

import com.google.inject.Inject;
import com.google.inject.Provider;

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;

import org.jetbrains.annotations.NotNull;

public class MyDslSyntaxHighlighterFactory extends SingleLazyInstanceSyntaxHighlighterFactory {
	
	@Inject Provider<SyntaxHighlighter> syntaxHighlighterProvider;
	
	public MyDslSyntaxHighlighterFactory() {
		org.xtext.example.mydsl.idea.lang.MyDslLanguage.INSTANCE.injectMembers(this);
	}
	
    @NotNull
    protected SyntaxHighlighter createHighlighter() {
        return syntaxHighlighterProvider.get();
    }

}
