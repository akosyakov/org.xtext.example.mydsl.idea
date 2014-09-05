package org.xtext.example.mydsl.idea.lang.psi

import com.intellij.psi.util.PsiModificationTracker
import org.eclipse.xtext.psi.BaseXtextCodeBlockModificationListener
import org.xtext.example.mydsl.idea.lang.MyDslLanguage

class MyDslCodeBlockModificationListener extends BaseXtextCodeBlockModificationListener {

	new(PsiModificationTracker psiModificationTracker) {
		super(MyDslLanguage.INSTANCE, psiModificationTracker)
	}

}
