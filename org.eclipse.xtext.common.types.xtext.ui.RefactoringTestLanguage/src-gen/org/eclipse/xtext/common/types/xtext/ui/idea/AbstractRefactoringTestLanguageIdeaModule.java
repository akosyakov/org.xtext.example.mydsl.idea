package org.eclipse.xtext.common.types.xtext.ui.idea;

public class AbstractRefactoringTestLanguageIdeaModule extends org.eclipse.xtext.idea.DefaultIdeaModule {
	
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public void configureStubKeys(com.google.inject.Binder binder) {
		binder.bind(new com.google.inject.TypeLiteral<com.intellij.psi.stubs.StubIndexKey<String, org.eclipse.xtext.psi.PsiNamedEObject>>() {}) 
			.annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex.EOBJECT_NAME))
			.toInstance(com.intellij.psi.stubs.StubIndexKey.<String, org.eclipse.xtext.psi.PsiNamedEObject>createIndexKey("org.eclipse.xtext.common.types.xtext.ui.RefactoringTestLanguage" + org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex.EOBJECT_NAME));
		binder.bind(new com.google.inject.TypeLiteral<com.intellij.psi.stubs.StubIndexKey<String, org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject>>() {}) 
			.annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex.CLASS_SHORT_NAMES))
			.toInstance(com.intellij.psi.stubs.StubIndexKey.<String, org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject>createIndexKey("org.eclipse.xtext.common.types.xtext.ui.RefactoringTestLanguage" + org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex.CLASS_SHORT_NAMES));
		binder.bind(new com.google.inject.TypeLiteral<com.intellij.psi.stubs.StubIndexKey<String, org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject>>() {}) 
			.annotatedWith(com.google.inject.name.Names.named(org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex.CLASS_FQN))
			.toInstance(com.intellij.psi.stubs.StubIndexKey.<String, org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject>createIndexKey("org.eclipse.xtext.common.types.xtext.ui.RefactoringTestLanguage" + org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex.CLASS_FQN));
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex> bindPsiNamedEObjectIndex() {
		return org.eclipse.xtext.common.types.xtext.ui.idea.lang.psi.stubindex.RefactoringTestLanguagePsiNamedEObjectIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex> bindJvmDeclaredTypeShortNameIndex() {
		return org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.stubindex.RefactoringTestLanguageJvmDeclaredTypeShortNameIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex> bindJvmDeclaredTypeFullClassNameIndex() {
		return org.eclipse.xtext.common.types.xtext.ui.idea.lang.types.stubindex.RefactoringTestLanguageJvmDeclaredTypeFullClassNameIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.openapi.fileTypes.SyntaxHighlighter> bindSyntaxHighlighter() {
		return org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageSyntaxHighlighter.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.lexer.Lexer> bindLexer() {
		return org.eclipse.xtext.common.types.xtext.ui.idea.lang.parser.RefactoringTestLanguageLexer.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends org.eclipse.xtext.generator.idea.TokenTypeProvider> bindTokenTypeProvider() {
		return org.eclipse.xtext.common.types.xtext.ui.idea.lang.parser.RefactoringTestLanguageTokenTypeProvider.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	public Class<? extends com.intellij.lang.ParserDefinition> bindParserDefinition() {
		return org.eclipse.xtext.common.types.xtext.ui.idea.lang.parser.RefactoringTestLanguageParserDefinition.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends org.eclipse.xtext.idea.lang.IElementTypeProvider> bindIElementTypeProvider() {
		return org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageElementTypeProvider.class;
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
	public Class<? extends org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator> bindJvmModelAssociator() {
		return org.eclipse.xtext.idea.jvmmodel.PsiJvmModelAssociator.class;
	}
	
	
}
