package org.eclipse.xtext.generator.idea

import com.intellij.lexer.Lexer
import com.intellij.openapi.fileTypes.SyntaxHighlighter
import java.util.Set
import javax.inject.Inject
import org.eclipse.xpand2.output.Outlet
import org.eclipse.xpand2.output.Output
import org.eclipse.xpand2.output.OutputImpl
import org.eclipse.xtext.AbstractRule
import org.eclipse.xtext.Grammar
import org.eclipse.xtext.generator.BindFactory
import org.eclipse.xtext.generator.Binding
import org.eclipse.xtext.generator.Generator
import org.eclipse.xtext.generator.Xtend2ExecutionContext
import org.eclipse.xtext.generator.Xtend2GeneratorFragment

class IdeaPluginGenerator extends Xtend2GeneratorFragment {
	
	private static String META_INF_PLUGIN = "META_INF_PLUGIN"
	
	private static String PLUGIN = "PLUGIN"
	
	private static String DOT_IDEA = "DOT_IDEA"
	
	private String encoding
	
	private String fileExtension
	
	private Set<String> libraries = newHashSet();
	
	private String pathIdeaPluginProject
	
	@Inject
	extension IdeaPluginExtension
	
	@Inject
	extension IdeaPluginClassNames
	
	override generate(Grammar grammar, Xtend2ExecutionContext ctx) {
//		for (rule:grammar.rules) {
//			ctx.writeFile(Generator::SRC_GEN, grammar.getPsiElementPath(rule), grammar.compilePsiElement(rule))
//			ctx.writeFile(Generator::SRC_GEN, grammar.getPsiElementImplPath(rule), grammar.compilePsiElementImpl(rule))	
//		}
		var outlet_src_gen = Generator.SRC_GEN 
		var outlet_src = Generator.SRC
		if (pathIdeaPluginProject != null) {
			val newOutlet = new Outlet("IDEA_SRC_GEN") => [
				name = "IDEA_SRC_GEN"
				path = pathIdeaPluginProject+"/src-gen"
			]
			ctx.xpandExecutionContext.output.addOutlet(newOutlet)
			outlet_src_gen = newOutlet.name
			
			val newSrcOutlet = new Outlet("IDEA_SRC") => [
				name = "IDEA_SRC"
				path = pathIdeaPluginProject+"/src"
			]
			ctx.xpandExecutionContext.output.addOutlet(newSrcOutlet)
			outlet_src = newSrcOutlet.name
		}
		
		val bindFactory = new BindFactory();
		bindFactory.addTypeToType(SyntaxHighlighter.name, grammar.syntaxHighlighterName)
		bindFactory.addTypeToType(Lexer.name, grammar.lexerName)
		val bindings = bindFactory.bindings
		
		ctx.writeFile(outlet_src, grammar.standaloneSetupIdea.toJavaPath, grammar.compileStandaloneSetup)
		ctx.writeFile(outlet_src_gen, grammar.languageName.toJavaPath, grammar.compileLanguage)
		ctx.writeFile(outlet_src_gen, grammar.fileTypeName.toJavaPath, grammar.compileFileType)
		ctx.writeFile(outlet_src_gen, grammar.fileTypeFactoryName.toJavaPath, grammar.compileFileTypeFactory)
		ctx.writeFile(outlet_src_gen, grammar.fileImplName.toJavaPath, grammar.compileFileImpl)
		ctx.writeFile(outlet_src_gen, grammar.tokenTypesName.toJavaPath, grammar.compileTokenTypes);
		ctx.writeFile(outlet_src_gen, grammar.lexerName.toJavaPath, grammar.compileLexer);
		ctx.writeFile(outlet_src_gen, grammar.tokenTypeProviderName.toJavaPath, grammar.compileTokenTypeProvider);
		ctx.writeFile(outlet_src_gen, grammar.parserDefinitionName.toJavaPath, grammar.compileParserDefinition);
		ctx.writeFile(outlet_src_gen, grammar.syntaxHighlighterName.toJavaPath, grammar.compileSyntaxHighlighter);
		ctx.writeFile(outlet_src_gen, grammar.syntaxHighlighterFactoryName.toJavaPath, grammar.compileSyntaxHighlighterFactory);
		ctx.writeFile(outlet_src_gen, grammar.abstractIdeaModuleName.toJavaPath, grammar.compileGuiceModuleIdeaGenerated(bindings));
		
		
		if (pathIdeaPluginProject != null) {
			var output = new OutputImpl();
			output.addOutlet(PLUGIN, pathIdeaPluginProject);
			output.addOutlet(META_INF_PLUGIN, pathIdeaPluginProject + "/META-INF");
			output.addOutlet(DOT_IDEA, pathIdeaPluginProject + "/.idea");
			
			output.writeFile(META_INF_PLUGIN, "plugin.xml", grammar.compilePluginXml)
			output.writeFile(PLUGIN, iml, grammar.compileIml)
			output.writeFile(DOT_IDEA, "modules.xml", grammar.compileModulesXml);
			output.writeFile(DOT_IDEA, "misc.xml", grammar.compileMiscXml);
		}
	}
	
