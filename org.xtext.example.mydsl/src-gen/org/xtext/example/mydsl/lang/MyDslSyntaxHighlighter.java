package org.xtext.example.mydsl.lang;

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import org.xtext.example.mydsl.lang.parser.MyDslLexer;
import org.xtext.example.mydsl.lang.parser.MyDslTokenTypes;
import org.jetbrains.annotations.NotNull;

public class MyDslSyntaxHighlighter extends SyntaxHighlighterBase {

    @NotNull
    public Lexer getHighlightingLexer() {
        return new MyDslLexer();
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (MyDslTokenTypes.STRINGS.contains(tokenType)) {
            return pack(SyntaxHighlighterColors.STRING);
        }
		if (MyDslTokenTypes.LINE_COMMENTS.contains(tokenType)) {
			return pack(SyntaxHighlighterColors.LINE_COMMENT);
		}
		if (MyDslTokenTypes.BLOCK_COMMENTS.contains(tokenType)) {
			return pack(SyntaxHighlighterColors.JAVA_BLOCK_COMMENT);
		}
        String myDebugName = tokenType.toString();
		if (myDebugName.matches("^'.*\\w.*'$")) {
			return pack(SyntaxHighlighterColors.KEYWORD);
        }
        return new TextAttributesKey[0];
    }

}
