package org.xtext.example.mydsl.idea.tests.refactoring;

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;
import org.eclipse.xtend2.lib.StringConcatenation;

@SuppressWarnings("all")
public class RenameTest extends LightCodeInsightFixtureTestCase {
  public void testRenameQualifiedName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("entity Foo {");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("}");
    _builder.newLine();
    _builder.append("}");
    _builder.newLine();
    this.myFixture.configureByText("Foo.mydsl", _builder.toString());
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("entity Bar {");
    _builder_1.newLine();
    _builder_1.append("\t");
    _builder_1.append("foo.Fo<caret>o foo");
    _builder_1.newLine();
    _builder_1.append("}");
    _builder_1.newLine();
    this.myFixture.configureByText("Bar.mydsl", _builder_1.toString());
    this.myFixture.renameElementAtCaret("Foo2");
    StringConcatenation _builder_2 = new StringConcatenation();
    _builder_2.append("entity Bar {");
    _builder_2.newLine();
    _builder_2.append("\t");
    _builder_2.append("foo.Foo2 foo");
    _builder_2.newLine();
    _builder_2.append("}");
    _builder_2.newLine();
    this.myFixture.checkResult(_builder_2.toString());
  }
}
