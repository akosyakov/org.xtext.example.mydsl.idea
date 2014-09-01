package org.eclipse.xtext.idea.types

import com.intellij.lang.ASTNode
import org.eclipse.xtext.idea.lang.parser.AbstractXtextParserDefinition
import org.eclipse.xtext.idea.types.psi.impl.PsiJvmNamedEObjectImpl

abstract class AbstractJvmTypesParserDefinition extends AbstractXtextParserDefinition {

	override createElement(ASTNode node) {
		if (elementTypeProvider.namedObjectType == node.elementType) {
			return new PsiJvmNamedEObjectImpl(node, elementTypeProvider.nameType)
		}
		return super.createElement(node)
	}

}
