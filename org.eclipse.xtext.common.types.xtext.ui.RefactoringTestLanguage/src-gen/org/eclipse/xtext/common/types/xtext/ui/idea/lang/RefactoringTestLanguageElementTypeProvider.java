package org.eclipse.xtext.common.types.xtext.ui.idea.lang;

import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub;
import org.eclipse.xtext.idea.types.psi.stubs.elements.PsiJvmNamedEObjectType;
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.psi.impl.RefactoringTestLanguageFileImpl;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IStubFileElementType;

public class RefactoringTestLanguageElementTypeProvider implements IElementTypeProvider {

	public static final IFileElementType FILE_TYPE = new IStubFileElementType<PsiFileStub<RefactoringTestLanguageFileImpl>>(RefactoringTestLanguageLanguage.INSTANCE);
	
	public static final IElementType NAME_TYPE = new IElementType("NAME", RefactoringTestLanguageLanguage.INSTANCE);
	
	public static final IElementType EOBJECT_TYPE = new IElementType("EOBJECT_TYPE", RefactoringTestLanguageLanguage.INSTANCE);
	
	public static final IStubElementType<PsiJvmNamedEObjectStub, PsiJvmNamedEObject> NAMED_EOBJECT_TYPE = new PsiJvmNamedEObjectType("NAMED_EOBJECT", RefactoringTestLanguageLanguage.INSTANCE);
	
	public static final IElementType CROSS_REFERENCE_TYPE = new IElementType("CROSS_REFERENCE", RefactoringTestLanguageLanguage.INSTANCE);

	public IFileElementType getFileType() {
		return FILE_TYPE;
	}

	public IElementType getObjectType() {
		return EOBJECT_TYPE;
	}

	public IElementType getCrossReferenceType() {
		return CROSS_REFERENCE_TYPE;
	}

	public IElementType getNameType() {
		return NAME_TYPE;
	}

	public IStubElementType<PsiJvmNamedEObjectStub, PsiJvmNamedEObject> getNamedObjectType() {
		return NAMED_EOBJECT_TYPE;
	}

}
