package org.eclipse.xtext.generator.idea;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractElement;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.Assignment;
import org.eclipse.xtext.CrossReference;
import org.eclipse.xtext.EnumLiteralDeclaration;
import org.eclipse.xtext.Grammar;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.TerminalRule;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Functions.Function1;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.IteratorExtensions;
import org.eclipse.xtext.xbase.lib.StringExtensions;

@SuppressWarnings("all")
public class IdeaPluginExtension {
  private static String TOKEN_TYPES = "TokenTypes";
  
  private static String LANGUAGE = "Language";
  
  private static String FILE_TYPE = "FileType";
  
  private static String FILE_TYPE_FACTORY = "FileTypeFactory";
  
  private static String FILE_IMPL = "FileImpl";
  
  private static String PARSER = "Parser";
  
  private static String TOKEN_TYPE_PROVIDER = "TokenTypeProvider";
  
  private static String LEXER = "Lexer";
  
  private static String PARSER_DEFINITION = "ParserDefinition";
  
  private static String SETUP = "IdeaSetup";
  
  private static String SYNTAX_HIGHLIGHTER = "SyntaxHighlighter";
  
  private static String SYNTAX_HIGHLIGHTER_FACTORY = "SyntaxHighlighterFactory";
  
  private static String LANG_PACKAGE = ".lang";
  
  private static String PARSER_PACKAGE = (IdeaPluginExtension.LANG_PACKAGE + ".parser");
  
  private static String PARSER_ANTLR_INTERNAL_PACKAGE = ".parser.antlr.internal";
  
  private static String PSI_PACKAGE = ".lang.psi";
  
  private static String PSI_IMPL_PACKAGE = ".lang.psi.impl";
  
  private static String STUB_ELEMENT_TYPE = "StubElementType";
  
  public List<AbstractRule> getAllRules(final Grammar grammar) {
    ArrayList<AbstractRule> _newArrayList = CollectionLiterals.<AbstractRule>newArrayList();
    return this.getAllRules(grammar, _newArrayList);
  }
  
  public List<AbstractRule> getAllRules(final Grammar grammar, final List<AbstractRule> rules) {
    EList<AbstractRule> _rules = grammar.getRules();
    final Function1<AbstractRule, Boolean> _function = new Function1<AbstractRule, Boolean>() {
      public Boolean apply(final AbstractRule e) {
        boolean _and = false;
        if (!(!(e instanceof TerminalRule))) {
          _and = false;
        } else {
          final Function1<AbstractRule, Boolean> _function = new Function1<AbstractRule, Boolean>() {
            public Boolean apply(final AbstractRule r) {
              String _name = r.getName();
              String _name_1 = e.getName();
              return Boolean.valueOf(_name.equals(_name_1));
            }
          };
          Iterable<AbstractRule> _filter = IterableExtensions.<AbstractRule>filter(rules, _function);
          boolean _isEmpty = IterableExtensions.isEmpty(_filter);
          _and = _isEmpty;
        }
        return Boolean.valueOf(_and);
      }
    };
    Iterable<AbstractRule> _filter = IterableExtensions.<AbstractRule>filter(_rules, _function);
    Iterables.<AbstractRule>addAll(rules, _filter);
    EList<Grammar> _usedGrammars = grammar.getUsedGrammars();
    for (final Grammar usedGrammar : _usedGrammars) {
      this.getAllRules(usedGrammar, rules);
    }
    return rules;
  }
  
  public String getSimpleName(final Grammar grammar) {
    return GrammarUtil.getName(grammar);
  }
  
  public String getPackageName(final Grammar grammar) {
    return GrammarUtil.getNamespace(grammar);
  }
  
  public String getPath(final String packageName) {
    return packageName.replaceAll("\\.", "/");
  }
  
  public String getTokenTypesPath(final Grammar grammar) {
    String _parsingPackageName = this.getParsingPackageName(grammar);
    String _tokenTypesClassName = this.getTokenTypesClassName(grammar);
    return this.getFilePath(_parsingPackageName, _tokenTypesClassName);
  }
  