	def CharSequence compileGuiceModuleIdeaGenerated(Grammar grammar, Set<Binding> bindings) '''
		package «grammar.abstractIdeaModuleName.toPackageName»;
		
		public class «grammar.abstractIdeaModuleName.toSimpleName» extends org.eclipse.xtext.idea.DefaultIdeaModule {
			
			«FOR it : bindings»
				«IF !value.provider && value.statements.isEmpty»
					// contributed by «contributedBy»
					«IF key.singleton»@org.eclipse.xtext.service.SingletonBinding«IF key.eagerSingleton»(eager=true)«ENDIF»«ENDIF»
					public «IF value.expression==null»Class<? extends «key.type»>«ELSE»«key.type»«ENDIF» «bindMethodName(it)»() {
						return «IF value.expression!=null»«value.expression»«ELSE»«value.typeName».class«ENDIF»;
					}
				«ELSEIF value.statements.isEmpty»
					// contributed by «contributedBy»
					«IF key.singleton»@org.eclipse.xtext.service.SingletonBinding«IF key.eagerSingleton»(eager=true)«ENDIF»«ENDIF»
					public «IF value.expression==null»Class<? extends com.google.inject.Provider<«key.type»>>«ELSE»com.google.inject.Provider<«key.type»>«ENDIF» «bindMethodName(it)»() {
						return «IF value.expression!=null»«value.expression»«ELSE»«value.typeName».class«ENDIF»;
					}
				«ELSE»
					// contributed by «contributedBy»
					public void «bindMethodName(it)»(com.google.inject.Binder binder) {
				«FOR statement : value.statements»
						«statement»«IF !statement.endsWith(";")»;«ENDIF»
				«ENDFOR»
					}
				«ENDIF»
			«ENDFOR»
			
			
		}
	'''
	
	def bindMethodName(Binding it) {
		val prefix = if (!it.value.provider && it.value.statements.isEmpty) 
			'bind' 
		else {
			if (it.value.statements.isEmpty)
				'provide'
			else 
				'configure'
		}
		val suffix = if (value.expression!=null && !value.provider) 'ToInstance' else ''
		return prefix + simpleMethodName(key.type) + suffix
	}
	
	def private simpleMethodName(String qn) {
		qn.replaceAll('<','\\.').replaceAll('>','\\.').split('\\.').filter(e|e.matches('[A-Z].*')).join('$');
	}
	
	def iml() {
		pathIdeaPluginProject.substring(pathIdeaPluginProject.lastIndexOf("/") + 1) + ".iml"
	}
	
	def addOutlet(Output output, String outletName, String path) {
		output.addOutlet(new Outlet(false, getEncoding(), outletName, false, path))
	}
	
	def writeFile(Output output, String outletName, String filename, CharSequence contents) {
		output.openFile(filename, outletName);
		output.write(contents.toString);
		output.closeFile();
	}
	
	def getEncoding() {
		if (encoding != null) {
			return encoding;
		}
		return System::getProperty("file.encoding");
	}
	
	def addLibrary(String library) {
		libraries.add(library)
	}
	
	def setFileExtensions(String fileExtensions) {
		this.fileExtension = fileExtensions.split("\\s*,\\s*").get(0)
	}
	
	def setEncoding(String encoding) {
		this.encoding = encoding
	}
	
