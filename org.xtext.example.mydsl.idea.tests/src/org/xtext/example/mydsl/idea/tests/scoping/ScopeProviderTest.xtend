package org.xtext.example.mydsl.idea.tests.scoping

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.eclipse.xtext.idea.lang.IXtextLanguage
import org.eclipse.xtext.linking.lazy.CrossReferenceDescription.CrossReferenceDescriptionProvider
import org.eclipse.xtext.naming.IQualifiedNameConverter
import org.eclipse.xtext.psi.PsiEObject
import org.xtext.example.mydsl.myDsl.Entity
import org.xtext.example.mydsl.myDsl.MyDslPackage

class ScopeProviderTest extends LightCodeInsightFixtureTestCase {

	def void testImports() {
		val file = myFixture.configureByText("import.mydsl", "import foo.bar.*")
		myFixture.configureByText("foo.mydsl",
			'''
				foo.bar {
					entity Person {
						String name
					}
					datatype String
				}
			''')
		
		val language = file.language as IXtextLanguage
		val nameConverter = language.getInstance(IQualifiedNameConverter)
		val crossReferenceDescriptionProvider = language.getInstance(CrossReferenceDescriptionProvider)
		
		val fileRoot = (file.firstChild as PsiEObject).resource.contents.head
		val crossReferenceDescription = crossReferenceDescriptionProvider.get(fileRoot, MyDslPackage.Literals.FILE__ELEMENTS, null)
		val names = crossReferenceDescription.variants.map[name].toSet
		assertEquals(names.toString, 5, names.size)
		assertTrue(names.contains(nameConverter.toQualifiedName("Person")))
		assertTrue(names.contains(nameConverter.toQualifiedName("String")))
		assertTrue(names.contains(nameConverter.toQualifiedName("foo.bar")))
		assertTrue(names.contains(nameConverter.toQualifiedName("foo.bar.Person")))
		assertTrue(names.contains(nameConverter.toQualifiedName("foo.bar.String")))
	}
	
	def void testRelativeContext() {
		val file = myFixture.configureByText("relative.mydsl",
			'''
				stuff {
					baz {
						datatype String
					}
					entity Person {}
				}
			''')
		
		val language = file.language as IXtextLanguage
		val nameConverter = language.getInstance(IQualifiedNameConverter)
		val crossReferenceDescriptionProvider = language.getInstance(CrossReferenceDescriptionProvider)
		
		val entity = (file.firstChild as PsiEObject).resource.allContents.filter(Entity).head
		val crossReferenceDescription = crossReferenceDescriptionProvider.get(entity, MyDslPackage.Literals.PROPERTY__TYPE, null)
		val names = crossReferenceDescription.variants.map[name].toSet
		assertEquals(names.toString, 4, names.size)
		assertTrue(names.contains(nameConverter.toQualifiedName("Person")))
		assertTrue(names.contains(nameConverter.toQualifiedName("stuff.Person")))
		assertTrue(names.contains(nameConverter.toQualifiedName("baz.String")))
		assertTrue(names.contains(nameConverter.toQualifiedName("stuff.baz.String")))
	}
	
	def void testRelativePath() {
		val file = myFixture.configureByText("relative.mydsl",
			'''
				stuff {
					import baz.*
					baz {
						datatype String
					}
					entity Person {}
				}
			''')
		
		val language = file.language as IXtextLanguage
		val nameConverter = language.getInstance(IQualifiedNameConverter)
		val crossReferenceDescriptionProvider = language.getInstance(CrossReferenceDescriptionProvider)
			
		val entity = (file.firstChild as PsiEObject).resource.allContents.filter(Entity).head
		val crossReferenceDescription = crossReferenceDescriptionProvider.get(entity, MyDslPackage.Literals.PROPERTY__TYPE, null)
		val names = crossReferenceDescription.variants.map[name].toSet
		assertEquals(names.toString, 5, names.size)
		assertTrue(names.contains(nameConverter.toQualifiedName("Person")))
		assertTrue(names.contains(nameConverter.toQualifiedName("stuff.Person")))
		assertTrue(names.contains(nameConverter.toQualifiedName("String")))
		assertTrue(names.contains(nameConverter.toQualifiedName("baz.String")))
		assertTrue(names.contains(nameConverter.toQualifiedName("stuff.baz.String")))
	}

}