  public String getLexerPath(final Grammar grammar) {
    String _parsingPackageName = this.getParsingPackageName(grammar);
    String _lexerClassName = this.getLexerClassName(grammar);
    return this.getFilePath(_parsingPackageName, _lexerClassName);
  }
  
  public String getParserPath(final Grammar grammar) {
    String _parsingPackageName = this.getParsingPackageName(grammar);
    String _parserClassName = this.getParserClassName(grammar);
    return this.getFilePath(_parsingPackageName, _parserClassName);
  }
  
  public String tokenTypeProviderPath(final Grammar grammar) {
    String _parsingPackageName = this.getParsingPackageName(grammar);
    String _tokenTypeProviderClassName = this.getTokenTypeProviderClassName(grammar);
    return this.getFilePath(_parsingPackageName, _tokenTypeProviderClassName);
  }
  
  public String getParserDefinitionPath(final Grammar grammar) {
    String _parsingPackageName = this.getParsingPackageName(grammar);
    String _parserDefinitionClassName = this.getParserDefinitionClassName(grammar);
    return this.getFilePath(_parsingPackageName, _parserDefinitionClassName);
  }
  
  public String getSyntaxHighlighterPath(final Grammar grammar) {
    String _langPackageName = this.getLangPackageName(grammar);
    String _syntaxHighlighterClassName = this.getSyntaxHighlighterClassName(grammar);
    return this.getFilePath(_langPackageName, _syntaxHighlighterClassName);
  }
  
  public String getSyntaxHighlighterFactoryPath(final Grammar grammar) {
    String _langPackageName = this.getLangPackageName(grammar);
    String _syntaxHighlighterFactoryClassName = this.getSyntaxHighlighterFactoryClassName(grammar);
    return this.getFilePath(_langPackageName, _syntaxHighlighterFactoryClassName);
  }
  
  public String getLanguagePath(final Grammar grammar) {
    String _langPackageName = this.getLangPackageName(grammar);
    String _languageClassName = this.getLanguageClassName(grammar);
    return this.getFilePath(_langPackageName, _languageClassName);
  }
  
  public String getFileTypePath(final Grammar grammar) {
    String _langPackageName = this.getLangPackageName(grammar);
    String _fileTypeClassName = this.getFileTypeClassName(grammar);
    return this.getFilePath(_langPackageName, _fileTypeClassName);
  }
  
  public String getFileTypeFactoryPath(final Grammar grammar) {
    String _langPackageName = this.getLangPackageName(grammar);
    String _fileTypeFactoryClassName = this.getFileTypeFactoryClassName(grammar);
    return this.getFilePath(_langPackageName, _fileTypeFactoryClassName);
  }
  
  public String fileImplPath(final Grammar grammar) {
    String _psiImplPackageName = this.getPsiImplPackageName(grammar);
    String _fileImplClassName = this.getFileImplClassName(grammar);
    return this.getFilePath(_psiImplPackageName, _fileImplClassName);
  }
  
  public String getPsiElementPath(final Grammar grammar, final AbstractRule abstractRule) {
    String _psiPackageName = this.getPsiPackageName(grammar);
    String _psiElementClassName = this.getPsiElementClassName(abstractRule);
    return this.getFilePath(_psiPackageName, _psiElementClassName);
  }
  
  public String getPsiElementImplPath(final Grammar grammar, final AbstractRule abstractRule) {
    String _psiImplPackageName = this.getPsiImplPackageName(grammar);
    String _psiElementImplClassName = this.getPsiElementImplClassName(abstractRule);
    return this.getFilePath(_psiImplPackageName, _psiElementImplClassName);
  }
  
  public String getFilePath(final String packageName, final String className) {
    String _path = this.getPath((packageName + "."));
    String _plus = (_path + className);
    return (_plus + ".java");
  }
  
