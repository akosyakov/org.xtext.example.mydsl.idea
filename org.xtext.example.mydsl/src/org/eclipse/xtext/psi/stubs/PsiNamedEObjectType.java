package org.eclipse.xtext.psi.stubs;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectStubImpl;
import org.xtext.example.mydsl.lang.MyDslIdeaSetup;
import org.xtext.example.mydsl.myDsl.MyDslPackage;

import com.google.inject.Inject;
import com.intellij.lang.Language;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;

public class PsiNamedEObjectType extends IStubElementType<PsiNamedEObjectStub, PsiNamedEObject> {
	
	@Inject
	private IElementTypeProvider elementTypeProvider;

	public PsiNamedEObjectType(String debugName, Language language) {
		super(debugName, language);
		MyDslIdeaSetup.getInjector().injectMembers(this);
	}

	public String getExternalId() {
		return getLanguage().getID() + "." + super.toString();
	}

	public void serialize(PsiNamedEObjectStub stub, StubOutputStream dataStream) throws IOException {
		dataStream.writeName(stub.getName());
		dataStream.writeUTF(stub.getType().getName());
	}

	public PsiNamedEObjectStub deserialize(StubInputStream dataStream, StubElement parentStub) throws IOException {
		StringRef name = dataStream.readName();
		EClass type = (EClass) MyDslPackage.eINSTANCE.getEClassifier(dataStream.readUTF());
		return new PsiNamedEObjectStubImpl(parentStub, name, type, elementTypeProvider.getNamedObjectType());
	}

	public void indexStub(PsiNamedEObjectStub stub, IndexSink sink) {
		sink.occurrence(PsiNamedEObjectIndex.NAME_INDEX_KEY, stub.getName());
	}

	@Override
	public PsiNamedEObject createPsi(PsiNamedEObjectStub stub) {
		return new PsiNamedEObjectImpl(stub, elementTypeProvider.getNamedObjectType(), elementTypeProvider.getNameType());
	}

	@Override
	public PsiNamedEObjectStub createStub(PsiNamedEObject psi, StubElement parentStub) {
		String name = psi.getName();
		EReference reference = psi.getEReference();
		return new PsiNamedEObjectStubImpl(parentStub, StringRef.fromString(name), reference.eClass(), elementTypeProvider.getNamedObjectType());
	}

}
