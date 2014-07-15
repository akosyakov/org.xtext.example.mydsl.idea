package org.eclipse.xtext.generator.idea

import java.util.Set
import javax.inject.Inject
import org.eclipse.xpand2.output.Outlet
import org.eclipse.xpand2.output.Output
import org.eclipse.xpand2.output.OutputImpl
import org.eclipse.xtext.AbstractRule
import org.eclipse.xtext.Grammar
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
		ctx.writeFile(outlet_src, naming.asPath(grammar.ideaSetupName)+".java", grammar.compileStandaloneSetup)
		ctx.writeFile(outlet_src_gen, grammar.languagePath, grammar.compileLanguage)
		ctx.writeFile(outlet_src_gen, grammar.fileTypePath, grammar.compileFileType)
		ctx.writeFile(outlet_src_gen, grammar.fileTypeFactoryPath, grammar.compileFileTypeFactory)
		ctx.writeFile(outlet_src_gen, grammar.fileImplPath, grammar.compileFileImpl)
		ctx.writeFile(outlet_src_gen, grammar.tokenTypesPath, grammar.compileTokenTypes);
		ctx.writeFile(outlet_src_gen, grammar.lexerPath, grammar.compileLexer);
		ctx.writeFile(outlet_src_gen, grammar.tokenTypeProviderPath, grammar.compileTokenTypeProvider);
		ctx.writeFile(outlet_src_gen, grammar.parserDefinitionPath, grammar.compileParserDefinition);
		ctx.writeFile(outlet_src_gen, grammar.syntaxHighlighterPath, grammar.compileSyntaxHighlighter);
		ctx.writeFile(outlet_src_gen, grammar.syntaxHighlighterFactoryPath, grammar.compileSyntaxHighlighterFactory);
		
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
		<lang.syntaxHighlighterFactory key="«grammar.languageID»" implementationClass="«grammar.langPackageName».«grammar.syntaxHighlighterFactoryClassName»"/>
		<lang.parserDefinition language="«grammar.languageID»" implementationClass="«grammar.parsingPackageName».«grammar.parserDefinitionClassName»"/>
		<fileTypeFactory implementation="«grammar.langPackageName».«grammar.fileTypeFactoryClassName»"/>
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
import «grammar.langPackageName».«grammar.fileTypeClassName»;
import «grammar.langPackageName».«grammar.languageClassName»;

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;

public final class «grammar.fileImplClassName» extends BaseXtextFile {

	public «grammar.fileImplClassName»(FileViewProvider viewProvider) {
		super(viewProvider, «grammar.languageClassName».INSTANCE);
	}

	public FileType getFileType() {
		return «grammar.fileTypeClassName».INSTANCE;
	}

}
	'''
	
	def compileFileTypeFactory(Grammar grammar)'''
package «grammar.langPackageName»;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class «grammar.fileTypeFactoryClassName» extends FileTypeFactory {

	public void createFileTypes(@NotNull FileTypeConsumer consumer) {
		consumer.consume(«grammar.fileTypeClassName».INSTANCE, «grammar.fileTypeClassName».DEFAULT_EXTENSION);
	}

}
	'''
	
	def compileFileType(Grammar grammar)'''
package «grammar.langPackageName»;

import javax.swing.Icon;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;

public final class «grammar.fileTypeClassName» extends LanguageFileType {

	public static final «grammar.fileTypeClassName» INSTANCE = new «grammar.fileTypeClassName»();
	
	@NonNls 
	public static final String DEFAULT_EXTENSION = "«fileExtension»";

