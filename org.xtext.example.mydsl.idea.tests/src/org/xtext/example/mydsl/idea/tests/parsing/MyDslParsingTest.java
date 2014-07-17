package org.xtext.example.mydsl.idea.tests.parsing;

import org.eclipse.xtext.idea.lang.BaseXtextASTFactory;
import org.xtext.example.mydsl.idea.lang.MyDslLanguage;
import org.xtext.example.mydsl.idea.lang.parser.MyDslParserDefinition;

import com.intellij.lang.LanguageASTFactory;
import com.intellij.testFramework.ParsingTestCase;

public class MyDslParsingTest extends ParsingTestCase {

	public MyDslParsingTest() {
		super("", "mydsl", new MyDslParserDefinition());
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		addExplicitExtension(LanguageASTFactory.INSTANCE, MyDslLanguage.INSTANCE, new BaseXtextASTFactory());
	}
	
	public void testPsiEObjectParsingTestData() {
		doTest(true);
	}
	
	public void testEntityParsing() {
		doTest(true);
	}
	
	@Override
	protected String getTestDataPath() {
		return "./testData";
	}
	
	@Override
	protected boolean skipSpaces() {
		return false;
	}
	
	@Override
	protected boolean includeRanges() {
		return true;
	}
	
}
