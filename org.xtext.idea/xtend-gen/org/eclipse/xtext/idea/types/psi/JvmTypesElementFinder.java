package org.eclipse.xtext.idea.types.psi;

import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElementFinder;
import com.intellij.psi.search.GlobalSearchScope;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredTypes;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.impl.BaseXtextFile;
import org.eclipse.xtext.psi.stubindex.ExportedObjectQualifiedNameIndex;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class JvmTypesElementFinder extends PsiElementFinder {
  @Inject
  @Extension
  private PsiJvmDeclaredTypes _psiJvmDeclaredTypes;
  
  @Inject
  private ExportedObjectQualifiedNameIndex exportedObjectQualifiedNameIndex;
  
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
      Collection<BaseXtextFile> _get = this.exportedObjectQualifiedNameIndex.get(qualifiedName, this.project, scope);
      for (final BaseXtextFile xtextFile : _get) {
        String[] _split = qualifiedName.split("\\.");
        QualifiedName _create = QualifiedName.create(_split);
        ArrayList<PsiJvmDeclaredType> _psiJvmDeclaredTypes = this._psiJvmDeclaredTypes.getPsiJvmDeclaredTypes(xtextFile, _create);
        Iterables.<PsiJvmDeclaredType>addAll(result, _psiJvmDeclaredTypes);
      }
      _xblockexpression = result;
    }
    return ((PsiClass[])Conversions.unwrapArray(_xblockexpression, PsiClass.class));
  }
}
