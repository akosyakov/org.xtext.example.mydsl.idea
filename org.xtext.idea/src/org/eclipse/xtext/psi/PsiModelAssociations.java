package org.eclipse.xtext.psi;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.idea.lang.BaseXtextPsiParser.PsiAdapter;
import org.eclipse.xtext.idea.resource.impl.StubEObjectDescription;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;

public class PsiModelAssociations implements IPsiModelAssociations {
	
    public EObject getEObject(PsiEObject element) {
    	if (element == null) {
            return null;
        }
    	return element.getEObject();
    }

    public PsiElement getPsiElement(EObject object) {
        CompositeElement composite = PsiAdapter.getComposite(object);
        if (composite == null) {
        	return null;
        }
        return composite.getPsi();
    }
    
	public PsiElement getPsiElement(IEObjectDescription objectDescription) {
		if (objectDescription == null) {
			return null;
		}
		if (objectDescription instanceof StubEObjectDescription) {
			StubEObjectDescription stubEObjectDescription = (StubEObjectDescription) objectDescription;
			return stubEObjectDescription.getPsiNamedEObject();
		}
		return getPsiElement(objectDescription.getEObjectOrProxy());
	}

    public ICrossReferenceDescription getCrossReferenceDescription(PsiReferenceEObject element) {
    	if (element == null) {
    		return null;
    	}
    	return element.getCrossReferenceDescription();
    }

}
