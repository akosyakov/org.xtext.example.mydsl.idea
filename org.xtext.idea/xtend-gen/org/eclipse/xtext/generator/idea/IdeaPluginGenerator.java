package org.eclipse.xtext.generator.idea;

import com.google.common.base.Objects;
import com.intellij.lang.ParserDefinition;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.eclipse.xpand2.XpandExecutionContext;
import org.eclipse.xpand2.output.Outlet;
import org.eclipse.xpand2.output.Output;
import org.eclipse.xpand2.output.OutputImpl;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.generator.BindFactory;
import org.eclipse.xtext.generator.BindKey;
import org.eclipse.xtext.generator.BindValue;
import org.eclipse.xtext.generator.Binding;
import org.eclipse.xtext.generator.Generator;
import org.eclipse.xtext.generator.Naming;
import org.eclipse.xtext.generator.Xtend2ExecutionContext;
import org.eclipse.xtext.generator.Xtend2GeneratorFragment;
import org.eclipse.xtext.generator.idea.IdeaPluginClassNames;
import org.eclipse.xtext.generator.idea.IdeaPluginExtension;
import org.eclipse.xtext.generator.idea.TokenTypeProvider;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SuppressWarnings("all")
public class IdeaPluginGenerator extends Xtend2GeneratorFragment {
  private static String META_INF_PLUGIN = "META_INF_PLUGIN";
  
  private static String PLUGIN = "PLUGIN";
  
  private static String DOT_IDEA = "DOT_IDEA";
  
  private String encoding;
  
  private String fileExtension;
  
  private Set<String> libraries = CollectionLiterals.<String>newHashSet();
  
  private String pathIdeaPluginProject;
  
  @Inject
  @Extension
  private IdeaPluginExtension _ideaPluginExtension;
  
  @Inject
  @Extension
  private IdeaPluginClassNames _ideaPluginClassNames;
  
