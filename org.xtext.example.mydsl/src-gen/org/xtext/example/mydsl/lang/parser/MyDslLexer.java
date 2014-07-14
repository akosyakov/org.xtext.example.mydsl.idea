package org.xtext.example.mydsl.lang.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonToken;
import org.antlr.runtime.Token;
import org.xtext.example.mydsl.parser.antlr.internal.InternalMyDslLexer;

import com.intellij.lexer.LexerBase;
import com.intellij.psi.tree.IElementType;

public class MyDslLexer extends LexerBase {

    private InternalMyDslLexer internalLexer;
    private CommonToken token;

    private CharSequence buffer;
    private int startOffset;
    private int endOffset;

    public void start(CharSequence buffer, int startOffset, int endOffset, int initialState) {
        this.buffer = buffer;
        this.startOffset = startOffset;
        this.endOffset = endOffset;

        String text = buffer.subSequence(startOffset, endOffset).toString();
        internalLexer = new InternalMyDslLexer(new ANTLRStringStream(text));
    }

    public int getState() {
        return token != null ? token.getType() : 0;
    }

    public IElementType getTokenType() {
        locateToken();
        if (token == null) {
            return null;
        }
        int type = token.getType();
        return MyDslTokenTypes.tokenTypes[type];
    }

    public int getTokenStart() {
        locateToken();
        return startOffset + token.getStartIndex();
    }

    public int getTokenEnd() {
        locateToken();
        return startOffset + token.getStopIndex() + 1;
    }

    public void advance() {
        locateToken();
        token = null;
    }

    public CharSequence getBufferSequence() {
        return buffer;
    }

    public int getBufferEnd() {
        return endOffset;
    }

    private void locateToken() {
        if (token == null) {
            try {
                token = (CommonToken) internalLexer.nextToken();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (token == Token.EOF_TOKEN) {
                token = null;
            }
        }
    }

}
