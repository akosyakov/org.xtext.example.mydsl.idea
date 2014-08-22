package org.eclipse.xtext.psi;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.ImplementedBy;
import com.intellij.psi.PsiElement;

@ImplementedBy(PsiModelAssociations.class)
public interface IPsiModelAssociations {
	EObject getEObject(PsiEObject element);

	PsiElement getPsiElement(EObject object);

	PsiElement getPsiElement(IEObjectDescription objectDescription, EObject context);

	ICrossReferenceDescription getCrossReferenceDescription(PsiReferenceEObject element);
}
