package org.eclipse.xtext.psi;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.idea.lang.BaseXtextPsiParser.PsiAdapter;
import org.eclipse.xtext.linking.lazy.CrossReferenceDescription.CrossReferenceDescriptionProvider;
import org.eclipse.xtext.linking.lazy.ICrossReferenceDescription;

import com.google.inject.Inject;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.CompositeElement;

public class PsiModelAssociations implements IPsiModelAssociations {

//    @Inject
//    @Named(Constants.LANGUAGE_NAME)
//    private String languageName;
    
    @Inject
    private CrossReferenceDescriptionProvider crossReferenceDescriptionProvider;

    public EObject getEObject(PsiEObject element) {
    	if (element == null) {
            return null;
        }
    	return element.getINode().getSemanticElement();
    }

    public PsiElement getPsiElement(EObject object) {
        CompositeElement composite = PsiAdapter.getComposite(object);
        if (composite == null) {
        	return null;
        }
        return composite.getPsi();
    }

    public ICrossReferenceDescription getCrossReferenceDescription(PsiReferenceEObject element) {
    	if (element == null) {
    		return null;
    	}
		PsiReferenceEObject psiReferenceEObject = (PsiReferenceEObject) element;
		EObject proxy = psiReferenceEObject.getEProxy();
		EObject context = psiReferenceEObject.getEContext();
		EReference reference = psiReferenceEObject.getEReference();
        return crossReferenceDescriptionProvider.get(proxy, context, reference);
    }
        

//    protected static class Adapter extends AdapterImpl {
//
//        public Map<EObject, PsiElement> eObjectToPsiElementMap = Maps.newHashMapWithExpectedSize(40);
//        public Map<PsiElement, EObject> psiElementToEObjectMap = Maps.newHashMapWithExpectedSize(40);
//        public Map<PsiElement, ICrossReferenceDescription> crossReferenceDescriptions = Maps.newHashMapWithExpectedSize(40);
//
//        @Override
//        public boolean isAdapterForType(Object type) {
//            return Adapter.class == type;
//        }
//    }
//
//    protected Adapter getOrInstall(PsiFile psiFile) {
//        if (!(psiFile instanceof BaseXtextFile)) {
//            return new Adapter();
//        }
//        return getOrInstall(((BaseXtextFile) psiFile).getResource(), psiFile);
//    }
//
//    protected Adapter getOrInstall(Resource resource, PsiFile psiFile) {
//        if (!(resource instanceof XtextResource))
//            return new Adapter();
//        if (!languageName.equals(((XtextResource) resource).getLanguageName()))
//            return new Adapter();
//        Adapter adapter = (Adapter) EcoreUtil.getAdapter(resource.eAdapters(), Adapter.class);
//        if (adapter == null) {
//            adapter = createAssociations(resource, psiFile, new Adapter());
//            resource.eAdapters().add(adapter);
//        }
//        return adapter;
//    }
//
//    protected Adapter createAssociations(Resource resource, PsiFile psiFile, Adapter adapter) {
//        EObject root = resource.getContents().get(0);
//        PsiElement element = psiFile.getFirstChild();
//        createAssociations(root, element, adapter);
//        return adapter;
//    }
//
//    @SuppressWarnings("unchecked")
//	protected void createAssociations(EObject object, PsiElement element, Adapter adapter) {
//        Map<EReference, Integer> referenceToIndex = Maps.newHashMap();
//        for (PsiElement nextElement : element.getChildren()) {
//        	if (nextElement instanceof PsiEObject) {
//                PsiEObject psiEObject = (PsiEObject) nextElement;
//                EReference reference = psiEObject.getEReference();
//                if (reference == null) {
//                    continue;
//                }
//                Object value = object.eGet(reference, false);
//                if (reference.isMany()) {
//                    Integer index = referenceToIndex.get(reference);
//                    if (index == null) {
//                        index = 0;
//                    }
//                    referenceToIndex.put(reference, index + 1);
//                    value = ((InternalEList<EObject>) value).get(index);
//                }
//                EObject nextObject = (EObject) value;
//                if (reference.isContainment()) {
//                    adapter.psiElementToEObjectMap.put(psiEObject, nextObject);
//                    adapter.eObjectToPsiElementMap.put(nextObject, psiEObject);
//                    createAssociations(nextObject, psiEObject, adapter);
//                } else {
//                	ICrossReferenceDescription crossReferenceDescription = crossReferenceDescriptionProvider.get(nextObject, object, reference);
//                    adapter.crossReferenceDescriptions.put(psiEObject, crossReferenceDescription);
//                }
//            }
//        }
//    }

}
