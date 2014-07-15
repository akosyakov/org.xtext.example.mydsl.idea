package org.xtext.example.mydsl.idea.lang;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.xtext.example.mydsl.idea.lang.parser.MyDslLexer;
import org.xtext.example.mydsl.idea.lang.parser.MyDslTokenTypes;
import org.jetbrains.annotations.NotNull;

public class MyDslSyntaxHighlighter extends SyntaxHighlighterBase {

    @NotNull
    public Lexer getHighlightingLexer() {
        return new MyDslLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (MyDslTokenTypes.STRINGS.contains(tokenType)) {
            return pack(DefaultLanguageHighlighterColors.STRING);
        }
		if (MyDslTokenTypes.LINE_COMMENTS.contains(tokenType)) {
			return pack(DefaultLanguageHighlighterColors.LINE_COMMENT);
		}
		if (MyDslTokenTypes.BLOCK_COMMENTS.contains(tokenType)) {
			return pack(DefaultLanguageHighlighterColors.BLOCK_COMMENT);
		}
        String myDebugName = tokenType.toString();
		if (myDebugName.matches("^'.*\\w.*'$")) {
			return pack(DefaultLanguageHighlighterColors.KEYWORD);
        }
        return new TextAttributesKey[0];
    }

}
