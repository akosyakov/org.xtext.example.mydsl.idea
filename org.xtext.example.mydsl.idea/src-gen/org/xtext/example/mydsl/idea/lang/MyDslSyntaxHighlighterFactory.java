package org.xtext.example.mydsl.idea.lang;

import com.google.inject.Inject;
import com.google.inject.Provider;

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;

import org.xtext.example.mydsl.idea.lang.MyDslLanguage;

import org.jetbrains.annotations.NotNull;

public class MyDslSyntaxHighlighterFactory extends SingleLazyInstanceSyntaxHighlighterFactory {
	
    @NotNull
    protected SyntaxHighlighter createHighlighter() {
        return MyDslLanguage.INSTANCE.getInstance(SyntaxHighlighter.class);
    }

}