	def setPathIdeaPluginProject(String pathIdeaPluginProject) {
		this.pathIdeaPluginProject = pathIdeaPluginProject
	}
	
	def compileModulesXml(Grammar grammar)'''
		<?xml version="1.0" encoding="UTF-8"?>
		<project version="4">
		  <component name="ProjectModuleManager">
		    <modules>
		      <module fileurl="file://$PROJECT_DIR$/«iml»" filepath="$PROJECT_DIR$/«iml»" />
		    </modules>
		  </component>
		</project>
	'''
	
	def compileMiscXml(Grammar grammar)'''
		<?xml version="1.0" encoding="UTF-8"?>
		<project version="4">
		  <component name="ProjectRootManager" version="2" languageLevel="JDK_1_6" assert-keyword="true" jdk-15="true" project-jdk-name="IDEA IC-123.72" project-jdk-type="IDEA JDK" />
		  <output url="file://$PROJECT_DIR$/out" />
		</project>
	'''
	
	def compilePluginXml(Grammar grammar)'''
		<idea-plugin version="2">
			<id>«grammar.languageID»</id>
			<name>«grammar.simpleName» Support</name>
			<description>
		      This plugin enables smart editing of «grammar.simpleName» files.
			</description>
			<version>1.0.0</version>
			<vendor>My Company</vendor>
		
			<idea-version since-build="123.72"/>
		
			<extensions defaultExtensionNs="com.intellij">
				<lang.syntaxHighlighterFactory key="«grammar.languageID»" implementationClass="«grammar.syntaxHighlighterFactoryName»"/>
				<lang.parserDefinition language="«grammar.languageID»" implementationClass="«grammar.parserDefinitionName»"/>
				<fileTypeFactory implementation="«grammar.fileTypeFactoryName»"/>
			</extensions>
		
		</idea-plugin>
	'''
	
	def compileIml(Grammar grammar)'''
		<?xml version="1.0" encoding="UTF-8"?>
		<module type="PLUGIN_MODULE" version="4">
		  <component name="DevKit.ModuleBuildProperties" url="file://$MODULE_DIR$/META-INF/plugin.xml" />
		  <component name="NewModuleRootManager" inherit-compiler-output="true">
		    <exclude-output />
		    <content url="file://$MODULE_DIR$">
		      <sourceFolder url="file://$MODULE_DIR$/src" isTestSource="false" />
		      <sourceFolder url="file://$MODULE_DIR$/test" isTestSource="true" />
		    </content>
		 	<orderEntry type="jdk" jdkName="IDEA IC-123.72" jdkType="IDEA JDK" />
		    <orderEntry type="sourceFolder" forTests="false" />
		    «FOR library:libraries»
		    <orderEntry type="module-library">
		      <library>
		        <CLASSES>
		          <root url="jar://«library»!/" />
		        </CLASSES>
		        <JAVADOC />
		        <SOURCES />
		      </library>
		    </orderEntry>
		    «ENDFOR»
		  </component>
		</module>
	'''
	
	def compilePsiElement(Grammar grammar, AbstractRule rule)'''
		package «grammar.psiPackageName»;
		«IF rule.hasMultipleAssigment»
		
		import java.util.List;
		«ENDIF»
		
		import com.intellij.psi.«rule.psiElementSuperClassName»;
		
		public interface «rule.psiElementClassName» extends «rule.psiElementSuperClassName» {
			«FOR assignment:rule.assignmentsWithoutName»
			
			«assignment.typeName» «assignment.getter»();
			
			void «assignment.setter»(«assignment.typeName» «assignment.feature»);
			«ENDFOR»
		
		}
	'''
	
	def compileFileImpl(Grammar grammar)'''
		package «grammar.psiImplPackageName»;
		
		import org.eclipse.xtext.psi.impl.BaseXtextFile;
		import «grammar.fileTypeName»;
		import «grammar.languageName»;
		
		import com.intellij.openapi.fileTypes.FileType;
		import com.intellij.psi.FileViewProvider;
		
		public final class «grammar.fileImplName.toSimpleName» extends BaseXtextFile {
		
			public «grammar.fileImplName.toSimpleName»(FileViewProvider viewProvider) {
				super(viewProvider, «grammar.languageName.toSimpleName».INSTANCE);
			}
		
			public FileType getFileType() {
				return «grammar.fileTypeName.toSimpleName».INSTANCE;
			}
		
		}
	'''
	
