package org.xtext.example.domainmodel.idea;

import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex;
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;

import com.google.inject.Binder;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import com.intellij.psi.stubs.StubIndexKey;


public class AbstractDomainmodelIdeaModule extends org.eclipse.xtext.idea.DefaultIdeaModule {
	
	public void configureStubKeys(Binder binder) {
		binder.bind(new TypeLiteral<StubIndexKey<String, PsiNamedEObject>>() {}).annotatedWith(Names.named(PsiNamedEObjectIndex.EOBJECT_NAME))
				.toInstance(StubIndexKey.<String, PsiNamedEObject>createIndexKey("org.xtext.example.domainmodel.Domainmodel.eobject.name"));
		binder.bind(new TypeLiteral<StubIndexKey<String, PsiJvmNamedEObject>>() {}).annotatedWith(Names.named(JvmDeclaredTypeShortNameIndex.CLASS_SHORT_NAMES))
				.toInstance(StubIndexKey.<String, PsiJvmNamedEObject>createIndexKey("org.xtext.example.domainmodel.Domainmodel.java.class.shortname"));
		binder.bind(new TypeLiteral<StubIndexKey<String, PsiJvmNamedEObject>>() {}).annotatedWith(Names.named(JvmDeclaredTypeFullClassNameIndex.CLASS_FQN))
				.toInstance(StubIndexKey.<String, PsiJvmNamedEObject>createIndexKey("org.xtext.example.domainmodel.Domainmodel.java.class.fqn"));
	}
	
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends PsiNamedEObjectIndex> bindPsiNamedEObjectIndex() {
		return org.xtext.example.domainmodel.idea.lang.psi.stubindex.DomainmodelPsiNamedEObjectIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends JvmDeclaredTypeShortNameIndex> bindJvmDeclaredTypeShortNameIndex() {
		return org.xtext.example.domainmodel.idea.lang.types.stubindex.DomainmodelJvmDeclaredTypeShortNameIndex.class;
	}
	// contributed by org.eclipse.xtext.generator.idea.IdeaPluginGenerator
	@org.eclipse.xtext.service.SingletonBinding
	public Class<? extends JvmDeclaredTypeFullClassNameIndex> bindJvmDeclaredTypeFullClassNameIndex() {
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
	
}