	private «grammar.fileTypeClassName»() {
		super(«grammar.languageClassName».INSTANCE);
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
		package «grammar.langPackageName»;
		
		import org.eclipse.xtext.idea.lang.AbstractXtextLanguage;
		
		import com.google.inject.Injector;
		
		public final class «grammar.languageClassName» extends AbstractXtextLanguage {
		
			public static final «grammar.languageClassName» INSTANCE = new «grammar.languageClassName»();
		
			private Injector injector;
		
			private «grammar.languageClassName»() {
				super("«grammar.languageID»");
				this.injector = new «getIdeaSetupName(grammar)»().createInjectorAndDoEMFRegistration();
				
			}
		
			@Override
			protected Injector getInjector() {
				return injector;
			}
		}
	'''
	
	def compileStandaloneSetup(Grammar grammar) {
		val setupName = getIdeaSetupName(grammar)
		'''
		package «naming.toPackageName(setupName)»;
		
		import org.eclipse.xtext.util.Modules2;
		import «naming.setupImpl(grammar)»;
		
		import com.google.inject.Guice;
		import com.google.inject.Injector;
		import com.google.inject.Module;
		
		public class «naming.toSimpleName(setupName)» extends «naming.toSimpleName(naming.setupImpl(grammar))» {
		
		    @Override
		    public Injector createInjector() {
		        Module runtimeModule = new «naming.guiceModuleRt(grammar)»();
		        Module ideaModule = new «guiceModuleIdea(grammar)»();
		        Module mergedModule = Modules2.mixin(runtimeModule, ideaModule);
		        return Guice.createInjector(mergedModule);
		    }
		
		}
		'''
	}
	
	def compileTokenTypes(Grammar grammar)'''
		package «grammar.parsingPackageName»;
		
		import static «grammar.internalParsingPackageName».«grammar.parserClassName».tokenNames;
		
		import java.util.HashMap;
		import java.util.Map;
		
		import «grammar.langPackageName».«grammar.languageClassName»;
		import «grammar.internalParsingPackageName».«grammar.parserClassName»;
		
		import com.intellij.psi.tree.IElementType;
		import com.intellij.psi.tree.TokenSet;
		
		public abstract class «grammar.tokenTypesClassName» {
		
			public static final IElementType[] tokenTypes = new IElementType[tokenNames.length];
			
			public static final Map<String, IElementType> nameToTypeMap = new HashMap<String, IElementType>();
		
			static {
				for (int i = 0; i < tokenNames.length; i++) {
					tokenTypes[i] = new IElementType(tokenNames[i], «grammar.languageClassName».INSTANCE);
					nameToTypeMap.put(tokenNames[i], tokenTypes[i]);
				}
			}
		
			public static final TokenSet COMMENTS = TokenSet.create(tokenTypes[«grammar.parserClassName».RULE_SL_COMMENT],
					tokenTypes[«grammar.parserClassName».RULE_ML_COMMENT]);
			
			public static final TokenSet LINE_COMMENTS = TokenSet.create(tokenTypes[«grammar.parserClassName».RULE_SL_COMMENT]);
			
			public static final TokenSet BLOCK_COMMENTS = TokenSet.create(tokenTypes[«grammar.parserClassName».RULE_ML_COMMENT]);
		
			public static final TokenSet WHITESPACES = TokenSet.create(tokenTypes[«grammar.parserClassName».RULE_WS]);
		
			public static final TokenSet STRINGS = TokenSet.create(tokenTypes[«grammar.parserClassName».RULE_STRING]);
		
		}
	'''
	
	def compileLexer(Grammar grammar)'''
		package «grammar.parsingPackageName»;
		
		import org.antlr.runtime.ANTLRStringStream;
		import org.antlr.runtime.CommonToken;
		import org.antlr.runtime.Token;
		import «grammar.internalParsingPackageName».«grammar.antlrLexerClassName»;
		
		import com.intellij.lexer.LexerBase;
		import com.intellij.psi.tree.IElementType;
		
		public class «grammar.lexerClassName» extends LexerBase {
		
		    private «grammar.antlrLexerClassName» internalLexer;
		    private CommonToken token;
		
		    private CharSequence buffer;
		    private int startOffset;
		    private int endOffset;
		
		    public void start(CharSequence buffer, int startOffset, int endOffset, int initialState) {
		        this.buffer = buffer;
		        this.startOffset = startOffset;
		        this.endOffset = endOffset;
		
		        String text = buffer.subSequence(startOffset, endOffset).toString();
		        internalLexer = new «grammar.antlrLexerClassName»(new ANTLRStringStream(text));
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
		        return «grammar.tokenTypesClassName».tokenTypes[type];
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
		package «grammar.parsingPackageName»;
		
		import java.util.Arrays;
		import java.util.List;
		
		import org.eclipse.xtext.generator.idea.TokenTypeProvider;
		
		import com.intellij.psi.tree.IElementType;
		
		public class «grammar.tokenTypeProviderClassName» implements TokenTypeProvider {
		
		    public static final List<IElementType> I_ELEMENT_TYPES = Arrays.asList(«grammar.tokenTypesClassName».tokenTypes);
		
		    public int getType(IElementType iElementType) {
		        return I_ELEMENT_TYPES.indexOf(iElementType);
		    }
		
		}
	'''

	def compileSyntaxHighlighterFactory(Grammar grammar)'''
		package «grammar.langPackageName»;
		
		import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
		import com.intellij.openapi.fileTypes.SyntaxHighlighter;
		import org.jetbrains.annotations.NotNull;
		
		public class «grammar.syntaxHighlighterFactoryClassName» extends SingleLazyInstanceSyntaxHighlighterFactory {
		
		    @NotNull
		    protected SyntaxHighlighter createHighlighter() {
		        return new «grammar.syntaxHighlighterClassName»();
		    }
		
		}
	'''
	
	def compileSyntaxHighlighter(Grammar grammar)'''
		package «grammar.langPackageName»;
		
		import com.intellij.lexer.Lexer;
		import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
		import com.intellij.openapi.editor.colors.TextAttributesKey;
		import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
		import com.intellij.psi.tree.IElementType;
		import «grammar.parsingPackageName».«grammar.lexerClassName»;
		import «grammar.parsingPackageName».«grammar.tokenTypesClassName»;
		import org.jetbrains.annotations.NotNull;
		
		public class «grammar.syntaxHighlighterClassName» extends SyntaxHighlighterBase {
		
		    @NotNull
		    public Lexer getHighlightingLexer() {
		        return new «grammar.lexerClassName»();
		    }
		
		    @NotNull
		    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
		        if («grammar.tokenTypesClassName».STRINGS.contains(tokenType)) {
		            return pack(DefaultLanguageHighlighterColors.STRING);
		        }
				if («grammar.tokenTypesClassName».LINE_COMMENTS.contains(tokenType)) {
					return pack(DefaultLanguageHighlighterColors.LINE_COMMENT);
				}
				if («grammar.tokenTypesClassName».BLOCK_COMMENTS.contains(tokenType)) {
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
		package «grammar.parsingPackageName»;
		
		import org.eclipse.xtext.idea.lang.BaseXtextPsiParser;
		import org.eclipse.xtext.idea.lang.IElementTypeProvider;
		import org.eclipse.xtext.psi.impl.PsiEObjectImpl;
		import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl;
		import org.eclipse.xtext.psi.impl.PsiReferenceEObjectImpl;
		import org.jetbrains.annotations.NotNull;
		import «grammar.langPackageName».«grammar.languageClassName»;
		import «grammar.psiImplPackageName».«grammar.fileImplClassName»;
		
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
		
		public class «grammar.parserDefinitionClassName» implements ParserDefinition {
			
			@Inject
			private IElementTypeProvider elementTypeProvider;
			
			@Inject
			private Provider<BaseXtextPsiParser> baseXtextPsiParserProvider; 
			
			public «grammar.parserDefinitionClassName»() {
				«grammar.languageClassName».INSTANCE.injectMembers(this);
			}
		
			@NotNull
			public Lexer createLexer(Project project) {
				return new «grammar.lexerClassName»();
			}
		
			public IFileElementType getFileNodeType() {
				return elementTypeProvider.getFileType();
			}
		
			@NotNull
			public TokenSet getWhitespaceTokens() {
				return «grammar.tokenTypesClassName».WHITESPACES;
			}
		
			@NotNull
			public TokenSet getCommentTokens() {
				return «grammar.tokenTypesClassName».COMMENTS;
			}
		
			@NotNull
			public TokenSet getStringLiteralElements() {
				return «grammar.tokenTypesClassName».STRINGS;
			}
		
			@NotNull
			public PsiParser createParser(Project project) {
				return baseXtextPsiParserProvider.get();
			}
		
			public PsiFile createFile(FileViewProvider viewProvider) {
				return new «grammar.fileImplClassName»(viewProvider);
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