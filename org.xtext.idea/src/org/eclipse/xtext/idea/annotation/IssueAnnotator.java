package org.eclipse.xtext.idea.annotation;

import java.util.List;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.idea.ProcessCanceledExceptionHandling;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.psi.PsiEObject;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;

import com.intellij.lang.Language;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.progress.ProgressIndicatorProvider;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

public class IssueAnnotator implements Annotator {

	public void annotate(PsiElement element, AnnotationHolder holder) {
		if (!(element instanceof PsiEObject)) {
			return;
		}
		PsiEObject psiEObject = (PsiEObject) element;
		if (!psiEObject.isRoot()) {
			return;
		}
		Resource resource = psiEObject.getResource();
		if (resource == null) {
			return;
		}
		Language language = psiEObject.getLanguage();
		if (!(language instanceof IXtextLanguage)) {
			return;
		}
		IXtextLanguage xtextLanguage = (IXtextLanguage) language;
		IResourceValidator resourceValidator = xtextLanguage.getInstance(IResourceValidator.class);
		try {
			for (Issue issue : getIssues(resource, resourceValidator)) {
				if (issue.isSyntaxError()) {
					continue;
				}
				HighlightSeverity highlightSeverity = getHighlightSeverity(issue.getSeverity());
				if (highlightSeverity == null) {
					continue;
				}
				int endOffset = issue.getOffset() + issue.getLength();
				holder.createAnnotation(highlightSeverity, new TextRange(issue.getOffset(), endOffset), issue.getMessage());
			}
		} catch (OperationCanceledError e) {
			throw e.getWrapped();
		}
	}

	protected List<Issue> getIssues(Resource resource, IResourceValidator resourceValidator) {
		ProgressIndicatorProvider.checkCanceled();
		try {
			return resourceValidator.validate(resource, CheckMode.NORMAL_AND_FAST, new CancelIndicator() {
				
				public boolean isCanceled() {
					ProgressIndicatorProvider.checkCanceled();
					return false;
				}
				
			});
		} catch (Exception e) {
    		throw ProcessCanceledExceptionHandling.unWrappedReThrow(e);
    	}
	}

	protected HighlightSeverity getHighlightSeverity(Severity severity) {
		if (severity == Severity.ERROR) {
			return HighlightSeverity.ERROR;
		}
		if (severity == Severity.WARNING) {
			return HighlightSeverity.WARNING;
		}
		if (severity == Severity.INFO) {
			return HighlightSeverity.INFORMATION;
		}
		return null;
	}

}
