package org.xtext.example.domainmodel.idea.lang.psi

import com.intellij.psi.impl.PsiTreeChangeEventImpl
import com.intellij.psi.util.PsiModificationTracker
import org.eclipse.xtext.psi.BaseXtextCodeBlockModificationListener
import org.xtext.example.domainmodel.idea.lang.DomainmodelLanguage

class DomainmodelCodeBlockModificationListener extends BaseXtextCodeBlockModificationListener {
	
	new(PsiModificationTracker psiModificationTracker) {
		super(DomainmodelLanguage.INSTANCE, psiModificationTracker)
	}
	
	override protected hasJavaStructuralChanges(PsiTreeChangeEventImpl event) {
		true
	}
	
}