	def compileFileTypeFactory(Grammar grammar)'''
		package «grammar.fileTypeFactoryName.toPackageName»;
		
		import com.intellij.openapi.fileTypes.FileTypeConsumer;
		import com.intellij.openapi.fileTypes.FileTypeFactory;
		import org.jetbrains.annotations.NotNull;
		
		public class «grammar.fileTypeFactoryName.toSimpleName» extends FileTypeFactory {
		
			public void createFileTypes(@NotNull FileTypeConsumer consumer) {
				consumer.consume(«grammar.fileTypeName».INSTANCE, «grammar.fileTypeName».DEFAULT_EXTENSION);
			}
		
		}
	'''
	
	def compileFileType(Grammar grammar)'''
		package «grammar.fileTypeName.toPackageName»;
		
		import javax.swing.Icon;
		
		import com.intellij.openapi.fileTypes.LanguageFileType;
		import org.jetbrains.annotations.NonNls;
		
		public final class «grammar.fileTypeName.toSimpleName» extends LanguageFileType {
		
			public static final «grammar.fileTypeName.toSimpleName» INSTANCE = new «grammar.fileTypeName.toSimpleName»();
			
			@NonNls 
			public static final String DEFAULT_EXTENSION = "«fileExtension»";
		
			private «grammar.fileTypeName.toSimpleName»() {
				super(«grammar.languageName.toSimpleName».INSTANCE);
			}
		
			public String getDefaultExtension() {
				return DEFAULT_EXTENSION;
			}
		
			public String getDescription() {
				return "«grammar.simpleName» files";
			}
		
			public Icon getIcon() {
				return null;
			}
		
			public String getName() {
				return "«grammar.simpleName»";
			}
		
		}
	'''
	
	def compileLanguage(Grammar grammar)'''
		package «grammar.languageName.toPackageName»;
		
		import org.eclipse.xtext.idea.lang.AbstractXtextLanguage;
		
		import com.google.inject.Injector;
		
		public final class «grammar.languageName.toSimpleName» extends AbstractXtextLanguage {
		
			public static final «grammar.languageName.toSimpleName» INSTANCE = new «grammar.languageName.toSimpleName»();
		
			private Injector injector;
		
			private «grammar.languageName.toSimpleName»() {
				super("«grammar.languageID»");
				this.injector = new «grammar.standaloneSetupIdea»().createInjectorAndDoEMFRegistration();
				
			}
		
			@Override
			protected Injector getInjector() {
				return injector;
			}
		}
	'''
	
	def compileStandaloneSetup(Grammar grammar) '''
		package «grammar.standaloneSetupIdea.toPackageName»;
		
		import org.eclipse.xtext.util.Modules2;
		import «naming.setupImpl(grammar)»;
		
		import com.google.inject.Guice;
		import com.google.inject.Injector;
		import com.google.inject.Module;
		
		public class «grammar.standaloneSetupIdea.toSimpleName» extends «naming.toSimpleName(naming.setupImpl(grammar))» {
		
		    @Override
		    public Injector createInjector() {
		        Module runtimeModule = new «naming.guiceModuleRt(grammar)»();
		        Module ideaModule = new «grammar.ideaModuleName»();
		        Module mergedModule = Modules2.mixin(runtimeModule, ideaModule);
		        return Guice.createInjector(mergedModule);
		    }
		
		}
	'''
	