  public String guiceModuleIdea(final Grammar grammar) {
    String _basePackageName = this.getBasePackageName(grammar);
    String _plus = (_basePackageName + ".");
    String _simpleName = this.getSimpleName(grammar);
    String _plus_1 = (_plus + _simpleName);
    return (_plus_1 + "IdeaModule");
  }
  
  public String getIdeaSetupName(final Grammar grammar) {
    String _basePackageName = this.getBasePackageName(grammar);
    String _plus = (_basePackageName + ".");
    String _simpleName = this.getSimpleName(grammar);
    String _plus_1 = (_plus + _simpleName);
    return (_plus_1 + "StandaloneSetupIdea");
  }
  
  public String getInstanceName(final AbstractRule abstractRule) {
    String _name = abstractRule.getName();
    return _name.toUpperCase();
  }
  
  public String ruleName(final TerminalRule terminalRule) {
    String _instanceName = this.getInstanceName(terminalRule);
    return ("RULE_" + _instanceName);
  }
  
  public String getRuleInstanceName(final Assignment assignment) {
    AbstractElement _terminal = assignment.getTerminal();
    AbstractRule _rule = ((RuleCall) _terminal).getRule();
    return this.getInstanceName(_rule);
  }
  
  public String getInstanceName(final Assignment assignment) {
    AbstractRule _containingRule = GrammarUtil.containingRule(assignment);
    return this.getInstanceName(_containingRule, assignment);
  }
  
  public String getInstanceName(final EnumLiteralDeclaration enumLiteralDeclaration) {
    AbstractRule _containingRule = GrammarUtil.containingRule(enumLiteralDeclaration);
    return this.getInstanceName(_containingRule, enumLiteralDeclaration);
  }
  
  public String getInstanceName(final RuleCall ruleCall) {
    Assignment _containingAssignment = GrammarUtil.containingAssignment(ruleCall);
    return this.getInstanceName(_containingAssignment);
  }
  
  public String getInstanceName(final AbstractRule rule, final Assignment assignment) {
    String _instanceName = this.getInstanceName(rule);
    String _plus = (_instanceName + "_");
    String _feature = assignment.getFeature();
    String _upperCase = _feature.toUpperCase();
    return (_plus + _upperCase);
  }
  
  public String getInstanceName(final AbstractRule rule, final EnumLiteralDeclaration enumLiteralDeclaration) {
    String _instanceName = this.getInstanceName(rule);
    String _plus = (_instanceName + "_");
    EEnumLiteral _enumLiteral = enumLiteralDeclaration.getEnumLiteral();
    String _literal = _enumLiteral.getLiteral();
    String _upperCase = _literal.toUpperCase();
    return (_plus + _upperCase);
  }
  
  public String getPsiElementImplClassName(final Grammar grammar, final AbstractRule abstractRule) {
    String _psiImplPackageName = this.getPsiImplPackageName(grammar);
    String _plus = (_psiImplPackageName + ".");
    String _psiElementImplClassName = this.getPsiElementImplClassName(abstractRule);
    return (_plus + _psiElementImplClassName);
  }
  
  public String getPsiElementImplClassName(final AbstractRule abstractRule) {
    String _name = abstractRule.getName();
    return (_name + "Impl");
  }
  
  public String getPsiElementClassName(final AbstractRule abstractRule) {
    return abstractRule.getName();
  }
  
  public String getPsiElementSuperClassName(final AbstractRule abstractRule) {
    boolean _hasName = this.hasName(abstractRule);
    if (_hasName) {
      return "PsiNamedElement";
    }
    return "PsiElement";
  }
  
  public boolean hasName(final AbstractRule rule) {
    List<Assignment> _assignments = this.getAssignments(rule);
    final Function1<Assignment, Boolean> _function = new Function1<Assignment, Boolean>() {
      public Boolean apply(final Assignment a) {
        String _feature = a.getFeature();
        return Boolean.valueOf(_feature.equals("name"));
      }
    };
    Iterable<Assignment> _filter = IterableExtensions.<Assignment>filter(_assignments, _function);
    boolean _isEmpty = IterableExtensions.isEmpty(_filter);
    return (!_isEmpty);
  }
  
