package org.xtext.example.domainmodel.jvmmodel;

import com.google.inject.Inject;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.common.types.JvmGenericType;
import org.eclipse.xtext.common.types.JvmTypeReference;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.xbase.jvmmodel.AbstractModelInferrer;
import org.eclipse.xtext.xbase.jvmmodel.IJvmDeclaredTypeAcceptor;
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder;
import org.eclipse.xtext.xbase.lib.Extension;
import org.xtext.example.domainmodel.domainmodel.Entity;

@SuppressWarnings("all")
public class DomainmodelJvmModelInferrer extends AbstractModelInferrer {
  @Inject
  @Extension
  private JvmTypesBuilder _jvmTypesBuilder;
  
  @Inject
  private IQualifiedNameProvider qualifiedNameProvider;
  
  protected void _infer(final Entity element, final IJvmDeclaredTypeAcceptor acceptor, final boolean isPreIndexingPhase) {
    final JvmTypeReference listType = this._typeReferenceBuilder.typeRef(List.class);
    final JvmTypeReference mapType = this._typeReferenceBuilder.typeRef(Map.class);
    final JvmTypeReference stackType = this._typeReferenceBuilder.typeRef(Stack.class);
    final JvmTypeReference collectionsType = this._typeReferenceBuilder.typeRef(Collections.class);
    final JvmTypeReference arraysType = this._typeReferenceBuilder.typeRef(Arrays.class);
    QualifiedName _fullyQualifiedName = this.qualifiedNameProvider.getFullyQualifiedName(element);
    JvmGenericType _class = this._jvmTypesBuilder.toClass(element, _fullyQualifiedName);
    acceptor.<JvmGenericType>accept(_class);
  }
  
  public void infer(final EObject element, final IJvmDeclaredTypeAcceptor acceptor, final boolean isPreIndexingPhase) {
    if (element instanceof Entity) {
      _infer((Entity)element, acceptor, isPreIndexingPhase);
      return;
    } else if (element != null) {
      _infer(element, acceptor, isPreIndexingPhase);
      return;
    } else {
      throw new IllegalArgumentException("Unhandled parameter types: " +
        Arrays.<Object>asList(element, acceptor, isPreIndexingPhase).toString());
    }
  }
}
