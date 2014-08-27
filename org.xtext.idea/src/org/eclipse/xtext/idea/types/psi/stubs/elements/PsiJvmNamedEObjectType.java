package org.eclipse.xtext.idea.types.psi.stubs.elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmNamedEObjectImpl;
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmNamedEObjectStubImpl;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmDeclaredTypeDTO;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub;
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex;
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.IAcceptor;

import com.google.common.collect.Lists;
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

public class PsiJvmNamedEObjectType extends IStubElementType<PsiJvmNamedEObjectStub, PsiJvmNamedEObject> {
	
	@Inject
	private IElementTypeProvider elementTypeProvider;
	
	@Inject
	private PsiNamedEObjectIndex psiNamedEObjectIndex;
	
	@Inject
	private JvmDeclaredTypeShortNameIndex jvmDeclaredTypeShortNameIndex;
	
	@Inject
	private JvmDeclaredTypeFullClassNameIndex jvmDeclaredTypeFullClassNameIndex;
	
	@Inject
	private IDefaultResourceDescriptionStrategy resourceDescriptionStrategy;

	public PsiJvmNamedEObjectType(String debugName, Language language) {
		super(debugName, language);
		if (language instanceof IXtextLanguage) {
			((IXtextLanguage) language).injectMembers(this);
		}
	}
	
	public String getExternalId() {
		return getLanguage().getID() + "." + super.toString();
	}

	public void serialize(PsiJvmNamedEObjectStub stub, StubOutputStream dataStream) throws IOException {
		dataStream.writeName(stub.getName());
		writeEClass(dataStream, stub.getEClass());
		dataStream.writeUTF(stub.getQualifiedName().toString());
		writeJvmDeclaredTypes(dataStream, stub.getClasses());
	}

	protected void writeJvmDeclaredTypes(StubOutputStream dataStream, List<PsiJvmDeclaredTypeDTO> jvmDeclaredTypes) throws IOException {
		dataStream.writeInt(jvmDeclaredTypes.size());
		for (PsiJvmDeclaredTypeDTO psiClass : jvmDeclaredTypes) {
			dataStream.writeUTF(psiClass.getName());
			dataStream.writeUTF(psiClass.getQualifiedName());
			writeEClass(dataStream, psiClass.getType());
		}
	}

	protected void writeEClass(StubOutputStream dataStream, EClass eClass) throws IOException {
		dataStream.writeUTF(eClass.getEPackage().getNsURI());
		dataStream.writeUTF(eClass.getName());
	}

	@SuppressWarnings("rawtypes")
	public PsiJvmNamedEObjectStub deserialize(StubInputStream dataStream, StubElement parentStub) throws IOException {
		StringRef name = dataStream.readName();
		EClass type = readEClass(dataStream);
		
		String[] segments = dataStream.readUTF().split("\\.");
		QualifiedName qualifiedName = QualifiedName.create(segments);
		
		PsiJvmNamedEObjectStubImpl stubImpl = new PsiJvmNamedEObjectStubImpl(parentStub, name, qualifiedName, type, elementTypeProvider.getNamedObjectType());
		stubImpl.setClasses(readJvmDeclaredTypes(dataStream, stubImpl.getPsi(), parentStub));
		return stubImpl;
	}

	protected List<PsiJvmDeclaredTypeDTO> readJvmDeclaredTypes(StubInputStream dataStream, PsiJvmNamedEObject psiJvmNamedEObject, StubElement parentStub) throws IOException {
		List<PsiJvmDeclaredTypeDTO> jvmDeclaredTypes = Lists.newArrayList();
		int count = dataStream.readInt();
		for (int i = 0; i < count; i++) {
			String name = dataStream.readUTF();
			String qualifiedName = dataStream.readUTF();
			EClass type = readEClass(dataStream);
			jvmDeclaredTypes.add(new PsiJvmDeclaredTypeDTO(name, qualifiedName, type));
		}
		return jvmDeclaredTypes;
	}

	protected EClass readEClass(StubInputStream dataStream) throws IOException {
		String packageURI = dataStream.readUTF();
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageURI);
		return (EClass) ePackage.getEClassifier(dataStream.readUTF());
	}

	public void indexStub(PsiJvmNamedEObjectStub stub, IndexSink sink) {
		sink.occurrence(psiNamedEObjectIndex.getKey(), stub.getName());
		
		for (PsiJvmDeclaredTypeDTO psiClass : stub.getClasses()) {
			sink.occurrence(jvmDeclaredTypeShortNameIndex.getKey(), psiClass.getName());
        	sink.occurrence(jvmDeclaredTypeFullClassNameIndex.getKey(), psiClass.getQualifiedName());
		}
	}

	@Override
	public PsiJvmNamedEObject createPsi(PsiJvmNamedEObjectStub stub) {
		return new PsiJvmNamedEObjectImpl(stub, elementTypeProvider.getNamedObjectType(), elementTypeProvider.getNameType());
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PsiJvmNamedEObjectStub createStub(PsiJvmNamedEObject psi, StubElement parentStub) {
		String name = psi.getName();
		QualifiedName qualifiedName = psi.getQualifiedName();
		PsiJvmNamedEObjectStubImpl psiJvmNamedEObjectStubImpl = new PsiJvmNamedEObjectStubImpl(parentStub, StringRef.fromString(name), qualifiedName, psi.getEClass(), elementTypeProvider.getNamedObjectType());
		psiJvmNamedEObjectStubImpl.setClasses(getClasses(psi));
		return psiJvmNamedEObjectStubImpl;
	}

	protected List<PsiJvmDeclaredTypeDTO> getClasses(PsiJvmNamedEObject psi) {
		List<PsiJvmDeclaredTypeDTO> classes = new ArrayList<PsiJvmDeclaredTypeDTO>();
		for(PsiJvmDeclaredType psiClass : psi.getClasses()) {
			classes.add(new PsiJvmDeclaredTypeDTO(psiClass.getName(), psiClass.getQualifiedName(), psiClass.getType()));
		}
		return classes;
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