  public List<Assignment> getAssignmentsWithoutName(final AbstractRule rule) {
    List<Assignment> _assignments = this.getAssignments(rule);
    final Function1<Assignment, Boolean> _function = new Function1<Assignment, Boolean>() {
      public Boolean apply(final Assignment a) {
        String _feature = a.getFeature();
        boolean _equals = _feature.equals("name");
        return Boolean.valueOf((!_equals));
      }
    };
    Iterable<Assignment> _filter = IterableExtensions.<Assignment>filter(_assignments, _function);
    return IterableExtensions.<Assignment>toList(_filter);
  }
  
  public List<Assignment> getAssignments(final AbstractRule rule) {
    TreeIterator<EObject> _eAllContents = rule.eAllContents();
    Iterator<Assignment> _filter = Iterators.<Assignment>filter(_eAllContents, Assignment.class);
    final Function1<Assignment, Boolean> _function = new Function1<Assignment, Boolean>() {
      public Boolean apply(final Assignment a) {
        AbstractRule _containingRule = GrammarUtil.containingRule(a);
        return Boolean.valueOf(_containingRule.equals(rule));
      }
    };
    Iterator<Assignment> _filter_1 = IteratorExtensions.<Assignment>filter(_filter, _function);
    return IteratorExtensions.<Assignment>toList(_filter_1);
  }
  
  public List<EnumLiteralDeclaration> getEnumLiteralDeclarations(final AbstractRule rule) {
    TreeIterator<EObject> _eAllContents = rule.eAllContents();
    Iterator<EnumLiteralDeclaration> _filter = Iterators.<EnumLiteralDeclaration>filter(_eAllContents, EnumLiteralDeclaration.class);
    final Function1<EnumLiteralDeclaration, Boolean> _function = new Function1<EnumLiteralDeclaration, Boolean>() {
      public Boolean apply(final EnumLiteralDeclaration a) {
        AbstractRule _containingRule = GrammarUtil.containingRule(a);
        return Boolean.valueOf(_containingRule.equals(rule));
      }
    };
    Iterator<EnumLiteralDeclaration> _filter_1 = IteratorExtensions.<EnumLiteralDeclaration>filter(_filter, _function);
    return IteratorExtensions.<EnumLiteralDeclaration>toList(_filter_1);
  }
  
  public String getLocalName(final AbstractRule abstractRule) {
    String _name = abstractRule.getName();
    return StringExtensions.toFirstLower(_name);
  }
  
  public String getRuleLocalName(final AbstractElement element) {
    AbstractRule _containingRule = GrammarUtil.containingRule(element);
    return this.getLocalName(_containingRule);
  }
  
  public String getRuleInstanceName(final AbstractElement element) {
    AbstractRule _containingRule = GrammarUtil.containingRule(element);
    return this.getInstanceName(_containingRule);
  }
  
  public String getLocalName(final Assignment assignment) {
    AbstractRule _containingRule = GrammarUtil.containingRule(assignment);
    String _localName = this.getLocalName(_containingRule);
    String _feature = assignment.getFeature();
    String _firstUpper = StringExtensions.toFirstUpper(_feature);
    return (_localName + _firstUpper);
  }
  
  public String getLocalName(final RuleCall ruleCall) {
    Assignment _containingAssignment = GrammarUtil.containingAssignment(ruleCall);
    return this.getLocalName(_containingAssignment);
  }
  
  public boolean hasMultipleAssigment(final AbstractRule rule) {
    Iterable<Assignment> _multipleAssignments = this.getMultipleAssignments(rule);
    boolean _isEmpty = IterableExtensions.isEmpty(_multipleAssignments);
    return (!_isEmpty);
  }
  
