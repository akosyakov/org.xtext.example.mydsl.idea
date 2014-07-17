package org.xtext.example.mydsl.validation

import org.eclipse.xtext.validation.Check
import org.xtext.example.mydsl.myDsl.Entity
import org.xtext.example.mydsl.myDsl.MyDslPackage

/**
 * Custom validation rules. 
 *
 * see http://www.eclipse.org/Xtext/documentation.html#validation
 */
class MyDslValidator extends AbstractMyDslValidator {

	public static val INVALID_NAME = 'invalidName'

	@Check
	def checkGreetingStartsWithCapital(Entity entity) {
		if (!Character.isUpperCase(entity.name.charAt(0))) {
			warning('Name should start with a capital', MyDslPackage.Literals.TYPE__NAME, INVALID_NAME)
		}
	}

}
