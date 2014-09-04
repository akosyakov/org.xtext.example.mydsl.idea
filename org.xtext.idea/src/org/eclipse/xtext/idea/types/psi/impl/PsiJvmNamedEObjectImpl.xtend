package org.eclipse.xtext.idea.types.psi.impl

import com.google.inject.Inject
import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.tree.IElementType
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub
import org.eclipse.xtext.psi.IPsiModelAssociations
import org.eclipse.xtext.psi.PsiNamedEObject
import org.eclipse.xtext.psi.PsiNamedEObjectStub
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations

class PsiJvmNamedEObjectImpl extends PsiNamedEObjectImpl<PsiJvmNamedEObjectStub> implements PsiJvmNamedEObject {
	
	@Inject
	extension IPsiModelAssociations
	
	@Inject
	extension IJvmModelAssociations	

	new(PsiJvmNamedEObjectStub stub, IStubElementType<? extends PsiNamedEObjectStub, ? extends PsiNamedEObject> nodeType,
		IElementType nameType) {
		super(stub, nodeType, nameType);
	}

	new(ASTNode node, IElementType nameType) {
		super(node, nameType);
	}

	override findClasses(String qualifiedName) {
		classes.filter[class|class.qualifiedName == qualifiedName].toList
	}

	override findClassesByName(String shortName) {
		classes.filter[class|class.name == shortName].toList
	}

	override getClasses() {
		val stub = stub
		if (stub != null) {
			stub.classes.map [
				new PsiJvmDeclaredTypeImpl(qualifiedName, type, this)
			]
		} else {
			val result = newArrayList
			for (jvmElement : EObject.jvmElements.filter(JvmDeclaredType)) {
				val psiJvmDeclaredType = jvmElement.psiElement 
				if (psiJvmDeclaredType instanceof PsiJvmDeclaredType) {
					result += psiJvmDeclaredType
				}	
			}
			result
		}
	}

}
