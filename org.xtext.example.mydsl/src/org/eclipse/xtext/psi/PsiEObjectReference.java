package org.eclipse.xtext.psi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.xtext.example.mydsl.lang.MyDslIdeaSetup;

import com.google.inject.Inject;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTFactory;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceBase;
import com.intellij.psi.impl.source.codeStyle.CodeEditUtil;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.util.IncorrectOperationException;

public class PsiEObjectReference extends PsiReferenceBase<PsiReferenceEObject> implements PsiReference {

    @Inject
    private IPsiModelAssociations psiModelAssociations;

	public PsiEObjectReference(PsiReferenceEObject element, TextRange range) {
		super(element, range);
		MyDslIdeaSetup.getInjector().injectMembers(this);
	}

	public Object[] getVariants() {
        ICrossReferenceDescription crossReferenceDescription = psiModelAssociations.getCrossReferenceDescription(myElement);
        if (crossReferenceDescription == null) {
            return new Object[0];
        }
        List<LookupElement> variants = new ArrayList<LookupElement>();
        for (IEObjectDescription objectDescription : crossReferenceDescription.getVariants()) {
            PsiElement element = psiModelAssociations.getPsiElement(objectDescription.getEObjectOrProxy());
            if (element instanceof PsiNamedElement) {
                PsiNamedElement namedElement = (PsiNamedElement) element;
                String name = namedElement.getName();
                if (name != null && name.length() > 0) {
                    variants.add(LookupElementBuilder.create(namedElement).withTypeText(element.getContainingFile().getName()));
                }
            }
        }
        return variants.toArray();
	}

	public PsiElement resolve() {
        ICrossReferenceDescription crossReferenceDescription = psiModelAssociations.getCrossReferenceDescription(myElement);
        if (crossReferenceDescription == null) {
            return null;
        }
        EObject object = crossReferenceDescription.resolve();
        return psiModelAssociations.getPsiElement(object);
	}
	
	@Override
	public PsiElement handleElementRename(String newElementName)
			throws IncorrectOperationException {
		PsiReferenceEObject element = getElement();
		ASTNode referenceNode = element.getNode();
		ASTNode oldNode = referenceNode.getFirstChildNode();
		LeafElement newChild = ASTFactory.leaf(oldNode.getElementType(), newElementName);
		CodeEditUtil.setNodeGenerated(newChild, true);
		referenceNode.replaceChild(oldNode, newChild);
		return element;
	}
}
