package org.eclipse.xtext.linking.lazy;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.idea.ProcessCanceledExceptionHandling;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.scoping.IScopeProvider;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class CrossReferenceDescription implements ICrossReferenceDescription {
	
	@Inject
	private IScopeProvider scopeProvider;
	
    private EObject proxy;
    private EObject context;
	private EReference reference;

    public EObject resolve() {
    	try {
    		return (EObject) context.eGet(reference);
    	} catch (Exception e) {
    		throw ProcessCanceledExceptionHandling.unWrappedReThrow(e);
    	}
    }

    public Iterable<IEObjectDescription> getVariants() {
    	return scopeProvider.getScope(context, reference).getAllElements();
    }
    
    public static class CrossReferenceDescriptionProvider {
    	
    	@Inject
    	private Provider<CrossReferenceDescription> delegate;
    	
    	public ICrossReferenceDescription get(EObject proxy, EObject context, EReference reference) {
    		CrossReferenceDescription crossReferenceDescription = delegate.get();
    		crossReferenceDescription.proxy = proxy;
    		crossReferenceDescription.context = context;
    		crossReferenceDescription.reference = reference;
    		return crossReferenceDescription;
    	}
    	
    }

}