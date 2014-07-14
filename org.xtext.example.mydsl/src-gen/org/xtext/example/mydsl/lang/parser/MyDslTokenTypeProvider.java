package org.xtext.example.mydsl.lang.parser;

import java.util.Arrays;
import java.util.List;

import org.eclipse.xtext.generator.idea.TokenTypeProvider;

import com.intellij.psi.tree.IElementType;

public class MyDslTokenTypeProvider implements TokenTypeProvider {

    public static final List<IElementType> I_ELEMENT_TYPES = Arrays.asList(MyDslTokenTypes.tokenTypes);

    public int getType(IElementType iElementType) {
        return I_ELEMENT_TYPES.indexOf(iElementType);
    }

}