	def compileTokenTypes(Grammar grammar)'''
		package «grammar.tokenTypesName.toPackageName»;
		
		import static «grammar.internalParserName».*;
		
		import java.util.HashMap;
		import java.util.Map;
		
		import «grammar.languageName»;
		
		import com.intellij.psi.tree.IElementType;
		import com.intellij.psi.tree.TokenSet;
		
		public abstract class «grammar.tokenTypesName.toSimpleName» {
		
			public static final IElementType[] tokenTypes = new IElementType[tokenNames.length];
			
			public static final Map<String, IElementType> nameToTypeMap = new HashMap<String, IElementType>();
		
			static {
				for (int i = 0; i < tokenNames.length; i++) {
					tokenTypes[i] = new IElementType(tokenNames[i], «grammar.languageName.toSimpleName».INSTANCE);
					nameToTypeMap.put(tokenNames[i], tokenTypes[i]);
				}
			}
		
			public static final TokenSet COMMENTS = TokenSet.create(tokenTypes[RULE_SL_COMMENT],
					tokenTypes[RULE_ML_COMMENT]);
			
			public static final TokenSet LINE_COMMENTS = TokenSet.create(tokenTypes[RULE_SL_COMMENT]);
			
			public static final TokenSet BLOCK_COMMENTS = TokenSet.create(tokenTypes[RULE_ML_COMMENT]);
		
			public static final TokenSet WHITESPACES = TokenSet.create(tokenTypes[RULE_WS]);
		
			public static final TokenSet STRINGS = TokenSet.create(tokenTypes[RULE_STRING]);
		
		}
	'''
	
	def compileLexer(Grammar grammar)'''
		package «grammar.lexerName.toPackageName»;
		
		import org.antlr.runtime.ANTLRStringStream;
		import org.antlr.runtime.CommonToken;
		import org.antlr.runtime.Token;
		import «grammar.antlrLexerName»;
		
		import com.intellij.lexer.LexerBase;
		import com.intellij.psi.tree.IElementType;
		
		public class «grammar.lexerName.toSimpleName» extends LexerBase {
		
		    private «grammar.antlrLexerName.toSimpleName» internalLexer;
		    private CommonToken token;
		
		    private CharSequence buffer;
		    private int startOffset;
		    private int endOffset;
		
		    public void start(CharSequence buffer, int startOffset, int endOffset, int initialState) {
		        this.buffer = buffer;
		        this.startOffset = startOffset;
		        this.endOffset = endOffset;
		
		        String text = buffer.subSequence(startOffset, endOffset).toString();
		        internalLexer = new «grammar.antlrLexerName.toSimpleName»(new ANTLRStringStream(text));
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
		        return «grammar.tokenTypesName».tokenTypes[type];
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
	'''
	
	def compileTokenTypeProvider(Grammar grammar)'''
		package «grammar.tokenTypeProviderName.toPackageName»;
		
		import java.util.Arrays;
		import java.util.List;
		
		import org.eclipse.xtext.generator.idea.TokenTypeProvider;
		
		import com.intellij.psi.tree.IElementType;
		
		public class «grammar.tokenTypeProviderName.toSimpleName» implements TokenTypeProvider {
		
		    public static final List<IElementType> I_ELEMENT_TYPES = Arrays.asList(«grammar.tokenTypesName».tokenTypes);
		
		    public int getType(IElementType iElementType) {
		        return I_ELEMENT_TYPES.indexOf(iElementType);
		    }
		
		}
	'''

	def compileSyntaxHighlighterFactory(Grammar grammar)'''
		package «grammar.syntaxHighlighterFactoryName.toPackageName»;
		
		import com.google.inject.Inject;
		import com.google.inject.Provider;
		
		import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
		import com.intellij.openapi.fileTypes.SyntaxHighlighter;
		
		import org.jetbrains.annotations.NotNull;
		
		public class «grammar.syntaxHighlighterFactoryName.toSimpleName» extends SingleLazyInstanceSyntaxHighlighterFactory {
			
			@Inject Provider<SyntaxHighlighter> syntaxHighlighterProvider;
			
			public MyDslSyntaxHighlighterFactory() {
				«grammar.languageName».INSTANCE.injectMembers(this);
			}
			
		    @NotNull
		    protected SyntaxHighlighter createHighlighter() {
		        return syntaxHighlighterProvider.get();
		    }
		
		}
	'''
	