  public Iterable<Assignment> getMultipleAssignments(final AbstractRule rule) {
    List<Assignment> _assignments = this.getAssignments(rule);
    final Function1<Assignment, Boolean> _function = new Function1<Assignment, Boolean>() {
      public Boolean apply(final Assignment a) {
        return Boolean.valueOf(IdeaPluginExtension.this.isMultiple(a));
      }
    };
    return IterableExtensions.<Assignment>filter(_assignments, _function);
  }
  
  public String getTypeName(final Assignment assignment) {
    boolean _isMultiple = this.isMultiple(assignment);
    if (_isMultiple) {
      String _internalTypeName = this.getInternalTypeName(assignment);
      String _plus = ("List<" + _internalTypeName);
      return (_plus + ">");
    }
    return this.getInternalTypeName(assignment);
  }
  
  public boolean isMultiple(final Assignment assignment) {
    String _operator = assignment.getOperator();
    return "+=".equals(_operator);
  }
  
  public boolean isBoolean(final Assignment assignment) {
    String _operator = assignment.getOperator();
    return "?=".equals(_operator);
  }
  
  public boolean isOneOrNone(final AbstractElement element) {
    String _cardinality = element.getCardinality();
    return "?".equals(_cardinality);
  }
  
  public boolean isExactlyOne(final AbstractElement element) {
    String _cardinality = element.getCardinality();
    return Objects.equal(_cardinality, null);
  }
  
  public boolean isAny(final AbstractElement element) {
    String _cardinality = element.getCardinality();
    return "*".equals(_cardinality);
  }
  
  public boolean isOneOrMore(final AbstractElement element) {
    String _cardinality = element.getCardinality();
    return "+".equals(_cardinality);
  }
  
  public String getVariableName(final AbstractElement abstartElement) {
    AbstractRule _containingRule = GrammarUtil.containingRule(abstartElement);
    TreeIterator<EObject> _eAllContents = _containingRule.eAllContents();
    Iterator<AbstractElement> _filter = Iterators.<AbstractElement>filter(_eAllContents, AbstractElement.class);
    List<AbstractElement> _list = IteratorExtensions.<AbstractElement>toList(_filter);
    int _indexOf = _list.indexOf(abstartElement);
    return ("variable_" + Integer.valueOf(_indexOf));
  }
  
  protected String _getInternalTypeName(final Assignment assignment) {
    boolean _isBoolean = this.isBoolean(assignment);
    if (_isBoolean) {
      return "boolean";
    }
    AbstractElement _terminal = assignment.getTerminal();
    return this.getInternalTypeName(_terminal);
  }
  
  protected String _getInternalTypeName(final CrossReference crossReference) {
    AbstractElement _terminal = crossReference.getTerminal();
    return this.getInternalTypeName(_terminal);
  }
  
  protected String _getInternalTypeName(final RuleCall ruleCall) {
    AbstractRule _rule = ruleCall.getRule();
    if ((_rule instanceof TerminalRule)) {
      return "String";
    }
    AbstractRule _rule_1 = ruleCall.getRule();
    return _rule_1.getName();
  }
  
  protected String _getInternalTypeName(final AbstractElement abstractElement) {
    return "";
  }
  
  public String getGetter(final Assignment assignment) {
    boolean _isBoolean = this.isBoolean(assignment);
    if (_isBoolean) {
      String _feature = assignment.getFeature();
      String _firstUpper = StringExtensions.toFirstUpper(_feature);
      return ("is" + _firstUpper);
    }
    String _feature_1 = assignment.getFeature();
    String _firstUpper_1 = StringExtensions.toFirstUpper(_feature_1);
    return ("get" + _firstUpper_1);
  }
  
  public String getSetter(final Assignment assignment) {
    String _feature = assignment.getFeature();
    String _firstUpper = StringExtensions.toFirstUpper(_feature);
    return ("set" + _firstUpper);
  }
  
  public String getPsiElementImplClassName(final Assignment assignment) {
    AbstractElement _terminal = assignment.getTerminal();
    AbstractRule _rule = ((RuleCall) _terminal).getRule();
    return this.getPsiElementImplClassName(_rule);
  }
  
