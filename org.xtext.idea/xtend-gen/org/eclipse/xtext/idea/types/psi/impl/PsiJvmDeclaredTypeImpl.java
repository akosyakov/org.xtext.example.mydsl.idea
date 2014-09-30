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
import com.intellij.psi.impl.light.AbstractLightClass;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtend.lib.annotations.AccessorType;
import org.eclipse.xtend.lib.annotations.Accessors;
import org.eclipse.xtext.common.types.JvmDeclaredType;
import org.eclipse.xtext.idea.lang.IXtextLanguage;
import org.eclipse.xtext.idea.types.psi.PsiJvmDeclaredType;
import org.eclipse.xtext.service.OperationCanceledError;
import org.eclipse.xtext.xbase.compiler.GeneratorConfig;
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Exceptions;
import org.eclipse.xtext.xbase.lib.Extension;
import org.eclipse.xtext.xbase.lib.IterableExtensions;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;
import org.eclipse.xtext.xbase.lib.Pure;

@SuppressWarnings("all")
public class PsiJvmDeclaredTypeImpl extends AbstractLightClass implements PsiJvmDeclaredType {
  @Inject
  @Extension
  private JvmModelGenerator _jvmModelGenerator;
  
  private PsiClass delegate;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final EClass type;
  
  @Accessors(AccessorType.PUBLIC_GETTER)
  private final String qualifiedName;
  
  private final PsiElement psiElement;
  
  private final JvmDeclaredType declaredType;
  
  public PsiJvmDeclaredTypeImpl(final JvmDeclaredType declaredType, final PsiElement psiElement) {
    super(psiElement.getManager(), psiElement.getLanguage());
    this.type = this.type;
    this.declaredType = declaredType;
    String _qualifiedName = declaredType.getQualifiedName();
    this.qualifiedName = _qualifiedName;
    this.psiElement = psiElement;
    final Language language = this.getLanguage();
    if ((language instanceof IXtextLanguage)) {
      ((IXtextLanguage)language).injectMembers(this);
    }
  }
  
  public PsiElement copy() {
    return new PsiJvmDeclaredTypeImpl(this.declaredType, this.psiElement);
  }
  
  public PsiClass getDelegate() {
    PsiClass _xblockexpression = null;
    {
      boolean _equals = Objects.equal(this.delegate, null);
      if (_equals) {
        CharSequence _xtrycatchfinallyexpression = null;
        try {
          GeneratorConfig _generatorConfig = new GeneratorConfig();
          final Procedure1<GeneratorConfig> _function = new Procedure1<GeneratorConfig>() {
            public void apply(final GeneratorConfig it) {
              it.setGenerateExpressions(false);
            }
          };
          GeneratorConfig _doubleArrow = ObjectExtensions.<GeneratorConfig>operator_doubleArrow(_generatorConfig, _function);
          _xtrycatchfinallyexpression = this._jvmModelGenerator.generateType(this.declaredType, _doubleArrow);
        } catch (final Throwable _t) {
          if (_t instanceof OperationCanceledError) {
            final OperationCanceledError cancel = (OperationCanceledError)_t;
            throw cancel.getWrapped();
          } else {
            throw Exceptions.sneakyThrow(_t);
          }
        }
        final CharSequence text = _xtrycatchfinallyexpression;
        Project _project = this.getProject();
        PsiFileFactory _instance = PsiFileFactory.getInstance(_project);
        PsiFile _createFileFromText = _instance.createFileFromText("_Dummy_.java", JavaFileType.INSTANCE, text);
        final PsiFile psiFile = _createFileFromText;
        boolean _matched = false;
        if (!_matched) {
          if (psiFile instanceof PsiJavaFile) {
            _matched=true;
            PsiClass[] _classes = ((PsiJavaFile)psiFile).getClasses();
            PsiClass _head = IterableExtensions.<PsiClass>head(((Iterable<PsiClass>)Conversions.doWrapArray(_classes)));
            this.delegate = _head;
          }
        }
      }
      _xblockexpression = this.delegate;
    }
    return _xblockexpression;
  }
  
  public PsiElement getNavigationElement() {
    return this.psiElement;
  }
  
  public boolean isValid() {
    return this.psiElement.isValid();
  }
  
  public boolean isEquivalentTo(final PsiElement another) {
    boolean _xblockexpression = false;
    {
      if ((another instanceof PsiJvmDeclaredType)) {
        return this.isEquivalent(this, ((PsiJvmDeclaredType)another));
      }
      if ((another instanceof PsiClass)) {
        String _qualifiedName = ((PsiClass)another).getQualifiedName();
        return Objects.equal(_qualifiedName, this.qualifiedName);
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
