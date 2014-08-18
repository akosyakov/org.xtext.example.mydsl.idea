package org.eclipse.xtext.idea;

import com.google.inject.Binder;
import com.google.inject.Singleton;
import com.google.inject.binder.AnnotatedBindingBuilder;
import org.eclipse.xtext.idea.resource.impl.StubBasedResourceDescriptions;
import org.eclipse.xtext.idea.resource.impl.StubContainerManager;
import org.eclipse.xtext.psi.stubs.PsiNamedEObjectIndex;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.service.AbstractGenericModule;

@SuppressWarnings("all")
public class DefaultIdeaModule extends AbstractGenericModule {
  @Singleton
  public Class<? extends PsiNamedEObjectIndex> bindPsiNamedEObjectIndex() {
    return PsiNamedEObjectIndex.class;
  }
  
  public void configureIResourceDescriptions(final Binder binder) {
    AnnotatedBindingBuilder<IResourceDescriptions> _bind = binder.<IResourceDescriptions>bind(IResourceDescriptions.class);
    _bind.to(StubBasedResourceDescriptions.class);
  }
  
  public Class<? extends IContainer.Manager> bindIContainer$Manager() {
    return StubContainerManager.class;
  }
}