  public String getPsiElementClassName(final Assignment assignment) {
    AbstractElement _terminal = assignment.getTerminal();
    AbstractRule _rule = ((RuleCall) _terminal).getRule();
    return this.getPsiElementClassName(_rule);
  }
  
  public String getTokenTypesClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.TOKEN_TYPES);
  }
  
  public String getLanguageClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.LANGUAGE);
  }
  
  public String getFileTypeClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.FILE_TYPE);
  }
  
  public String getFileTypeFactoryClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.FILE_TYPE_FACTORY);
  }
  
  public String getFileImplClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.FILE_IMPL);
  }
  
  public String getLanguageID(final Grammar grammar) {
    return grammar.getName();
  }
  
  public String getParserClassName(final Grammar grammar) {
    String _className = this.getClassName(grammar, IdeaPluginExtension.PARSER);
    return ("Internal" + _className);
  }
  
  public String getTokenTypeProviderClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.TOKEN_TYPE_PROVIDER);
  }
  
  public String getParserDefinitionClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.PARSER_DEFINITION);
  }
  
  public String getSetupClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.SETUP);
  }
  
  public String getSyntaxHighlighterClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.SYNTAX_HIGHLIGHTER);
  }
  
  public String getSyntaxHighlighterFactoryClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.SYNTAX_HIGHLIGHTER_FACTORY);
  }
  
  public String getLexerClassName(final Grammar grammar) {
    return this.getClassName(grammar, IdeaPluginExtension.LEXER);
  }
  
  public String getAntlrLexerClassName(final Grammar grammar) {
    String _className = this.getClassName(grammar, IdeaPluginExtension.LEXER);
    return ("Internal" + _className);
  }
  
  public String getClassName(final Grammar grammar, final String typeName) {
    String _simpleName = this.getSimpleName(grammar);
    return (_simpleName + typeName);
  }
  
  public String getBasePackageName(final Grammar grammar) {
    String _packageName = this.getPackageName(grammar);
    return (_packageName + ".idea");
  }
  
  public String getLangPackageName(final Grammar grammar) {
    String _basePackageName = this.getBasePackageName(grammar);
    return (_basePackageName + IdeaPluginExtension.LANG_PACKAGE);
  }
  
  public String getParsingPackageName(final Grammar grammar) {
    String _basePackageName = this.getBasePackageName(grammar);
    return (_basePackageName + IdeaPluginExtension.PARSER_PACKAGE);
  }
  
  public String getInternalParsingPackageName(final Grammar grammar) {
    String _packageName = this.getPackageName(grammar);
    return (_packageName + IdeaPluginExtension.PARSER_ANTLR_INTERNAL_PACKAGE);
  }
  
  public String getPsiImplPackageName(final Grammar grammar) {
    String _basePackageName = this.getBasePackageName(grammar);
    return (_basePackageName + IdeaPluginExtension.PSI_IMPL_PACKAGE);
  }
  
  public String getPsiPackageName(final Grammar grammar) {
    String _basePackageName = this.getBasePackageName(grammar);
    return (_basePackageName + IdeaPluginExtension.PSI_PACKAGE);
  }
  
  public String getStubElementTypeClassName(final AbstractRule abstractRule) {
    String _name = abstractRule.getName();
    return (_name + IdeaPluginExtension.STUB_ELEMENT_TYPE);
  }
  
  public String getInternalTypeName(final AbstractElement assignment) {
    if (assignment instanceof Assignment) {
      return _getInternalTypeName((Assignment)assignment);
    } else if (assignment instanceof CrossReference) {
      return _getInternalTypeName((CrossReference)assignment);
    } else if (assignment instanceof RuleCall) {
      return _getInternalTypeName((RuleCall)assignment);
    } else if (assignment != null) {
      return _getInternalTypeName(assignment);
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(assignment).toString());
    }
  }
}
