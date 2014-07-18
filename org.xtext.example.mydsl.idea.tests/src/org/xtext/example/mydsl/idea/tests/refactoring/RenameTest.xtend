package org.xtext.example.mydsl.idea.tests.refactoring

import com.intellij.psi.PsiElement
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EReference
import org.eclipse.xtext.psi.PsiNamedEObject
import org.eclipse.xtext.psi.PsiReferenceEObject
import org.xtext.example.mydsl.myDsl.MyDslPackage

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
	
	def void testRenameWithSeveralCandidates() {
		myFixture.configureByText("Foo.mydsl",
			'''
				foo {
				    import bar.*
				    bar {
				        datatype String
				    }
				    entity Foo {
				        String abc
				    }
				    datatype Strin<caret>g2
				}
			''')
		myFixture.renameElementAtCaret("String")
		myFixture.checkResult('''
			foo {
			    import bar.*
			    bar {
			        datatype String
			    }
			    entity Foo {
			        String abc
			    }
			    datatype String
			}
		''')
	}
	
	def getType(PsiElement it) {
		getPsiEReferences(MyDslPackage.Literals.PROPERTY__TYPE).head
	}
	
	def getNamespaces(PsiElement it) {
		getPsiNamedEObjects(MyDslPackage.Literals.NAMESPACE)
	}
	
	def getEntities(PsiElement it) {
		getPsiNamedEObjects(MyDslPackage.Literals.ENTITY)
	}
	
	def getDatatypes(PsiElement it) {
		getPsiNamedEObjects(MyDslPackage.Literals.DATATYPE)
	}
	
	def getProperties(PsiElement it) {
		getPsiNamedEObjects(MyDslPackage.Literals.PROPERTY)
	}
	
	def getPsiNamedEObjects(PsiElement it, EClass eClass) {
		children.filter(PsiNamedEObject).filter[eClass.isSuperTypeOf(EClass)]
	}
	
	def getPsiEReferences(PsiElement it, EReference eReference) {
		children.filter(PsiReferenceEObject).filter[EReference == eReference]
	}

}
