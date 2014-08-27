package org.eclipse.xtext.idea.types.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.tree.IElementType
import java.io.IOException
import org.eclipse.xtext.common.types.JvmDeclaredType
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject
import org.eclipse.xtext.idea.types.psi.stubs.PsiJvmNamedEObjectStub
import org.eclipse.xtext.psi.PsiNamedEObject
import org.eclipse.xtext.psi.PsiNamedEObjectStub
import org.eclipse.xtext.psi.impl.PsiNamedEObjectImpl
import org.eclipse.xtext.resource.DerivedStateAwareResource
import org.eclipse.xtext.util.RuntimeIOException
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelAssociations

class PsiJvmNamedEObjectImpl extends PsiNamedEObjectImpl<PsiJvmNamedEObjectStub> implements PsiJvmNamedEObject {

	new(PsiJvmNamedEObjectStub stub, IStubElementType<? extends PsiNamedEObjectStub, ? extends PsiNamedEObject> nodeType, IElementType nameType) {
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
		val manager = manager
		val language = language
		if (stub != null) { 
			stub.classes.map [
				new PsiJvmDeclaredTypeImpl(qualifiedName, type, this, manager, language)
			]
		} else {
			jvmElements.filter(JvmDeclaredType).toList.map [
				new PsiJvmDeclaredTypeImpl(it, this, manager, language)
			]
		}
	}
	
	protected def getJvmElements() {
		switch resource : resource {
			DerivedStateAwareResource: {
				if (!resource.loaded) {
					try {
						resource.load(resource.resourceSet.loadOptions)
					} catch (IOException e) {
						throw new RuntimeIOException(e)
					}
				}
				val isInitialized = resource.fullyInitialized || resource.isInitializing
				try {
					if (!isInitialized) {
						resource.eSetDeliver(false);
						resource.installDerivedState(true);
					}
					xtextLanguage.getInstance(IJvmModelAssociations).getJvmElements(EObject)
				} finally {
					if (!isInitialized) {
						resource.discardDerivedState();
						resource.eSetDeliver(true);
					}
				}
			}
			default: emptyList
		}
	}

}
