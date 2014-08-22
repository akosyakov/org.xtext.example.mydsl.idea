package org.eclipse.xtext.idea.types;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.impl.java.stubs.index.JavaShortClassNameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.xtext.common.types.JvmVoid;
import org.eclipse.xtext.common.types.TypesFactory;
import org.eclipse.xtext.common.types.access.IJvmTypeProvider;
import org.eclipse.xtext.common.types.xtext.AbstractTypeScope;
import org.eclipse.xtext.idea.types.access.StubJvmTypeProvider;
import org.eclipse.xtext.idea.types.access.StubURIHelper;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.EObjectDescription;
import org.eclipse.xtext.resource.IEObjectDescription;

@SuppressWarnings("all")
public class StubBasedTypeScope extends AbstractTypeScope {
  protected StubBasedTypeScope(final StubJvmTypeProvider typeProvider, final IQualifiedNameConverter qualifiedNameConverter, final Predicate<IEObjectDescription> filter) {
    super(typeProvider, qualifiedNameConverter, filter);
  }
  
  protected Iterable<IEObjectDescription> internalGetAllElements() {
    ArrayList<IEObjectDescription> _xblockexpression = null;
    {
      final ArrayList<IEObjectDescription> allScopedElements = Lists.<IEObjectDescription>newArrayListWithExpectedSize(25000);
      IJvmTypeProvider _typeProvider = this.getTypeProvider();
      final Project project = ((StubJvmTypeProvider) _typeProvider).getProject();
      final GlobalSearchScope scope = GlobalSearchScope.allScope(project);
      final JavaShortClassNameIndex index = JavaShortClassNameIndex.getInstance();
      Collection<String> _allKeys = index.getAllKeys(project);
      for (final String fullClassName : _allKeys) {
        Collection<PsiClass> _get = index.get(fullClassName, project, scope);
        for (final PsiClass psiClass : _get) {
          {
            String _qualifiedName = psiClass.getQualifiedName();
            final InternalEObject proxy = this.createProxy(_qualifiedName);
            IQualifiedNameConverter _qualifiedNameConverter = this.getQualifiedNameConverter();
            String _qualifiedName_1 = psiClass.getQualifiedName();
            final QualifiedName qualifiedName = _qualifiedNameConverter.toQualifiedName(_qualifiedName_1);
            IEObjectDescription _create = EObjectDescription.create(qualifiedName, proxy);
            allScopedElements.add(_create);
          }
        }
      }
      _xblockexpression = allScopedElements;
    }
    return _xblockexpression;
  }
  
  protected InternalEObject createProxy(final String fullClassName) {
    InternalEObject _xblockexpression = null;
    {
      IJvmTypeProvider _typeProvider = this.getTypeProvider();
      StubURIHelper _uriHelper = ((StubJvmTypeProvider) _typeProvider).getUriHelper();
      final URI uri = _uriHelper.getFullURI(fullClassName);
      JvmVoid _createJvmVoid = TypesFactory.eINSTANCE.createJvmVoid();
      final InternalEObject proxy = ((InternalEObject) _createJvmVoid);
      proxy.eSetProxyURI(uri);
      _xblockexpression = proxy;
    }
    return _xblockexpression;
  }
}
