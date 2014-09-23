package org.eclipse.xtext.psi.stubs

import com.google.inject.Inject
import com.google.inject.Provider
import com.intellij.lang.Language
import com.intellij.psi.stubs.IndexSink
import com.intellij.psi.stubs.PsiFileStub
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import com.intellij.psi.stubs.StubOutputStream
import com.intellij.psi.tree.IStubFileElementType
import java.io.IOException
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EPackage
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.common.types.TypesPackage
import org.eclipse.xtext.idea.lang.IXtextLanguage
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex
import org.eclipse.xtext.psi.stubindex.ExportedObjectQualifiedNameIndex

class XtextFileElementType<T extends XtextFileStub<?>> extends IStubFileElementType<T> {

	@Inject
	Provider<XtextStubBuilder> xtextStubBuilderProvider

	@Inject
	ExportedObjectQualifiedNameIndex exportedObjectQualifiedNameIndex
	
	@Inject
	JvmDeclaredTypeShortNameIndex jvmDeclaredTypeShortNameIndex

	new(Language language) {
		super(language)
		if (language instanceof IXtextLanguage) {
			language.injectMembers(this)
		}
	}

	override getExternalId() {
		"xtext.file"
	}

	override getBuilder() {
		xtextStubBuilderProvider.get
	}

	override serialize(T stub, StubOutputStream it) throws IOException {
		writeExportedObjects(stub.exportedObjects)
	}

	protected def void writeExportedObjects(StubOutputStream it, List<ExportedObject> exportedObjects) throws IOException {
		writeInt(exportedObjects.size)
		for (exportedObject : exportedObjects) {
			writeUTF(exportedObject.name)
			writeUTF(exportedObject.qualifiedName)
			writeEClass(exportedObject.type)
		}
	}
	
	protected def writeEClass(StubOutputStream it, EClass type) {
		writeUTF(type.EPackage.nsURI)
		writeUTF(type.name)
	}

	override deserialize(StubInputStream it, StubElement parentStub) throws IOException {
		val stub = new XtextFileStub(null, this)
		stub.exportedObjects = readExportedObjects
		stub as T
	}

	protected def readExportedObjects(StubInputStream it) throws IOException {
		val exportedObjects = newArrayList
		val count = readInt
		for (var i = 0; i < count; i++) {
			val name = readUTF
			val qualifiedName = readUTF
			val type = readEClass
			exportedObjects += new ExportedObject(name, qualifiedName, type)
		}
		exportedObjects
	}
	
	protected def readEClass(StubInputStream it) {
		val packageURI = readUTF
		val ePackage = EPackage.Registry.INSTANCE.getEPackage(packageURI)
		ePackage.getEClassifier(readUTF) as EClass	
	}

	override indexStub(PsiFileStub stub, extension IndexSink sink) {
		if (stub instanceof XtextFileStub<?>) {
			for (exportedObject : stub.exportedObjects) {
				exportedObjectQualifiedNameIndex.key.occurrence(exportedObject.qualifiedName)
				if (EcoreUtil2.isAssignableFrom(TypesPackage.Literals.JVM_DECLARED_TYPE, exportedObject.type)) {
					jvmDeclaredTypeShortNameIndex.key.occurrence(exportedObject.name)
				}
			}
		}
	}
	
	override indexStub(T stub, IndexSink sink) {
		(stub as PsiFileStub<?>).indexStub(sink)
	}
	
}
