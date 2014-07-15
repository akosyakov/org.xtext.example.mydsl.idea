package org.xtext.example.mydsl.lang;

import org.eclipse.xtext.idea.lang.IElementTypeProvider;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.psi.PsiNamedEObjectStub;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectType;
import org.xtext.example.mydsl.idea.lang.MyDslLanguage;
import org.xtext.example.mydsl.idea.lang.psi.impl.MyDslFileImpl;

import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.PsiFileStub;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.IStubFileElementType;

public class MyDslElementTypeProvider implements IElementTypeProvider {

	public static final IFileElementType FILE_TYPE = new IStubFileElementType<PsiFileStub<MyDslFileImpl>>(MyDslLanguage.INSTANCE);
	
	public static final IElementType NAME_TYPE = new IElementType("NAME", MyDslLanguage.INSTANCE);
	
	public static final IElementType EOBJECT_TYPE = new IElementType("EOBJECT_TYPE", MyDslLanguage.INSTANCE);
	
	public static final IStubElementType<PsiNamedEObjectStub, PsiNamedEObject> NAMED_EOBJECT_TYPE = new PsiNamedEObjectType("NAMED_EOBJECT", MyDslLanguage.INSTANCE);
	
	public static final IElementType CROSS_REFERENCE_TYPE = new IElementType("CROSS_REFERENCE", MyDslLanguage.INSTANCE);

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
