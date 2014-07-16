package org.xtext.example.mydsl.idea.tests.linking;

import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiReferenceEObject;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase;

public class MyDslLinkingTest extends LightCodeInsightFixtureTestCase {
	
	@Override
	protected String getTestDataPath() {
		return "./testData";
	}
	
	public void testLocalLinking() {
		myFixture.configureByFiles("LocalLinkingTestData.mydsl");
		PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
		assertTrue("" + element, element instanceof PsiReferenceEObject);
		assertEquals("Foo", ((com.intellij.psi.PsiNamedElement) element.getParent().getParent()).getName());
		
		PsiReference reference = element.getReference();
		assertNotNull(reference);
		
		PsiElement resolved = reference.resolve();
		assertTrue("" + resolved, resolved instanceof PsiNamedEObject);
		
		PsiNamedEObject namedEObject = (PsiNamedEObject) resolved;
		assertEquals("Foo", namedEObject.getName());
	}
	
	public void testGlobalLinking() {
		myFixture.configureByFiles("GlobalLinkingTestData.mydsl", "LocalLinkingTestData.mydsl");
		PsiElement element = myFixture.getFile().findElementAt(myFixture.getCaretOffset()).getParent();
		assertTrue("" + element, element instanceof PsiReferenceEObject);
		assertEquals("Bar", ((com.intellij.psi.PsiNamedElement) element.getParent().getParent()).getName());
		
		PsiReference reference = element.getReference();
		assertNotNull(reference);
		
		PsiElement resolved = reference.resolve();
		assertTrue("" + resolved, resolved instanceof PsiNamedEObject);
		
		PsiNamedEObject namedEObject = (PsiNamedEObject) resolved;
		assertEquals("Foo", namedEObject.getName());
	}

}
