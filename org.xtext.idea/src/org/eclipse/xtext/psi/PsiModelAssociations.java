package org.eclipse.xtext.psi;

import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.idea.resource.impl.StubEObjectDescription;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Singleton;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;

@Singleton
public class PsiModelAssociations implements IPsiModelAssociations, IPsiModelAssociator {
	
	public static class PsiAdapter extends AdapterImpl {
		
		private PsiElement psiElement;
		
		private final CompositeElement composite;

		private final PsiElementProvider psiElementProvider;

		public PsiAdapter(final PsiElement psiElement) {
			this(new PsiElementProvider() {
				
				@Override
				public PsiElement get() {
					return psiElement;
				}
				
			});
		}

		public PsiAdapter(PsiElementProvider psiElementProvider) {
			this.composite = null;
			this.psiElementProvider = psiElementProvider;
		}

		public PsiAdapter(CompositeElement composite) {
			this.composite = composite;
			this.psiElementProvider = null;
		}
		
		public CompositeElement getComposite() {
			return composite;
		}
		
		public PsiElement getPsi() {
			if (composite != null) {
				return composite.getPsi();
			}
			if (psiElement == null && psiElementProvider != null) {
				psiElement = psiElementProvider.get();
			}
			return psiElement;
		}
		
		public static CompositeElement getComposite(EObject object) {
			PsiAdapter psiAdapter = get(object);
			if (psiAdapter != null) {
				return psiAdapter.getComposite();
			}
			return null;
		}
		
		public static PsiElement getPsi(EObject object) {
			PsiAdapter psiAdapter = get(object);
			if (psiAdapter != null) {
				return psiAdapter.getPsi();
			}
			return null;
		}
		
		public static PsiAdapter get(EObject object) {
			if (object == null) {
				return null;
			}
			Iterator<Adapter> adapters = object.eAdapters().iterator();
			while (adapters.hasNext()) {
				Adapter adapter = adapters.next();
				if (adapter instanceof PsiAdapter) {
					return ((PsiAdapter) adapter);
				}
			}
			return null;
		}
		
	}
	
    public EObject getEObject(PsiEObject element) {
    	if (element == null) {
            return null;
        }
    	return element.getEObject();
    }

    public PsiElement getPsiElement(EObject object) {
        return PsiAdapter.getPsi(object);
    }
    
	public PsiElement getPsiElement(IEObjectDescription objectDescription, EObject context) {
		if (objectDescription == null) {
			return null;
		}
		if (objectDescription instanceof StubEObjectDescription) {
			StubEObjectDescription stubEObjectDescription = (StubEObjectDescription) objectDescription;
			return stubEObjectDescription.getPsiNamedEObject();
		}
		EObject object = objectDescription.getEObjectOrProxy();
		if (object.eIsProxy()) {
			InternalEObject proxy = (InternalEObject) object;
			object = context.eResource().getResourceSet().getEObject(proxy.eProxyURI(), true);
		}
		return getPsiElement(object);
	}

    public ICrossReferenceDescription getCrossReferenceDescription(PsiReferenceEObject element) {
    	if (element == null) {
    		return null;
    	}
    	return element.getCrossReferenceDescription();
    }

	@Override
	public boolean associate(EObject eObject, PsiElementProvider psiElementProvider) {
		if (eObject == null || psiElementProvider == null) {
			return false;
		}
		if (PsiAdapter.get(eObject) != null) {
			return false;
		}
		return eObject.eAdapters().add(new PsiAdapter(psiElementProvider));
	}

	@Override
	public boolean associatePrimary(EObject eObject, PsiElementProvider psiElementProvider) {
		if (eObject == null || psiElementProvider == null) {
			return false;
		}
		PsiAdapter psiAdapter = PsiAdapter.get(eObject);
		if (psiAdapter != null) {
			eObject.eAdapters().remove(psiAdapter);
		}
		return eObject.eAdapters().add(new PsiAdapter(psiElementProvider));
	}

}
