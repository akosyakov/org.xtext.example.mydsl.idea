package org.eclipse.xtext.idea.types.psi;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFinder;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeFullClassNameIndex;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class JvmTypesElementFinder extends PsiElementFinder {
  @Inject
  private JvmDeclaredTypeFullClassNameIndex jvmDeclaredTypeFullClassNameIndex;
  
  private final Project project;
  
  public JvmTypesElementFinder(final IXtextLanguage language, final Project project) {
    language.injectMembers(this);
    this.project = project;
  }
  
  public PsiClass findClass(final String qualifiedName, final GlobalSearchScope scope) {
    PsiClass[] _findClasses = this.findClasses(qualifiedName, scope);
    return IterableExtensions.<PsiClass>head(((Iterable<PsiClass>)Conversions.doWrapArray(_findClasses)));
  }
  
  public PsiClass[] findClasses(final String qualifiedName, final GlobalSearchScope scope) {
    ArrayList<PsiJvmDeclaredType> _xblockexpression = null;
    {
      final ArrayList<PsiJvmDeclaredType> result = CollectionLiterals.<PsiJvmDeclaredType>newArrayList();
      Collection<PsiJvmNamedEObject> _get = this.jvmDeclaredTypeFullClassNameIndex.get(qualifiedName, this.project, scope);
      for (final PsiJvmNamedEObject namedEObject : _get) {
        List<PsiJvmDeclaredType> _findClasses = namedEObject.findClasses(qualifiedName);
        Iterables.<PsiJvmDeclaredType>addAll(result, _findClasses);
      }
      _xblockexpression = result;
    }
    return ((PsiClass[])Conversions.unwrapArray(_xblockexpression, PsiClass.class));
  }
}
