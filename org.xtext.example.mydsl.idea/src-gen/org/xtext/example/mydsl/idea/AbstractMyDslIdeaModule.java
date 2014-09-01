package org.xtext.example.mydsl.idea;

public class AbstractMyDslIdeaModule extends org.eclipse.xtext.idea.DefaultIdeaModule {
	
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public void configureStubKeys(com.google.inject.Binder binder) {
		binder.bind(new com.google.inject.TypeLiteral<com.intellij.psi.stubs.StubIndexKey<String, org.eclipse.xtext.psi.PsiNamedEObject>>() {}) 
			.annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex.EOBJECT_NAME))
			.toInstance(com.intellij.psi.stubs.StubIndexKey.<String, org.eclipse.xtext.psi.PsiNamedEObject>createIndexKey("org.xtext.example.mydsl.MyDsl" + org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex.EOBJECT_NAME));
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex> bindPsiNamedEObjectIndex() {
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
