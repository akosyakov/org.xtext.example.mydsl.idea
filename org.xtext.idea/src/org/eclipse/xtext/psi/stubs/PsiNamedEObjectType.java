package org.eclipse.xtext.psi.stubs;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectStubImpl;

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
		if (language instanceof IXtextLanguage) {
			((IXtextLanguage) language).injectMembers(this);
		}
	}

	public String getExternalId() {
		return getLanguage().getID() + "." + super.toString();
	}

	public void serialize(PsiNamedEObjectStub stub, StubOutputStream dataStream) throws IOException {
		dataStream.writeName(stub.getName());
		dataStream.writeUTF(stub.getType().getEPackage().getNsURI());
		dataStream.writeUTF(stub.getType().getName());
	}

	public PsiNamedEObjectStub deserialize(StubInputStream dataStream, StubElement parentStub) throws IOException {
		StringRef name = dataStream.readName();
		String packageURI = dataStream.readUTF();
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageURI);
		EClass type = (EClass) ePackage.getEClassifier(dataStream.readUTF());
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
		return new PsiNamedEObjectStubImpl(parentStub, StringRef.fromString(name), psi.getType(), elementTypeProvider.getNamedObjectType());
	}

}
