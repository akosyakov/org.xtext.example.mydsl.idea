package org.xtext.example.domainmodel.idea;

import org.eclipse.xtext.util.Modules2;
import org.xtext.example.domainmodel.DomainmodelStandaloneSetupGenerated;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class DomainmodelStandaloneSetupIdea extends DomainmodelStandaloneSetupGenerated {

    @Override
    public Injector createInjector() {
        Module runtimeModule = new org.xtext.example.domainmodel.DomainmodelRuntimeModule();
        Module ideaModule = new org.xtext.example.domainmodel.idea.DomainmodelIdeaModule();
        Module mergedModule = Modules2.mixin(runtimeModule, ideaModule);
        return Guice.createInjector(mergedModule);
    }

}
