package org.eclipse.xtext.psi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.diagnostics.ExceptionDiagnostic;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Inject;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.lang.ASTFactory;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.openapi.progress.ProcessCanceledException;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
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
		Language language = element.getLanguage();
		if (language instanceof IXtextLanguage) {
			((IXtextLanguage) language).injectMembers(this);
		}
	}

	public Object[] getVariants() {
		try {
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
		} catch (WrappedException e) {
			if (e.getCause() instanceof ProcessCanceledException) {
				throw (ProcessCanceledException) e.getCause();
			}
			throw e;
		}
	}

	public PsiElement resolve() {
		ProgressIndicatorProvider.checkCanceled();
		try {
	        ICrossReferenceDescription crossReferenceDescription = psiModelAssociations.getCrossReferenceDescription(myElement);
	        if (crossReferenceDescription == null) {
	            return null;
	        }
	        EObject object = crossReferenceDescription.resolve();
	        return psiModelAssociations.getPsiElement(object);
		}  catch (WrappedException e) {
			if (e.getCause() instanceof ProcessCanceledException) {
				throw (ProcessCanceledException) e.getCause();
			}
			throw e;
		}
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

	protected void checkCanceled() {
		PsiFile containingFile = myElement.getContainingFile();
		if (!(containingFile instanceof BaseXtextFile)) {
			return;
		}
		BaseXtextFile xtextFile = (BaseXtextFile) containingFile;
		Resource resource = xtextFile.getResource();
		if (resource == null) {
			return;
		}
		EList<Diagnostic> errors = resource.getErrors();
		if (errors == null) {
			return;
		}
		for (Diagnostic error : errors) {
			if (!(error instanceof ExceptionDiagnostic)) {
				continue;
			}
			ExceptionDiagnostic exceptionDiagnostic = (ExceptionDiagnostic) error;
			Exception exception = exceptionDiagnostic.getException();
			if (exception instanceof ProcessCanceledException) {
				throw (ProcessCanceledException) exception;
			}
		}
	}

}