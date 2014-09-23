package org.eclipse.xtext.common.types.xtext.ui.idea.lang;

import org.eclipse.xtext.common.types.xtext.ui.idea.lang.psi.impl.RefactoringTestLanguageFileImpl;
import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectType;
import org.eclipse.xtext.psi.stubs.XtextFileElementType;
import org.eclipse.xtext.psi.stubs.XtextFileStub;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;

public class RefactoringTestLanguageElementTypeProvider implements IElementTypeProvider {

	public static final IFileElementType FILE_TYPE = new XtextFileElementType<XtextFileStub<RefactoringTestLanguageFileImpl>>(RefactoringTestLanguageLanguage.INSTANCE);
	
	public static final IElementType NAME_TYPE = new IElementType("NAME", RefactoringTestLanguageLanguage.INSTANCE);
	
	public static final IElementType EOBJECT_TYPE = new IElementType("EOBJECT_TYPE", RefactoringTestLanguageLanguage.INSTANCE);
	
	public static final IStubElementType<PsiNamedEObjectStub, PsiNamedEObject> NAMED_EOBJECT_TYPE = new PsiNamedEObjectType("NAMED_EOBJECT", RefactoringTestLanguageLanguage.INSTANCE);
	
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

	public IStubElementType<PsiNamedEObjectStub, PsiNamedEObject> getNamedObjectType() {
		return NAMED_EOBJECT_TYPE;
	}

}
