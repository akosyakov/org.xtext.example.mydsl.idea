package org.eclipse.xtext.idea.types.psi.impl;

import com.google.common.base.Objects;
import com.google.inject.Inject;
import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.lang.Language;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.light.AbstractLightClass;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.common.types.descriptions.IStubGenerator;
import org.eclipse.xtext.generator.InMemoryFileSystemAccess;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.idea.types.psi.impl.JvmDeclaredTypeResourceDescription;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.psi.PsiNamedEObject;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class PsiJvmDeclaredTypeImpl extends AbstractLightClass implements PsiJvmDeclaredType {
  @Inject
  private IStubGenerator stubGenerator;
  
  @Inject
  private IQualifiedNameConverter qualifiedNameConverter;
  
  private PsiClass delegate;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final EClass type;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final String qualifiedName;
  
  private final PsiNamedEObject psiNamedEObject;
  
  protected PsiJvmDeclaredTypeImpl(final JvmDeclaredType declaredType, final PsiNamedEObject psiNamedEObject, final PsiManager manager, final Language language) {
    this(declaredType.getQualifiedName(), declaredType.eClass(), psiNamedEObject, manager, language);
  }
  
  public PsiJvmDeclaredTypeImpl(final String qualifiedName, final EClass type, final PsiNamedEObject psiNamedEObject, final PsiManager manager, final Language language) {
    super(manager, language);
    if ((language instanceof IXtextLanguage)) {
      ((IXtextLanguage)language).injectMembers(this);
    }
    this.type = type;
    this.qualifiedName = qualifiedName;
    this.psiNamedEObject = psiNamedEObject;
  }
  
  public PsiElement copy() {
    PsiManager _manager = this.getManager();
    Language _language = this.getLanguage();
    return new PsiJvmDeclaredTypeImpl(this.qualifiedName, this.type, this.psiNamedEObject, _manager, _language);
  }
  
  public PsiClass getDelegate() {
    PsiClass _xblockexpression = null;
    {
      boolean _equals = Objects.equal(this.delegate, null);
      if (_equals) {
        final InMemoryFileSystemAccess fsa = new InMemoryFileSystemAccess();
        QualifiedName _qualifiedName = this.qualifiedNameConverter.toQualifiedName(this.qualifiedName);
        JvmDeclaredTypeResourceDescription _jvmDeclaredTypeResourceDescription = new JvmDeclaredTypeResourceDescription(_qualifiedName, this.type);
        this.stubGenerator.doGenerateStubs(fsa, _jvmDeclaredTypeResourceDescription);
        Map<String, CharSequence> _textFiles = fsa.getTextFiles();
        Set<Map.Entry<String, CharSequence>> _entrySet = _textFiles.entrySet();
        Map.Entry<String, CharSequence> _head = IterableExtensions.<Map.Entry<String, CharSequence>>head(_entrySet);
        final CharSequence text = _head.getValue();
        Project _project = this.getProject();
        PsiFileFactory _instance = PsiFileFactory.getInstance(_project);
        PsiFile _createFileFromText = _instance.createFileFromText("_Dummy_.java", JavaFileType.INSTANCE, text);
        final PsiFile psiFile = _createFileFromText;
        boolean _matched = false;
        if (!_matched) {
          if (psiFile instanceof PsiJavaFile) {
            _matched=true;
            PsiClass[] _classes = ((PsiJavaFile)psiFile).getClasses();
            PsiClass _head_1 = IterableExtensions.<PsiClass>head(((Iterable<PsiClass>)Conversions.doWrapArray(_classes)));
            this.delegate = _head_1;
          }
        }
      }
      _xblockexpression = this.delegate;
    }
    return _xblockexpression;
  }
  
  public PsiElement getNavigationElement() {
    return this.psiNamedEObject;
  }
  
  public boolean isValid() {
    return this.psiNamedEObject.isValid();
  }
  
  public boolean isEquivalentTo(final PsiElement another) {
    boolean _xblockexpression = false;
    {
      if ((another instanceof PsiJvmDeclaredType)) {
        return this.isEquivalent(this, ((PsiJvmDeclaredType)another));
      }
      _xblockexpression = false;
    }
    return _xblockexpression;
  }
  
  protected boolean isEquivalent(final PsiJvmDeclaredType one, final PsiJvmDeclaredType another) {
    String _qualifiedName = one.getQualifiedName();
    String _qualifiedName_1 = another.getQualifiedName();
    return Objects.equal(_qualifiedName, _qualifiedName_1);
  }
  
  @Pure
  public EClass getType() {
    return this.type;
  }
  
  @Pure
  public String getQualifiedName() {
    return this.qualifiedName;
  }
}