  public void generate(final Grammar grammar, final Xtend2ExecutionContext ctx) {
    String outlet_src_gen = Generator.SRC_GEN;
    String outlet_src = Generator.SRC;
    boolean _notEquals = (!Objects.equal(this.pathIdeaPluginProject, null));
    if (_notEquals) {
      Outlet _outlet = new Outlet("IDEA_SRC_GEN");
      final Procedure1<Outlet> _function = new Procedure1<Outlet>() {
        public void apply(final Outlet it) {
          it.setName("IDEA_SRC_GEN");
          it.setPath((IdeaPluginGenerator.this.pathIdeaPluginProject + "/src-gen"));
        }
      };
      final Outlet newOutlet = ObjectExtensions.<Outlet>operator_doubleArrow(_outlet, _function);
      XpandExecutionContext _xpandExecutionContext = ctx.getXpandExecutionContext();
      Output _output = _xpandExecutionContext.getOutput();
      _output.addOutlet(newOutlet);
      String _name = newOutlet.getName();
      outlet_src_gen = _name;
      Outlet _outlet_1 = new Outlet("IDEA_SRC");
      final Procedure1<Outlet> _function_1 = new Procedure1<Outlet>() {
        public void apply(final Outlet it) {
          it.setName("IDEA_SRC");
          it.setPath((IdeaPluginGenerator.this.pathIdeaPluginProject + "/src"));
        }
      };
      final Outlet newSrcOutlet = ObjectExtensions.<Outlet>operator_doubleArrow(_outlet_1, _function_1);
      XpandExecutionContext _xpandExecutionContext_1 = ctx.getXpandExecutionContext();
      Output _output_1 = _xpandExecutionContext_1.getOutput();
      _output_1.addOutlet(newSrcOutlet);
      String _name_1 = newSrcOutlet.getName();
      outlet_src = _name_1;
    }
    final BindFactory bindFactory = new BindFactory();
    String _name_2 = SyntaxHighlighter.class.getName();
    String _syntaxHighlighterName = this._ideaPluginClassNames.getSyntaxHighlighterName(grammar);
    bindFactory.addTypeToType(_name_2, _syntaxHighlighterName);
    String _name_3 = Lexer.class.getName();
    String _lexerName = this._ideaPluginClassNames.getLexerName(grammar);
    bindFactory.addTypeToType(_name_3, _lexerName);
    String _name_4 = TokenTypeProvider.class.getName();
    String _tokenTypeProviderName = this._ideaPluginClassNames.getTokenTypeProviderName(grammar);
    bindFactory.addTypeToType(_name_4, _tokenTypeProviderName);
    String _name_5 = ParserDefinition.class.getName();
    String _parserDefinitionName = this._ideaPluginClassNames.getParserDefinitionName(grammar);
    bindFactory.addTypeToType(_name_5, _parserDefinitionName);
    String _name_6 = IElementTypeProvider.class.getName();
    String _elementTypeProviderName = this._ideaPluginClassNames.getElementTypeProviderName(grammar);
    bindFactory.addTypeToTypeSingleton(_name_6, _elementTypeProviderName);
    final Set<Binding> bindings = bindFactory.getBindings();
    String _standaloneSetupIdea = this._ideaPluginClassNames.getStandaloneSetupIdea(grammar);
    String _javaPath = this._ideaPluginClassNames.toJavaPath(_standaloneSetupIdea);
    CharSequence _compileStandaloneSetup = this.compileStandaloneSetup(grammar);
    ctx.writeFile(outlet_src, _javaPath, _compileStandaloneSetup);
    String _ideaModuleName = this._ideaPluginClassNames.getIdeaModuleName(grammar);
    String _javaPath_1 = this._ideaPluginClassNames.toJavaPath(_ideaModuleName);
    CharSequence _compileIdeaModule = this.compileIdeaModule(grammar);
    ctx.writeFile(outlet_src, _javaPath_1, _compileIdeaModule);
    String _languageName = this._ideaPluginClassNames.getLanguageName(grammar);
    String _javaPath_2 = this._ideaPluginClassNames.toJavaPath(_languageName);
    CharSequence _compileLanguage = this.compileLanguage(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_2, _compileLanguage);
    String _fileTypeName = this._ideaPluginClassNames.getFileTypeName(grammar);
    String _javaPath_3 = this._ideaPluginClassNames.toJavaPath(_fileTypeName);
    CharSequence _compileFileType = this.compileFileType(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_3, _compileFileType);
    String _fileTypeFactoryName = this._ideaPluginClassNames.getFileTypeFactoryName(grammar);
    String _javaPath_4 = this._ideaPluginClassNames.toJavaPath(_fileTypeFactoryName);
    CharSequence _compileFileTypeFactory = this.compileFileTypeFactory(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_4, _compileFileTypeFactory);
    String _fileImplName = this._ideaPluginClassNames.getFileImplName(grammar);
    String _javaPath_5 = this._ideaPluginClassNames.toJavaPath(_fileImplName);
    CharSequence _compileFileImpl = this.compileFileImpl(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_5, _compileFileImpl);
    String _lexerName_1 = this._ideaPluginClassNames.getLexerName(grammar);
    String _javaPath_6 = this._ideaPluginClassNames.toJavaPath(_lexerName_1);
    CharSequence _compileLexer = this.compileLexer(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_6, _compileLexer);
    String _tokenTypeProviderName_1 = this._ideaPluginClassNames.getTokenTypeProviderName(grammar);
    String _javaPath_7 = this._ideaPluginClassNames.toJavaPath(_tokenTypeProviderName_1);
    CharSequence _compileTokenTypeProvider = this.compileTokenTypeProvider(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_7, _compileTokenTypeProvider);
    String _elementTypeProviderName_1 = this._ideaPluginClassNames.getElementTypeProviderName(grammar);
    String _javaPath_8 = this._ideaPluginClassNames.toJavaPath(_elementTypeProviderName_1);
    CharSequence _compileElementTypeProvider = this.compileElementTypeProvider(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_8, _compileElementTypeProvider);
    String _parserDefinitionName_1 = this._ideaPluginClassNames.getParserDefinitionName(grammar);
    String _javaPath_9 = this._ideaPluginClassNames.toJavaPath(_parserDefinitionName_1);
    CharSequence _compileParserDefinition = this.compileParserDefinition(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_9, _compileParserDefinition);
    String _syntaxHighlighterName_1 = this._ideaPluginClassNames.getSyntaxHighlighterName(grammar);
    String _javaPath_10 = this._ideaPluginClassNames.toJavaPath(_syntaxHighlighterName_1);
    CharSequence _compileSyntaxHighlighter = this.compileSyntaxHighlighter(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_10, _compileSyntaxHighlighter);
    String _syntaxHighlighterFactoryName = this._ideaPluginClassNames.getSyntaxHighlighterFactoryName(grammar);
    String _javaPath_11 = this._ideaPluginClassNames.toJavaPath(_syntaxHighlighterFactoryName);
    CharSequence _compileSyntaxHighlighterFactory = this.compileSyntaxHighlighterFactory(grammar);
    ctx.writeFile(outlet_src_gen, _javaPath_11, _compileSyntaxHighlighterFactory);
    String _abstractIdeaModuleName = this._ideaPluginClassNames.getAbstractIdeaModuleName(grammar);
    String _javaPath_12 = this._ideaPluginClassNames.toJavaPath(_abstractIdeaModuleName);
    CharSequence _compileGuiceModuleIdeaGenerated = this.compileGuiceModuleIdeaGenerated(grammar, bindings);
    ctx.writeFile(outlet_src_gen, _javaPath_12, _compileGuiceModuleIdeaGenerated);
    boolean _notEquals_1 = (!Objects.equal(this.pathIdeaPluginProject, null));
    if (_notEquals_1) {
      OutputImpl output = new OutputImpl();
      this.addOutlet(output, IdeaPluginGenerator.PLUGIN, this.pathIdeaPluginProject);
      this.addOutlet(output, IdeaPluginGenerator.META_INF_PLUGIN, (this.pathIdeaPluginProject + "/META-INF"));
      this.addOutlet(output, IdeaPluginGenerator.DOT_IDEA, (this.pathIdeaPluginProject + "/.idea"));
      CharSequence _compilePluginXml = this.compilePluginXml(grammar);
      this.writeFile(output, IdeaPluginGenerator.META_INF_PLUGIN, "plugin.xml", _compilePluginXml);
      String _iml = this.iml();
      CharSequence _compileIml = this.compileIml(grammar);
      this.writeFile(output, IdeaPluginGenerator.PLUGIN, _iml, _compileIml);
      CharSequence _compileModulesXml = this.compileModulesXml(grammar);
      this.writeFile(output, IdeaPluginGenerator.DOT_IDEA, "modules.xml", _compileModulesXml);
      CharSequence _compileMiscXml = this.compileMiscXml(grammar);
      this.writeFile(output, IdeaPluginGenerator.DOT_IDEA, "misc.xml", _compileMiscXml);
    }
  }
  
