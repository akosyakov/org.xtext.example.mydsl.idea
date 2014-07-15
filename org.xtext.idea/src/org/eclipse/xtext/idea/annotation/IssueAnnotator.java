package org.eclipse.xtext.idea.annotation;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.resource.XtextSyntaxDiagnostic;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;

public class IssueAnnotator implements Annotator {

	public void annotate(PsiElement element, AnnotationHolder holder) {
		if (!(element instanceof BaseXtextFile)) {
			return;
		}
		BaseXtextFile xtextFile = (BaseXtextFile) element;
		Resource resource = xtextFile.getResource();
		if (resource == null) {
			return;
		}
		for (Diagnostic error : resource.getErrors()) {
			if (error instanceof org.eclipse.xtext.diagnostics.Diagnostic && !(error instanceof XtextSyntaxDiagnostic)) {
				org.eclipse.xtext.diagnostics.Diagnostic xtextDiagnostic = (org.eclipse.xtext.diagnostics.Diagnostic) error;
				int endOffset = xtextDiagnostic.getOffset() + xtextDiagnostic.getLength();
				holder.createErrorAnnotation(new TextRange(xtextDiagnostic.getOffset(), endOffset), xtextDiagnostic.getMessage());
			}
		}
		for (Diagnostic warning : resource.getWarnings()) {
			if (warning instanceof org.eclipse.xtext.diagnostics.Diagnostic && !(warning instanceof XtextSyntaxDiagnostic)) {
				org.eclipse.xtext.diagnostics.Diagnostic xtextDiagnostic = (org.eclipse.xtext.diagnostics.Diagnostic) warning;
				int endOffset = xtextDiagnostic.getOffset() + xtextDiagnostic.getLength();
				holder.createWarningAnnotation(new TextRange(xtextDiagnostic.getOffset(), endOffset), xtextDiagnostic.getMessage());
			}
		}
	}

}
