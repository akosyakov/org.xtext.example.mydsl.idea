package org.eclipse.xtext.psi.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.util.io.StringRef;

public class PsiNamedEObjectStubImpl extends StubBase<PsiNamedEObject> implements PsiNamedEObjectStub {

	private final StringRef name;
	
	private final EClass type;

	@SuppressWarnings("rawtypes")
	public PsiNamedEObjectStubImpl(StubElement parent, StringRef name, EClass type, IStubElementType<? extends PsiNamedEObjectStub, ? extends PsiNamedEObject> elementType) {
		super(parent, elementType);
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return StringRef.toString(name);
	}

	public EClass getType() {
		return type;
	}

}
