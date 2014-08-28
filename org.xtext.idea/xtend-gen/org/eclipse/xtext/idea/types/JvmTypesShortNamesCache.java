package org.eclipse.xtext.idea.types;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiShortNamesCache;
import com.intellij.util.ArrayUtil;
import com.intellij.util.Processor;
import com.intellij.util.containers.ContainerUtil;
import com.intellij.util.containers.HashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.PsiJvmNamedEObject;
import org.eclipse.xtext.idea.types.stubindex.JvmDeclaredTypeShortNameIndex;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;

@SuppressWarnings("all")
public class JvmTypesShortNamesCache extends PsiShortNamesCache {
  private final static PsiMethod[] NO_METHODS = {};
  
  private final static PsiField[] NO_FIELDS = {};
  
  @Inject
  private JvmDeclaredTypeShortNameIndex jvmDeclaredTypeShortNameIndex;
  
  private final Project project;
  
  public JvmTypesShortNamesCache(final IXtextLanguage language, final Project project) {
    language.injectMembers(this);
    this.project = project;
  }
  
  public String[] getAllClassNames() {
    return ((String[])Conversions.unwrapArray(this.jvmDeclaredTypeShortNameIndex.getAllKeys(this.project), String.class));
  }
  
  public void getAllClassNames(final HashSet<String> dest) {
    String[] _allClassNames = this.getAllClassNames();
    Iterables.<String>addAll(dest, ((Iterable<? extends String>)Conversions.doWrapArray(_allClassNames)));
  }
  
  public String[] getAllFieldNames() {
    return ArrayUtil.EMPTY_STRING_ARRAY;
  }
  
  public void getAllFieldNames(final HashSet<String> set) {
  }
  
  public String[] getAllMethodNames() {
    return ArrayUtil.EMPTY_STRING_ARRAY;
  }
  
  public void getAllMethodNames(final HashSet<String> set) {
  }
  
  public PsiClass[] getClassesByName(final String name, final GlobalSearchScope scope) {
    ArrayList<PsiJvmDeclaredType> _xblockexpression = null;
    {
      final ArrayList<PsiJvmDeclaredType> result = CollectionLiterals.<PsiJvmDeclaredType>newArrayList();
      final Collection<PsiJvmNamedEObject> namedEObjects = this.jvmDeclaredTypeShortNameIndex.get(name, this.project, scope);
      for (final PsiJvmNamedEObject namedEObject : namedEObjects) {
        List<PsiJvmDeclaredType> _findClassesByName = namedEObject.findClassesByName(name);
        Iterables.<PsiJvmDeclaredType>addAll(result, _findClassesByName);
      }
      _xblockexpression = result;
    }
    return ((PsiClass[])Conversions.unwrapArray(_xblockexpression, PsiClass.class));
  }
  
  public PsiField[] getFieldsByName(final String name, final GlobalSearchScope scope) {
    return JvmTypesShortNamesCache.NO_FIELDS;
  }
  
  public PsiField[] getFieldsByNameIfNotMoreThan(final String name, final GlobalSearchScope scope, final int maxCount) {
    return JvmTypesShortNamesCache.NO_FIELDS;
  }
  
  public PsiMethod[] getMethodsByName(final String name, final GlobalSearchScope scope) {
    return JvmTypesShortNamesCache.NO_METHODS;
  }
  
  public PsiMethod[] getMethodsByNameIfNotMoreThan(final String name, final GlobalSearchScope scope, final int maxCount) {
    return JvmTypesShortNamesCache.NO_METHODS;
  }
  
  public boolean processMethodsWithName(final String name, final GlobalSearchScope scope, final Processor<PsiMethod> processor) {
    PsiMethod[] _methodsByName = this.getMethodsByName(name, scope);
    return ContainerUtil.<PsiMethod>process(_methodsByName, processor);
  }
}