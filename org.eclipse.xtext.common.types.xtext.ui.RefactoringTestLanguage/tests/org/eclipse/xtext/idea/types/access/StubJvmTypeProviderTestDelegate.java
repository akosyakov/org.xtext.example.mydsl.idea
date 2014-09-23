package org.eclipse.xtext.idea.types.access;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.common.types.access.IJvmTypeProvider;
import org.eclipse.xtext.common.types.access.impl.AbstractTypeProviderTest;
import org.eclipse.xtext.common.types.access.impl.IndexedJvmTypeAccess;
import org.eclipse.xtext.common.types.xtext.ui.idea.lang.RefactoringTestLanguageLanguage;
import org.eclipse.xtext.psi.IPsiModelAssociator;

import com.google.inject.Inject;
import com.intellij.openapi.project.Project;

public class StubJvmTypeProviderTestDelegate extends AbstractTypeProviderTest {

	@Inject
	protected ResourceSet resourceSet;

	@Inject
	protected IndexedJvmTypeAccess indexedJvmTypeAccess;
	
	@Inject
	protected IPsiModelAssociator psiModelAssociator;

	private IJvmTypeProvider typeProvider;

	public void setUp(Project project) throws Exception {
		super.setUp();
		RefactoringTestLanguageLanguage.INSTANCE.injectMembers(this);
		typeProvider = new StubJvmTypeProvider(project, resourceSet, indexedJvmTypeAccess, psiModelAssociator);
	}

	public void tearDown() {
		typeProvider = null;
	}

	@Override
	protected String getCollectionParamName() {
		return "collection";
	}

	@Override
	protected IJvmTypeProvider getTypeProvider() {
		return typeProvider;
	}

}