package org.xtext.example.mydsl.idea.lang;

import org.eclipse.xtext.util.Modules2;
import org.xtext.example.mydsl.MyDslStandaloneSetupGenerated;
import org.xtext.example.mydsl.idea.MyDslIdeaModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

public class MyDslIdeaSetup extends MyDslStandaloneSetupGenerated {

    @Override
    public Injector createInjector() {
        Module runtimeModule = new org.xtext.example.mydsl.MyDslRuntimeModule();
        Module ideaModule = new MyDslIdeaModule();
        Module mergedModule = Modules2.mixin(runtimeModule, ideaModule);
        return Guice.createInjector(mergedModule);
    }

}
