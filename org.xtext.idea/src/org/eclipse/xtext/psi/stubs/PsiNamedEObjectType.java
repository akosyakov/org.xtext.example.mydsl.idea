package org.eclipse.xtext.psi.stubs;

import java.io.IOException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl;
import org.eclipse.xtext.psi.impl.PsiNamedEObjectStubImpl;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.IAcceptor;

import com.google.inject.Inject;
import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.IndexSink;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;

public class PsiNamedEObjectType extends IStubElementType<PsiNamedEObjectStub, PsiNamedEObject> {
	
	@Inject
	private IElementTypeProvider elementTypeProvider;
	
	@Inject
	private IDefaultResourceDescriptionStrategy resourceDescriptionStrategy;

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
		dataStream.writeUTF(stub.getEClass().getEPackage().getNsURI());
		dataStream.writeUTF(stub.getEClass().getName());
		dataStream.writeUTF(stub.getQualifiedName().toString());
	}

	@SuppressWarnings("rawtypes")
	public PsiNamedEObjectStub deserialize(StubInputStream dataStream, StubElement parentStub) throws IOException {
		StringRef name = dataStream.readName();
		String packageURI = dataStream.readUTF();
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageURI);
		EClass type = (EClass) ePackage.getEClassifier(dataStream.readUTF());
		
		String[] segments = dataStream.readUTF().split("\\.");
		QualifiedName qualifiedName = QualifiedName.create(segments);
		return new PsiNamedEObjectStubImpl(parentStub, name, qualifiedName, type, elementTypeProvider.getNamedObjectType());
	}

	public void indexStub(PsiNamedEObjectStub stub, IndexSink sink) {
		sink.occurrence(PsiNamedEObjectIndex.NAME_INDEX_KEY, stub.getName());
	}

	@Override
	public PsiNamedEObject createPsi(PsiNamedEObjectStub stub) {
		return new PsiNamedEObjectImpl(stub, elementTypeProvider.getNamedObjectType(), elementTypeProvider.getNameType());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PsiNamedEObjectStub createStub(PsiNamedEObject psi, StubElement parentStub) {
		String name = psi.getName();
		QualifiedName qualifiedName = psi.getQualifiedName();
		return new PsiNamedEObjectStubImpl(parentStub, StringRef.fromString(name), qualifiedName, psi.getEClass(), elementTypeProvider.getNamedObjectType());
	}
	
	@Override
	public boolean shouldCreateStub(ASTNode node) {
		PsiElement psiElement = node.getPsi();
		if (psiElement instanceof PsiNamedEObject) {
			final PsiNamedEObject namedEObject = (PsiNamedEObject) psiElement;
			namedEObject.setQualifiedName(null);
			resourceDescriptionStrategy.createEObjectDescriptions(namedEObject.getEObject(), new IAcceptor<IEObjectDescription>() {
				
				public void accept(IEObjectDescription t) {
					namedEObject.setQualifiedName(t.getQualifiedName());
				}

			});
			return namedEObject.getQualifiedName() != null;
		}
		return super.shouldCreateStub(node);
	}

}
