package org.eclipse.xtext.linking.lazy;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.InternalEObject;
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
        URI proxyURI = ((InternalEObject) proxy).eProxyURI();
        return context.eResource().getResourceSet().getEObject(proxyURI, true);
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