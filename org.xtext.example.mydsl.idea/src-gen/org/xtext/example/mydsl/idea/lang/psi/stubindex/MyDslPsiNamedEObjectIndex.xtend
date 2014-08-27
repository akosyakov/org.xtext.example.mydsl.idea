package org.xtext.example.mydsl.idea.lang.psi.stubindex

import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex
import org.xtext.example.mydsl.idea.lang.MyDslLanguage

class MyDslPsiNamedEObjectIndex extends PsiNamedEObjectIndex {
	
	new() {
		super(MyDslLanguage.INSTANCE)
	}
	
}