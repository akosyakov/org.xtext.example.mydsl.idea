package org.xtext.example.domainmodel.jvmmodel

import com.google.inject.Inject
import java.util.Arrays
import java.util.Collections
import java.util.List
import java.util.Map
import java.util.Stack
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
   		val listType = List.typeRef
   		val mapType = Map.typeRef
   		val stackType = Stack.typeRef
   		val collectionsType = Collections.typeRef
   		val arraysType = Arrays.typeRef
   		acceptor.accept(element.toClass(qualifiedNameProvider.getFullyQualifiedName(element)))
   	}

}