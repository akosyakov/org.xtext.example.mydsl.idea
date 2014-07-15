package org.eclipse.xtext.psi;

import org.eclipse.emf.ecore.EClass;

import com.intellij.psi.stubs.NamedStub;

public interface PsiNamedEObjectStub extends NamedStub<PsiNamedEObject> {

	EClass getType();

}
