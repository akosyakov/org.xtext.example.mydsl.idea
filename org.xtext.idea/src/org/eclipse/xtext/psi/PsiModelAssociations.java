package org.eclipse.xtext.psi;

import java.util.Iterator;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.idea.resource.impl.StubEObjectDescription;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;

public class PsiModelAssociations implements IPsiModelAssociations {
	
	public static class PsiAdapter extends AdapterImpl {
		
		private final PsiElement psiElement;

		private final CompositeElement composite;

		public PsiAdapter(PsiElement psiElement) {
			this.composite = null;
			this.psiElement = psiElement;
		}

		public PsiAdapter(CompositeElement composite) {
			this.psiElement = null;
			this.composite = composite;
		}
		
		public CompositeElement getComposite() {
			return composite;
		}
		
		public PsiElement getPsi() {
			if (composite != null) {
				return composite.getPsi();
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

}
