package org.xtext.example.mydsl.idea.lang;

import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import org.jetbrains.annotations.NotNull;

public class MyDslSyntaxHighlighterFactory extends SingleLazyInstanceSyntaxHighlighterFactory {

    @NotNull
    protected SyntaxHighlighter createHighlighter() {
        return new MyDslSyntaxHighlighter();
    }

}
