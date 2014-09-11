package org.eclipse.xtext.common.types.xtext.ui.idea;

import org.eclipse.xtext.util.Modules2;
import org.eclipse.xtext.common.types.xtext.ui.RefactoringTestLanguageStandaloneSetupGenerated;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class RefactoringTestLanguageStandaloneSetupIdea extends RefactoringTestLanguageStandaloneSetupGenerated {

    @Override
    public Injector createInjector() {
        Module runtimeModule = new org.eclipse.xtext.common.types.xtext.ui.RefactoringTestLanguageRuntimeModule();
        Module ideaModule = new org.eclipse.xtext.common.types.xtext.ui.idea.RefactoringTestLanguageIdeaModule();
        Module mergedModule = Modules2.mixin(runtimeModule, ideaModule);
        return Guice.createInjector(mergedModule);
    }

}
