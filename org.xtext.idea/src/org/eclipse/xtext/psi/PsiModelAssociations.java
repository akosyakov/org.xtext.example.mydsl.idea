package org.eclipse.xtext.psi;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.idea.lang.BaseXtextPsiParser.PsiAdapter;
import org.eclipse.xtext.linking.lazy.CrossReferenceDescription.CrossReferenceDescriptionProvider;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;

import com.google.inject.Inject;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;

public class PsiModelAssociations implements IPsiModelAssociations {

    @Inject
    private CrossReferenceDescriptionProvider crossReferenceDescriptionProvider;

    public EObject getEObject(PsiEObject element) {
    	if (element == null) {
            return null;
        }
    	return element.getINode().getSemanticElement();
    }

    public PsiElement getPsiElement(EObject object) {
        CompositeElement composite = PsiAdapter.getComposite(object);
        if (composite == null) {
        	return null;
        }
        return composite.getPsi();
    }

    public ICrossReferenceDescription getCrossReferenceDescription(PsiReferenceEObject element) {
    	if (element == null) {
    		return null;
    	}
		PsiReferenceEObject psiReferenceEObject = (PsiReferenceEObject) element;
		Integer index = psiReferenceEObject.getIndex();
		EObject context = psiReferenceEObject.getEContext();
		EReference reference = psiReferenceEObject.getEReference();
        return crossReferenceDescriptionProvider.get(context, reference, index);
    }

}