  public CharSequence compileGuiceModuleIdeaGenerated(final Grammar grammar, final Set<Binding> bindings) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _abstractIdeaModuleName = this._ideaPluginClassNames.getAbstractIdeaModuleName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_abstractIdeaModuleName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _abstractIdeaModuleName_1 = this._ideaPluginClassNames.getAbstractIdeaModuleName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_abstractIdeaModuleName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends org.eclipse.xtext.idea.DefaultIdeaModule {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    {
      for(final Binding it : bindings) {
        {
          boolean _and = false;
          BindValue _value = it.getValue();
          boolean _isProvider = _value.isProvider();
          boolean _not = (!_isProvider);
          if (!_not) {
            _and = false;
          } else {
            BindValue _value_1 = it.getValue();
            String[] _statements = _value_1.getStatements();
            boolean _isEmpty = ((List<String>)Conversions.doWrapArray(_statements)).isEmpty();
            _and = _isEmpty;
          }
          if (_and) {
            _builder.append("\t");
            _builder.append("// contributed by ");
            String _contributedBy = it.getContributedBy();
            _builder.append(_contributedBy, "\t");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            {
              BindKey _key = it.getKey();
              boolean _isSingleton = _key.isSingleton();
              if (_isSingleton) {
                _builder.append("@org.eclipse.xtext.service.SingletonBinding");
                {
                  BindKey _key_1 = it.getKey();
                  boolean _isEagerSingleton = _key_1.isEagerSingleton();
                  if (_isEagerSingleton) {
                    _builder.append("(eager=true)");
                  }
                }
              }
            }
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("public ");
            {
              BindValue _value_2 = it.getValue();
              String _expression = _value_2.getExpression();
              boolean _equals = Objects.equal(_expression, null);
              if (_equals) {
                _builder.append("Class<? extends ");
                BindKey _key_2 = it.getKey();
                String _type = _key_2.getType();
                _builder.append(_type, "\t");
                _builder.append(">");
              } else {
                BindKey _key_3 = it.getKey();
                String _type_1 = _key_3.getType();
                _builder.append(_type_1, "\t");
              }
            }
            _builder.append(" ");
            String _bindMethodName = this.bindMethodName(it);
            _builder.append(_bindMethodName, "\t");
            _builder.append("() {");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("\t");
            _builder.append("return ");
            {
              BindValue _value_3 = it.getValue();
              String _expression_1 = _value_3.getExpression();
              boolean _notEquals = (!Objects.equal(_expression_1, null));
              if (_notEquals) {
                BindValue _value_4 = it.getValue();
                String _expression_2 = _value_4.getExpression();
                _builder.append(_expression_2, "\t\t");
              } else {
                BindValue _value_5 = it.getValue();
                String _typeName = _value_5.getTypeName();
                _builder.append(_typeName, "\t\t");
                _builder.append(".class");
              }
            }
            _builder.append(";");
            _builder.newLineIfNotEmpty();
            _builder.append("\t");
            _builder.append("}");
            _builder.newLine();
          } else {
            BindValue _value_6 = it.getValue();
            String[] _statements_1 = _value_6.getStatements();
            boolean _isEmpty_1 = ((List<String>)Conversions.doWrapArray(_statements_1)).isEmpty();
            if (_isEmpty_1) {
              _builder.append("\t");
              _builder.append("// contributed by ");
              String _contributedBy_1 = it.getContributedBy();
              _builder.append(_contributedBy_1, "\t");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              {
                BindKey _key_4 = it.getKey();
                boolean _isSingleton_1 = _key_4.isSingleton();
                if (_isSingleton_1) {
                  _builder.append("@org.eclipse.xtext.service.SingletonBinding");
                  {
                    BindKey _key_5 = it.getKey();
                    boolean _isEagerSingleton_1 = _key_5.isEagerSingleton();
                    if (_isEagerSingleton_1) {
                      _builder.append("(eager=true)");
                    }
                  }
                }
              }
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("public ");
              {
                BindValue _value_7 = it.getValue();
                String _expression_3 = _value_7.getExpression();
                boolean _equals_1 = Objects.equal(_expression_3, null);
                if (_equals_1) {
                  _builder.append("Class<? extends com.google.inject.Provider<");
                  BindKey _key_6 = it.getKey();
                  String _type_2 = _key_6.getType();
                  _builder.append(_type_2, "\t");
                  _builder.append(">>");
                } else {
                  _builder.append("com.google.inject.Provider<");
                  BindKey _key_7 = it.getKey();
                  String _type_3 = _key_7.getType();
                  _builder.append(_type_3, "\t");
                  _builder.append(">");
                }
              }
              _builder.append(" ");
              String _bindMethodName_1 = this.bindMethodName(it);
              _builder.append(_bindMethodName_1, "\t");
              _builder.append("() {");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("\t");
              _builder.append("return ");
              {
                BindValue _value_8 = it.getValue();
                String _expression_4 = _value_8.getExpression();
                boolean _notEquals_1 = (!Objects.equal(_expression_4, null));
                if (_notEquals_1) {
                  BindValue _value_9 = it.getValue();
                  String _expression_5 = _value_9.getExpression();
                  _builder.append(_expression_5, "\t\t");
                } else {
                  BindValue _value_10 = it.getValue();
                  String _typeName_1 = _value_10.getTypeName();
                  _builder.append(_typeName_1, "\t\t");
                  _builder.append(".class");
                }
              }
              _builder.append(";");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("}");
              _builder.newLine();
            } else {
              _builder.append("\t");
              _builder.append("// contributed by ");
              String _contributedBy_2 = it.getContributedBy();
              _builder.append(_contributedBy_2, "\t");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("public void ");
              String _bindMethodName_2 = this.bindMethodName(it);
              _builder.append(_bindMethodName_2, "\t");
              _builder.append("(com.google.inject.Binder binder) {");
              _builder.newLineIfNotEmpty();
              {
                BindValue _value_11 = it.getValue();
                String[] _statements_2 = _value_11.getStatements();
                for(final String statement : _statements_2) {
                  _builder.append("\t\t\t\t");
                  _builder.append(statement, "\t\t\t\t");
                  {
                    boolean _endsWith = statement.endsWith(";");
                    boolean _not_1 = (!_endsWith);
                    if (_not_1) {
                      _builder.append(";");
                    }
                  }
                  _builder.newLineIfNotEmpty();
                }
              }
              _builder.append("\t");
              _builder.append("}");
              _builder.newLine();
            }
          }
        }
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public String bindMethodName(final Binding it) {
    String _xifexpression = null;
    boolean _and = false;
    BindValue _value = it.getValue();
    boolean _isProvider = _value.isProvider();
    boolean _not = (!_isProvider);
    if (!_not) {
      _and = false;
    } else {
      BindValue _value_1 = it.getValue();
      String[] _statements = _value_1.getStatements();
      boolean _isEmpty = ((List<String>)Conversions.doWrapArray(_statements)).isEmpty();
      _and = _isEmpty;
    }
    if (_and) {
      _xifexpression = "bind";
    } else {
      String _xifexpression_1 = null;
      BindValue _value_2 = it.getValue();
      String[] _statements_1 = _value_2.getStatements();
      boolean _isEmpty_1 = ((List<String>)Conversions.doWrapArray(_statements_1)).isEmpty();
      if (_isEmpty_1) {
        _xifexpression_1 = "provide";
      } else {
        _xifexpression_1 = "configure";
      }
      _xifexpression = _xifexpression_1;
    }
    final String prefix = _xifexpression;
    String _xifexpression_2 = null;
    boolean _and_1 = false;
    BindValue _value_3 = it.getValue();
    String _expression = _value_3.getExpression();
    boolean _notEquals = (!Objects.equal(_expression, null));
    if (!_notEquals) {
      _and_1 = false;
    } else {
      BindValue _value_4 = it.getValue();
      boolean _isProvider_1 = _value_4.isProvider();
      boolean _not_1 = (!_isProvider_1);
      _and_1 = _not_1;
    }
    if (_and_1) {
      _xifexpression_2 = "ToInstance";
    } else {
      _xifexpression_2 = "";
    }
    final String suffix = _xifexpression_2;
    BindKey _key = it.getKey();
    String _type = _key.getType();
    String _simpleMethodName = this.simpleMethodName(_type);
    String _plus = (prefix + _simpleMethodName);
    return (_plus + suffix);
  }
  
  private String simpleMethodName(final String qn) {
    String _replaceAll = qn.replaceAll("<", "\\.");
    String _replaceAll_1 = _replaceAll.replaceAll(">", "\\.");
    String[] _split = _replaceAll_1.split("\\.");
    final Function1<String, Boolean> _function = new Function1<String, Boolean>() {
      public Boolean apply(final String e) {
        return Boolean.valueOf(e.matches("[A-Z].*"));
      }
    };
    Iterable<String> _filter = IterableExtensions.<String>filter(((Iterable<String>)Conversions.doWrapArray(_split)), _function);
    return IterableExtensions.join(_filter, "$");
  }
  
  public String iml() {
    int _lastIndexOf = this.pathIdeaPluginProject.lastIndexOf("/");
    int _plus = (_lastIndexOf + 1);
    String _substring = this.pathIdeaPluginProject.substring(_plus);
    return (_substring + ".iml");
  }
  
  public void addOutlet(final Output output, final String outletName, final String path) {
    String _encoding = this.getEncoding();
    Outlet _outlet = new Outlet(false, _encoding, outletName, false, path);
    output.addOutlet(_outlet);
  }
  
  public void writeFile(final Output output, final String outletName, final String filename, final CharSequence contents) {
    output.openFile(filename, outletName);
    String _string = contents.toString();
    output.write(_string);
    output.closeFile();
  }
  
  public String getEncoding() {
    boolean _notEquals = (!Objects.equal(this.encoding, null));
    if (_notEquals) {
      return this.encoding;
    }
    return System.getProperty("file.encoding");
  }
  
  public boolean addLibrary(final String library) {
    return this.libraries.add(library);
  }
  
  public String setFileExtensions(final String fileExtensions) {
    String[] _split = fileExtensions.split("\\s*,\\s*");
    String _get = _split[0];
    return this.fileExtension = _get;
  }
  
  public String setEncoding(final String encoding) {
    return this.encoding = encoding;
  }
  
  public String setPathIdeaPluginProject(final String pathIdeaPluginProject) {
    return this.pathIdeaPluginProject = pathIdeaPluginProject;
  }
  
  public CharSequence compileModulesXml(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<project version=\"4\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<component name=\"ProjectModuleManager\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<modules>");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("<module fileurl=\"file://$PROJECT_DIR$/");
    String _iml = this.iml();
    _builder.append(_iml, "      ");
    _builder.append("\" filepath=\"$PROJECT_DIR$/");
    String _iml_1 = this.iml();
    _builder.append(_iml_1, "      ");
    _builder.append("\" />");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("</modules>");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("</component>");
    _builder.newLine();
    _builder.append("</project>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileMiscXml(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<project version=\"4\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<component name=\"ProjectRootManager\" version=\"2\" languageLevel=\"JDK_1_6\" assert-keyword=\"true\" jdk-15=\"true\" project-jdk-name=\"IDEA IC-123.72\" project-jdk-type=\"IDEA JDK\" />");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<output url=\"file://$PROJECT_DIR$/out\" />");
    _builder.newLine();
    _builder.append("</project>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compilePluginXml(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<idea-plugin version=\"2\">");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<id>");
    String _languageID = this._ideaPluginExtension.getLanguageID(grammar);
    _builder.append(_languageID, "\t");
    _builder.append("</id>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<name>");
    String _simpleName = this._ideaPluginExtension.getSimpleName(grammar);
    _builder.append(_simpleName, "\t");
    _builder.append(" Support</name>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("<description>");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("This plugin enables smart editing of ");
    String _simpleName_1 = this._ideaPluginExtension.getSimpleName(grammar);
    _builder.append(_simpleName_1, "      ");
    _builder.append(" files.");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</description>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<version>1.0.0</version>");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<vendor>My Company</vendor>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<idea-version since-build=\"123.72\"/>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("<extensions defaultExtensionNs=\"com.intellij\">");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("<lang.syntaxHighlighterFactory key=\"");
    String _languageID_1 = this._ideaPluginExtension.getLanguageID(grammar);
    _builder.append(_languageID_1, "\t\t");
    _builder.append("\" implementationClass=\"");
    String _syntaxHighlighterFactoryName = this._ideaPluginClassNames.getSyntaxHighlighterFactoryName(grammar);
    _builder.append(_syntaxHighlighterFactoryName, "\t\t");
    _builder.append("\"/>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<lang.parserDefinition language=\"");
    String _languageID_2 = this._ideaPluginExtension.getLanguageID(grammar);
    _builder.append(_languageID_2, "\t\t");
    _builder.append("\" implementationClass=\"");
    String _parserDefinitionName = this._ideaPluginClassNames.getParserDefinitionName(grammar);
    _builder.append(_parserDefinitionName, "\t\t");
    _builder.append("\"/>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("<fileTypeFactory implementation=\"");
    String _fileTypeFactoryName = this._ideaPluginClassNames.getFileTypeFactoryName(grammar);
    _builder.append(_fileTypeFactoryName, "\t\t");
    _builder.append("\"/>");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("</extensions>");
    _builder.newLine();
    _builder.newLine();
    _builder.append("</idea-plugin>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileIml(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    _builder.newLine();
    _builder.append("<module type=\"PLUGIN_MODULE\" version=\"4\">");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<component name=\"DevKit.ModuleBuildProperties\" url=\"file://$MODULE_DIR$/META-INF/plugin.xml\" />");
    _builder.newLine();
    _builder.append("  ");
    _builder.append("<component name=\"NewModuleRootManager\" inherit-compiler-output=\"true\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<exclude-output />");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<content url=\"file://$MODULE_DIR$\">");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("<sourceFolder url=\"file://$MODULE_DIR$/src\" isTestSource=\"false\" />");
    _builder.newLine();
    _builder.append("      ");
    _builder.append("<sourceFolder url=\"file://$MODULE_DIR$/test\" isTestSource=\"true\" />");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("</content>");
    _builder.newLine();
    _builder.append(" \t");
    _builder.append("<orderEntry type=\"jdk\" jdkName=\"IDEA IC-123.72\" jdkType=\"IDEA JDK\" />");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("<orderEntry type=\"sourceFolder\" forTests=\"false\" />");
    _builder.newLine();
    {
      for(final String library : this.libraries) {
        _builder.append("    ");
        _builder.append("<orderEntry type=\"module-library\">");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("<library>");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("<CLASSES>");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("      ");
        _builder.append("<root url=\"jar://");
        _builder.append(library, "          ");
        _builder.append("!/\" />");
        _builder.newLineIfNotEmpty();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("</CLASSES>");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("<JAVADOC />");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("    ");
        _builder.append("<SOURCES />");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("  ");
        _builder.append("</library>");
        _builder.newLine();
        _builder.append("    ");
        _builder.append("</orderEntry>");
        _builder.newLine();
      }
    }
    _builder.append("  ");
    _builder.append("</component>");
    _builder.newLine();
    _builder.append("</module>");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compilePsiElement(final Grammar grammar, final AbstractRule rule) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _psiPackageName = this._ideaPluginClassNames.getPsiPackageName(grammar);
    _builder.append(_psiPackageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    {
      boolean _hasMultipleAssigment = this._ideaPluginExtension.hasMultipleAssigment(rule);
      if (_hasMultipleAssigment) {
        _builder.newLine();
        _builder.append("import java.util.List;");
        _builder.newLine();
      }
    }
    _builder.newLine();
    _builder.append("import com.intellij.psi.");
    String _psiElementSuperClassName = this._ideaPluginExtension.getPsiElementSuperClassName(rule);
    _builder.append(_psiElementSuperClassName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public interface ");
    String _psiElementClassName = this._ideaPluginExtension.getPsiElementClassName(rule);
    _builder.append(_psiElementClassName, "");
    _builder.append(" extends ");
    String _psiElementSuperClassName_1 = this._ideaPluginExtension.getPsiElementSuperClassName(rule);
    _builder.append(_psiElementSuperClassName_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    {
      List<Assignment> _assignmentsWithoutName = this._ideaPluginExtension.getAssignmentsWithoutName(rule);
      for(final Assignment assignment : _assignmentsWithoutName) {
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        String _typeName = this._ideaPluginExtension.getTypeName(assignment);
        _builder.append(_typeName, "\t");
        _builder.append(" ");
        String _getter = this._ideaPluginExtension.getGetter(assignment);
        _builder.append(_getter, "\t");
        _builder.append("();");
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        _builder.newLine();
        _builder.append("\t");
        _builder.append("void ");
        String _setter = this._ideaPluginExtension.getSetter(assignment);
        _builder.append(_setter, "\t");
        _builder.append("(");
        String _typeName_1 = this._ideaPluginExtension.getTypeName(assignment);
        _builder.append(_typeName_1, "\t");
        _builder.append(" ");
        String _feature = assignment.getFeature();
        _builder.append(_feature, "\t");
        _builder.append(");");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileFileImpl(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _psiImplPackageName = this._ideaPluginClassNames.getPsiImplPackageName(grammar);
    _builder.append(_psiImplPackageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.psi.impl.BaseXtextFile;");
    _builder.newLine();
    _builder.append("import ");
    String _fileTypeName = this._ideaPluginClassNames.getFileTypeName(grammar);
    _builder.append(_fileTypeName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.append("import ");
    String _languageName = this._ideaPluginClassNames.getLanguageName(grammar);
    _builder.append(_languageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import com.intellij.openapi.fileTypes.FileType;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.FileViewProvider;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public final class ");
    String _fileImplName = this._ideaPluginClassNames.getFileImplName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_fileImplName);
    _builder.append(_simpleName, "");
    _builder.append(" extends BaseXtextFile {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public ");
    String _fileImplName_1 = this._ideaPluginClassNames.getFileImplName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_fileImplName_1);
    _builder.append(_simpleName_1, "\t");
    _builder.append("(FileViewProvider viewProvider) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(viewProvider, ");
    String _languageName_1 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_2 = this._ideaPluginClassNames.toSimpleName(_languageName_1);
    _builder.append(_simpleName_2, "\t\t");
    _builder.append(".INSTANCE);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public FileType getFileType() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return ");
    String _fileTypeName_1 = this._ideaPluginClassNames.getFileTypeName(grammar);
    String _simpleName_3 = this._ideaPluginClassNames.toSimpleName(_fileTypeName_1);
    _builder.append(_simpleName_3, "\t\t");
    _builder.append(".INSTANCE;");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileFileTypeFactory(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _fileTypeFactoryName = this._ideaPluginClassNames.getFileTypeFactoryName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_fileTypeFactoryName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import com.intellij.openapi.fileTypes.FileTypeConsumer;");
    _builder.newLine();
    _builder.append("import com.intellij.openapi.fileTypes.FileTypeFactory;");
    _builder.newLine();
    _builder.append("import org.jetbrains.annotations.NotNull;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _fileTypeFactoryName_1 = this._ideaPluginClassNames.getFileTypeFactoryName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_fileTypeFactoryName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends FileTypeFactory {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public void createFileTypes(@NotNull FileTypeConsumer consumer) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("consumer.consume(");
    String _fileTypeName = this._ideaPluginClassNames.getFileTypeName(grammar);
    _builder.append(_fileTypeName, "\t\t");
    _builder.append(".INSTANCE, ");
    String _fileTypeName_1 = this._ideaPluginClassNames.getFileTypeName(grammar);
    _builder.append(_fileTypeName_1, "\t\t");
    _builder.append(".DEFAULT_EXTENSION);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileFileType(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _fileTypeName = this._ideaPluginClassNames.getFileTypeName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_fileTypeName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import javax.swing.Icon;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import com.intellij.openapi.fileTypes.LanguageFileType;");
    _builder.newLine();
    _builder.append("import org.jetbrains.annotations.NonNls;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public final class ");
    String _fileTypeName_1 = this._ideaPluginClassNames.getFileTypeName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_fileTypeName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends LanguageFileType {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final ");
    String _fileTypeName_2 = this._ideaPluginClassNames.getFileTypeName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_fileTypeName_2);
    _builder.append(_simpleName_1, "\t");
    _builder.append(" INSTANCE = new ");
    String _fileTypeName_3 = this._ideaPluginClassNames.getFileTypeName(grammar);
    String _simpleName_2 = this._ideaPluginClassNames.toSimpleName(_fileTypeName_3);
    _builder.append(_simpleName_2, "\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@NonNls ");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final String DEFAULT_EXTENSION = \"");
    _builder.append(this.fileExtension, "\t");
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private ");
    String _fileTypeName_4 = this._ideaPluginClassNames.getFileTypeName(grammar);
    String _simpleName_3 = this._ideaPluginClassNames.toSimpleName(_fileTypeName_4);
    _builder.append(_simpleName_3, "\t");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(");
    String _languageName = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_4 = this._ideaPluginClassNames.toSimpleName(_languageName);
    _builder.append(_simpleName_4, "\t\t");
    _builder.append(".INSTANCE);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public String getDefaultExtension() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return DEFAULT_EXTENSION;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public String getDescription() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return \"");
    String _simpleName_5 = this._ideaPluginExtension.getSimpleName(grammar);
    _builder.append(_simpleName_5, "\t\t");
    _builder.append(" files\";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Icon getIcon() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return null;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public String getName() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return \"");
    String _simpleName_6 = this._ideaPluginExtension.getSimpleName(grammar);
    _builder.append(_simpleName_6, "\t\t");
    _builder.append("\";");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileLanguage(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _languageName = this._ideaPluginClassNames.getLanguageName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_languageName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.idea.lang.AbstractXtextLanguage;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import com.google.inject.Injector;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public final class ");
    String _languageName_1 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_languageName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends AbstractXtextLanguage {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final ");
    String _languageName_2 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_languageName_2);
    _builder.append(_simpleName_1, "\t");
    _builder.append(" INSTANCE = new ");
    String _languageName_3 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_2 = this._ideaPluginClassNames.toSimpleName(_languageName_3);
    _builder.append(_simpleName_2, "\t");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private Injector injector;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private ");
    String _languageName_4 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_3 = this._ideaPluginClassNames.toSimpleName(_languageName_4);
    _builder.append(_simpleName_3, "\t");
    _builder.append("() {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("super(\"");
    String _languageID = this._ideaPluginExtension.getLanguageID(grammar);
    _builder.append(_languageID, "\t\t");
    _builder.append("\");");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("this.injector = new ");
    String _standaloneSetupIdea = this._ideaPluginClassNames.getStandaloneSetupIdea(grammar);
    _builder.append(_standaloneSetupIdea, "\t\t");
    _builder.append("().createInjectorAndDoEMFRegistration();");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("protected Injector getInjector() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return injector;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileStandaloneSetup(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _standaloneSetupIdea = this._ideaPluginClassNames.getStandaloneSetupIdea(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_standaloneSetupIdea);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.util.Modules2;");
    _builder.newLine();
    _builder.append("import ");
    Naming _naming = this.getNaming();
    String _setupImpl = _naming.setupImpl(grammar);
    _builder.append(_setupImpl, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import com.google.inject.Guice;");
    _builder.newLine();
    _builder.append("import com.google.inject.Injector;");
    _builder.newLine();
    _builder.append("import com.google.inject.Module;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _standaloneSetupIdea_1 = this._ideaPluginClassNames.getStandaloneSetupIdea(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_standaloneSetupIdea_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends ");
    Naming _naming_1 = this.getNaming();
    Naming _naming_2 = this.getNaming();
    String _setupImpl_1 = _naming_2.setupImpl(grammar);
    String _simpleName_1 = _naming_1.toSimpleName(_setupImpl_1);
    _builder.append(_simpleName_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public Injector createInjector() {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("Module runtimeModule = new ");
    Naming _naming_3 = this.getNaming();
    String _guiceModuleRt = _naming_3.guiceModuleRt(grammar);
    _builder.append(_guiceModuleRt, "        ");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("Module ideaModule = new ");
    String _ideaModuleName = this._ideaPluginClassNames.getIdeaModuleName(grammar);
    _builder.append(_ideaModuleName, "        ");
    _builder.append("();");
    _builder.newLineIfNotEmpty();
    _builder.append("        ");
    _builder.append("Module mergedModule = Modules2.mixin(runtimeModule, ideaModule);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return Guice.createInjector(mergedModule);");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileIdeaModule(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _ideaModuleName = this._ideaPluginClassNames.getIdeaModuleName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_ideaModuleName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _ideaModuleName_1 = this._ideaPluginClassNames.getIdeaModuleName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_ideaModuleName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends ");
    String _abstractIdeaModuleName = this._ideaPluginClassNames.getAbstractIdeaModuleName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_abstractIdeaModuleName);
    _builder.append(_simpleName_1, "");
    _builder.append(" {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileElementTypeProvider(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _elementTypeProviderName = this._ideaPluginClassNames.getElementTypeProviderName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_elementTypeProviderName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.idea.lang.IElementTypeProvider;");
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.psi.PsiNamedEObject;");
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.psi.PsiNamedEObjectStub;");
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.psi.stubs.PsiNamedEObjectType;");
    _builder.newLine();
    _builder.append("import ");
    String _fileImplName = this._ideaPluginClassNames.getFileImplName(grammar);
    _builder.append(_fileImplName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import com.intellij.psi.stubs.IStubElementType;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.stubs.PsiFileStub;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.tree.IElementType;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.tree.IFileElementType;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.tree.IStubFileElementType;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _elementTypeProviderName_1 = this._ideaPluginClassNames.getElementTypeProviderName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_elementTypeProviderName_1);
    _builder.append(_simpleName, "");
    _builder.append(" implements IElementTypeProvider {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final IFileElementType FILE_TYPE = new IStubFileElementType<PsiFileStub<");
    String _fileImplName_1 = this._ideaPluginClassNames.getFileImplName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_fileImplName_1);
    _builder.append(_simpleName_1, "\t");
    _builder.append(">>(");
    String _languageName = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_2 = this._ideaPluginClassNames.toSimpleName(_languageName);
    _builder.append(_simpleName_2, "\t");
    _builder.append(".INSTANCE);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final IElementType NAME_TYPE = new IElementType(\"NAME\", ");
    String _languageName_1 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_3 = this._ideaPluginClassNames.toSimpleName(_languageName_1);
    _builder.append(_simpleName_3, "\t");
    _builder.append(".INSTANCE);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final IElementType EOBJECT_TYPE = new IElementType(\"EOBJECT_TYPE\", ");
    String _languageName_2 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_4 = this._ideaPluginClassNames.toSimpleName(_languageName_2);
    _builder.append(_simpleName_4, "\t");
    _builder.append(".INSTANCE);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final IStubElementType<PsiNamedEObjectStub, PsiNamedEObject> NAMED_EOBJECT_TYPE = new PsiNamedEObjectType(\"NAMED_EOBJECT\", ");
    String _languageName_3 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_5 = this._ideaPluginClassNames.toSimpleName(_languageName_3);
    _builder.append(_simpleName_5, "\t");
    _builder.append(".INSTANCE);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public static final IElementType CROSS_REFERENCE_TYPE = new IElementType(\"CROSS_REFERENCE\", ");
    String _languageName_4 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_6 = this._ideaPluginClassNames.toSimpleName(_languageName_4);
    _builder.append(_simpleName_6, "\t");
    _builder.append(".INSTANCE);");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public IFileElementType getFileType() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return FILE_TYPE;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public IElementType getObjectType() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return EOBJECT_TYPE;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public IElementType getCrossReferenceType() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return CROSS_REFERENCE_TYPE;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public IElementType getNameType() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return NAME_TYPE;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public IStubElementType<PsiNamedEObjectStub, PsiNamedEObject> getNamedObjectType() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return NAMED_EOBJECT_TYPE;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileTokenTypeProvider(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _tokenTypeProviderName = this._ideaPluginClassNames.getTokenTypeProviderName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_tokenTypeProviderName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import static ");
    String _internalParserName = this._ideaPluginClassNames.getInternalParserName(grammar);
    _builder.append(_internalParserName, "");
    _builder.append(".*;");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.generator.idea.TokenTypeProvider;");
    _builder.newLine();
    _builder.append("import ");
    String _languageName = this._ideaPluginClassNames.getLanguageName(grammar);
    _builder.append(_languageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import com.google.inject.Singleton;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.tree.IElementType;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.tree.TokenSet;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("@Singleton public class ");
    String _tokenTypeProviderName_1 = this._ideaPluginClassNames.getTokenTypeProviderName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_tokenTypeProviderName_1);
    _builder.append(_simpleName, "");
    _builder.append(" implements TokenTypeProvider {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private static final IElementType[] tokenTypes = new IElementType[tokenNames.length];");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("static {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("for (int i = 0; i < tokenNames.length; i++) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("tokenTypes[i] = new IndexedElementType(tokenNames[i], i, ");
    String _languageName_1 = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_languageName_1);
    _builder.append(_simpleName_1, "\t\t\t");
    _builder.append(".INSTANCE);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private static final TokenSet WHITESPACE_TOKENS = TokenSet.create(tokenTypes[RULE_WS]);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private static final TokenSet COMMENT_TOKENS = TokenSet.create(tokenTypes[RULE_SL_COMMENT], tokenTypes[RULE_ML_COMMENT]);");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("private static final TokenSet STRING_TOKENS = TokenSet.create(tokenTypes[RULE_STRING]);");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public int getAntlrType(IElementType iElementType) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return ((IndexedElementType)iElementType).getLocalIndex();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("    ");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public IElementType getIElementType(int antlrType) {");
    _builder.newLine();
    _builder.append("    \t");
    _builder.append("return tokenTypes[antlrType];");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public TokenSet getWhitespaceTokens() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return WHITESPACE_TOKENS;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public TokenSet getCommentTokens() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return COMMENT_TOKENS;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public TokenSet getStringLiteralTokens() {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return STRING_TOKENS;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileLexer(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _lexerName = this._ideaPluginClassNames.getLexerName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_lexerName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.antlr.runtime.ANTLRStringStream;");
    _builder.newLine();
    _builder.append("import org.antlr.runtime.Lexer;");
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.idea.lang.parser.AbstractAntlrDelegatingIdeaLexer;");
    _builder.newLine();
    _builder.append("import ");
    String _antlrLexerName = this._ideaPluginClassNames.getAntlrLexerName(grammar);
    _builder.append(_antlrLexerName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("public class ");
    String _lexerName_1 = this._ideaPluginClassNames.getLexerName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_lexerName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends AbstractAntlrDelegatingIdeaLexer {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Override");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public Lexer createAntlrLexer(String text) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return new ");
    String _antlrLexerName_1 = this._ideaPluginClassNames.getAntlrLexerName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_antlrLexerName_1);
    _builder.append(_simpleName_1, "\t\t");
    _builder.append("(new ANTLRStringStream(text));");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileSyntaxHighlighterFactory(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _syntaxHighlighterFactoryName = this._ideaPluginClassNames.getSyntaxHighlighterFactoryName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_syntaxHighlighterFactoryName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.jetbrains.annotations.NotNull;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;");
    _builder.newLine();
    _builder.append("import com.intellij.openapi.fileTypes.SyntaxHighlighter;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _syntaxHighlighterFactoryName_1 = this._ideaPluginClassNames.getSyntaxHighlighterFactoryName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_syntaxHighlighterFactoryName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends SingleLazyInstanceSyntaxHighlighterFactory {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@NotNull");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("protected SyntaxHighlighter createHighlighter() {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return ");
    String _languageName = this._ideaPluginClassNames.getLanguageName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_languageName);
    _builder.append(_simpleName_1, "        ");
    _builder.append(".INSTANCE.getInstance(SyntaxHighlighter.class);");
    _builder.newLineIfNotEmpty();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileSyntaxHighlighter(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _syntaxHighlighterName = this._ideaPluginClassNames.getSyntaxHighlighterName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_syntaxHighlighterName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.generator.idea.TokenTypeProvider;");
    _builder.newLine();
    _builder.append("import org.jetbrains.annotations.NotNull;");
    _builder.newLine();
    _builder.append("import ");
    String _antlrLexerName = this._ideaPluginClassNames.getAntlrLexerName(grammar);
    _builder.append(_antlrLexerName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import com.google.inject.Inject;");
    _builder.newLine();
    _builder.append("import com.google.inject.Provider;");
    _builder.newLine();
    _builder.append("import com.intellij.lexer.Lexer;");
    _builder.newLine();
    _builder.append("import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;");
    _builder.newLine();
    _builder.append("import com.intellij.openapi.editor.colors.TextAttributesKey;");
    _builder.newLine();
    _builder.append("import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.tree.IElementType;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _syntaxHighlighterName_1 = this._ideaPluginClassNames.getSyntaxHighlighterName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_syntaxHighlighterName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends SyntaxHighlighterBase {");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Inject TokenTypeProvider tokenTypeProvider;");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("@Inject Provider<Lexer> lexerProvider; ");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@NotNull");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public Lexer getHighlightingLexer() {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return lexerProvider.get();");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("    ");
    _builder.append("@NotNull");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("if (tokenTypeProvider.getStringLiteralTokens().contains(tokenType)) {");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("return pack(DefaultLanguageHighlighterColors.STRING);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (tokenTypeProvider.getIElementType(");
    String _antlrLexerName_1 = this._ideaPluginClassNames.getAntlrLexerName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_antlrLexerName_1);
    _builder.append(_simpleName_1, "\t\t");
    _builder.append(".RULE_SL_COMMENT) == tokenType) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("return pack(DefaultLanguageHighlighterColors.LINE_COMMENT);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (tokenTypeProvider.getIElementType(");
    String _antlrLexerName_2 = this._ideaPluginClassNames.getAntlrLexerName(grammar);
    String _simpleName_2 = this._ideaPluginClassNames.toSimpleName(_antlrLexerName_2);
    _builder.append(_simpleName_2, "\t\t");
    _builder.append(".RULE_ML_COMMENT) == tokenType) {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t\t\t");
    _builder.append("return pack(DefaultLanguageHighlighterColors.BLOCK_COMMENT);");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("String myDebugName = tokenType.toString();");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("if (myDebugName.matches(\"^\'.*\\\\w.*\'$\")) {");
    _builder.newLine();
    _builder.append("\t\t\t");
    _builder.append("return pack(DefaultLanguageHighlighterColors.KEYWORD);");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("}");
    _builder.newLine();
    _builder.append("        ");
    _builder.append("return new TextAttributesKey[0];");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
  
  public CharSequence compileParserDefinition(final Grammar grammar) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("package ");
    String _parserDefinitionName = this._ideaPluginClassNames.getParserDefinitionName(grammar);
    String _packageName = this._ideaPluginClassNames.toPackageName(_parserDefinitionName);
    _builder.append(_packageName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import org.eclipse.xtext.idea.lang.parser.AbstractXtextParserDefinition;");
    _builder.newLine();
    _builder.append("import ");
    String _fileImplName = this._ideaPluginClassNames.getFileImplName(grammar);
    _builder.append(_fileImplName, "");
    _builder.append(";");
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    _builder.append("import com.intellij.psi.FileViewProvider;");
    _builder.newLine();
    _builder.append("import com.intellij.psi.PsiFile;");
    _builder.newLine();
    _builder.newLine();
    _builder.append("public class ");
    String _parserDefinitionName_1 = this._ideaPluginClassNames.getParserDefinitionName(grammar);
    String _simpleName = this._ideaPluginClassNames.toSimpleName(_parserDefinitionName_1);
    _builder.append(_simpleName, "");
    _builder.append(" extends AbstractXtextParserDefinition {");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("public PsiFile createFile(FileViewProvider viewProvider) {");
    _builder.newLine();
    _builder.append("\t\t");
    _builder.append("return new ");
    String _fileImplName_1 = this._ideaPluginClassNames.getFileImplName(grammar);
    String _simpleName_1 = this._ideaPluginClassNames.toSimpleName(_fileImplName_1);
    _builder.append(_simpleName_1, "\t\t");
    _builder.append("(viewProvider);");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    return _builder;
  }
}
