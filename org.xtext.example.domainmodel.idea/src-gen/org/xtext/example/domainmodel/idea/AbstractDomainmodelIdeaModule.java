package org.xtext.example.domainmodel.idea;

import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex;
import org.eclipse.xtext.service.SingletonBinding;
import org.eclipse.xtext.xbase.typesystem.internal.IFeatureScopeTracker;
import org.eclipse.xtext.xbase.typesystem.internal.OptimizingFeatureScopeTrackerProvider;

public class AbstractDomainmodelIdeaModule extends org.eclipse.xtext.idea.DefaultIdeaModule {

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
	public Class<? extends org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator> bindJvmModelAssociator() {
		return org.eclipse.xtext.idea.jvmmodel.PsiJvmModelAssociator.class;
	}

	@SingletonBinding
	public Class<? extends JvmDeclaredTypeShortNameIndex> bindJvmDeclaredTypeShortNameIndex() {
		return JvmDeclaredTypeShortNameIndex.class;
	}

	public Class<? extends IFeatureScopeTracker.Provider> bindIFeatureScopeTracker$Provider() {
		return OptimizingFeatureScopeTrackerProvider.class;
	}

}
