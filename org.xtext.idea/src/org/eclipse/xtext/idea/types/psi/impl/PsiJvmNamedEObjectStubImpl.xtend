package org.eclipse.xtext.idea.types.psi.impl

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import com.intellij.util.io.StringRef
import java.util.List
import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmDeclaredTypeDTO
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.psi.PsiNamedEObject
import org.eclipse.xtext.psi.PsiNamedEObjectStub
import org.eclipse.xtext.psi.impl.PsiNamedEObjectStubImpl

class PsiJvmNamedEObjectStubImpl extends PsiNamedEObjectStubImpl<PsiJvmNamedEObject> implements PsiJvmNamedEObjectStub {
	
	@Accessors
	List<PsiJvmDeclaredTypeDTO> classes
	
	new(StubElement parent, StringRef name, QualifiedName qualifiedName, EClass type, IStubElementType<? extends PsiNamedEObjectStub, ? extends PsiNamedEObject> elementType) {
		super(parent, name, qualifiedName, type, elementType)
	}
	
}