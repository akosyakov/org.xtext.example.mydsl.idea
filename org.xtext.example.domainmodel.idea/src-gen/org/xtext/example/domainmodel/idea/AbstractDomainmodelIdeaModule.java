package org.xtext.example.domainmodel.idea;

public class AbstractDomainmodelIdeaModule extends org.eclipse.xtext.idea.DefaultIdeaModule {
	
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public void configureStubKeys(com.google.inject.Binder binder) {
		binder.bind(new com.google.inject.TypeLiteral<com.intellij.psi.stubs.StubIndexKey<String, org.eclipse.xtext.psi.PsiNamedEObject>>() {}) 
			.annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex.EOBJECT_NAME))
			.toInstance(com.intellij.psi.stubs.StubIndexKey.<String, org.eclipse.xtext.psi.PsiNamedEObject>createIndexKey("org.xtext.example.domainmodel.Domainmodel" + org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex.EOBJECT_NAME));
		binder.bind(new com.google.inject.TypeLiteral<com.intellij.psi.stubs.StubIndexKey<String, org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject>>() {}) 
			.annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex.CLASS_SHORT_NAMES))
			.toInstance(com.intellij.psi.stubs.StubIndexKey.<String, org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject>createIndexKey("org.xtext.example.domainmodel.Domainmodel" + org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex.CLASS_SHORT_NAMES));
		binder.bind(new com.google.inject.TypeLiteral<com.intellij.psi.stubs.StubIndexKey<String, org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject>>() {}) 
			.annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex.CLASS_FQN))
			.toInstance(com.intellij.psi.stubs.StubIndexKey.<String, org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject>createIndexKey("org.xtext.example.domainmodel.Domainmodel" + org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex.CLASS_FQN));
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex> bindPsiNamedEObjectIndex() {
		return org.xtext.example.domainmodel.idea.lang.psi.stubindex.DomainmodelPsiNamedEObjectIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex> bindJvmDeclaredTypeShortNameIndex() {
		return org.xtext.example.domainmodel.idea.lang.types.stubindex.DomainmodelJvmDeclaredTypeShortNameIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex> bindJvmDeclaredTypeFullClassNameIndex() {
		return org.xtext.example.domainmodel.idea.lang.types.stubindex.DomainmodelJvmDeclaredTypeFullClassNameIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.openapi.fileTypes.SyntaxHighlighter> bindSyntaxHighlighter() {
		return org.xtext.example.domainmodel.idea.lang.DomainmodelSyntaxHighlighter.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.lexer.Lexer> bindLexer() {
		return org.xtext.example.domainmodel.idea.lang.parser.DomainmodelLexer.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends org.eclipse.xtext.generator.idea.TokenTypeProvider> bindTokenTypeProvider() {
		return org.xtext.example.domainmodel.idea.lang.parser.DomainmodelTokenTypeProvider.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.lang.ParserDefinition> bindParserDefinition() {
		return org.xtext.example.domainmodel.idea.lang.parser.DomainmodelParserDefinition.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.idea.lang.IElementTypeProvider> bindIElementTypeProvider() {
		return org.xtext.example.domainmodel.idea.lang.DomainmodelElementTypeProvider.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends org.eclipse.xtext.common.types.access.IJvmTypeProvider.Factory> bindIJvmTypeProvider$Factory() {
		return org.eclipse.xtext.idea.types.access.StubTypeProviderFactory.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends org.eclipse.xtext.common.types.xtext.AbstractTypeScopeProvider> bindAbstractTypeScopeProvider() {
		return org.eclipse.xtext.idea.types.StubBasedTypeScopeProvider.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.psi.IPsiModelAssociations> bindIPsiModelAssociations() {
		return org.eclipse.xtext.idea.types.psi.PsiJvmModelAssociations.class;
	}
	
	
}
