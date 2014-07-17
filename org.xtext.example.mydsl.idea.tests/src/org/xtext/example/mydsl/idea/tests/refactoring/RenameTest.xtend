package org.xtext.example.mydsl.idea.tests.refactoring

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

class RenameTest extends LightCodeInsightFixtureTestCase {

	def void testRenameQualifiedName() {
		myFixture.configureByText("Foo.mydsl",
			'''
				foo {
					entity Foo {
					}
				}
			''')
		myFixture.configureByText("Bar.mydsl",
			'''
				entity Bar {
					foo.Fo<caret>o foo
				}
			''')
		myFixture.renameElementAtCaret("Foo2")
		myFixture.checkResult('''
			entity Bar {
				foo.Foo2 foo
			}
		''')
	}

}
