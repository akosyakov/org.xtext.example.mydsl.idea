package org.xtext.example.domainmodel.idea;

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
	
	
}
