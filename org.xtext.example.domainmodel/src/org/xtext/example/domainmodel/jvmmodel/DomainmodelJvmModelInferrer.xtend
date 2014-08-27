package org.xtext.example.domainmodel.jvmmodel

import com.google.inject.Inject
import org.eclipse.xtext.naming.IQualifiedNameProvider
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder
import org.xtext.example.domainmodel.domainmodel.Entity

class DomainmodelJvmModelInferrer extends AbstractModelInferrer {

    @Inject 
    extension JvmTypesBuilder
    
    @Inject
    IQualifiedNameProvider qualifiedNameProvider

   	def dispatch void infer(Entity element, IJvmDeclaredTypeAcceptor acceptor, boolean isPreIndexingPhase) {
   		acceptor.accept(element.toClass(qualifiedNameProvider.getFullyQualifiedName(element)))
   	}

}

