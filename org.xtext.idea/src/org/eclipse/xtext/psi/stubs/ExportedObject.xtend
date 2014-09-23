package org.eclipse.xtext.psi.stubs

import org.eclipse.emf.ecore.EClass
import org.eclipse.xtend.lib.annotations.Accessors
import org.eclipse.xtend.lib.annotations.FinalFieldsConstructor

@FinalFieldsConstructor
class ExportedObject {
	@Accessors val String name
	@Accessors val String qualifiedName
	@Accessors val EClass type 
}