	def compileSyntaxHighlighter(Grammar grammar)'''
		package «grammar.syntaxHighlighterName.toPackageName»;
		
		import com.intellij.lexer.Lexer;
		import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
		import com.intellij.openapi.editor.colors.TextAttributesKey;
		import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
		import com.intellij.psi.tree.IElementType;
		import «grammar.lexerName»;
		import «grammar.tokenTypesName»;
		import org.jetbrains.annotations.NotNull;
		
		public class «grammar.syntaxHighlighterName.toSimpleName» extends SyntaxHighlighterBase {
		
		    @NotNull
		    public Lexer getHighlightingLexer() {
		        return new «grammar.lexerName.toSimpleName»();
		    }
		
		    @NotNull
		    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		        if («grammar.tokenTypesName.toSimpleName».STRINGS.contains(tokenType)) {
		            return pack(DefaultLanguageHighlighterColors.STRING);
		        }
				if («grammar.tokenTypesName.toSimpleName».LINE_COMMENTS.contains(tokenType)) {
					return pack(DefaultLanguageHighlighterColors.LINE_COMMENT);
				}
				if («grammar.tokenTypesName.toSimpleName».BLOCK_COMMENTS.contains(tokenType)) {
					return pack(DefaultLanguageHighlighterColors.BLOCK_COMMENT);
				}
		        String myDebugName = tokenType.toString();
				if (myDebugName.matches("^'.*\\w.*'$")) {
					return pack(DefaultLanguageHighlighterColors.KEYWORD);
		        }
		        return new TextAttributesKey[0];
		    }
		
		}
	'''
	
	def compileParserDefinition(Grammar grammar)'''
		package «grammar.parserDefinitionName.toPackageName»;
		
		import org.eclipse.xtext.idea.lang.BaseXtextPsiParser;
		import org.eclipse.xtext.idea.lang.IElementTypeProvider;
		import org.eclipse.xtext.psi.impl.PsiEObjectImpl;
		import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl;
		import org.eclipse.xtext.psi.impl.PsiReferenceEObjectImpl;
		import org.jetbrains.annotations.NotNull;
		import «grammar.languageName»;
		import «grammar.fileImplName»;
		
		import com.google.inject.Inject;
		import com.google.inject.Provider;
		import com.intellij.lang.ASTNode;
		import com.intellij.lang.ParserDefinition;
		import com.intellij.lang.PsiParser;
		import com.intellij.lexer.Lexer;
		import com.intellij.openapi.project.Project;
		import com.intellij.psi.FileViewProvider;
		import com.intellij.psi.PsiElement;
		import com.intellij.psi.PsiFile;
		import com.intellij.psi.tree.IFileElementType;
		import com.intellij.psi.tree.TokenSet;
		
		public class «grammar.parserDefinitionName.toSimpleName» implements ParserDefinition {
			
			@Inject
			private IElementTypeProvider elementTypeProvider;
			
			@Inject
			private Provider<BaseXtextPsiParser> baseXtextPsiParserProvider;
			
			@Inject
			private Provider<Lexer> lexerProvider; 
			
			public «grammar.parserDefinitionName.toSimpleName»() {
				«grammar.languageName.toSimpleName».INSTANCE.injectMembers(this);
			}
		
			@NotNull
			public Lexer createLexer(Project project) {
				return lexerProvider.get();
			}
		
			public IFileElementType getFileNodeType() {
				return elementTypeProvider.getFileType();
			}
		
			@NotNull
			public TokenSet getWhitespaceTokens() {
				return «grammar.tokenTypesName».WHITESPACES;
			}
		
			@NotNull
			public TokenSet getCommentTokens() {
				return «grammar.tokenTypesName».COMMENTS;
			}
		
			@NotNull
			public TokenSet getStringLiteralElements() {
				return «grammar.tokenTypesName».STRINGS;
			}
		
			@NotNull
			public PsiParser createParser(Project project) {
				return baseXtextPsiParserProvider.get();
			}
		
			public PsiFile createFile(FileViewProvider viewProvider) {
				return new «grammar.fileImplName.toSimpleName»(viewProvider);
			}
		
			public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
				return SpaceRequirements.MAY;
			}
		
			@NotNull
			public PsiElement createElement(ASTNode node) {
				if (elementTypeProvider.getNamedObjectType().equals(node.getElementType())) {
					return new PsiNamedEObjectImpl(node, elementTypeProvider.getNameType());
				}
				if (elementTypeProvider.getCrossReferenceType().equals(node.getElementType())) {
					return new PsiReferenceEObjectImpl(node);
				}
				return new PsiEObjectImpl(node);
			}
		
		}
	'''
	
}