package org.eclipse.xtext.psi.stubs

import com.google.inject.Inject
import com.intellij.psi.PsiFile
import com.intellij.psi.stubs.DefaultStubBuilder
import org.eclipse.xtext.idea.lang.IElementTypeProvider
import org.eclipse.xtext.psi.impl.BaseXtextFile

class XtextStubBuilder extends DefaultStubBuilder {
	
	@Inject
	IElementTypeProvider elementTypeProvider
	
	override protected createStubForFile(PsiFile file) {
		switch file {
			BaseXtextFile: file.createStubForFile
			default: super.createStubForFile(file)  
		}
	}
	
	protected def createStubForFile(BaseXtextFile file) {
		val stub = new XtextFileStub(file, elementTypeProvider.fileType as XtextFileElementType<?>)
		stub.exportedObjects = newArrayList
		val resourceDescription = file.resourceDescription
		if (resourceDescription != null) {
			for (exportedObject : resourceDescription.exportedObjects) {
				val qualifiedName = exportedObject.qualifiedName
				stub.exportedObjects += new ExportedObject(qualifiedName.lastSegment, qualifiedName.toString, exportedObject.EClass)
			}
		}
		stub
	}
	
}