package org.xtext.example.mydsl.idea;

import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.intellij.psi.stubs.StubIndexKey;

public class AbstractMyDslIdeaModule extends org.eclipse.xtext.idea.DefaultIdeaModule {
	
	public void configureStubKeys(Binder binder) {
		binder.bind(new TypeLiteral<StubIndexKey<String, PsiNamedEObject>>() {}).annotatedWith(Names.named(PsiNamedEObjectIndex.EOBJECT_NAME))
				.toInstance(StubIndexKey.<String, PsiNamedEObject>createIndexKey("org.xtext.example.mydsl.MyDsl.eobject.name"));
	}
	
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends PsiNamedEObjectIndex> bindPsiNamedEObjectIndex() {
		return org.xtext.example.mydsl.idea.lang.psi.stubindex.MyDslPsiNamedEObjectIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.openapi.fileTypes.SyntaxHighlighter> bindSyntaxHighlighter() {
		return org.xtext.example.mydsl.idea.lang.MyDslSyntaxHighlighter.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.lexer.Lexer> bindLexer() {
		return org.xtext.example.mydsl.idea.lang.parser.MyDslLexer.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends org.eclipse.xtext.generator.idea.TokenTypeProvider> bindTokenTypeProvider() {
		return org.xtext.example.mydsl.idea.lang.parser.MyDslTokenTypeProvider.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.lang.ParserDefinition> bindParserDefinition() {
		return org.xtext.example.mydsl.idea.lang.parser.MyDslParserDefinition.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.idea.lang.IElementTypeProvider> bindIElementTypeProvider() {
		return org.xtext.example.mydsl.idea.lang.MyDslElementTypeProvider.class;
	}
	
	
}
